package com.sylas.newsapp.ui.fragments.webview

import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.sylas.newsapp.R
import com.sylas.newsapp.data.local.db.ArticleDataBase
import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.data.remote.RetrofitInstance
import com.sylas.newsapp.databinding.FragmentWebViewBinding
import com.sylas.newsapp.repository.NewsRepository
import com.sylas.newsapp.ui.fragments.base.BaseFragment

class WebViewFragment : BaseFragment<WebViewViewModel, FragmentWebViewBinding>(){

    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_article -> {
                viewModel.saveArticle(article)
                Toast.makeText(requireContext(),"Artigo salvo com sucesso", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun getViewModel(): Class<WebViewViewModel> = WebViewViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWebViewBinding = FragmentWebViewBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDataBase.invoke(requireContext()))
}