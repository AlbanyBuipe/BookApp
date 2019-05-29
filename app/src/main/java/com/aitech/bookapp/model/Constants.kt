package com.aitech.bookapp.model

class Constants {
    companion object Global {

        const val DB_VERSION: Int = 1
        const val DB_NAME: String = "book.db"

        const val TABLE_NAME: String = "books"
        const val TABLE_ID: String = "id"
        const val BOOK_TITLE: String = "title"
        const val BOOK_AUTHOR: String = "author"
        const val BOOK_DATE: String = "date"
        val SORT_METHODS = listOf(BOOK_AUTHOR, BOOK_TITLE, BOOK_DATE)
        var SORT_TYPE: String = SORT_METHODS[0]
    }
}