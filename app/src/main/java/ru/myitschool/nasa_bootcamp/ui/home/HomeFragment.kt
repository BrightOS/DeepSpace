package ru.myitschool.nasa_bootcamp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.material.composethemeadapter.MdcTheme
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.ui.home.components.NavigationCard
import ru.myitschool.nasa_bootcamp.ui.home.components.NewsCarousel
import ru.myitschool.nasa_bootcamp.utils.Resource

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels<HomeViewModelTestImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets {
                        HomeScreen(viewModel = viewModel,
                            onMenuClick = { (activity as MainActivity).openDrawer() })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, onMenuClick: () -> Unit) {
    val imageOfTheDayModel by viewModel.getImageOfTheDayModel()
        .observeAsState(Resource.loading(null))
    val articles by viewModel.getArticles().observeAsState(Resource.loading(null))
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ImageOfTheDay(
                onRetryButtonClick = { viewModel.reloadImageOfTheDay() },
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
            onItemClick = { TODO() },
            onShowMoreClick = { TODO() },
            title = stringResource(R.string.nasa_news)
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
                NavigationCard(
                    painter = painterResource(R.drawable.space_x_background3),
                    title = stringResource(R.string.spacex_card_title),
                    description = stringResource(R.string.spacex_card_description),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                NavigationCard(
                    painter = painterResource(R.drawable.space_x_background3),
                    title = stringResource(R.string.spacex_card_title),
                    description = stringResource(R.string.spacex_card_description),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }
    }
}