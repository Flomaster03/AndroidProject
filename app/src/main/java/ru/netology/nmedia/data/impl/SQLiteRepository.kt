package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class SQLiteRepository(private val dao: PostDao) : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should be not null"
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = dao.getAll()
        data = MutableLiveData(initialPosts)
    }

    override fun like(id: Long) {
        data.value = posts.map {
            if (it.id != id) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1
            )
        }
    }

    override fun share(id: Long) {
        data.value = posts.map {
            if (it.id != id) it
            else it.copy(
                shares = it.shares + 1
            )
        }
    }

    override fun delete(id: Long) {
        data.value = posts.filter { it.id != id }
    }

    fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }

    }

    fun likeById(id: Long) {
        dao.likeById(id)
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }

    }

    fun removeById(id: Long) {
        dao.removeById(id)
        data.value= posts.filter { it.id != id }
    }
}