package com.example.mednow

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mednow.adapter.Main_do_content_medicamento
import com.example.mednow.db.DatabaseHandler
import com.example.mednow.model.Medicamento
import kotlinx.android.synthetic.main.activitymedicamentotelaeregistro.*

class Main_Medicamento_tela_e_registro : AppCompatActivity() {

    var databaseHandler1 = DatabaseHandler(this )
    var searchRemMed: Boolean = false
    var searchStrRemMed: String = ""
    var medicamentoList = ArrayList <Medicamento>()
    var LinearLayoutMedicamento: LinearLayoutManager? = null
    var maindocontentmedicamento: Main_do_content_medicamento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymedicamentotelaeregistro)
fabMedicamento.setOnClickListener {
    val intent=Intent(this,Medicamento_Activity::class.java)
    startActivity(intent)
}
    fabVoltarMed.setOnClickListener {
        val intent = Intent(this,TelaIntermediariaDosCadastros::class.java)
        startActivity(intent)
    }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view_medicamento,menu)
        val searchMed = menu.findItem(R.id.search_medicamento)
        val searchViewMed = searchMed.actionView as SearchView
        searchViewMed.queryHint = getString(R.string.searchTitle)



        searchViewMed.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?) : Boolean{
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString().isNotEmpty()){
                    searchRemMed = true
                    searchStrRemMed = p0!!
                }
                else{
                    searchRemMed = false
                    searchStrRemMed = ""

                }
                initViewMedicamento()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



    override fun onResume() {
        super.onResume()
        initViewMedicamento()
    }

    private fun initViewMedicamento(){

        medicamentoList = if (!searchRemMed) databaseHandler1.getMedicamentoList()
        else databaseHandler1.searchMedicamento(searchStrRemMed)
        maindocontentmedicamento = Main_do_content_medicamento(medicamentoList, this, this::editMedicamento)
        LinearLayoutMedicamento = LinearLayoutManager(this)
        recyclerViewMedicamento.layoutManager=LinearLayoutMedicamento
        recyclerViewMedicamento.adapter=maindocontentmedicamento
    }


    private fun editMedicamento(id: Int) {
        val intent = Intent(this, Medicamento_Activity::class.java)
        intent.putExtra("modeMed", "EditMed")
        intent.putExtra("idMed", id)
        startActivity(intent)

    }
}