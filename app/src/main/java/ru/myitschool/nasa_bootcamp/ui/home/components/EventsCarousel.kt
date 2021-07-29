package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.myitschool.nasa_bootcamp.data.model.EventModel
import ru.myitschool.nasa_bootcamp.utils.Resource

@Composable
fun EventsCarousel(
    title: String,
    eventsResource: Resource<List<EventModel>>,
    onItemClick: () -> Unit
) {
    CarouselTemplate(
        modelsResource = eventsResource,
        title = title,
        onItemClick = onItemClick, cardContent = { eventModel ->
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6,
                maxLines = 4,
                text = eventModel.title
            )
        })
}