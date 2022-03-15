package com.example.mednow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.banco_de_dados_estudo.model.Paciente
import com.example.mednow.db.DatabaseHandler
import com.example.mednow.function.CPFUtil
import com.example.mednow.function.Mask
import com.example.mednow.function.PeriodClass
import kotlinx.android.synthetic.main.activity_paciente.*


class Paciente_Activity : AppCompatActivity() {
    val databaseHandler1 = DatabaseHandler(this)
    var paciente: Paciente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        //Faz com que crie barras ao digitar o CEP
        caixacep.addTextChangedListener(Mask.mask("#####-###", caixacep))
        //Faz com que crie barras ao digitar o telefone
        caixatelefone.addTextChangedListener(Mask.mask("(##) #####-####", caixatelefone))
        //Faz com que Crie barras ao digitar a data de nascimento
        etBirth.addTextChangedListener(Mask.mask("##/##/####", etBirth))
        //Faz com que Crie barras ao digitar o RG
        caixarg.addTextChangedListener(Mask.mask("##.###.###-#", caixarg))
        //Faz com que Crie barras ao digitar o Cpf
        caixacpf.addTextChangedListener(Mask.mask("###.###.###-##", caixacpf))



        var generos = arrayOf("MASCULINO", "FEMININO")
        spinnersexo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generos)
        //Spinner estado
        var estados = arrayOf(
            "AC", "AL", "AM", "AP", "BA", "CE", "ES", "GO", "MA", "MG", "MS", "MT",
            "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"
        )
        spinnerestado.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)
        //Spinner saúde
        var planosdesaude = arrayOf("SUS", "ALLIANZ SAÚDE", "AMEPLAN SAÚDE", "AMIL FACIL",
            "AMIL SAÚDE", "BIOSAÚDE", "BIOVIDA SAÚDE", "BLUE MED SAÚDE", "BRADESCO SAÚDE", "CLASSES LABORIOSAS",
            "CRUZ AZUL SAÚDE", "GS SAÚDE", "GOLDEN CROSS", "HAPVIDA", "HEALTH SANTARIS",
            "INTERCLINICAS SAÚDE", "KIPP SAÚDE", "MEDICAL HEALTH", "MED TOUR SAÚDE", "NOTRE DAME INTERMÉDICA",
            "PLANSAÚDE", "PLENA SAÚDE", "PORTO SEGURO SAÚDE", "PREVENT SENIOR", "QSAÚDE", "SANTA HELENA SAÚDE",
            "SÃO CRISTÓVÃO SAÚDE", "SÃO MIGUEL SAÚDE", "SEGUROS UNIMED", "SOMPO SAÚDE",
            "SUL AMÉRICA SAÚDE", "TOTAL MEDCARE SAÚDE", "TRASMONTANO SAÚDE", "UNIHOSP SAÚDE",
            "UNIMED")
        spinnerplanodesaude.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, planosdesaude)
//Spinner relacionamento
        var relacionamentos = arrayOf("CASADO", "DIVORCIADO", "SEPARADO", "SOLTEIRO", "VIÚVO")
        spinnerestadocivil.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, relacionamentos)


        //Configuração dos botões na tela


        if (intent.getStringExtra("modePac") == "EditPac") {
            paciente = databaseHandler1.getPaciente(intent.getIntExtra("idPac", 0))
            caixanomecompleto.setText(paciente!!.nomePaciente)
            etBirth.setText(paciente!!.idadePaciente)
            spinnersexo.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generos).getPosition(paciente!!.sexoPaciente))
            caixaendereco.setText(paciente!!.enderecoPaciente)
            caixanumerodaresidencia.setText(paciente!!.numerodaresidenciaPaciente)
            caixacomplemento.setText(paciente!!.complementoPaciente)
            caixacidade.setText(paciente!!.cidadePaciente)
            spinnerestado.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados).getPosition(paciente!!.estadoPaciente))
            caixacep.setText(paciente!!.cepPaciente)
            spinnerplanodesaude.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, planosdesaude).getPosition(paciente!!.planodesaudePaciente))
            caixatelefone.setText(paciente!!.telefonePaciente)
            spinnerestadocivil.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, relacionamentos).getPosition(paciente!!.estadocivilPaciente))
            caixarg.setText(paciente!!.rgPaciente)
            caixacpf.setText(paciente!!.cpfPaciente)

//Deletar Pessoa
            btnDel.setOnClickListener {
                databaseHandler1.delPaciente(paciente!!.idPaciente)
                finish()
            }
        } else {
            btnDel.visibility = View.GONE
        }
        //Salvar Pessoa
        btnSave.setOnClickListener {

            if (testePac()) {
                if (intent.getStringExtra("modePac") == "EditPac") {
                        paciente = populatePaciente(paciente)
                        databaseHandler1.updatePaciente(paciente!!)
                        Toast.makeText(this, "CPF VÁLIDO", Toast.LENGTH_SHORT).show()
                }else{
                    paciente = populatePaciente(null)
                    databaseHandler1.addPaciente(paciente!!)
                    Toast.makeText(this, "CPF VÁLIDO", Toast.LENGTH_SHORT).show()
                    aposapertarsalvar()
                }
                finish()

            }
            else {
                Toast.makeText(this, "Dados não correspondem", Toast.LENGTH_SHORT).show()
            }

        }
            btnCancel.setOnClickListener {
                finish()
            }
    }
    private fun aposapertarsalvar(){
        val intent=Intent(this,Main_Paciente_tela_e_registro::class.java)
        startActivity(intent)
    }
    private fun testePac(): Boolean{

        return caixanomecompleto.text.toString() != ""
        && etBirth.text.toString().length == 10
        && PeriodClass().checkPeriod(etBirth.text.toString())
        && CPFUtil.myValidateCPF(caixacpf.text.toString())
    }
    private fun populatePaciente(p0: Paciente?): Paciente {
        val paciente = Paciente()
        if (p0 != null) paciente.idPaciente = p0!!.idPaciente
        paciente.nomePaciente = caixanomecompleto.text.toString()
        paciente.idadePaciente = etBirth.text.toString()
        paciente.sexoPaciente = spinnersexo.selectedItem.toString()
        paciente.enderecoPaciente=caixaendereco.text.toString()
        paciente.numerodaresidenciaPaciente=caixanumerodaresidencia.text.toString()
        paciente.complementoPaciente=caixacomplemento.text.toString()
        paciente.cidadePaciente=caixacidade.text.toString()
        paciente.estadoPaciente=spinnerestado.selectedItem.toString()
        paciente.cepPaciente=caixacep.text.toString()
        paciente.planodesaudePaciente=spinnerplanodesaude.selectedItem.toString()
        paciente.telefonePaciente=caixatelefone.text.toString()
        paciente.estadocivilPaciente=spinnerestadocivil.selectedItem.toString()
        paciente.rgPaciente=caixarg.text.toString()
        paciente.cpfPaciente=caixacpf.text.toString()
        return paciente
    }
}

