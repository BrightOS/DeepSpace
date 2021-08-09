package ru.myitschool.nasa_bootcamp.ui.home.social_media.pages

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaFragmentDirections
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.Feed
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp

@Composable
fun BlogsScreen(viewModel: SocialMediaViewModel, navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val listResource by viewModel.getBlogs().observeAsState(Resource.success(listOf()))
    val action = SocialMediaFragmentDirections.actionSocialMediaFragmentToCommentsFragment()
    val currentUser by viewModel.getCurrentUser().observeAsState()
    Feed(
        onRetryButtonClick = { viewModel.getViewModelScope().launch { viewModel.loadBlogs() } },
        itemContent = { item: PostModel -> BlogItemContent(item) },
        onLikeButtonClick = {
            val liveData = MutableLiveData(Resource.loading(null))
            viewModel.getViewModelScope().launch {
                liveData.postValue(viewModel.pressedLikeOnItem(it))
            }
            liveData
        },
        onItemClick = {
            viewModel.setSelectedPost(it)
            navController.navigate(action)
        },
        onLikeInCommentClick = { item, comment ->
            val liveData = MutableLiveData(Resource.loading(null))
            viewModel.getViewModelScope().launch {
                liveData.postValue(viewModel.pressedLikeOnComment(item, comment))
            }
            liveData
        },
        listResource = listResource,
        currentUser = currentUser,
        headerContent = {
            BlogCreatePost { title, postItems ->
                val liveData = MutableLiveData(Resource.loading(null))
                viewModel.getViewModelScope().launch {
                    liveData.postValue(viewModel.createPost(title, postItems))
                }
                liveData.observe(lifecycleOwner) {
                    if (it.status == Status.SUCCESS)
                        viewModel.getViewModelScope().launch {
                            delay(1000)
                            viewModel.loadBlogs()
                        }
                }
                liveData
            }
        },
        onDeleteComment = { comment, item ->
            viewModel.getViewModelScope().launch { viewModel.deleteComment(comment, item) }
        }
    )
}

@Composable
fun BlogItemContent(item: PostModel) {
    Column {
        if (item.postItems.isNotEmpty()
            && Patterns.WEB_URL.matcher(item.postItems.first()).matches()
        )
            Image(
                painter = rememberImagePainter(item.postItems.first()),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(bottom = 8.dp)
            )
        Text(
            text = item.title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = getDateFromUnixTimestamp(item.date),
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = stringResource(R.string.create_by_space_colon) + item.author.name,
            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 4.dp)
        )
    }
}

@Composable
fun BlogCreatePost(
    onSendButton: (String, List<Any>) -> LiveData<Resource<Nothing>>
) {
    var isExpanded by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var title by remember { mutableStateOf("") }
    var postItems by remember { mutableStateOf<List<Any>>(listOf()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    if (imageUri != null) {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageUri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageUri!!)
            ImageDecoder.decodeBitmap(source)
        }
        postItems = postItems.plus(bitmap!!)
        imageUri = null
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            if (isExpanded) {
                TextField(
                    value = title,
                    singleLine = true,
                    label = { Text(stringResource(R.string.title)) },
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                postItems.forEachIndexed { index, item ->
                    when (item) {
                        is String -> TextField(
                            value = item,
                            maxLines = 5,
                            label = { Text(stringResource(R.string.text)) },
                            onValueChange = {
                                val newList = postItems.toMutableList()
                                newList[index] = it
                                postItems = newList
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        is Bitmap -> Image(
                            bitmap = item.asImageBitmap(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    IconButton(
                        onClick = { postItems = postItems.plus("") },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_paper_plus),
                            "add text"
                        )
                    }
                    IconButton(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_image_2),
                            "choose photo"
                        )
                    }
                    IconButton(
                        onClick = { postItems = postItems.dropLast(1) },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_paper_negative),
                            "remove last element"
                        )
                    }
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    if (isExpanded) {
                        focusManager.clearFocus()
                        val liveData = onSendButton(title, postItems)
                        liveData.observe(lifecycleOwner) {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    postItems = listOf()
                                    isExpanded = false
                                }
                                Status.ERROR -> Toast.makeText(
                                    context,
                                    "Failed to create post",
                                    Toast.LENGTH_SHORT
                                ).show()
                                else -> {
                                }
                            }
                        }
                    } else isExpanded = true
                },
                shape = RoundedCornerShape(16.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(1f)
            ) {
                Text(stringResource(R.string.create_post))
            }
            IconButton(onClick = {
                if (isExpanded) {
                    postItems = listOf()
                    title = ""
                    isExpanded = false
                } else isExpanded = true
            }, modifier = Modifier.padding(end = 16.dp)) {
                if (isExpanded)
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "close"
                    ) else Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_edit_square),
                    contentDescription = null
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}