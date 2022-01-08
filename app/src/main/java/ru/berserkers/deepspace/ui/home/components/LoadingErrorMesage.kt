package ru.berserkers.deepspace.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.berserkers.deepspace.R

/*
 * @author Samuil Nalisin
 */
@Composable
fun ErrorMessage(onClick: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            stringResource(R.string.loading_error_message),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )
        Button(
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}