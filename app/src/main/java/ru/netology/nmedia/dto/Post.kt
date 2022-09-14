package ru.netology.nmedia.dto

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 998,
    var shares: Int = 998,
    val likedByMe: Boolean = false,
    val videoLink: String = "gfjvjh@h.com"
        )