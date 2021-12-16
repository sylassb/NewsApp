package com.sylas.newsapp.presentation.ui.fragments.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sylas.newsapp.R
import com.sylas.newsapp.data.local.db.ArticleDataBase
import com.sylas.newsapp.data.remote.RetrofitInstance
import com.sylas.newsapp.databinding.FragmentFavoriteBinding
import com.sylas.newsapp.data.repository.NewsRepository
import com.sylas.newsapp.presentation.ui.adapter.MainAdapter
import com.sylas.newsapp.presentation.ui.fragments.base.BaseFragment
import com.sylas.newsapp.presentation.util.state.ArticleListEvent
import com.sylas.newsapp.presentation.util.state.ArticleListState

class FavoriteFragment : BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dispatch(ArticleListEvent.Fetch)
        setupRecycleView()
        observerResults()


    }

    private fun observerResults() {
        viewModel.favorite.observe(viewLifecycleOwner, { response ->
            when(response){
                is ArticleListState.Success -> {
                    binding.tvEmptyList.visibility = View.INVISIBLE
                        mainAdapter.differ.submitList(response.list)
                }
                is ArticleListState.ErrorMessage -> {
                    binding.tvEmptyList.visibility = View.INVISIBLE
                    Toast.makeText(
                    requireContext(), "Ocorreu um erro:${response.errorMessage}",
                    Toast.LENGTH_SHORT).show()

                }
                is ArticleListState.Loading -> {
                    binding.tvEmptyList.visibility = View.VISIBLE
                }

                is ArticleListState.Empty -> {
                    binding.tvEmptyList.visibility = View.VISIBLE
                    mainAdapter.differ.submitList(emptyList())
                }
            }
        })
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val mass = mainAdapter.differ.currentList[position]
            viewModel.deleteArticle(mass)
            Snackbar.make(
                viewHolder.itemView,
                getString(R.string.article_delete_successful),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(getString(R.string.undo)){
                    viewModel.saveArticle(mass)
                    mainAdapter.notifyItemInserted(position)
                }
                show()
            }
            observerResults()
        }
    }

    private fun setupRecycleView() {
        with(binding.rvFavorite) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }

        mainAdapter.setOnclickListener { article ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToWebViewFragment(article)
            findNavController().navigate(action)
        }
    }

    override fun getViewModel(): Class<FavoriteViewModel> = FavoriteViewModel::class.java


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))


}