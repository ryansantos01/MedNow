
    package com.example.mednow.adapter

    import android.content.Context
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.LinearLayout
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.mednow.R
    import com.example.mednow.model.Medicamento
    import kotlinx.android.synthetic.main.content_medicamento.view.*

    class Main_do_content_medicamento(medicamentoList: ArrayList<Medicamento>, internal var ctxMed: Context, private val callbackmedicamento: (Int) -> Unit) :
        RecyclerView.Adapter<Main_do_content_medicamento.ViewHolder>() {
        private var medicamentoList = ArrayList<Medicamento>()

        init {
            this.medicamentoList = medicamentoList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val viewMed = LayoutInflater.from(ctxMed).inflate(R.layout.content_medicamento, parent, false)
            return ViewHolder(viewMed)
        }

        override fun onBindViewHolder(holderMedicamento:ViewHolder, positionMedicamento: Int) {
            val medicamento = medicamentoList[positionMedicamento]
            holderMedicamento.nomepacienteMedicamento.text = medicamento.nomepacienteMedicamento
            holderMedicamento.nomeMedicamento.text = medicamento.nomeMed
            holderMedicamento.datadevalidadeMedicamento.text = medicamento.dataValidadeMed
            holderMedicamento.descMedicamento.text = medicamento.descMed
            holderMedicamento.qnttotalMedicamento.text = medicamento.qtdTotalMed
            holderMedicamento.unidadeqnttotalMedicamento.text = medicamento.unidadeqtdtotalMed
            holderMedicamento.localaplicacaoMedicamento.text = medicamento.tipoLocalMed
            holderMedicamento.embalagemMedicamento.text = medicamento.embalagemMed
            holderMedicamento.horainicialMedicamento.text = medicamento.horaInicialMed
            holderMedicamento.numvezesMedicamento.text = medicamento.numVezesMed
            holderMedicamento.dosagemMedicamento.text = medicamento.dosagemMed
            holderMedicamento.unidadedosagemMedicamento.text = medicamento.unidadedosagemMed

//Quando clicar no layout já feito volta para a tela de cadastro
            holderMedicamento.layMedicamento.setOnClickListener {
                callbackmedicamento(medicamento.idMedicamento)
            }
//Aqui acaba o comando
            if (positionMedicamento == medicamentoList.size - 1) holderMedicamento.endLineMedicamento.visibility = View.VISIBLE
        }

        override fun getItemCount(): Int {
            return medicamentoList.size
        }


        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var layMedicamento: LinearLayout =view.layContentMedicamento
            var nomepacienteMedicamento: TextView =view.tvContentNomePacienteMed
            var nomeMedicamento: TextView = view.tvContentnomeMed
            var datadevalidadeMedicamento: TextView = view.tvContentdatavalidade
            var descMedicamento: TextView = view.tvContentdescMed
            var qnttotalMedicamento: TextView = view.tvContentqnttotal
            var unidadeqnttotalMedicamento: TextView = view.tvContentUnidadetotalMed
            var localaplicacaoMedicamento: TextView = view.tvContentlocalaplicacao
            var embalagemMedicamento: TextView = view.tvContentembalagemMed
            var horainicialMedicamento: TextView = view.tvContenthorainicial
            var numvezesMedicamento: TextView = view.tvContentnumerodevezes
            var dosagemMedicamento: TextView = view.tvContentdosagemMed
            var unidadedosagemMedicamento: TextView = view.tvContentunidadedosagemMed
            var endLineMedicamento: LinearLayout = view.endLine_medicamento
}
}


