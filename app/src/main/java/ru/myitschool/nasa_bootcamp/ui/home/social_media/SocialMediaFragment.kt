package ru.myitschool.nasa_bootcamp.ui.home.social_media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.BlogsScreen
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.NewsScreen

@AndroidEntryPoint
class SocialMediaFragment : Fragment() {
    val args: SocialMediaFragmentArgs by navArgs()
    val viewModel: SocialMediaViewModel
            by navGraphViewModels<SocialMediaViewModelImpl>(R.id.socialMediaNavGraph) { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getViewModelScope().launch(Dispatchers.IO) {
            viewModel.loadNews()
            viewModel.loadBlogs()
            viewModel.loadCurrentUser()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets {
                        SocialMediaScreen(
                            args = args,
                            viewModel = viewModel,
                            navController = findNavController()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SocialMediaScreen(
    args: SocialMediaFragmentArgs,
    viewModel: SocialMediaViewModel,
    navController: NavController
) {
    val tabs = listOf(
        TabItem.News(viewModel, navController),
        TabItem.Blogs(viewModel, navController)
    )
    val pagerState = rememberPagerState(tabs.size, initialPage = args.index)
    Column {
        Spacer(modifier = Modifier.statusBarsPadding())
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "")
            }
            Text(
                style = MaterialTheme.typography.h5,
                text = stringResource(tabs[pagerState.currentPage].titleId)
            )
        }
        Tabs(
            tabs = tabs,
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        )
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            tabs[page].content()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(tab.iconId),
                        contentDescription = null
                    )
                })
        }
    }
}

sealed class TabItem(
    val iconId: Int,
    val titleId: Int,
    val content: @Composable () -> Unit
) {
    class News(viewModel: SocialMediaViewModel, navController: NavController) :
        TabItem(R.drawable.ic_paper, R.string.news, { NewsScreen(viewModel, navController) })

    class Blogs(viewModel: SocialMediaViewModel, navController: NavController) :
        TabItem(R.drawable.ic_chat, R.string.blogs, { BlogsScreen(viewModel, navController) })
}