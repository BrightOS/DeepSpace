package ru.myitschool.nasa_bootcamp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.composethemeadapter.MdcTheme
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.ui.home.components.EventsCarousel
import ru.myitschool.nasa_bootcamp.ui.home.components.NewsCarousel
import ru.myitschool.nasa_bootcamp.utils.Resource

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val imageOfTheDayModel by viewModel.getImageOfTheDayModel().observeAsState()
    val articles by viewModel.getArticles().observeAsState(Resource.loading(null))
    val events by viewModel.getEvents().observeAsState(Resource.loading(null))

    Column {
        ImageOfTheDay(imageOfTheDayModel)
        NewsCarousel(
            articlesResource = articles,
            onItemClick = { TODO() },
            title = stringResource(R.string.nasa_news)
        )
        EventsCarousel(
            eventsResource = events,
            onItemClick = { TODO() },
            title = stringResource(R.string.events)
        )
    }
}