package com.aitech.bookapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aitech.bookapp.R
import com.aitech.bookapp.helper.Helper
import kotlinx.android.synthetic.main.book_add.*

class BookAdditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_add)

        btnSaveBook.setOnClickListener {
            val (title: String, firstName: String, lastName: String) = Helper(this)
                .getBookParams(txtTitle, txtFirstName, txtLastName)

            if (title.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                Helper(this).saveBook(title, firstName, lastName)
            } else {
                Helper(this).reportTextFieldError(txtTitle) // report title field error
                Helper(this).reportTextFieldError(txtFirstName) // report first name field error
                Helper(this).reportTextFieldError(txtLastName) // report last name field error
            }
        }

        btnCancel.setOnClickListener {
            this.finish()
        }

    }
}