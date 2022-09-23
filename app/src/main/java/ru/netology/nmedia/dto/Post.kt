package ru.netology.nmedia.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 998,
    var shares: Int = 998,
    val likedByMe: Boolean = false,
    val videoLink: String = "https://youtu.be/lK7_j23TJ6g"
        )