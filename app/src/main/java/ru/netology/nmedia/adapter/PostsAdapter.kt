package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
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
                        R.id.edit -> {
                           listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likes.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onShareClicked(post) }
            binding.optionsMenu.setOnClickListener{popupMenu.show()}

            binding.videoContent.setOnClickListener { listener.onVideoPlayButtonClicked(post)}
            binding.videoPlay.setOnClickListener { listener.onVideoPlayButtonClicked(post)}

            binding.content.setOnClickListener { listener.onSinglePostClicked(post) }
            binding.authorName.setOnClickListener { listener.onSinglePostClicked(post) }
            binding.published.setOnClickListener { listener.onSinglePostClicked(post) }
            binding.avatar.setOnClickListener { listener.onSinglePostClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                published.text = post.published
                content.text = post.content
                likes.isChecked = post.likedByMe
                likes.text = countCheck(post.likes)
                share.text = countCheck(post.shares)
                videoVisibility.visibility =
                    if (post.videoLink.isBlank()) View.GONE else View.VISIBLE

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