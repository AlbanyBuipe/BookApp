package com.aitech.bookapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.aitech.bookapp.model.Author
import com.aitech.bookapp.model.Book
import com.aitech.bookapp.model.Constants.Global.BOOK_AUTHOR
import com.aitech.bookapp.model.Constants.Global.BOOK_DATE
import com.aitech.bookapp.model.Constants.Global.BOOK_TITLE
import com.aitech.bookapp.model.Constants.Global.DB_NAME
import com.aitech.bookapp.model.Constants.Global.DB_VERSION
import com.aitech.bookapp.model.Constants.Global.TABLE_ID
import com.aitech.bookapp.model.Constants.Global.TABLE_NAME
import com.aitech.bookapp.model.name

class BookDBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery =
            "CREATE TABLE $TABLE_NAME($TABLE_ID INTEGER PRIMARY KEY, " +
                    "$BOOK_TITLE TEXT, $BOOK_AUTHOR TEXT, $BOOK_DATE LONG);"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        onCreate(db)
    }

    /**
     *  CRUD CREATE - READ - UPDATE - DELETE
     */

    /**
     *  CREATE - Book Insertion Instruction
     */

    fun insertBook(book: Book) {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put(BOOK_TITLE, book.title.toUpperCase())
        values.put(BOOK_AUTHOR, book.author.name)
        values.put(BOOK_DATE, System.currentTimeMillis())

        val insert = db.insert(TABLE_NAME, null, values)
        Log.d("Book Insertion", "$insert: ${book.title} inserted successfully!")
        db.close()
    }

    /**
     *  READ - Book Retrieval Instruction
     */
    fun getBook(id: Int): Book {
        val db: SQLiteDatabase = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME,
            arrayOf(TABLE_ID, BOOK_TITLE, BOOK_AUTHOR, BOOK_DATE),
            "$TABLE_ID = ?", arrayOf(id.toString()),
            null, null, null, null)

        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndex(TABLE_ID))
        val title = cursor.getString(cursor.getColumnIndex(BOOK_TITLE))
        val name = cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)).split(" ")
        val firstName = name[0]
        val lastName = name[1]
        val author = Author(firstName, lastName)
        val date = cursor.getLong(cursor.getColumnIndex(BOOK_DATE))

        return Book(title, author, date, id)
    }

    /**
     *  Collective Book Retrieval Instruction
     */
    fun getBooks(): ArrayList<Book> {
        val db: SQLiteDatabase = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val list: ArrayList<Book> = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(TABLE_ID))
                val title = cursor.getString(cursor.getColumnIndex(BOOK_TITLE))
                val name = cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)).split(" ")
                val firstName = name[0]
                val lastName = name[1]
                val date = cursor.getLong(cursor.getColumnIndex(BOOK_DATE))
                val book = Book(title, Author(firstName, lastName), date, id)
                list.add(book)
            } while (cursor.moveToNext())
        }
        return list
    }

    /**
     *  Retrieve Sorted Books
     */
    fun getSortedBooks(method: String): ArrayList<Book> {
        val db: SQLiteDatabase = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $method ASC"
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        val list: ArrayList<Book> = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(TABLE_ID))
                val title = cursor.getString(cursor.getColumnIndex(BOOK_TITLE))
                val name = cursor.getString(cursor.getColumnIndex(BOOK_AUTHOR)).split(" ")
                val firstName = name[0]
                val lastName = name[1]
                val date = cursor.getLong(cursor.getColumnIndex(BOOK_DATE))
                val book = Book(title, Author(firstName, lastName), date, id)
                list.add(book)
            } while (cursor.moveToNext())
        }
        return list
    }

    /**
     *  COUNT - Books Counting Instruction
     */
    fun getBooksCount(): Int {
        val db: SQLiteDatabase = readableDatabase
        val countQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(countQuery, null)
        return cursor.count
    }

    /**
     *  UPDATE - Book Update Instruction
     */
    fun updateBook(book: Book): Int {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put(BOOK_TITLE, book.title.toUpperCase())
        values.put(BOOK_AUTHOR, book.author.name)
        values.put(BOOK_DATE, book.date)

        return db.update(TABLE_NAME, values, "$TABLE_ID = ?", arrayOf(book.id.toString()))
    }

    /**
     *  DELETE - Book Deletion Instruction
     */
    fun deleteBook(book: Book) {
        val db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, "$TABLE_ID = ?", arrayOf(book.id.toString()))
        db.close()
    }

}