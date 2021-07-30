package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
    onItemClick: (item: T) -> Unit,
    onShowMoreClick: () -> Unit,
    cardContent: @Composable BoxScope.(item: T) -> Unit
) {
    val listState = rememberLazyListState()
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                style = MaterialTheme.typography.h5,
                text = title,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
            )
            Text(
                style =
                MaterialTheme.typography.h6,
//                    .plus(TextStyle(textDecoration = TextDecoration.Underline)),
                text = stringResource(R.string.show_more),
                modifier = Modifier
                    .clickable { onShowMoreClick() }
                    .align(Alignment.CenterEnd)
            )
        }
        Box(modifier = Modifier.padding(bottom = 4.dp)) {
            if (modelsResource.status == Status.LOADING)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LazyRow(state = listState) {
                if (modelsResource.status == Status.SUCCESS)
                    items(modelsResource.data!!) { item ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp, onClick = { onItemClick(item) }, modifier = Modifier
                                .padding(8.dp, 4.dp, 0.dp, 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(160.dp)
                                    .height(160.dp)
                            ) {
                                cardContent(item)
                            }
                        }
                    }
            }
        }
    }
}