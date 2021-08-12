package ru.myitschool.nasa_bootcamp.ui.home.social_media.comments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.ui.home.components.clearFocusOnKeyboardDismiss
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModel
import ru.myitschool.nasa_bootcamp.ui.home.social_media.SocialMediaViewModelImpl
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.CommentItem
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.LikeButton
import ru.myitschool.nasa_bootcamp.ui.home.social_media.pages.common.ToolBar
import ru.myitschool.nasa_bootcamp.utils.Resource
import ru.myitschool.nasa_bootcamp.utils.Status
import ru.myitschool.nasa_bootcamp.utils.getDateFromUnixTimestamp
import ru.myitschool.nasa_bootcamp.utils.parseNewsDate

@AndroidEntryPoint
class CommentsFragment : Fragment() {
    val viewModel: SocialMediaViewModel
            by navGraphViewModels<SocialMediaViewModelImpl>(R.id.socialMediaNavGraph) { defaultViewModelProviderFactory }

    override fun onResume() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        super.onResume()
    }

    override fun onPause() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        CommentsScreen(viewModel, findNavController())
                    }
                }
            }
        }
    }
}

@Composable
fun CommentsScreen(viewModel: SocialMediaViewModel, navController: NavController) {
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    var selectedComment: Comment? by remember { mutableStateOf(null) }
    val currentUser by viewModel.getCurrentUser().observeAsState()
    val article by viewModel.getSelectedArticle()?.observeAsState()
        ?: remember { mutableStateOf(null) }
    val post by viewModel.getSelectedPost()?.observeAsState()
        ?: remember { mutableStateOf(null) }
    val likeButton: @Composable () -> Unit = {
        LikeButton(
            list = article?.likes ?: post!!.likes,
            currentUser = currentUser,
            onClick = {
                Log.d("HELP", "CommentsScreen: ${article ?: post!!}")
                val liveData = MutableLiveData(Resource.loading(null))
                viewModel.getViewModelScope().launch {
                    liveData.postValue(viewModel.pressedLikeOnItem(article ?: post!!))
                }
                liveData
            })
    }
    Column {
        LazyColumn(modifier = Modifier.weight(1f), state = listState) {
            item {
                if (post != null)
                    PostContent(
                        postModel = post!!.content,
                        likeButton = likeButton,
                        toolbar = { ToolBar(navController = navController, title = it) })
                else ArticleContent(
                    item = article!!,
                    likeButton = likeButton,
                    toolbar = { ToolBar(navController = navController, title = it) })
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
            items(article?.comments ?: post!!.comments) {
                CommentItem(
                    comment = it,
                    currentUser = currentUser,
                    onLikeClick = {
                        val liveData = MutableLiveData(Resource.loading(null))
                        viewModel.getViewModelScope().launch {
                            liveData.postValue(
                                viewModel.pressedLikeOnComment(
                                    article ?: post!!,
                                    it
                                )
                            )
                        }
                        liveData
                    },
                    onCommentClick = {
                        selectedComment = it
                        focusRequester.requestFocus()
                    },
                    onDeleteComment = { comment ->
                        viewModel.getViewModelScope()
                            .launch { viewModel.deleteComment(comment, article ?: post!!) }
                    }
                )
            }
        }
        BottomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsWithImePadding(),
            currentUser = currentUser,
            selectedComment = selectedComment,
            focusRequester = focusRequester,
            clearSelectedUser = { selectedComment = null },
            onClick = { text, selectedComment ->
                val comment =
                    if (selectedComment is SubComment)
                        selectedComment.parentComment
                    else selectedComment
                val liveData = MutableLiveData(Resource.loading(null))
                viewModel.getViewModelScope().launch {
                    liveData.postValue(
                        viewModel.sendMessage(
                            message = text,
                            parentComment = comment,
                            id = if (article != null) article!!.content.id else post!!.content.id,
                            _class = if (article != null) ArticleModel::class.java else PostModel::class.java
                        )
                    )
                }
                val lastItemIndex = listState.layoutInfo.totalItemsCount - 1
                liveData.observe(lifecycleOwner) {
                    if (it.status == Status.SUCCESS)
                        coroutineScope.launch {
                            if (comment == null)
                                listState.scrollToItem(lastItemIndex)
                        }
                }
                liveData
            },
        )
    }
}

@Composable
fun BottomTextField(
    modifier: Modifier = Modifier,
    currentUser: UserModel?,
    selectedComment: Comment? = null,
    focusRequester: FocusRequester,
    onClick: (String, Comment?) -> LiveData<Resource<Nothing>>,
    clearSelectedUser: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var isButtonEnabled by remember { mutableStateOf(true) }
    var messageTextField by remember { mutableStateOf("") }
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            if (selectedComment != null)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        stringResource(R.string.reply_to) + selectedComment.author.name,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = clearSelectedUser) {
                        Icon(Icons.Filled.Close, "")
                    }
                }
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
                        .focusRequester(focusRequester)
                        .clearFocusOnKeyboardDismiss()
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
                            val liveData = onClick(messageTextField, selectedComment)
                            liveData.observe(lifecycleOwner) {
                                isButtonEnabled = false
                                when (it.status) {
                                    Status.SUCCESS -> {
                                        isButtonEnabled = true
                                        messageTextField = ""
                                        clearSelectedUser()
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
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ArticleContent(
    item: ContentWithLikesAndComments<ArticleModel>,
    likeButton: @Composable () -> Unit,
    toolbar: @Composable (String) -> Unit
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
        toolbar(stringResource(R.string.article))
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
                likeButton()
            }
        }
    }
}

@Composable
fun PostContent(
    postModel: PostModel,
    likeButton: @Composable () -> Unit,
    toolbar: @Composable (String) -> Unit
) {
    Column {
        toolbar(stringResource(R.string.post))
        Text(
            text = postModel.title,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = stringResource(R.string.create_by_space_colon) + postModel.author.name,
            modifier = Modifier.padding(8.dp)
        )
        postModel.postItems.forEach {
            if (Patterns.WEB_URL.matcher(it).matches()) {
                Image(
                    painter = rememberImagePainter(it),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(vertical = 12.dp)
                )
            } else {
                Text(text = it, modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = getDateFromUnixTimestamp(postModel.date), modifier = Modifier.weight(1f))
            likeButton()
        }
    }
}