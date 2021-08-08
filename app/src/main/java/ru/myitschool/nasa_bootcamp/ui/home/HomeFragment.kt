package ru.myitschool.nasa_bootcamp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.ui.home.components.NavigationCard
import ru.myitschool.nasa_bootcamp.ui.home.components.NavigationCardData
import ru.myitschool.nasa_bootcamp.ui.home.components.NewsCarousel
import ru.myitschool.nasa_bootcamp.utils.Resource

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getViewModelScope().launch(Dispatchers.IO) {
            viewModel.loadImageOfTheDay()
            viewModel.loadArticles()
        }
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets {
                        val uriHandler = LocalUriHandler.current
                        HomeScreen(viewModel = viewModel,
                            onNavCardClick = { action -> findNavController().navigate(action) },
                            onMenuClick = { (activity as MainActivity).openDrawer() },
                            onNewsItemClick = { article ->
                                try {
                                    uriHandler.openUri(article.url)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },
                            onShowMoreNewsClick = {
                                val action =
                                    HomeFragmentDirections.actionHomeFragmentToSocialMediaFragment()
                                findNavController().navigate(action)
                            })
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMenuClick: () -> Unit,
    onNavCardClick: (action: NavDirections) -> Unit,
    onNewsItemClick: (articleModel: ArticleModel) -> Unit,
    onShowMoreNewsClick: () -> Unit
) {
    val imageOfTheDayModel by viewModel.getImageOfTheDayModel()
        .observeAsState(Resource.loading(null))
    val articles by viewModel.getArticles().observeAsState(Resource.success(listOf()))
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ImageOfTheDay(
                onRetryButtonClick = {
                    viewModel.getViewModelScope()
                        .launch(Dispatchers.IO) { viewModel.loadImageOfTheDay() }
                },
                model = imageOfTheDayModel,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(250.dp)
            )
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .statusBarsPadding()
            ) {
                Icon(Icons.Filled.Menu, "")
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        NewsCarousel(
            articlesResource = articles,
            onItemClick = onNewsItemClick,
            onShowMoreClick = onShowMoreNewsClick,
            title = stringResource(R.string.nasa_news),
            onRetryButtonClick = {
                viewModel.getViewModelScope()
                    .launch(Dispatchers.IO) { viewModel.loadArticles() }
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Card(
            shape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(40.dp),
                topEnd = CornerSize(40.dp)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
                Text(
                    text = "Explore",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                val cardModifier =
                    Modifier
                        .aspectRatio(1f)
                        .padding(6.dp)
                val navDataList = listOf(
                    NavigationCardData(
                        painter = painterResource(R.drawable.space_x_background3),
                        title = stringResource(R.string.spacex_card_title),
                        modifier = cardModifier,
                        onClick = {
                            onNavCardClick(HomeFragmentDirections.actionHomeFragmentToSpaceXFragment())
                        }
                    ),
                    NavigationCardData(
                        painter = painterResource(R.drawable.blogs),
                        title = stringResource(R.string.blogs_and_news),
                        modifier = cardModifier,
                        onClick = {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToSocialMediaFragment()
                            action.arguments.putInt("index", 2)
                            onNavCardClick(action)
                        }
                    ),
                    NavigationCardData(
                        painter = painterResource(R.drawable.mars_background),
                        title = stringResource(R.string.mars_rovers),
                        modifier = cardModifier,
                        onClick = {
                            onNavCardClick(HomeFragmentDirections.actionHomeFragmentToMarsRoversFragment())
                        }
                    ),
                    NavigationCardData(
                        painter = painterResource(R.drawable.starlink),
                        title = stringResource(R.string.upcoming_events),
                        modifier = cardModifier,
                        onClick = {
                            onNavCardClick(HomeFragmentDirections.actionHomeFragmentToUpcomingEventsFragment())
                        }
                    ),
                    NavigationCardData(
                        painter = painterResource(R.drawable.venus_background),
                        title = stringResource(R.string.space_nav),
                        modifier = cardModifier,
                        onClick = {
                            onNavCardClick(HomeFragmentDirections.actionHomeFragmentToWaitingFragment())
                        }
                    ),
                    NavigationCardData(
                        painter = painterResource(R.drawable.danger_asteroid1),
                        title = stringResource(R.string.asteroid_radar),
                        modifier = cardModifier,
                        onClick = {
                            onNavCardClick(HomeFragmentDirections.actionHomeFragmentToAsteroidRadarFragment())
                        }
                    )
                )
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        navDataList.take(3).forEach {
                            NavigationCard(data = it)
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
//                        Spacer(modifier = Modifier.height(48.dp))
                        navDataList.takeLast(3).forEach {
                            NavigationCard(data = it)
                        }
                    }
                }
            }
        }
    }
}