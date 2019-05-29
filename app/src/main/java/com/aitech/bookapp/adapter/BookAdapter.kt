package com.aitech.bookapp.adapter

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aitech.bookapp.R
import com.aitech.bookapp.helper.BookDBHandler
import com.aitech.bookapp.helper.Helper
import com.aitech.bookapp.model.Book
import com.aitech.bookapp.model.formattedDate
import com.aitech.bookapp.model.name
import kotlinx.android.synthetic.main.popup.view.*
import kotlin.random.Random

class BookAdapter(private val context: Context, private var books: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.book_list, parent, false))

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(books[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val colors = arrayOf(
            Color.GRAY,
            Color.DKGRAY,
            Color.LTGRAY,
            Color.CYAN,
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW,
            Color.MAGENTA
        )

        private fun randColorCode() = Random.nextInt(colors.size)

        fun bindItem(book: Book) {
            val title: TextView = itemView.findViewById(R.id.txtTitle)
            val author: TextView = itemView.findViewById(R.id.txtAuthor)
            val date: TextView = itemView.findViewById(R.id.txtDate)
            val edit: ImageView = itemView.findViewById(R.id.imgEdit)
            val del: ImageView = itemView.findViewById(R.id.imgDelete)

            title.text = book.title
            title.setTextColor(colors[randColorCode()])
            author.text = book.author.name
            date.text = book.formattedDate

            val dbHandler = BookDBHandler(context)
            edit.setOnClickListener {
                editBook(book)
                notifyItemChanged(book.id, book)
                // Toast.makeText(context, "Edit clicked", Toast.LENGTH_SHORT).show() // Todo: delete this line
            }

            del.setOnClickListener {
                val progressDialog = ProgressDialog(context)
                progressDialog.setMessage("Deleting ${book.title}...")
                dbHandler.deleteBook(book)
                books.removeAt(book.id)
                notifyItemRemoved(adapterPosition)
                // startActivity(context, Intent(context, BookListActivity::class.java), Bundle()) // Todo: delete this line after everything is set and done
                progressDialog.cancel()
            }

        }

        private fun editBook(book: Book) {
            val view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            val txtTitle = view.txtTitlePopup
            val txtFirstName = view.txtFirstNamePopup
            val txtLastName = view.txtLastNamePopup

            val dialogBuilder = AlertDialog.Builder(context).setView(view)
            val dialog = dialogBuilder.create()
            dialog.show()

            txtTitle.setText(book.title)
            txtFirstName.setText(book.author.firstName)
            txtLastName.setText(book.author.lastName)

            view.btnSaveBookPopup.text = "Update Book"
            view.btnSaveBookPopup.setOnClickListener {
                val (title: String, firstName: String, lastName: String) = Helper(context)
                    .getBookParams(txtTitle, txtFirstName, txtLastName)

                if (title.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                    book.title = title.toUpperCase()
                    book.author.firstName = firstName
                    book.author.lastName = lastName
                    BookDBHandler(context).updateBook(book)
                    notifyItemChanged(adapterPosition, book)
                    dialog.dismiss()
                } else {
                    Helper(context).reportTextFieldError(txtTitle) // report title field error
                    Helper(context).reportTextFieldError(txtFirstName) // report first name field error
                    Helper(context).reportTextFieldError(txtLastName) // report last name field error
                }
            }

            view.btnCancelPopup.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

}