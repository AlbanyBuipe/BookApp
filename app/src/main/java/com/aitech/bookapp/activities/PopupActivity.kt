package com.aitech.bookapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aitech.bookapp.R
import kotlinx.android.synthetic.main.popup.*

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup)

        btnSaveBookPopup.setOnClickListener {

        }

        btnCancelPopup.setOnClickListener {
            this.finish()
        }

    }

}