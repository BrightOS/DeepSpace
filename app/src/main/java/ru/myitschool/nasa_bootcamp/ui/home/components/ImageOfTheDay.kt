package ru.myitschool.nasa_bootcamp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.utils.Resource

@Composable
fun ImageOfTheDay(model: Resource<ImageOfTheDayModel>?, modifier: Modifier) {
    Image(
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        modifier = modifier
    )
}