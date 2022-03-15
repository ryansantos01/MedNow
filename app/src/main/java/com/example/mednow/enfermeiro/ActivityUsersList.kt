package com.example.mednow.enfermeiro

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mednow.R
import com.example.mednow.db.DatabaseHandler
import com.example.mednow.model.Enfermeiro

class ActivityUsersList : AppCompatActivity() {
    private val activity = this@ActivityUsersList
    private lateinit var textViewName: AppCompatTextView
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var listEnfermeiros: MutableList<Enfermeiro>
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        supportActionBar!!.title = ""
        initViews()
        initObjects()

    }

    /**
     * Para iniciar as views
     */
    private fun initViews() {
        textViewName = findViewById(R.id.textViewName) as AppCompatTextView
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers) as RecyclerView
    }

    /**
     * Para iniciar os objetos
     */
    private fun initObjects() {
        listEnfermeiros = ArrayList<Enfermeiro>()
        usersRecyclerAdapter = UsersRecyclerAdapter(listEnfermeiros)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = UsersRecyclerAdapter(listEnfermeiros)
        databaseHelper = DatabaseHandler(activity)

        val emailFromIntent = intent.getStringExtra("EMAIL")
        textViewName.text = emailFromIntent

        var getDataFromSQLite = GetDataFromSQLite()
        getDataFromSQLite.execute()
    }

    /**
     * classe para buscar todos os registros de usuário do SQLite
     */
    public final inner class GetDataFromSQLite : AsyncTask<Void, Void, ArrayList<Enfermeiro>>() {

        override fun doInBackground(vararg p0: Void?): ArrayList<Enfermeiro> {
            return databaseHelper.getAllUser()
        }

        override fun onPostExecute(result: ArrayList<Enfermeiro>?) {
            super.onPostExecute(result)
            listEnfermeiros.clear()
            listEnfermeiros.addAll(result!!)
        }

    }
}