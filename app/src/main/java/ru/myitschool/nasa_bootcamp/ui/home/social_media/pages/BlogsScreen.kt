package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.Feed
import ru.myitschool.nasa_bootcamp.utils.Resource

@Composable
fun BlogsScreen(viewModel: SocialMediaViewModel) {
    val listResource by viewModel.getBlogs().observeAsState(Resource.success(listOf()))
    Feed(
        onProfileClick = {},
        onRetryButtonClick = {},
        itemContent = { item: PostModel -> BlogItemContent(item) },
        onCommentClick = {},
        onLikeClick = {},
        listResource = listResource
    )
}

@Composable
fun BlogItemContent(item: PostModel) {
    Text(item.title)
    Text(item.text)
}