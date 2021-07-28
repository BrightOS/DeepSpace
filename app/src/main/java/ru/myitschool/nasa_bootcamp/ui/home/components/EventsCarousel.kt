package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.myitschool.nasa_bootcamp.data.model.EventModel
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@Composable
fun EventsCarousel(
    title: String,
    eventsResource: Resource<List<EventModel>>,
    onItemClick: () -> Unit
) {
    val listState = rememberLazyListState()
    Column {
        Box {
            Text(style = MaterialTheme.typography.h5, text = title)
        }
        Box {
            if (eventsResource.status == Status.LOADING)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LazyRow(state = listState) {
                if (eventsResource.status == Status.SUCCESS)
                    items(eventsResource.data!!) { item ->
                        EventRowItem(eventModel = item, onItemClick = onItemClick)
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventRowItem(eventModel: EventModel, onItemClick: () -> Unit) {
    Card(onClick = onItemClick) {
        Text(
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.h6,
            maxLines = 4,
            text = eventModel.title
        )
    }
}