package com.example.mednow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_de_dados_estudo.model.Paciente
import com.example.mednow.adapter.Main_do_content_paciente
import com.example.mednow.db.DatabaseHandler
import kotlinx.android.synthetic.main.activitypacientetelaeregistro.*

class Main_Paciente_tela_e_registro : AppCompatActivity() {
    var pacienteList = ArrayList<Paciente>()
    var maindocontentpaciente: Main_do_content_paciente? = null
    var LinearLayoutPaciente: LinearLayoutManager? = null

    var databaseHandler1 = DatabaseHandler(this )
    var search: Boolean = false
    var searchStr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitypacientetelaeregistro)


fabVoltarPac.setOnClickListener{
   val intent = Intent(this,TelaIntermediariaDosCadastros::class.java)
    startActivity(intent)//   startActivity(Intent(this,Main_Medicamento_tela_e_registro::class.java))
}


        fabPaciente.setOnClickListener{
            val intent = Intent(this,Paciente_Activity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view_paciente,menu)
        val searchPaciente = menu.findItem(R.id.search_paciente)
        val searchView = searchPaciente.actionView as SearchView
        searchView.queryHint = getString(R.string.searchTitle)



        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?) : Boolean{
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString().isNotEmpty()){
                    search = true
                    searchStr = p0!!
                }
                else{
                    search = false
                    searchStr = ""

                }
                initViewPaciente()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



    override fun onResume() {
        super.onResume()
        initViewPaciente()
    }


    private fun initViewPaciente(){
        pacienteList = if (!search) databaseHandler1.getPacienteList()
        else databaseHandler1.searchPaciente(searchStr)
        maindocontentpaciente = Main_do_content_paciente(pacienteList, this, this::editPaciente)
        LinearLayoutPaciente = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutPaciente
        recyclerView.adapter = maindocontentpaciente
    }
    //Troca de activity para editar
    private fun editPaciente(id:Int){
        val intent = Intent(this,Paciente_Activity::class.java)
        intent.putExtra("modePac","EditPac")
        intent.putExtra("idPac",id)
        startActivity(intent)

    }

}