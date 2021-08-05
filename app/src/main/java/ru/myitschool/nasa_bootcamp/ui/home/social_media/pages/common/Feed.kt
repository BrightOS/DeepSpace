package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.ui.home.components.ErrorMessage
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@Composable
fun <T> Feed(
    listResource: Resource<List<ContentWithLikesAndComments<T>>>,
    onRetryButtonClick: () -> Unit,
    itemContent: @Composable (T) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (listResource.status) {
            Status.SUCCESS ->
                LazyColumn(Modifier.fillMaxSize()) {
                    items(listResource.data!!) { item ->
                        ItemWithLikesAndComments(
                            item = item,
                            itemContent = itemContent,
                            onLikeClick = onLikeClick,
                            onCommentClick = onCommentClick,
                            onProfileClick = onProfileClick
                        )
                    }
                }
            Status.LOADING ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            Status.ERROR ->
                ErrorMessage(
                    onClick = onRetryButtonClick,
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}

@Composable
fun <T> ItemWithLikesAndComments(
    item: ContentWithLikesAndComments<T>,
    itemContent: @Composable (T) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val bestComment = item.comments.maxByOrNull { comment -> comment.likes.size }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            itemContent(item.content)
            Divider(modifier = Modifier.padding(8.dp))
            if (bestComment != null)
                CommentItem(
                    comment = bestComment,
                    onLikeClick,
                    onCommentClick,
                    onProfileClick,
                    maxLines = 5
                )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onLikeClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chat),
                            contentDescription = "comments"
                        )
                    }
                    Text(text = item.comments.size.toString())
                    IconButton(onClick = onLikeClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_heart),
                            contentDescription = "likes"
                        )
                    }
                    Text(text = item.likes.size.toString())
                }
            }
        }
    }
}