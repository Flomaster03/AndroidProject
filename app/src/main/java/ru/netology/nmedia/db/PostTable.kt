package ru.netology.nmedia.db

object PostTable {

    const val NAME = "posts"

    val DDL = """
        CREATE TABLE $NAME (
        
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHER.columnName} TEXT NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL, 
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
        ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
        ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.SHARES.columnName} INTEGER NOT NULL DEFAULT 0,	
        ${Column.VIDEO_LINK.columnName} TEXT NOT NULL
			
);
""".trimIndent()

    val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()

    enum class Column(val columnName: String) {

        ID("id"),
        AUTHER("auther"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKED_BY_ME("likedByMe"),
        LIKES("likes"),
        SHARES("shares"),
        VIDEO_LINK("videoLink")
    }
}