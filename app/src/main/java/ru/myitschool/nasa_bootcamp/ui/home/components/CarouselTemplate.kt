package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CarouselTemplate(
    modelsResource: Resource<List<T>>,
    title: String,
    onItemClick: () -> Unit,
    cardContent: @Composable (item: T) -> Unit
) {
    val listState = rememberLazyListState()
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                style = MaterialTheme.typography.h5,
                text = title,
                modifier = Modifier
                    .padding(4.dp)
                    .align(
                        Alignment.CenterStart
                    )
            )
            Text(
                text = stringResource(R.string.show_more),
                modifier = Modifier
                    .padding(4.dp)
                    .align(
                        Alignment.CenterEnd
                    )
            )
        }
        Box {
            if (modelsResource.status == Status.LOADING)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LazyRow(state = listState) {
                if (modelsResource.status == Status.SUCCESS)
                    items(modelsResource.data!!) { item ->
                        Card(
                            elevation = 4.dp, onClick = onItemClick, modifier = Modifier
                                .width(160.dp)
                                .height(160.dp)
                                .padding(4.dp)
                        ) {
                            cardContent(item)
                        }
                    }
            }
        }
    }
}