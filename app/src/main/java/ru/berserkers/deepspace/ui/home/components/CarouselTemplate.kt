package ru.berserkers.deepspace.ui.home.components

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
import androidx.compose.ui.unit.sp
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.utils.Resource
import ru.berserkers.deepspace.utils.Status

/*
 * @author Samuil Nalisin
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CarouselTemplate(
    modelsResource: Resource<List<T>>,
    title: String,
    onItemClick: (item: T) -> Unit,
    onShowMoreClick: () -> Unit,
    onRetryButtonClick: () -> Unit,
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
                fontSize = 18.sp,
                text = stringResource(R.string.show_more),
                modifier = Modifier
                    .clickable { onShowMoreClick() }
                    .align(Alignment.CenterEnd)
            )
        }
        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
        ) {
            when (modelsResource.status) {
                Status.LOADING -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                Status.SUCCESS -> LazyRow(state = listState) {
                    if (modelsResource.status == Status.SUCCESS)
                        items(modelsResource.data!!) { item ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                elevation = 4.dp,
                                onClick = { onItemClick(item) },
                                modifier = Modifier
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
                Status.ERROR -> ErrorMessage(
                    onClick = onRetryButtonClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
