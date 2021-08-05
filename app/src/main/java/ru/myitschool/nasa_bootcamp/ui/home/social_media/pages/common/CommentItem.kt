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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.Comment
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp

@Composable
fun CommentItem(
    comment: Comment,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    maxLines: Int = Int.MAX_VALUE
) {
        Column(modifier = Modifier
            .clickable { onCommentClick() }
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
                IconButton(onClick = onLikeClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_heart),
                        contentDescription = "like"
                    )
                }
                Text(text = comment.likes.size.toString())
            }
            Text(
                fontSize = 16.sp,
                text = comment.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines
            )
        }
}