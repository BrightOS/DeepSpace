package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@Composable
fun NewsCarousel(
    title: String,
    articlesResource: Resource<List<ArticleModel>>,
    onItemClick: () -> Unit
) {
    val listState = rememberLazyListState()
    Column {
        Box {
            Text(style = MaterialTheme.typography.h5, text = title)
        }
        Box {
            if (articlesResource.status == Status.LOADING)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LazyRow(state = listState) {
                if (articlesResource.status == Status.SUCCESS)
                    items(articlesResource.data!!) { item ->
                        ArticleRowItem(articleModel = item, onItemClick = onItemClick)
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleRowItem(articleModel: ArticleModel, onItemClick: () -> Unit) {
    Card(onClick = onItemClick) {
        Box {
            Image(painter = rememberImagePainter(articleModel.imageUrl), contentDescription = null)
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
    }
}
