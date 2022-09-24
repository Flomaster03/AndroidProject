package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.dto.Post

fun Cursor.toPost() = Post(
    id = getLong(getColumnIndexOrThrow(PostTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostTable.Column.AUTHER.columnName)),
    content = getString(getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)),
    published = getString(getColumnIndexOrThrow(PostTable.Column.PUBLISHED.columnName)),
    likedByMe = getInt(getColumnIndexOrThrow(PostTable.Column.LIKED_BY_ME.columnName)) != 0,
    likes = getInt(getColumnIndexOrThrow(PostTable.Column.LIKES.columnName)),
    shares = getInt(getColumnIndexOrThrow(PostTable.Column.SHARES.columnName)),
    videoLink = getString(getColumnIndexOrThrow(PostTable.Column.VIDEO_LINK.columnName))
)