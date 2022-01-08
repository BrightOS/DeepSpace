package ru.berserkers.deepspace.ui.home.social_media.pages.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.statusBarsPadding

/*
 * @author Samuil Nalisin
 */
@Composable
fun ToolBar(navController: NavController, title: String) {
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
            text = title
        )
    }
}