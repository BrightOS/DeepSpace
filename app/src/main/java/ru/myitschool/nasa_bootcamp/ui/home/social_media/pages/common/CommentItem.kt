package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.Comment
import ru.myitschool.nasa_bootcamp.data.model.UserModel
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp

@Composable
fun CommentItem(
    comment: Comment,
    currentUser: UserModel?,
    onLikeClick: (Comment) -> LiveData<Resource<Nothing>>,
    onCommentClick: (Comment) -> Unit,
    onDeleteComment: (Comment) -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    showSubComments: Boolean = true
) {
    Column {
        Column(modifier = modifier
            .clickable { onCommentClick(comment) }
            .fillMaxWidth()
            .padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = rememberImagePainter(comment.author.avatarUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(fill = true, weight = 1f)
                ) {
                    Text(fontSize = 18.sp, text = comment.author.name)
                    Text(
                        fontSize = 14.sp,
                        text = getDateFromUnixTimestamp(comment.date)
                    )
                }
                if (currentUser != null && currentUser.id == comment.author.id) {
                    IconButton(onClick = { onDeleteComment(comment) }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                            contentDescription = "delete comment"
                        )
                    }
                }
                LikeButton(list = comment.likes, currentUser = currentUser, onClick = { onLikeClick(comment) })
            }
            Text(
                fontSize = 16.sp,
                text = comment.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines
            )
        }
        if (showSubComments)
            comment.subComments.forEach { subComment ->
                CommentItem(
                    comment = subComment,
                    currentUser = currentUser,
                    onLikeClick = { onLikeClick(subComment) },
                    onDeleteComment = { onDeleteComment(subComment) },
                    onCommentClick = { onCommentClick(subComment) },
                    modifier = Modifier.padding(start = 32.dp),
                )
            }
    }
}