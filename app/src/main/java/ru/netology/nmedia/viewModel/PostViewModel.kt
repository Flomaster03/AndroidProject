package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val shareEvent = SingleLiveEvent<Post>()

    val videoPlayEvent = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    val navigateToPostContentScreenEvent = SingleLiveEvent<String?>()


    fun onSaveButtonClick(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Новый автор",
            content = content,
            published = "05 сентября 2022"
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onCancelButtonClick() {
        currentPost.value = null
    }

    fun onAddButtonClicked() {
        navigateToPostContentScreenEvent.call()
    }

    override fun onVideoPlayButtonClicked(post: Post) {
        currentPost.value = post
        videoPlayEvent.value = post.videoLink
    }


    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        shareEvent.value = post
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }



}