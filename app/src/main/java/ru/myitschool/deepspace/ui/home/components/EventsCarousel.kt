package ru.myitschool.deepspace.ui.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.myitschool.deepspace.data.model.EventModel
import ru.myitschool.deepspace.utils.Resource

/*
 * @author Samuil Nalisin
 */
@Composable
fun EventsCarousel(
    title: String,
    eventsResource: Resource<List<EventModel>>,
    onShowMoreClick: () -> Unit,
    onRetryButtonClick: () -> Unit,
    onItemClick: (event: EventModel) -> Unit
) {
    val titleTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    CarouselTemplate(
        modelsResource = eventsResource,
        title = title,
        onShowMoreClick = onShowMoreClick,
        onRetryButtonClick = onRetryButtonClick,
        onItemClick = onItemClick, cardContent = { eventModel ->
            Text(
                modifier = Modifier
                    .padding(8.dp),
                style = titleTextStyle,
                textAlign = TextAlign.Center,
                maxLines = 6,
                text = eventModel.title
            )
        })
}