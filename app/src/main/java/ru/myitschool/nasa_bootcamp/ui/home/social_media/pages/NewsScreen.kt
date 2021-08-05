package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaFragmentDirections
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.Feed
import ru.myitschool.nasa_bootcamp.utils.Resource

@Composable
fun NewsScreen(viewModel: SocialMediaViewModel, navController: NavController) {
    val listResource by viewModel.getNews().observeAsState(Resource.success(listOf()))
    val action = SocialMediaFragmentDirections.actionSocialMediaFragmentToCommentsFragment()
    Feed(
        onRetryButtonClick = { viewModel.getViewModelScope().launch { viewModel.loadNews() } },
        itemContent = { item: ArticleModel -> NewsItem(item) },
        onCommentButtonClick = {
            viewModel.setSelectedArticle(it)
            navController.navigate(action)
        },
        onLikeButtonClick = {
            viewModel.getViewModelScope().launch { viewModel.pressedLikeOnArticle(it) }
        },
        onItemClick = {
            viewModel.setSelectedArticle(it)
            navController.navigate(action)
        },
        onLikeInCommentClick = {
            viewModel.getViewModelScope().launch { viewModel.pressedLikeOnComment(it) }
        },
        listResource = listResource
    )
}

@Composable
fun NewsItem(item: ArticleModel) {
    Column {
        Image(
            painter = rememberImagePainter(item.imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = item.publishedAt,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}