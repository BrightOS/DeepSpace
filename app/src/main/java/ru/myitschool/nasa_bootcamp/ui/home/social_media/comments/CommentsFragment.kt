package ru.myitschool.nasa_bootcamp.ui.home.social_media.comments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.navGraphViewModels
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.data.model.UserModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModelImpl
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.CommentItem
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.LikeButton
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp
import ru.myitschool.nasa_bootcamp.utils.parseNewsDate

@AndroidEntryPoint
class CommentsFragment : Fragment() {
    val viewModel: SocialMediaViewModel
            by navGraphViewModels<SocialMediaViewModelImpl>(R.id.socialMediaNavGraph) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets {
                        CommentsScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CommentsScreen(viewModel: SocialMediaViewModel) {
    val currentUser by viewModel.getCurrentUser().observeAsState()
    if (viewModel.getSelectedPost() != null) {
        val post by viewModel.getSelectedPost()!!.observeAsState()
        Column {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    PostContent(postModel = post!!.content)
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
                items(post!!.comments) {
                    CommentItem(
                        comment = it,
                        currentUser = currentUser,
                        onLikeClick = {
                            val liveData = MutableLiveData(Resource.loading(null))
                            viewModel.getViewModelScope().launch {
                                liveData.postValue(viewModel.pressedLikeOnComment(post!!, it))
                            }
                            liveData
                        },
                        onCommentClick = { }
                    )
                }
            }
            BottomTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                currentUser = currentUser,
                onClick = {
                    val liveData = MutableLiveData(Resource.loading(null))
                    viewModel.getViewModelScope().launch {
                        liveData.postValue(
                            viewModel.sendMessage(
                                message = it,
                                id = post!!.content.id,
                                _class = PostModel::class.java
                            )
                        )
                    }
                    liveData
                })
        }
    } else if (viewModel.getSelectedArticle() != null) {
        val article by viewModel.getSelectedArticle()!!.observeAsState()
        Column {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    ArticleContent(item = article!!, currentUser = currentUser, onLikeClick = {
                        val liveData = MutableLiveData(Resource.loading(null))
                        viewModel.getViewModelScope().launch {
                            liveData.postValue(viewModel.pressedLikeOnItem(article!!))
                        }
                        liveData
                    })
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
                items(article!!.comments) {
                    CommentItem(
                        comment = it,
                        currentUser = currentUser,
                        onLikeClick = {
                            val liveData = MutableLiveData(Resource.loading(null))
                            viewModel.getViewModelScope().launch {
                                liveData.postValue(viewModel.pressedLikeOnComment(article!!, it))
                            }
                            liveData
                        },
                        onCommentClick = { }
                    )
                }
            }
            BottomTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                currentUser = currentUser,
                onClick = {
                    val liveData = MutableLiveData(Resource.loading(null))
                    viewModel.getViewModelScope().launch {
                        liveData.postValue(
                            viewModel.sendMessage(
                                message = it,
                                id = article!!.content.id,
                                _class = ArticleModel::class.java
                            )
                        )
                    }
                    liveData
                })
        }
    }
}

@Composable
fun BottomTextField(
    modifier: Modifier = Modifier,
    currentUser: UserModel?,
    onClick: (String) -> LiveData<Resource<Nothing>>
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var isButtonEnabled by remember { mutableStateOf(true) }
    var messageTextField by remember { mutableStateOf("") }
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            TextField(
                value = messageTextField,
                maxLines = 4,
                label = { Text(stringResource(R.string.comment)) },
                onValueChange = { messageTextField = it },
                modifier = Modifier
                    .weight(1f, true)
                    .padding(8.dp)
            )
            IconButton(
                enabled = isButtonEnabled,
                onClick = {
                    focusManager.clearFocus()
                    if (currentUser == null) {
                        Toast.makeText(
                            context,
                            "You need to log in",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val liveData = onClick(messageTextField)
                        liveData.observe(lifecycleOwner) {
                            isButtonEnabled = false
                            when (it.status) {
                                Status.SUCCESS -> {
                                    isButtonEnabled = true
                                    messageTextField = ""
                                }
                                Status.ERROR -> {
                                    isButtonEnabled = true
                                    Toast.makeText(
                                        context,
                                        "Failed to send a comment",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(painter = painterResource(R.drawable.ic_send), contentDescription = "send")
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ArticleContent(
    item: ContentWithLikesAndComments<ArticleModel>,
    currentUser: UserModel?,
    onLikeClick: () -> LiveData<Resource<Nothing>>
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.LightGray,
                textDecoration = TextDecoration.Underline,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        ) {
            withAnnotation(
                tag = "URL",
                annotation = item.content.url
            ) {
                append("Read in source")
            }
        }
    }
    Column {
        Image(
            painter = rememberImagePainter(item.content.imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.content.title, style = MaterialTheme.typography.h4)
            Text(text = item.content.summary, modifier = Modifier.padding(vertical = 8.dp))
            Text(text = parseNewsDate(item.content.publishedAt))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextWithLinks(annotatedText = annotatedText, modifier = Modifier.weight(1f))
                LikeButton(list = item.likes, currentUser = currentUser, onClick = onLikeClick)
            }
        }
    }
}

@Composable
fun PostContent(postModel: PostModel) {
    Column {
        Text(text = postModel.title, style = MaterialTheme.typography.h5)
        postModel.postItems.forEach {
            if (Patterns.WEB_URL.matcher(it).matches()) {
                Image(
                    painter = rememberImagePainter(it),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Text(text = it, modifier = Modifier.padding(vertical = 12.dp))
            }
        }
        Text(text = getDateFromUnixTimestamp(postModel.date))
    }
}