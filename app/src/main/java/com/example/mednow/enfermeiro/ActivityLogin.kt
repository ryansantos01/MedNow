package com.example.mednow.enfermeiro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.example.mednow.R
import com.example.mednow.TelaIntermediariaDosCadastros
import com.example.mednow.databinding.ActivityLoginBinding
import com.example.mednow.db.DatabaseHandler
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ActivityLogin  : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private val activity = this@ActivityLogin

    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    private lateinit var textViewLinkEsqueceusenha: AppCompatTextView
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }

    /**
     * Inicializar views
     */
    private fun initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView) as NestedScrollView
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword) as TextInputLayout
        textInputEditTextEmail = findViewById(R.id.EditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById(R.id.EditTextPassword) as TextInputEditText
        appCompatButtonLogin = findViewById(R.id.ButtonLogin) as AppCompatButton
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister) as AppCompatTextView
        textViewLinkEsqueceusenha = findViewById(R.id.textViewLinkEsqueceusenha) as AppCompatTextView

    }

    /**
     * Para iniciar os listeners
     */
    private fun initListeners() {

        appCompatButtonLogin!!.setOnClickListener(this)
        textViewLinkRegister!!.setOnClickListener(this)
        textViewLinkEsqueceusenha!!.setOnClickListener(this)
    }

    /**
     * Para iniciar os objetos
     */
    private fun initObjects() {

        databaseHelper = DatabaseHandler(this)
        inputValidation = InputValidation(activity)
    }

    /**
     * Este método implementado é ouvir o clique na visualização
     *
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.ButtonLogin -> verifyFromSQLite()

            R.id.textViewLinkRegister -> {
                val intentRegister = Intent(applicationContext, ActivityEnfermeiro::class.java)
                startActivity(intentRegister)
            }

            R.id.textViewLinkEsqueceusenha -> {
                val intentSenha = Intent(applicationContext, EsquecisenhaActivity::class.java)
                startActivity(intentSenha)
            }
        }
    }

    /**
     * Validação de login do Sqlite
     */
    private fun verifyFromSQLite() {

        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextEmail!!, textInputLayoutEmail!!, getString(
                    R.string.error_message_email
                )
            )
        ) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(
                textInputEditTextEmail!!, textInputLayoutEmail!!, getString(
                    R.string.error_message_email
                )
            )
        ) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextPassword!!, textInputLayoutPassword!!, getString(
                    R.string.error_message_password
                )
            )
        ) {
            return
        }

        if (databaseHelper!!.checkUser(
                textInputEditTextEmail!!.text.toString().trim { it <= ' ' },
                textInputEditTextPassword!!.text.toString().trim { it <= ' ' })
        ) {
            val accountsIntent = Intent(activity, TelaIntermediariaDosCadastros::class.java)
            accountsIntent.putExtra(
                "EMAIL",
                textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(accountsIntent)
        } else {

            //Messagem que indica que deu algum erro no cadastro
            Snackbar.make(
                nestedScrollView!!,
                getString(R.string.error_valid_email_password),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Usado para esvaziar/limpar todos os edittext de entrada
     */
    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
    }
}