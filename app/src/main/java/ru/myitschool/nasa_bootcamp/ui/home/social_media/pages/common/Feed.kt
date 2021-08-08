package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.Comment
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.UserModel
import ru.myitschool.nasa_bootcamp.ui.home.components.ErrorMessage
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@Composable
fun <T> Feed(
    listResource: Resource<List<LiveData<ContentWithLikesAndComments<T>>>>,
    currentUser: UserModel?,
    onRetryButtonClick: () -> Unit,
    itemContent: @Composable (T) -> Unit,
    headerContent: @Composable LazyItemScope.() -> Unit = { Spacer(Modifier) },
    onLikeButtonClick: (ContentWithLikesAndComments<T>) -> LiveData<Resource<Nothing>>,
    onLikeInCommentClick: (ContentWithLikesAndComments<T>, Comment) -> LiveData<Resource<Nothing>>,
    onItemClick: (LiveData<ContentWithLikesAndComments<T>>) -> Unit
) {
    Box {
        if (listResource.data != null) {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    headerContent()
                }
                items(listResource.data) { item ->
                    val content by item.observeAsState()
                    if (content != null)
                        ItemWithLikesAndComments(
                            item = content!!,
                            itemContent = itemContent,
                            currentUser = currentUser,
                            onLikeButtonClick = { onLikeButtonClick(content!!) },
                            onCommentButtonClick = { onItemClick(item) },
                            onLikeInCommentClick = { onLikeInCommentClick(content!!, it) },
                            onClick = { onItemClick(item) }
                        )
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    )
                }
            }
        }
        if (listResource.status == Status.LOADING)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        if (listResource.status == Status.ERROR)
            ErrorMessage(
                onClick = onRetryButtonClick,
                modifier = Modifier.align(Alignment.Center)
            )
    }
}

@Composable
fun <T> ItemWithLikesAndComments(
    item: ContentWithLikesAndComments<T>,
    currentUser: UserModel?,
    itemContent: @Composable (T) -> Unit,
    onLikeInCommentClick: (Comment) -> LiveData<Resource<Nothing>>,
    onLikeButtonClick: () -> LiveData<Resource<Nothing>>,
    onCommentButtonClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            itemContent(item.content)
            Divider(modifier = Modifier.padding(8.dp))
            val bestComment =
                item.comments.maxByOrNull { it.likes.size }
            if (bestComment != null)
                CommentItem(
                    comment = bestComment,
                    currentUser = currentUser,
                    onLikeClick = { onLikeInCommentClick(bestComment) },
                    onCommentClick = { onClick() },
                    maxLines = 5,
                    showSubComments = false
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
                    IconButton(onClick = onCommentButtonClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chat),
                            contentDescription = "comments"
                        )
                    }
                    Text(text = item.comments.size.toString())
                    LikeButton(
                        list = item.likes,
                        currentUser = currentUser,
                        onClick = onLikeButtonClick
                    )
                }
            }
        }
    }
}