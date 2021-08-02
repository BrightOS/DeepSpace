package ru.myitschool.nasa_bootcamp.ui.nasa.pages.newsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R


@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val newsViewModel: NewsViewModel by viewModels<NewsViewModelIml>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsViewModel.getViewModelScope().launch {
            newsViewModel.getNews()
        }


        newsViewModel.getArticlesList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

}