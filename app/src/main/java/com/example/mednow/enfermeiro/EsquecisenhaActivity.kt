package com.example.mednow.enfermeiro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.NestedScrollView
import com.example.mednow.R
import com.example.mednow.db.DatabaseHandler
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class EsquecisenhaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var appCompatButtonConfirm: AppCompatButton
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esquecisenha)
        supportActionBar!!.hide()
        initViews()
        initObjects()
        initListeners()
    }

    private fun initListeners() {
        appCompatButtonConfirm!!.setOnClickListener(this)
    }

    private fun initObjects() {

        databaseHelper = DatabaseHandler(this)
        inputValidation = InputValidation(this)
    }

    private fun initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView) as NestedScrollView
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail) as TextInputLayout
        textInputEditTextEmail = findViewById(R.id. textInputEditTextEmail) as TextInputEditText
        appCompatButtonConfirm = findViewById(R.id.appCompatButtonConfirm) as AppCompatButton

    }

    private fun verifyFromSQLite() {
        if (textInputEditTextEmail.text.toString().isEmpty()) {
            Toast.makeText(this, "Por favor, preencha seu email", Toast.LENGTH_SHORT).show()
            return
        }
        if (databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim { it <= ' ' })) {
            val accountsIntent = Intent(this, NovasenhaActivity::class.java)
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(accountsIntent)
        } else {
            Snackbar.make(nestedScrollView!!, getString(R.string.error_valid_email_password2), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null
    }

    override fun onClick(p0: View) {
        when(p0.id){R.id.appCompatButtonConfirm -> verifyFromSQLite()}
    }
}