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

class NovasenhaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textInputpass: TextInputEditText
    private lateinit var textInputConfirmPassword: TextInputEditText
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputConfirmPass: TextInputLayout
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHandler
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var appCompatButtonReset: AppCompatButton
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novasenha)
        supportActionBar!!.hide()
        initObjects()
        initViews()
        initListeners()

        val intent = intent
        email = intent.getStringExtra("EMAIL").toString()

    }

    private fun initObjects() {

        inputValidation = InputValidation(this)
        databaseHelper = DatabaseHandler(this)
    }

    private fun initViews() {
        textInputpass = findViewById(R.id.textInputpass) as TextInputEditText
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword) as TextInputEditText
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword) as TextInputLayout
        textInputConfirmPass = findViewById(R.id.textInputConfirmPass) as TextInputLayout
        nestedScrollView = findViewById(R.id.nestedScrollView) as NestedScrollView
        appCompatButtonReset = findViewById(R.id.appCompatButtonReset) as AppCompatButton

    }
    private fun initListeners() {
        appCompatButtonReset!!.setOnClickListener(this)
    }


    private fun updatePassword() {
        val value1 = textInputpass.text.toString().trim { it <= ' ' }
        val value2 = textInputConfirmPassword.text.toString().trim { it <= ' ' }
        if (value1.isEmpty() && value2.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
            return
        }
        if (!value1.contentEquals(value2)) {
            Toast.makeText(this, "Senhas não correspondentes", Toast.LENGTH_LONG).show()
            return
        }
        if (!databaseHelper.checkUser(email)) {
            Snackbar.make(nestedScrollView, "Email inválido", Snackbar.LENGTH_LONG).show()
            return
        } else {
            databaseHelper.updatePassword(email, value1)
            Toast.makeText(this, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show()
            emptyInputEditText()
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun emptyInputEditText() {
        textInputpass.setText("")
        textInputConfirmPassword.setText("")
    }

    override fun onClick(p0: View){

        when(p0.id){R.id.appCompatButtonReset-> updatePassword()}
    }

}