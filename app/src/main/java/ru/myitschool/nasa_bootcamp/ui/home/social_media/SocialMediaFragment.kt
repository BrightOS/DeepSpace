package ru.myitschool.nasa_bootcamp.ui.home.social_media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
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