package com.sylas.newsapp.presentation.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sylas.newsapp.data.local.db.ArticleDataBase
import com.sylas.newsapp.data.remote.RetrofitInstance
import com.sylas.newsapp.databinding.FragmentHomeBinding
import com.sylas.newsapp.data.repository.NewsRepository
import com.sylas.newsapp.presentation.ui.adapter.MainAdapter
import com.sylas.newsapp.presentation.ui.fragments.base.BaseFragment
import com.sylas.newsapp.presentation.util.hide
import com.sylas.newsapp.presentation.util.show
import com.sylas.newsapp.presentation.util.state.StateResource
import com.sylas.newsapp.presentation.util.toast

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        observerResults()

    }

    private fun observerResults() {
        viewModel.getAll.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is StateResource.Success -> {
                    binding.rvProgressBar.hide()
                    response.data?.let { data ->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }

                }
                is StateResource.Error ->{
                    binding.rvProgressBar.hide()
                    toast("Ocorreu um erro:${response.message.toString()}")
                }
                is StateResource.Loading -> {
                    binding.rvProgressBar.show()
                }
            }
        })
    }

    private fun setupRecycleView() = with(binding){
        rvNews.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        mainAdapter.setOnclickListener { article ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToWebViewFragment(article)
            findNavController().navigate(action)
        }

    }


    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}