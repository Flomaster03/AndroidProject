package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fy",
            published = "20 августа 2022"
        )
        binding.render(post)
        binding.firstPost.likes.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.firstPost.likes.setImageResource(getLikeIconResId(post.likedByMe))
            if (post.likedByMe) post.likes++ else post.likes--
            binding.firstPost.itemLikes.text = countCheck(post.likes)
        }

        binding.firstPost.share.setOnClickListener {
            post.shares++
            binding.firstPost.itemShare.text = countCheck(post.shares)
        }

    }

    private fun ActivityMainBinding.render(post: Post) {
        firstPost.authorName.text = post.author
        firstPost.published.text = post.published
        firstPost.content.text = post.content
        firstPost.likes.setImageResource(getLikeIconResId(post.likedByMe))
        firstPost.itemLikes.text = post.likes.toString()
        firstPost.itemShare.text = post.shares.toString()
    }
    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) = if(liked) R.drawable.ic_liked_24 else R.drawable.ic_like_24

    private fun countCheck(numberInput : Int): String {
        val number = when (numberInput) {
            in 0..999 -> "$numberInput"
            in 1000..1099 -> "${numberInput / 1000}K"
            in 1100..9999 -> "${numberInput / 1000}.${(numberInput / 100) % 10}K"
            in 10000..999999 -> "${numberInput / 1000}K"
            else -> "${numberInput / 1000000}.${(numberInput / 100000) % 10}M"
        }
        return number
    }
}