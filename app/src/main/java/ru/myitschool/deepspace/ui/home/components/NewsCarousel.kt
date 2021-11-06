package ru.myitschool.deepspace.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.myitschool.deepspace.data.model.ArticleModel
import ru.myitschool.deepspace.utils.Resource

/*
 * @author Samuil Nalisin
 */
@Composable
fun NewsCarousel(
    title: String,
    articlesResource: Resource<List<ArticleModel>>,
    onShowMoreClick: () -> Unit,
    onRetryButtonClick: () -> Unit,
    onItemClick: (article: ArticleModel) -> Unit
) {
    val titleTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    CarouselTemplate(
        modelsResource = articlesResource,
        title = title,
        onShowMoreClick = onShowMoreClick,
        onRetryButtonClick = onRetryButtonClick,
        onItemClick = onItemClick, cardContent = { articleModel ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter(articleModel.imageUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize()
                )
                Gradient(modifier = Modifier.matchParentSize())
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    text = articleModel.title,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        })
}