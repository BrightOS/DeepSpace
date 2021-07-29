package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.utils.Resource

@Composable
fun NewsCarousel(
    title: String,
    articlesResource: Resource<List<ArticleModel>>,
    onShowMoreClick: () -> Unit,
    onItemClick: () -> Unit
) {
    val titleTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    CarouselTemplate(
        modelsResource = articlesResource,
        title = title,
        onShowMoreClick = onShowMoreClick,
        onItemClick = onItemClick, cardContent = { articleModel ->
            Box {
                Image(
                    painter = rememberImagePainter(articleModel.imageUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Gradient(modifier = Modifier.matchParentSize())
                Text(
                    style = titleTextStyle,
                    maxLines = 2,
                    text = articleModel.title,
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }
        })
}