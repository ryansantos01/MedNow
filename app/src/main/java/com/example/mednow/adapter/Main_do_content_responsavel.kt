package com.example.mednow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mednow.R
import com.example.mednow.model.Responsavel
import kotlinx.android.synthetic.main.content_responsavel.view.*

class Main_do_content_responsavel(responsavelList: ArrayList<Responsavel>, internal var ctxResp: Context, private val callbackresponsavel: (Int) -> Unit) :
    RecyclerView.Adapter<Main_do_content_responsavel.ViewHolder>() {
    private var responsavelList = ArrayList<Responsavel>()

    init {
        this.responsavelList = responsavelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewResp = LayoutInflater.from(ctxResp).inflate(R.layout.content_responsavel, parent, false)
        return ViewHolder(viewResp)
    }

    override fun onBindViewHolder(holderResponsavel:ViewHolder, positionResponsavel: Int) {
        val responsavel = responsavelList[positionResponsavel]
        holderResponsavel.nomepacienteResponsavelresp.text = responsavel.nomepacienteResponsavel
        holderResponsavel.nomeResponsavelresp.text = responsavel.nomeResponsavel
        holderResponsavel.emailResponsavelresp.text = responsavel.emailResponsavel
        holderResponsavel.celResponsavelresp.text = responsavel.celResponsavel
        holderResponsavel.whatsappResponsavelresp.text = responsavel.whatsResponsavel
        holderResponsavel.tipoResponsavelresp.text = responsavel.tipoResponsavel

//Quando clicar no layout já feito volta para a tela de cadastro
        holderResponsavel.layResponsavel.setOnClickListener {
            callbackresponsavel(responsavel.idResponsavel)
        }
//Aqui acaba o comando
        if (positionResponsavel == responsavelList.size - 1) holderResponsavel.endLineResponsavel.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return responsavelList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layResponsavel: LinearLayout =view.layContentResponsavel
        var nomepacienteResponsavelresp: TextView =view.tvContentNomePacienteResp
        var nomeResponsavelresp: TextView = view.tvContentNomeResponsavelResp
        var emailResponsavelresp: TextView = view.tvContentEmailResponsavelResp
        var celResponsavelresp: TextView = view.tvContentCelularResponsavelResp
        var whatsappResponsavelresp: TextView = view.tvContentWhatsappResponsavelResp
        var tipoResponsavelresp: TextView = view.tvContentTipoResponsavelResp
        var endLineResponsavel: LinearLayout = view.endLine_responsavel
    }


}


