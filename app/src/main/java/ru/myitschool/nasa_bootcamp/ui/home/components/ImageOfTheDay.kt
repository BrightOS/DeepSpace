package ru.myitschool.nasa_bootcamp.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.ui.home.components.ErrorMessage
import ru.myitschool.nasa_bootcamp.ui.home.components.Gradient
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

/*
 * @author Samuil Nalisin
 */
@Composable
fun ImageOfTheDay(
    model: Resource<ImageOfTheDayModel>,
    modifier: Modifier,
    onRetryButtonClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        Box(modifier = modifier.clickable(onClick = { isExpanded = !isExpanded })) {
            when (model.status) {
                Status.SUCCESS -> {
                    Image(
                        contentScale = ContentScale.Crop,
                        painter = rememberImagePainter(model.data!!.url),
                        contentDescription = null,
                        modifier = Modifier.matchParentSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        Gradient(modifier = Modifier.matchParentSize(), color = Color(0xFF000000))
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text = model.data.title,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.align(Alignment.End)
                            )
                            Text(
                                fontSize = 12.sp,
                                text = stringResource(R.string.click_on_the_image_hint),
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
                Status.LOADING -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
                Status.ERROR -> ErrorMessage(
                    onClick = onRetryButtonClick, modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        if (model.status == Status.SUCCESS && isExpanded) {
            Text("Date: ${model.data!!.date}", modifier = Modifier.padding(8.dp))
            Text(model.data.explanation, modifier = Modifier.padding(8.dp))
        }
    }
}
