package ru.myitschool.nasa_bootcamp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationCard(
    painter: Painter,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        onClick = onClick
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp)
            )
            if (description != "")
                Text(
                    text = description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            else
                Spacer(modifier = Modifier.height(8.dp))
        }
    }

}