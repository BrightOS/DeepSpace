package ru.berserkers.deepspace.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.berserkers.deepspace.data.dto.firebase.CommentDto
import ru.berserkers.deepspace.data.dto.firebase.Post
import ru.berserkers.deepspace.data.dto.firebase.SubCommentDto
import ru.berserkers.deepspace.data.dto.firebase.UserDto
import ru.berserkers.deepspace.data.model.*
import ru.berserkers.deepspace.utils.Data
import ru.berserkers.deepspace.utils.Resource
import ru.berserkers.deepspace.utils.downloadFirebaseImage
import java.io.ByteArrayOutputStream
import java.util.*

class FirebaseRepositoryImpl(val appContext: Context) :
    FirebaseRepository {

    private val authenticator: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val dbInstance = FirebaseDatabase.getInstance()

    // VIEW CUSTOM USER POSTS
    override suspend fun getAllPosts(): Data<ArrayList<Post>> {
        val allPosts = ArrayList<Post>()
        try {
            dbInstance.getReference(USER_DATA).get().await().children.forEach {
                val username = getUsernameById(it.child(AUTHOR).getValue(String::class.java)!!)
                val postData = Post(
                    it.key!!,
                    it.child(TITLE).getValue(String::class.java)!!,
                    it.child("dateCreated").getValue(Long::class.java)!!,
                    username,
                    null
                )
                allPosts.add(postData)
            }
            return Data.Ok(allPosts)
        } catch (e: Exception) {
            return Data.Error(e.message.toString())
        }
    }

    override suspend fun getPostsFromDataSnapshot(snapshot: DataSnapshot):
            Resource<List<MutableLiveData<ContentWithLikesAndComments<PostModel>>>> {
        val returnData = mutableListOf<MutableLiveData<ContentWithLikesAndComments<PostModel>>>()
        try {
            snapshot.children.forEach {
                val _username = it.child(AUTHOR).child(NAME).getValue(String::class.java) ?: "anonymous"
                val _url =
                    it.child(AUTHOR).child(AVATAR_URL).getValue(String::class.java)?.toUri()
                val _id = it.child(AUTHOR).child(ID).getValue(String::class.java)!!
                val author = UserModel(_username, _url, _id)

                val data = mutableListOf<String>()
                it.child("postItems").children.forEach { _data ->
                    data.add(_data.getValue(String::class.java)!!)
                }

                val date = it.child(DATE).getValue(Long::class.java) ?: 1L

                val id = it.key!!.toLong()

                val title = it.child(TITLE).getValue(String::class.java) ?: ""

                val postModel = PostModel(id, title, date, data, author)
                val comments = getAllUserPostComments(id.toInt())
                val likes = getAllUserPostLikes(id.toInt())

                val liveData = MutableLiveData<ContentWithLikesAndComments<PostModel>>()
                liveData.postValue(ContentWithLikesAndComments(postModel, likes, comments))
                userPostCommentsAndLikesListener(liveData, postModel.id)
                returnData.add(liveData)
            }
            returnData.reverse()
            return Resource.success(returnData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.error(e.message.toString(), null)
        }
    }

    override suspend fun getAllPostsRawData():
            Resource<List<MutableLiveData<ContentWithLikesAndComments<PostModel>>>> {
        return getPostsFromDataSnapshot(
            dbInstance.getReference(USER_DATA)
                .orderByChild(DATE).get().await()
        )
    }

    private fun userPostCommentsAndLikesListener(
        postModel: MutableLiveData<ContentWithLikesAndComments<PostModel>>,
        postId: Long,
    ) {
        try {
            dbInstance.getReference(POSTS).child("UserPost")
                .child(postId.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val comments: MutableList<Comment> = mutableListOf()
                        val likes: MutableList<UserModel> = mutableListOf()

                        snapshot.child(COMMENTS).children.forEach {
                            val id = it.child(ID).getValue(Long::class.java)
                            val text = it.child(COMMENT).getValue(String::class.java)
                            val commentLikes = mutableListOf<UserModel>()

                            it.child(LIKES).children.forEach { like ->
                                val _username = like.child(NAME).getValue(String::class.java)!!
                                val _url =
                                    like.child(AVATAR_URL).getValue(String::class.java)!!.toUri()
                                val _id = like.child(ID).getValue(String::class.java)!!
                                commentLikes.add(UserModel(_username, _url, _id))
                            }

                            val authorUsername =
                                it.child(USER_ID).child(NAME).getValue(String::class.java)!!
                            val authorUrl =
                                it.child(USER_ID).child(AVATAR_URL)
                                    .getValue(String::class.java)!!
                                    .toUri()
                            val authorId =
                                it.child(USER_ID).child(ID).getValue(String::class.java)!!
                            val author = UserModel(authorUsername, authorUrl, authorId)

                            val date = it.child(DATE).getValue(Long::class.java)!!
                            val comment =
                                Comment(id!!, text!!, commentLikes, listOf(), author, date)
                            val subComments = getSubComments(it, comment)
                            comment.subComments = subComments
                            comments.add(comment)
                        }

                        snapshot.child(LIKES).children.forEach {
                            val _name = it.child(NAME).getValue(String::class.java)!!
                            val _url = it.child(AVATAR_URL).getValue(String::class.java)!!.toUri()
                            val _id = it.child(ID).getValue(String::class.java)!!
                            likes.add(UserModel(_name, _url, _id))
                        }

                        postModel.postValue(ContentWithLikesAndComments(postModel.value!!.content, likes, comments))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Firebase error posts", error.message)
                    }
                })
        } catch (e: Exception) {
            Log.d("Firebase error posts catch", e.message.toString())
        }
    }

    private suspend fun getAllUserPostComments(postId: Int): List<Comment> {
        val comments = mutableListOf<Comment>()
        dbInstance.getReference(POSTS).child("UserPost").child(postId.toString())
            .child(COMMENTS).get().await().children.forEach {

                val text = it.child(COMMENT).getValue(String::class.java)!!
                val id = it.child(ID).getValue(Long::class.java)!!
                val date = it.child(DATE).getValue(Long::class.java)!!
                val userDto = it.child(USER_ID).getValue(UserDto::class.java)!!
                val user = UserModel(userDto.name, userDto.avatarUrl!!.toUri(), userDto.id)
                val likes = mutableListOf<UserModel>()

                it.child(LIKES).children.forEach { like ->
                    val _userDto = like.getValue(UserDto::class.java)!!
                    val _user = UserModel(_userDto.name, _userDto.avatarUrl!!.toUri(), _userDto.id)
                    likes.add(_user)
                }

                val comment = Comment(id, text, likes, listOf(), user, date)
                val subComments = getSubComments(it, comment)
                comment.subComments = subComments
                comments.add(comment)
            }
        return comments
    }

    private suspend fun getAllUserPostLikes(postId: Int): List<UserModel> {
        val likes = mutableListOf<UserModel>()
        dbInstance.getReference(POSTS)
            .child("UserPost")
            .child(postId.toString())
            .child(LIKES)

            .get().await().children.forEach {
                val userDto = it.getValue(UserDto::class.java)!!
                val user = UserModel(userDto.name, userDto.avatarUrl!!.toUri(), userDto.id)
                likes.add(user)
            }
        return likes
    }

    override suspend fun downloadImage(
        postId: String,
        imageId: String,
    ): Data<Bitmap> {
        val storageRef = storage.getReference("$POSTS/$postId/$imageId")
        return downloadFirebaseImage(storageRef)
    }

    // USER CUSTOM POST CREATION:
    override suspend fun createPost(title: String, postItems: List<Any>):
            Resource<MutableLiveData<ContentWithLikesAndComments<PostModel>>> {
        try {
            val postId = getLastPostId()
            var photoCount = 1
            val reference = dbInstance.getReference(USER_POSTS).child(postId)
            reference.child(TITLE).setValue(title)
            val fbPostItems = mutableListOf<String>()

            for (value in postItems) {
                if (value::class.java == String::class.java) {
                    fbPostItems.add(value as String)
                } else {
                    val baos = ByteArrayOutputStream()
                    (value as Bitmap).compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val byte = baos.toByteArray()
                    storage.getReference("$POSTS/$postId/$photoCount")
                        .putBytes(byte)
                        .await()
                    val url =
                        storage.getReference("$POSTS/$postId/$photoCount").downloadUrl.await()
                    photoCount++
                    fbPostItems.add(url.toString())
                }
            }
            val user = getCurrentUser()!!
            val userDto = UserDto(user.name, user.avatarUrl.toString(), user.id)
            reference.child(AUTHOR).setValue(userDto).await()
            val date = Date().time
            reference.child(DATE).setValue(date).await()
            reference.child("postItems").setValue(fbPostItems).await()

            val post = ContentWithLikesAndComments(PostModel(postId.toLong(), title, date, fbPostItems, user), listOf(), listOf())
            val liveData = MutableLiveData<ContentWithLikesAndComments<PostModel>>()
            liveData.postValue(post)
            userPostCommentsAndLikesListener(liveData, postId.toLong())
            return Resource.success(liveData)
        } catch (e: Exception) {
            return Resource.error(e.message.toString(), null)
        }
    }

    //USER CUSTOM POST CREATION:
    override fun uploadImage(
        postId: String,
        imageId: Int,
        imagePath: Uri,
    ): LiveData<Data<String>> {
        val returnData = MutableLiveData<Data<String>>()
        try {
            val storageRef = storage.getReference(POSTS).child(postId).child("$imageId")
            storageRef.putFile(imagePath).addOnSuccessListener {
                returnData.postValue(Data.Ok("Ok"))
            }.addOnFailureListener {
                returnData.postValue(Data.Error(it.message!!))
            }
        } catch (e: java.lang.Exception) {
            returnData.postValue(Data.Error(e.message!!))
        }
        return returnData
    }

    override suspend fun getLastPostId(): String {
        var returnData = "0"
        try {
            returnData = (dbInstance.getReference(USER_POSTS).get()
                .await().children.last().key?.toLong()!! + 1).toString()
        } catch (e: Exception) {
            Log.e("FB_ERROR_TAG", "${e.printStackTrace()}")
        }

        return returnData
    }

    // COMMENTS AND LIKES OPERATIONS:
    override suspend fun pushComment(source: String, postId: Int, comment: String): Resource<Unit> {
        if (authenticator.uid != null) {
            try {
                val commentId = getLastCommentId(source, postId) + 1
                val user = getCurrentUser()!!

                val commentObject = CommentDto(
                    commentId,
                    comment,
                    listOf(),
                    listOf(),
                    UserDto(user.name, user.avatarUrl.toString(), user.id),
                    Date().time
                )
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(COMMENTS)
                    .child(commentId.toString()).setValue(commentObject).await()
                return Resource.success(null)
            } catch (e: Exception) {
                return Resource.error(e.message.toString(), null)
            }
        }
        return Resource.error("User is not authenticated.", null)
    }

    override suspend fun pushSubComment(source: String, postId: Int,
        fatherCommentId: Long, comment: String): Resource<Unit> {
        if (authenticator.uid != null) {
            try {
                val subCommentId = getLastSubCommentId(source, postId, fatherCommentId) + 1
                val user = getCurrentUser()!!
                val userDto = UserDto(user.name, user.avatarUrl!!.toString(), user.id)
                val subCommentObject = SubCommentDto(
                    subCommentId, comment, listOf(),
                    userDto, Date().time
                )
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(COMMENTS)
                    .child(fatherCommentId.toString()).child(SUB_COMMENTS)
                    .child(subCommentId.toString())
                    .setValue(subCommentObject).await()
                return Resource.success(null)
            } catch (e: Exception) {
                return Resource.error(e.message.toString(), null)
            }
        } else {
            return Resource.error("User is not authenticated.", null)
        }
    }

    override suspend fun deleteComment(source: String, postId: Int, commentId: Long): Resource<Unit> {
        return try {
            dbInstance.getReference(POSTS).child(source).child(postId.toString())
                .child(COMMENTS)
                .child(commentId.toString()).removeValue().await()
            Resource.success(null)
        } catch (e: Exception) {
            Resource.error("Comment doesn't exist", null)
        }
    }

    override suspend fun deleteSubComment(
        source: String, postId: Int,
        fatherCommentId: Long, subCommentId: Long,
    ): Resource<Unit> {
        return try {
            dbInstance.getReference(POSTS).child(source).child(postId.toString())
                .child(COMMENTS)
                .child(fatherCommentId.toString()).child(SUB_COMMENTS)
                .child(subCommentId.toString()).removeValue().await()
            Resource.success(null)
        } catch (e: java.lang.Exception) {
            Resource.error("SubComment doesn't exist", null)
        }
    }

    override suspend fun pushLike(source: String, postId: Int): Resource<Unit> {
        return if (authenticator.uid != null && !checkIfHasLike(source, postId)) {
            try {
                val user = getCurrentUser()!!
                val userObject = UserDto(user.name, user.avatarUrl.toString(), user.id)
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(LIKES)
                    .child(authenticator.uid!!)
                    .setValue(userObject).await()
                Resource.success(null)
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.error(e.message.toString(), null)
            }
        } else {
            deleteLike(source, postId)
        }
    }

    override suspend fun pushLikeForComment(source: String, postId: Int, commentId: Long): Resource<Unit> {
        return if (authenticator.uid != null && !checkIfHasCommentLike(source, postId, commentId)) {
            try {
                val user = getCurrentUser()!!
                val userObject = UserDto(user.name, user.avatarUrl.toString(), user.id)
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(COMMENTS)
                    .child(commentId.toString()).child(LIKES).child(authenticator.uid!!)
                    .setValue(userObject).await()

                Resource.success(null)
            } catch (e: Exception) {
                Resource.error(e.message.toString(), null)
            }
        } else {
            deleteCommentLike(source, postId, commentId)
        }
    }

    override suspend fun pushLikeForSubComment(
        source: String, postId: Int,
        fatherCommentId: Long, subCommentId: Long,
    ): Resource<Unit> {
        if (authenticator.uid != null && !checkIfHasSubCommentLike(source, postId, fatherCommentId, subCommentId)) {
            return try {
                val user = getCurrentUser()!!
                val userDto = UserDto(user.name, user.avatarUrl.toString(), user.id)
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(COMMENTS)
                    .child(fatherCommentId.toString()).child(SUB_COMMENTS)
                    .child(subCommentId.toString()).child(LIKES).child(authenticator.uid!!)
                    .setValue(userDto).await()

                Resource.success(null)
            } catch (e: Exception) {
                Resource.error(e.message.toString(), null)
            }
        }
        return deleteSubCommentLike(source, postId, fatherCommentId, subCommentId)
    }

    override suspend fun deleteLike(source: String, postId: Int): Resource<Unit> {
        return if (authenticator.uid != null && checkIfHasLike(source, postId)) {
            try {
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(LIKES)
                    .child(authenticator.uid!!).removeValue().await()
                Resource.success(null)
            } catch (e: Exception) {
                Resource.error(e.message.toString(), null)
            }
        } else {
            Resource.error("User is not authenticated or he didn't`t like this post", null)
        }
    }

    override suspend fun deleteCommentLike(source: String, postId: Int, commentId: Long): Resource<Unit> {
        return if (authenticator.uid != null && checkIfHasCommentLike(source, postId, commentId)) {
            try {
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(COMMENTS)
                    .child(commentId.toString()).child(LIKES).child(authenticator.uid!!)
                    .removeValue()
                    .await()
                Resource.success(null)
            } catch (e: Exception) {
                Resource.error(e.message.toString(), null)
            }
        } else {
            Resource.error("User is not authenticated or he didn't`t like this comment", null)
        }
    }

    override suspend fun deleteSubCommentLike(
        source: String, postId: Int,
        fatherCommentId: Long, subCommentId: Long,
    ): Resource<Unit> {
        if (authenticator.uid != null && checkIfHasSubCommentLike(source, postId, fatherCommentId, subCommentId)) {
            return try {
                dbInstance.getReference(POSTS).child(source).child(postId.toString())
                    .child(COMMENTS)
                    .child(fatherCommentId.toString()).child(SUB_COMMENTS)
                    .child(subCommentId.toString()).child(LIKES).child(authenticator.uid!!)
                    .removeValue()
                    .await()
                Resource.success(null)
            } catch (e: Exception) {
                Resource.error(e.message.toString(), null)
            }
        }
        return Resource.error("User is not authenticated or he didn't`t like this subComment", null)
    }

    override suspend fun authenticateUser(context: Context, email: String, password: String): Data<FirebaseUser> {
        return try {
            val user = authenticator.signInWithEmailAndPassword(email, password).await()
            val userInfo = getUser(user.user!!.uid)

            val sharedPreferences =
                context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit()

            with(sharedPreferences) {
                putString(sharedPreferencesUserName, userInfo!!.name)
                putString(sharedPreferencesUri, userInfo.avatarUrl.toString())
                putString(sharedPreferencesId, user.user!!.uid)
                apply()
            }

            (Data.Ok(user?.user!!))
        } catch (e: Exception) {
            (Data.Error(e.message.toString()))
        }
    }

    override fun signOutUser(context: Context): LiveData<Data<String>> {
        val returnData: MutableLiveData<Data<String>> = MutableLiveData()
        try {
            authenticator.signOut()
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()

            returnData.postValue(Data.Ok("Ok"))
        } catch (e: Exception) {
            returnData.postValue(Data.Error(e.message.toString()))
        }
        return returnData
    }

    override suspend fun createUser(
        context: Context, userName: String,
        email: String, password: String, imagePath: Uri?,
    ): Data<FirebaseUser> {
        try {
            val user = authenticator.createUserWithEmailAndPassword(email, password).await()
            if (user != null) {
                dbInstance.getReference(USER_DATA).child(user.user!!.uid).child(USERNAME)
                    .setValue(userName).await()
                val storageRef = storage.getReference("$USER_DATA/${user.user?.uid}")

                if (imagePath != null) storageRef.putFile(imagePath).await()

                user.user!!.sendEmailVerification().await()

                val sharedPreferences = context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)
                    .edit()

                with(sharedPreferences) {
                    putString(sharedPreferencesUserName, userName)
                    putString(sharedPreferencesUri, imagePath.toString())
                    putString(sharedPreferencesId, user.user!!.uid)
                    apply()
                }
                return (Data.Ok(user.user!!))
            } else {
                return (Data.Error("Unknown error happened."))
            }
        } catch (e: java.lang.Exception) {
            return (Data.Error(e.message!!))
        }
    }

    override suspend fun getUser(uid: String): UserModel? {
        var user: UserModel? = null
        try {
            var avatarUrl: Uri? = null
            val userName: String = dbInstance.getReference(USER_DATA).child(uid).child("username").get().await()
                .getValue(String::class.java).toString()
            try {
                avatarUrl = storage.getReference("$USER_DATA/${uid}").downloadUrl.await()
            } catch (e: Exception) {
                Log.e("FB_ERROR_TAG", "${e.printStackTrace()}")
            }
            user = UserModel(userName, avatarUrl, uid)

        } catch (e: Exception) {
            Log.e("FB_ERROR_TAG", "${e.printStackTrace()}")
        }
        return user
    }

    override fun getCurrentUser(): UserModel? {
        val sharedPreferences = appContext.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)

        val id = sharedPreferences.getString(sharedPreferencesId, null)
        val userName = sharedPreferences.getString(sharedPreferencesUserName, null)
        val url = sharedPreferences.getString(sharedPreferencesUri, null)?.toUri()

        return if (id != null && userName != null && url != null)
            UserModel(userName, url, id) else
            null
    }

    override suspend fun getArticleModelComments(postId: Long): List<Comment> {
        val comments = mutableListOf<Comment>()
        dbInstance.getReference(POSTS).child("ArticleModel").child(postId.toString())
            .child(COMMENTS).get().await().children.forEach {
                val text = it.child(COMMENT).getValue(String::class.java)!!
                val date = it.child(DATE).getValue(Long::class.java)!!
                val id = it.child(ID).getValue(Long::class.java)!!
                val userDto = it.child(USER_ID).getValue(UserDto::class.java)!!
                val user = UserModel(userDto.name, userDto.avatarUrl!!.toUri(), userDto.id)
                val comment = Comment(id, text, getCommentLikes(it), listOf(), user, date)
                val subComments = getSubComments(it, comment)

                comment.subComments = subComments
                comments.add(comment)
            }
        return comments
    }

    override suspend fun getArticleModelLikes(postId: Long): List<UserModel> {
        var likes = listOf<UserModel>()
        dbInstance.getReference(POSTS).child(postId.toString()).get().await().children.forEach {
            likes = getCommentLikes(it)
        }
        return likes
    }

    override fun articleModelEventListener(
        articleModel: MutableLiveData<ContentWithLikesAndComments<ArticleModel>>,
        postId: Long,
    ) {
        val dbInstance = FirebaseDatabase.getInstance()
        try {
            dbInstance.getReference(POSTS).child("ArticleModel")
                .child(postId.toString()).child(ID)
                .setValue(articleModel.value!!.content.id.toString())
        } catch (e: Exception) {
            // value has already been set
        }
        try {
            dbInstance.getReference(POSTS).child("ArticleModel")
                .child(articleModel.value!!.content.id.toString())
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val comments: MutableList<Comment> = mutableListOf()
                        val likes: MutableList<UserModel> = mutableListOf()

                        snapshot.child(COMMENTS).children.forEach {
                            val id = it.child(ID).getValue(Long::class.java)
                            val text = it.child(COMMENT).getValue(String::class.java)
                            val commentLikes = mutableListOf<UserModel>()

                            it.child(LIKES).children.forEach { like ->
                                val _username = like.child(NAME).getValue(String::class.java)!!
                                val _url =
                                    like.child(AVATAR_URL).getValue(String::class.java)!!.toUri()
                                val _id = like.child(ID).getValue(String::class.java)!!
                                commentLikes.add(UserModel(_username, _url, _id))
                            }

                            val authorUsername =
                                it.child(USER_ID).child(NAME).getValue(String::class.java)!!
                            val authorUrl =
                                it.child(USER_ID).child(AVATAR_URL)
                                    .getValue(String::class.java)!!
                                    .toUri()
                            val authorId =
                                it.child(USER_ID).child(ID).getValue(String::class.java)!!
                            val author = UserModel(authorUsername, authorUrl, authorId)

                            val date = it.child(DATE).getValue(Long::class.java)!!

                            val comment = Comment(id!!, text!!, commentLikes, listOf(), author, date)
                            val subComments = getSubComments(it, comment)
                            comment.subComments = subComments
                            comments.add(comment)
                        }

                        snapshot.child(LIKES).children.forEach {
                            val _name = it.child(NAME).getValue(String::class.java)!!
                            val _url = it.child(AVATAR_URL).getValue(String::class.java)!!.toUri()
                            val _id = it.child(ID).getValue(String::class.java)!!
                            likes.add(UserModel(_name, _url, _id))
                        }

                        articleModel.postValue(
                            ContentWithLikesAndComments(
                                articleModel.value!!.content,
                                likes, comments)
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Firebase Error", error.message)
                    }
                })
        } catch (e: Exception) {
            Log.d("Firebase Error catch", e.message.toString())
        }
    }

    private fun getSubComments(snapshot: DataSnapshot, comment: Comment): List<SubComment> {
        val subComments = mutableListOf<SubComment>()
        snapshot.child(SUB_COMMENTS).children.forEach { subComment ->
            val subId = subComment.child(ID).getValue(Long::class.java)!!
            val text = subComment.child("text").getValue(String::class.java)!!
            val likes = getCommentLikes(subComment)
            val authorDto = subComment.child(USER_ID).getValue(UserDto::class.java)!!
            val author = UserModel(authorDto.name, authorDto.avatarUrl!!.toUri(), authorDto.id)
            val date = subComment.child(DATE).getValue(Long::class.java)!!
            subComments.add(SubComment(subId, comment, text, likes, author, date))
        }
        return subComments
    }

    private fun getCommentLikes(snapshot: DataSnapshot): List<UserModel> {
        val likes = mutableListOf<UserModel>()
        snapshot.child(LIKES).children.forEach { like ->
            val userDto = like.getValue(UserDto::class.java)!!
            likes.add(UserModel(userDto.name, userDto.avatarUrl!!.toUri(), userDto.id))
        }
        return likes
    }

    private suspend fun getUsernameById(uid: String): String =
        try {
            dbInstance.getReference(USER_DATA).child(uid).child(USERNAME).get().await()
                .getValue(String::class.java)!!
        } catch (e: Exception) {
            "DELETED USER"
        }

    private suspend fun getLastCommentId(source: String, postId: Int): Long {
        return try {
            dbInstance.getReference(POSTS).child(source).child(postId.toString())
                .child(COMMENTS).get()
                .await().children.last().key!!.toLong()
        } catch (e: java.lang.Exception) {
            1
        }
    }

    private suspend fun getLastSubCommentId(
        source: String,
        postId: Int,
        fatherCommentId: Long,
    ): Long {
        return try {
            dbInstance.getReference(POSTS).child(source).child(postId.toString())
                .child(COMMENTS)
                .child(fatherCommentId.toString()).child(SUB_COMMENTS)
                .get()
                .await().children.last().key!!.toLong()
        } catch (e: java.lang.Exception) {
            1
        }
    }

    suspend fun getLikeCount(source: String, postId: Int): Long {
        return dbInstance.getReference(POSTS).child(source).child(postId.toString())
            .child(LIKES).get()
            .await().childrenCount
    }

    private suspend fun checkIfHasLike(source: String, postId: Int): Boolean {
        return dbInstance.getReference(POSTS).child(source).child(postId.toString())
            .child(LIKES).get()
            .await()
            .hasChild(authenticator.uid!!)
    }

    private suspend fun checkIfHasCommentLike(
        source: String,
        postId: Int,
        commentId: Long,
    ): Boolean {
        return dbInstance.getReference(POSTS).child(source).child(postId.toString())
            .child(COMMENTS)
            .child(commentId.toString()).child(LIKES).get().await()
            .hasChild(authenticator.uid!!)
    }

    private suspend fun checkIfHasSubCommentLike(
        source: String, postId: Int,
        fatherCommentId: Long, subCommentId: Long,
    ): Boolean {
        return dbInstance.getReference(COMMENTS).child(source).child(postId.toString())
            .child(COMMENTS)
            .child(fatherCommentId.toString()).child(SUB_COMMENTS).child(subCommentId.toString())
            .child(LIKES).get().await()
            .hasChild(authenticator.uid!!)
    }

    companion object {
        private const val COMMENTS = "comments"
        private const val COMMENT = "comment"
        private const val POSTS = "posts"
        private const val LIKES = "likes"
        private const val SUB_COMMENTS = "subComments"
        private const val USER_ID = "userId"
        private const val USER_DATA = "user_data"
        private const val NAME = "name"
        private const val ID = "id"
        private const val DATE = "date"
        private const val AUTHOR = "author"
        private const val AVATAR_URL = "avatarUrl"
        private const val USERNAME = "username"
        private const val USER_POSTS = "user_posts"
        private const val TITLE = "title"
        private const val sharedPreferencesFileName = "currentUser"
        private const val sharedPreferencesUserName = "userName"
        private const val sharedPreferencesUri = "uri"
        private const val sharedPreferencesId = "uid"
    }
}
