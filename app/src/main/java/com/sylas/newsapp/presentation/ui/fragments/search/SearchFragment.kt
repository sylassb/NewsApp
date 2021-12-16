package com.sylas.newsapp.presentation.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sylas.newsapp.data.local.db.ArticleDataBase
import com.sylas.newsapp.data.remote.RetrofitInstance
import com.sylas.newsapp.databinding.FragmentSearchBinding
import com.sylas.newsapp.data.repository.NewsRepository
import com.sylas.newsapp.presentation.ui.adapter.MainAdapter
import com.sylas.newsapp.presentation.ui.fragments.base.BaseFragment
import com.sylas.newsapp.presentation.util.UtilQueryTextListener
import com.sylas.newsapp.presentation.util.state.StateResource

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        search()
        observerResults()
    }

    private fun observerResults() {
        viewModel.search.observe(viewLifecycleOwner, { response ->
            when(response){
                is StateResource.Success -> {
                    binding.rvProgressBarSearch.visibility = View.INVISIBLE
                    response.data?.let { data ->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }

                }
                is StateResource.Error ->{
                    binding.rvProgressBarSearch.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Ocorreu um erro:${response.message.toString()}", Toast.LENGTH_SHORT).show()

                }
                is StateResource.Loading -> {
                    binding.rvProgressBarSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun search(){
        binding.searchNews.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){  newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()) {
                        viewModel.fetchSearch(query)
                        binding.rvProgressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun setupRecycleView() = with(binding) {
        rvSearch.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        mainAdapter.setOnclickListener { article ->
            val action =
                SearchFragmentDirections.actionSearchFragmentToWebViewFragment(article)
            findNavController().navigate(action)
        }
    }

    override fun getViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))
}