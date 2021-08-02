package ru.myitschool.nasa_bootcamp.ui.home.social_media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.fragment.findNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R

class SocialMediaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets {
                        SocialMediaScreen(onBackArrowClick = { findNavController().navigateUp() })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SocialMediaScreen(onBackArrowClick: () -> Unit) {
    val tabs = listOf(
        TabItem.News,
        TabItem.Blogs,
        TabItem.Profile
    )
    val pagerState = rememberPagerState(tabs.size)
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackArrowClick, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Filled.ArrowBack, "")
                }
                Text(
                    style = MaterialTheme.typography.h5,
                    text = stringResource(tabs[pagerState.currentPage].titleId)
                )
            }
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                tabs[page].content()
            }
        }
        Tabs(
            tabs = tabs,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Card(shape = RoundedCornerShape(32.dp), modifier = modifier) {
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
}

sealed class TabItem(val iconId: Int, val titleId: Int, val content: @Composable () -> Unit) {
    object News : TabItem(R.drawable.ic_paper, R.string.news, { NewsScreen() })
    object Blogs : TabItem(R.drawable.ic_chat, R.string.blogs, { BlogsScreen() })
    object Profile : TabItem(R.drawable.ic_profile, R.string.profile, { ProfileScreen() })
}