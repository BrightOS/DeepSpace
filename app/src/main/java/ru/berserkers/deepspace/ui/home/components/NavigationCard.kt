package ru.berserkers.deepspace.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/*
 * @author Samuil Nalisin
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationCard(
    data: NavigationCardData
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = data.modifier,
        onClick = data.onClick
    ) {
        Column {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = data.painter,
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Gradient(modifier = Modifier.matchParentSize())
                Text(
                    text = data.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(8.dp, 8.dp, 8.dp, 0.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

data class NavigationCardData(
    val painter: Painter,
    val title: String,
    val onClick: () -> Unit,
    val modifier: Modifier = Modifier
)
