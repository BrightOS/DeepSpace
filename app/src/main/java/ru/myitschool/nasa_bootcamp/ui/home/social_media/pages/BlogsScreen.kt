package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
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
    val currentUser by viewModel.getCurrentUser().observeAsState()
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
        currentUser = currentUser,
        headerContent = {
            BlogCreatePost(
                onSendButton = { title, text, bitmap -> viewModel.createPost(title, text, bitmap) },
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
fun BlogCreatePost(
    bitmap: Bitmap? = null,
    onChoosePhoto: () -> Unit,
    onSendButton: (String, String, Bitmap?) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        if (isExpanded) {
            TextField(
                value = title,
                singleLine = true,
                label = { Text(stringResource(R.string.title)) },
                onValueChange = { title = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            TextField(
                value = text,
                maxLines = 5,
                label = { Text(stringResource(R.string.title)) },
                onValueChange = { text = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    if (isExpanded) onSendButton(title, text, bitmap) else isExpanded = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.create_post))
            }
            IconButton(onClick = onChoosePhoto) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_paper_upload),
                    "choose photo"
                )
            }
            IconButton(onClick = { isExpanded = false }, modifier = Modifier.padding(end = 16.dp)) {
                Icons.Filled.Close
            }
        }
    }
}