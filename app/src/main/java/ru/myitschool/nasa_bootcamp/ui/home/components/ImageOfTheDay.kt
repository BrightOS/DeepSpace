package ru.myitschool.nasa_bootcamp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.ui.home.components.Gradient
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@Composable
fun ImageOfTheDay(
    model: Resource<ImageOfTheDayModel>,
    modifier: Modifier,
    onRetryButtonClick: () -> Unit
) {
    Box(modifier = modifier) {
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
                    Gradient(modifier = Modifier.matchParentSize())
                    Text(
                        text = model.data.title,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
            Status.LOADING -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            Status.ERROR -> Column(modifier = Modifier.align(Alignment.Center)) {
                Text(stringResource(R.string.image_of_the_day_error_message))
                Button(onClick = onRetryButtonClick) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }
}