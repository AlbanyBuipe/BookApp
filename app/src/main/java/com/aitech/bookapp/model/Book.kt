package com.aitech.bookapp.model

import java.text.DateFormat
import java.util.*

data class Book(var title: String, val author: Author, var date: Long = 0L, val id: Int = 0)

data class Author(var firstName: String, var lastName: String)

val Author.name: String get() = "$firstName $lastName"

val Book.formattedDate: String get() = DateFormat.getDateInstance().format(Date(date).time)