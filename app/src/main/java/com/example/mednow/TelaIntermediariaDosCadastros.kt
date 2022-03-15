package com.example.mednow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tela_intermediaria_dos_cadastros.*

class TelaIntermediariaDosCadastros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_intermediaria_dos_cadastros)
        btnPac.setOnClickListener {
            val intent = Intent(this, Main_Paciente_tela_e_registro::class.java)
            startActivity(intent)
        }

        btnMed.setOnClickListener {
            val intent = Intent(this, Main_Medicamento_tela_e_registro::class.java)
            startActivity(intent)
        }

        btnResp.setOnClickListener {
            val intent = Intent(this,Main_Responsavel_tela_e_registro::class.java)
            startActivity(intent)
        }

}
}