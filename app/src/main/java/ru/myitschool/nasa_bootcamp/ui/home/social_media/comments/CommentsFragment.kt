package ru.myitschool.nasa_bootcamp.ui.home.social_media.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.Comment
import ru.myitschool.nasa_bootcamp.data.model.PostModel
import ru.myitschool.nasa_bootcamp.data.model.UserModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModelImpl
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.CommentItem
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp

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
    val scrollState = rememberScrollState()
    Box {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Spacer(modifier = Modifier.statusBarsPadding())
            when {
                viewModel.getSelectedArticle() != null -> {
                    val article = viewModel.getSelectedArticle()!!
                    ArticleContent(articleModel = article.content)
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    )
                    Comments(list = article.comments, currentUser = viewModel.getCurrentUser()) {
                        viewModel.getViewModelScope()
                            .launch { viewModel.pressedLikeOnComment(article, it) }
                    }
                }
                viewModel.getSelectedPost() != null -> {
                    val post = viewModel.getSelectedPost()!!
                    PostContent(postModel = post.content)
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    )
                    Comments(
                        list = post.comments,
                        currentUser = viewModel.getCurrentUser()
                    ) {
                        viewModel.getViewModelScope()
                            .launch { viewModel.pressedLikeOnComment(post, it) }
                    }
                }
                else -> throw KotlinNullPointerException()
            }
        }
        BottomTextField(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            onClick = {
                viewModel.getViewModelScope().launch {
                    viewModel.sendMessage(
                        message = it,
                        id = when {
                            viewModel.getSelectedArticle() != null -> viewModel.getSelectedArticle()!!.content.id
                            viewModel.getSelectedPost() != null -> viewModel.getSelectedPost()!!.content.id
                            else -> throw KotlinNullPointerException()
                        },
                        _class = when {
                            viewModel.getSelectedArticle() != null -> ArticleModel::class.java
                            viewModel.getSelectedPost() != null -> PostModel::class.java
                            else -> throw KotlinNullPointerException()
                        }
                    )
                }
            })
    }
}

@Composable
fun BottomTextField(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
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
            IconButton(onClick = { onClick(messageTextField) }, modifier = Modifier.padding(8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_send), contentDescription = "send")
            }
        }
    }
}

@Composable
fun Comments(
    list: List<Comment>,
    currentUser: UserModel,
    onLikeInCommentClick: (Comment) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        list.forEach {
            CommentItem(
                comment = it,
                currentUser = currentUser,
                onLikeClick = { onLikeInCommentClick(it) },
                onCommentClick = { }
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ArticleContent(articleModel: ArticleModel) {
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
                annotation = articleModel.url
            ) {
                append("Read in source")
            }
        }
    }
    Column {
        Image(
            painter = rememberImagePainter(articleModel.imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = articleModel.title, style = MaterialTheme.typography.h4)
            Text(text = articleModel.summary, modifier = Modifier.padding(vertical = 8.dp))
            Text(text = articleModel.publishedAt)
            TextWithLinks(annotatedText = annotatedText)
        }
    }
}

@Composable
fun PostContent(postModel: PostModel) {
    Column {
        if (postModel.imageUrl != null)
            Image(
                painter = rememberImagePainter(postModel.imageUrl),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        Text(text = postModel.title, style = MaterialTheme.typography.h5)
        Text(text = postModel.text, modifier = Modifier.padding(vertical = 12.dp))
        Text(text = getDateFromUnixTimestamp(postModel.date))
    }
}