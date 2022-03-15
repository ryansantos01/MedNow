package com.example.mednow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_de_dados_estudo.model.Paciente
import com.example.mednow.R
import kotlinx.android.synthetic.main.content_paciente.view.*

class Main_do_content_paciente(pacienteList: ArrayList<Paciente>, internal var ctx: Context, private val callback:(Int)-> Unit):
    RecyclerView.Adapter<Main_do_content_paciente.ViewHolder>() {
    private var pacienteList = ArrayList<Paciente>()

    init {
        this.pacienteList = pacienteList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewPac = LayoutInflater.from(ctx).inflate(R.layout.content_paciente, parent, false)
        return ViewHolder(viewPac)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paciente = pacienteList[position]
        holder.nome.text = paciente.nomePaciente
        holder.nascimento.text = paciente.idadePaciente
        holder.sexo.text = paciente.sexoPaciente
        holder.endereco.text = paciente.enderecoPaciente
        holder.numerodaresidencia.text = paciente.numerodaresidenciaPaciente
        holder.complemento.text = paciente.complementoPaciente
        holder.cidade.text = paciente.cidadePaciente
        holder.estado.text = paciente.estadoPaciente
        holder.cep.text = paciente.cepPaciente
        holder.planodesaude.text = paciente.planodesaudePaciente
        holder.telefone.text = paciente.telefonePaciente
        holder.estadocivil.text = paciente.estadocivilPaciente
        holder.rg.text = paciente.rgPaciente
        holder.cpf.text = paciente.cpfPaciente


//Quando clicar no layout já feito volto para a tela de cadastro
        holder.lay.setOnClickListener {
            callback(paciente.idPaciente)
        }
//Aqui acaba o comando
        if (position == pacienteList.size - 1) holder.endLine.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return pacienteList.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lay: LinearLayout = view.layContentPaciente
        var nome: TextView = view.tvContentNome
        var nascimento: TextView = view.tvContentNascimento
        var sexo: TextView = view.tvContentSexo
        var endereco: TextView = view.tvContentEndereco
        var numerodaresidencia: TextView = view.tvContentNumerodaresidencia
        var complemento: TextView = view.tvContentComplemento
        var cidade: TextView = view.tvContentCidade
        var estado: TextView = view.tvContentEstado
        var cep: TextView = view.tvContentCep
        var planodesaude: TextView = view.tvContentPlanodesaude
        var telefone: TextView = view.tvContentTelefone
        var estadocivil: TextView = view.tvContentEstadocivil
        var rg: TextView = view.tvContentRg
        var cpf: TextView = view.tvContentCpf
        var endLine: LinearLayout = view.endLine
    }
}
