package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.UserModel

@Composable
fun LikeButton(
    list: List<UserModel>,
    currentUser: UserModel?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClick, modifier = modifier) {
            Icon(
                painter = painterResource(R.drawable.ic_heart),
                tint = if (list.contains(currentUser))
                    Color.Red
                else LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                contentDescription = "like"
            )
        }
        Text(text = list.size.toString())
    }
}