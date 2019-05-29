package com.aitech.bookapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aitech.bookapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnReadingList.setOnClickListener {
            startActivity(Intent(this, BookListActivity::class.java))
        }

        btnAddBook.setOnClickListener {
            startActivity(Intent(this, BookAdditionActivity::class.java))
        }

    }
}
