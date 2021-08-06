package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaFragmentDirections
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.Feed
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp

@Composable
fun BlogsScreen(viewModel: SocialMediaViewModel, navController: NavController) {
    val listResource by viewModel.getBlogs().observeAsState(Resource.success(listOf()))
    val action = SocialMediaFragmentDirections.actionSocialMediaFragmentToCommentsFragment()
    Feed(
        onRetryButtonClick = { viewModel.getViewModelScope().launch { viewModel.loadBlogs() } },
        itemContent = { item: PostModel -> BlogItemContent(item) },
        onCommentButtonClick = {
            viewModel.setSelectedPost(it)
            navController.navigate(action)
        },
        onLikeButtonClick = {
            viewModel.getViewModelScope().launch { viewModel.pressedLikeOnItem(it) }
        },
        onItemClick = {
            viewModel.setSelectedPost(it)
            navController.navigate(action)
        },
        onLikeInCommentClick = { item, comment ->
            viewModel.getViewModelScope().launch { viewModel.pressedLikeOnComment(item, comment) }
        },
        listResource = listResource,
        currentUser = viewModel.getCurrentUser(),
        headerContent = {
            BlogHeader(
                onSendButton = { post -> viewModel.createPost(post) },
                onChoosePhoto = { null })
        }
    )
}

@Composable
fun BlogItemContent(item: PostModel) {
    Column {
        if (item.imageUrl != null)
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
            text = getDateFromUnixTimestamp(item.date),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun BlogHeader(onChoosePhoto: () -> Bitmap?, onSendButton: (PostModel) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
    }
}