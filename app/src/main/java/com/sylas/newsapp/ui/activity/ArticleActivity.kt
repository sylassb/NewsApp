package com.sylas.newsapp.ui.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sylas.newsapp.R
import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.databinding.ActivityArticleBinding
import com.sylas.newsapp.presenter.ViewHome
import com.sylas.newsapp.presenter.favorite.FavoritePresenter
import com.sylas.newsapp.repository.NewsDataSource


class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {url ->
                loadUrl(url)
            }
        }

        binding.fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it, R.string.article_saved_successful,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(articles: List<Article>) {}
}