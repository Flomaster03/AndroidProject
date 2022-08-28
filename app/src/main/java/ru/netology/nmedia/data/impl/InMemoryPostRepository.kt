package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {


    override val data = MutableLiveData(
        Post(
            id = 0L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fy",
            published = "20 августа 2022"
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should be not null"
        }
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = if (!currentPost.likedByMe) currentPost.likes + 1 else currentPost.likes - 1
        )
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should be not null"
        }
        val sharedPost = currentPost.copy(
            shares = currentPost.shares + 1
        )
        data.value = sharedPost
    }

}