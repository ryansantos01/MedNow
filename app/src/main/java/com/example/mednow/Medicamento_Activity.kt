package com.example.mednow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mednow.db.DatabaseHandler
import com.example.mednow.function.Mask
import com.example.mednow.function.PeriodClass
import com.example.mednow.model.Medicamento
import kotlinx.android.synthetic.main.activity_medicamento.*

class Medicamento_Activity : AppCompatActivity() {

    var medicamento: Medicamento? = null
    val databaseHandler1 = DatabaseHandler(this)//Não estava privado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamento)


        //Faz com que crie h e min na caixa de hora
caixamedhorainicial.addTextChangedListener(Mask.mask("##h : ##min",caixamedhorainicial))
        //Faz com que Crie barras ao digitar a data de nascimento
        etValidade.addTextChangedListener(Mask.mask("##/##/####", etValidade))

var unidadedosagem= arrayOf("ATOMIZAÇÃO(S)('ESPIRRADA')","CÁPSULA(S)","COMPRIMIDO(S)","COLHER DE CAFÉ-(2,5 ml)",
"COLHER DE CHÁ-(5,0 ml)", "COLHER DE SOBREMESA-(10 ml)","COLHER DE SOPA-(15 ml)","GOTA(S)","MICROGOTA(S)","Mg","Ml")
spinnerdosagem.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,unidadedosagem)
var unidadetotal = arrayOf("G","GOTAS","CÁPSULAS","COMPRIMIDOS","Mg", "Ml","UI")
spinnerunidadetotal.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unidadetotal)
var localdeaplicacao = arrayOf("ORAL","NASAL", "INJETADO","AURICULAR","OLHOS","PELE","ANAL")
spinnerlocaldeaplicacao.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, localdeaplicacao)
var embalagemmed= arrayOf("AMPOLA","FRASCO","BILÍSTER","BISNAGA","SERINGA","BOLSA")
spinnerembalagemmedicamento.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,embalagemmed)
var numerodevezesnodia= arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17",
"18","19","20","20","21","22","23","24")
spinnernumerodevezesnodia.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,numerodevezesnodia)

        if (intent.getStringExtra("modeMed")=="EditMed") {
            medicamento = databaseHandler1.getMedicamento(intent.getIntExtra("idMed", 0))
            caixamednomepaciente.setText(medicamento!!.nomepacienteMedicamento)
            caixamednomemedicamento.setText(medicamento!!.nomeMed)
            etValidade.setText(medicamento!!.dataValidadeMed)
            caixameddescricaomedicamento.setText(medicamento!!.descMed)
            caixamedqnttotalmedicamento.setText(medicamento!!.qtdTotalMed)
            spinnerunidadetotal.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unidadetotal).getPosition(medicamento!!.unidadeqtdtotalMed))
            spinnerlocaldeaplicacao.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, localdeaplicacao).getPosition(medicamento!!.tipoLocalMed))
            spinnerembalagemmedicamento.setSelection(ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,embalagemmed).getPosition(medicamento!!.embalagemMed))
            caixamedhorainicial.setText(medicamento!!.horaInicialMed)
            spinnernumerodevezesnodia.setSelection(ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,numerodevezesnodia).getPosition(medicamento!!.numVezesMed))
            caixamedqntdadosagem.setText(medicamento!!.dosagemMed)
            spinnerdosagem.setSelection(ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,unidadedosagem).getPosition(medicamento!!.unidadedosagemMed))

            btnDelMed.setOnClickListener {
                databaseHandler1.delMedicamento(medicamento!!.idMedicamento)
                finish()
            }
            }else {
            btnDelMed.visibility = View.GONE
            }


        btnSaveMed.setOnClickListener {
            if (testeDataMedicamento()) {
                if (intent.getStringExtra("modeMed") == "EditMed") {
                    medicamento = populateMedicamentoMed(medicamento)
                    databaseHandler1.updateMedicamento(medicamento!!)

                }else{
                    medicamento = populateMedicamentoMed(null)
                    databaseHandler1.addMedicamento(medicamento!!)//Comando está funcionado, o problema é que ele não coloca no Main_Medicameno_tela_e_registro
                    aposapertarsalvar()
                }
                finish()

            }
            else {
                Toast.makeText(this, "Dados não correspondem", Toast.LENGTH_SHORT).show()
            }
        }


        btnCancelMed.setOnClickListener {
            finish()
        }
    }
    private fun aposapertarsalvar(){
        val intent=Intent(this,Main_Medicamento_tela_e_registro::class.java)
        startActivity(intent)
    }

    private fun testeDataMedicamento(): Boolean{

        return caixamednomemedicamento.text.toString() != ""
        && etValidade.text.toString().length == 10//Esta função serve para limitar os caracteres para 10, pode tirar o mask já faz esta função.
        && PeriodClass().checkPeriod(etValidade.text.toString())

    }
    private fun populateMedicamentoMed(p0: Medicamento?): Medicamento {
        val medicamento = Medicamento()
        if (p0 != null) medicamento.idMedicamento = p0!!.idMedicamento
        medicamento.nomepacienteMedicamento = caixamednomepaciente.text.toString()
        medicamento.nomeMed = caixamednomemedicamento.text.toString()
        medicamento.dataValidadeMed = etValidade.text.toString()
        medicamento.descMed = caixameddescricaomedicamento.text.toString()
        medicamento.qtdTotalMed = caixamedqnttotalmedicamento.text.toString()
        medicamento.unidadeqtdtotalMed = spinnerunidadetotal.selectedItem.toString()
        medicamento.tipoLocalMed = spinnerlocaldeaplicacao.selectedItem.toString()
        medicamento.embalagemMed = spinnerembalagemmedicamento.selectedItem.toString()
        medicamento.horaInicialMed = caixamedhorainicial.text.toString()
        medicamento.numVezesMed = spinnernumerodevezesnodia.selectedItem.toString()
        medicamento.unidadedosagemMed = spinnerdosagem.selectedItem.toString()
        medicamento.dosagemMed = caixamedqntdadosagem.text.toString()
        return medicamento
    }
}