package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.myitschool.nasa_bootcamp.ui.home.components.ErrorMessage
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status

@Composable
fun <T> Feed(
    listResource: Resource<List<T>>,
    onRetryButtonClick: () -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (listResource.status) {
            Status.SUCCESS ->
                LazyColumn(Modifier.fillMaxSize()) {
                    items(listResource.data!!) { item ->
                        itemContent(item)
                    }
                }
            Status.LOADING ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            Status.ERROR ->
                ErrorMessage(
                    onClick = onRetryButtonClick,
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}