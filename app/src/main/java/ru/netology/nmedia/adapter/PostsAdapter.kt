package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.dto.Post

class PostsAdapter(
    val interactionListener: PostInteractionListener
) :
    ListAdapter<Post, PostsAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(
        private val binding: PostListItemBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.optionsMenu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { MenuItem ->
                    when (MenuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likes.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                published.text = post.published
                content.text = post.content
                likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                itemLikes.text = countCheck(post.likes)
                itemShare.text = countCheck(post.shares)
                optionsMenu.setOnClickListener{popupMenu.show()}
            }
        }
    }

    private object diffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater,
            parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

private fun countCheck(numberInput: Int): String {
    return when (numberInput) {
        in 0..999 -> "$numberInput"
        in 1000..1099 -> "${numberInput / 1000}K"
        in 1100..9999 -> "${numberInput / 1000}.${(numberInput / 100) % 10}K"
        in 10000..999999 -> "${numberInput / 1000}K"
        else -> "${numberInput / 1000000}.${(numberInput / 100000) % 10}M"
    }

}