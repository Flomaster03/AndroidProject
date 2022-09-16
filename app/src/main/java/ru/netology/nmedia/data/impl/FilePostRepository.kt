package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import java.util.prefs.Preferences
import kotlin.properties.Delegates

class FilePostRepository(private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private var nextID: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }


    private var posts
        get() = checkNotNull(data.value) {
            "Data value should be not null"
        }
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }


    override val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else {
            emptyList()
        }


        data = MutableLiveData(posts)
    }

    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1
            )
        }
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(
                shares = it.shares + 1
            )
        }
    }

    override fun delete(id: Long) {
        posts = posts.filter { it.id != id }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(
                id = ++nextID
            )
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {

        const val NEXT_ID_PREFS_KEY = "nextID"
        const val FILE_NAME = "posts.json"
    }
}