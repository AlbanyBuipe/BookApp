package com.aitech.bookapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.aitech.bookapp.R
import com.aitech.bookapp.adapter.BookAdapter
import com.aitech.bookapp.helper.BookDBHandler
import com.aitech.bookapp.helper.Helper
import com.aitech.bookapp.model.Constants.Global.SORT_METHODS
import kotlinx.android.synthetic.main.activity_book_list.*

class BookListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        val dbHandler = BookDBHandler(this)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        val books = dbHandler.getBooks()

        val adapter = BookAdapter(this, books)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId) {
            R.id.btnAddItem -> Helper(this).insertBookDialog()
            R.id.mnAuthorSort -> {
                val dbHandler = BookDBHandler(this)
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
                val books = dbHandler.getSortedBooks(SORT_METHODS[0])
                val adapter = BookAdapter(this, books)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            R.id.mnTitleSort -> {
                val dbHandler = BookDBHandler(this)
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
                val books = dbHandler.getSortedBooks(SORT_METHODS[1])
                val adapter = BookAdapter(this, books)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            R.id.mnDateSort -> {
                val dbHandler = BookDBHandler(this)
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
                val books = dbHandler.getSortedBooks(SORT_METHODS[2])
                val adapter = BookAdapter(this, books)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
