package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel

@Composable
fun ProfileScreen(viewModel: SocialMediaViewModel, navController: NavController) {
    Card(modifier = Modifier.fillMaxSize()) {
        Text("Profile")
    }
}