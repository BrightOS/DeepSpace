package ru.myitschool.nasa_bootcamp.ui.home.social_media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.material.composethemeadapter.MdcTheme
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
                        SocialMediaScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SocialMediaScreen() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabNames = mapOf(
        0 to stringResource(R.string.news),
        1 to stringResource(R.string.blogs),
        2 to stringResource(R.string.profile)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            tabNames[tabIndex]?.let { Text(style = MaterialTheme.typography.h5, text = it) }
        }
    }
}

@Composable
fun Tabs(tabIndex: Int, onTabClick: (Int) -> Unit) {
    Card(shape = RoundedCornerShape(32.dp)) {
        val tabData = listOf(
            ImageVector.vectorResource(R.drawable.ic_paper),
            ImageVector.vectorResource(R.drawable.ic_chat),
            ImageVector.vectorResource(R.drawable.ic_profile),
        )
        TabRow(selectedTabIndex = tabIndex) {
            tabData.forEachIndexed { index, icon ->
                Tab(
                    selected = index == tabIndex,
                    onClick = { onTabClick(index) },
                    icon = { Icon(imageVector = icon, contentDescription = null) })
            }
        }
    }
}

sealed class TabItem(val iconId: Int, val titleId: Int, val content: @Composable () -> Unit) {
    object News : TabItem(R.drawable.ic_paper, R.string.news, { NewsScreen() })
    object Blogs : TabItem(R.drawable.ic_chat, R.string.blogs, { BlogsScreen() })
    object Profile : TabItem(R.drawable.ic_profile, R.string.profile, { ProfileScreen() })
}