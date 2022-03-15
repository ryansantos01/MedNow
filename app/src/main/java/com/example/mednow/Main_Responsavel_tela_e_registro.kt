package com.example.mednow

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mednow.adapter.Main_do_content_responsavel
import com.example.mednow.db.DatabaseHandler
import com.example.mednow.model.Responsavel
import kotlinx.android.synthetic.main.activityresponsaveltelaeregistro.*

class Main_Responsavel_tela_e_registro : AppCompatActivity() {

    var databaseHandler1 = DatabaseHandler(this )
    var searchRemResp: Boolean = false
    var searchStrRemResp: String = ""
    var responsavelList = ArrayList <Responsavel>()
    var LinearLayoutResponsavel: LinearLayoutManager? = null
    var maindocontentresponsavel: Main_do_content_responsavel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityresponsaveltelaeregistro)

        fabResponsavel.setOnClickListener {
            val intent=Intent(this,Responsavel_Activity::class.java)
            startActivity(intent)
        }
        fabVoltarResp.setOnClickListener {
            val intent = Intent(this,TelaIntermediariaDosCadastros::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view_responsavel,menu)
        val searchResp = menu.findItem(R.id.search_responsavel)
        val searchViewResp = searchResp.actionView as SearchView
        searchViewResp.queryHint = getString(R.string.searchTitle)



        searchViewResp.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?) : Boolean{
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString().isNotEmpty()){
                    searchRemResp = true
                    searchStrRemResp = p0!!
                }
                else{
                    searchRemResp = false
                    searchStrRemResp = ""

                }
                initViewResponsavel()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



    override fun onResume() {
        super.onResume()
        initViewResponsavel()
    }

    private fun initViewResponsavel(){

        responsavelList = if (!searchRemResp) databaseHandler1.getResponsavelList()
        else databaseHandler1.searchResponsavel(searchStrRemResp)
        maindocontentresponsavel = Main_do_content_responsavel(responsavelList, this, this::editResponsavel)
        LinearLayoutResponsavel = LinearLayoutManager(this)
        recyclerViewResponsavel.layoutManager=LinearLayoutResponsavel
        recyclerViewResponsavel.adapter=maindocontentresponsavel
    }


    private fun editResponsavel(id: Int) {
        val intent = Intent(this, Responsavel_Activity::class.java)
        intent.putExtra("modeResp", "EditResp")
        intent.putExtra("idResp", id)
        startActivity(intent)

    }
}