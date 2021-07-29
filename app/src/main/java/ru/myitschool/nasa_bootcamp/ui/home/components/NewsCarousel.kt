package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.utils.Resource

@Composable
fun NewsCarousel(
    title: String,
    articlesResource: Resource<List<ArticleModel>>,
    onItemClick: () -> Unit
) {
    CarouselTemplate(
        modelsResource = articlesResource,
        title = title,
        onItemClick = onItemClick, cardContent = { articleModel ->
            Box {
                Image(
                    painter = rememberImagePainter(articleModel.imageUrl),
                    contentDescription = null
                )
                Box( // gradient
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x88000000)
                                )
                            )
                        )
                )
                Text(
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    text = articleModel.title,
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }
        })
}