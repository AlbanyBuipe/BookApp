package com.aitech.bookapp.helper

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.aitech.bookapp.R
import com.aitech.bookapp.activities.BookListActivity
import com.aitech.bookapp.model.Author
import com.aitech.bookapp.model.Book
import kotlinx.android.synthetic.main.popup.view.*

class Helper(context: Context) : View(context) {

    /**
     *  This method in general, validates all occurrences of
     *  the three EditText (PlainText) fields namely
     *  title, first name and last name.
     *  It returns 3 corresponding string values
     */
    fun getBookParams(titleField: EditText, firstNameField: EditText, lastNameField: EditText):
            Triple<String, String, String> {
        val title: String = titleField.text.toString()
        val firstName: String = firstNameField.text.toString()
        val lastName: String = lastNameField.text.toString()
        return Triple(title, firstName, lastName)
    }

    /**
     *  This method takes title, first name and last name,
     *  creates a book object and saves it to the database
     *  using the BookDBHandler insertBook() method.
     */
    fun saveBook(title: String, firstName: String, lastName: String) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Saving ...")
        val book = Book(title, Author(firstName, lastName))
        BookDBHandler(context).insertBook(book)
        progressDialog.cancel()
        startActivity(context, Intent(context, BookListActivity::class.java), Bundle())
        // Todo: remove the intent line above and implement dialog.cancel() in the proper loci
    }

    /**
     *  Takes an EditText object and reports if the field is empty.
     */
    fun reportTextFieldError(txtField: EditText) {
        val title = txtField.text.toString()
        if (title.isEmpty()) {
            txtField.setBackgroundColor(Color.LTGRAY)
            txtField.setHintTextColor(Color.RED)
            Toast.makeText(context, "Fill empty fields!", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertBookDialog() {

        val view = LayoutInflater.from(context).inflate(R.layout.popup, null)

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context).setView(view)
        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()

        val btnSave = view.btnSaveBookPopup
        val btnCancel = view.btnCancelPopup

        btnSave.setOnClickListener {
            val (title: String, firstName: String, lastName: String) = Helper(context)
                .getBookParams(view.txtTitlePopup, view.txtFirstNamePopup, view.txtLastNamePopup)

            if (title.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                this.saveBook(title, firstName, lastName)
            } else {
                this.reportTextFieldError(view.txtTitlePopup) // report title field error
                this.reportTextFieldError(view.txtFirstNamePopup) // report first name field error
                this.reportTextFieldError(view.txtLastNamePopup) // report last name field error
            }
        }

        btnCancel.setOnClickListener {
            dialog.cancel()
        }
    }

}