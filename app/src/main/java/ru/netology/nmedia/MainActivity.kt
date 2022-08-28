package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)

        }

        binding.firstPost.likes.setOnClickListener {
            viewModel.onLikeClicked()
        }

        binding.firstPost.share.setOnClickListener {
            viewModel.onShareClicked()
        }

    }

    private fun ActivityMainBinding.render(post: Post) {
        firstPost.authorName.text = post.author
        firstPost.published.text = post.published
        firstPost.content.text = post.content
        firstPost.likes.setImageResource(getLikeIconResId(post.likedByMe))
        firstPost.itemLikes.text = countCheck(post.likes)
        firstPost.itemShare.text = countCheck(post.shares)
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24 else R.drawable.ic_like_24

    private fun countCheck(numberInput: Int): String {
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