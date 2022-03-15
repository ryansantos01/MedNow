package com.example.mednow

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mednow.db.DatabaseHandler
import com.example.mednow.function.Mask
import com.example.mednow.model.Responsavel
import kotlinx.android.synthetic.main.activity_responsavel.*
import java.net.URLEncoder

class Responsavel_Activity : AppCompatActivity() {

    val databaseHandler1 = DatabaseHandler(this)
    var responsavel: Responsavel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_responsavel)

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS),111)
        }

        //Faz com que crie barras ao digitar o telefone do celular
        caixarespcelularresponsavel.addTextChangedListener(Mask.mask("(##) #####-####", caixarespcelularresponsavel))
        //Faz com que crie barras ao digitar o telefone do whatsapp
//        caixarespwhatsappresponsavel.addTextChangedListener(Mask.mask("(##) #####-####", caixarespwhatsappresponsavel))

        var tiporesponsavel = arrayOf("AMIGO(A)", "PARENTE","CONJUGÊ")
        spinnertiporesponsavel.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,tiporesponsavel)

        //Configuração dos botões na tela


        if (intent.getStringExtra("modeResp") == "EditResp") {
            responsavel = databaseHandler1.getResponsavel(intent.getIntExtra("idResp", 0))
            caixarespnomepaciente.setText(responsavel!!.nomepacienteResponsavel)
            caixarespnomeresponsavel.setText(responsavel!!.nomeResponsavel)
            caixarespemailresponsavel.setText(responsavel!!.emailResponsavel)
            caixarespcelularresponsavel.setText(responsavel!!.celResponsavel)
            caixarespwhatsappresponsavel.setText(responsavel!!.whatsResponsavel)
            spinnertiporesponsavel.setSelection(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiporesponsavel).getPosition(responsavel!!.tipoResponsavel))

            //Deletar Pessoa
            btnDelResp.setOnClickListener {
                databaseHandler1.delResponsavel(responsavel!!.idResponsavel)
                finish()
            }
        } else {
            btnDelResp.visibility = View.GONE
            deletartv.visibility = View.GONE
            btnEmerg.visibility = View.GONE
            emergenciaTv.visibility = View.GONE
        }

        btnEmerg.setOnClickListener {

            var sms=SmsManager.getDefault()
            sms.sendTextMessage(caixarespwhatsappresponsavel.text.toString(),"ME",caixamensagemEmergencia.text.toString(),null,null)
            Toast.makeText(this,"SMS enviado com Sucesso!", Toast.LENGTH_LONG).show()

            when {
                caixarespwhatsappresponsavel.text.isNullOrEmpty() -> {
                    showMessage(getString(R.string.please_enter_mobile_number))
                }
                caixarespwhatsappresponsavel.text?.length != 11 -> {
                    showMessage(getString(R.string.please_enter_valid_mobile_number))
                }
                else -> {
                    val url =
                        "https://api.whatsapp.com/send?phone=+55" + caixarespwhatsappresponsavel.text.toString() + "&text=" + URLEncoder.encode(
                            caixamensagemEmergencia.text.toString(),
                            "UTF-8"
                        )
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setPackage("com.whatsapp")
                        data = Uri.parse(url)
                    }
                    intent.resolveActivity(packageManager)?.let {
                        startActivity(intent)
                    }
                }
            }

            //parte de enviar sms

        }
        //Salvar Pessoa
        btnSaveResp.setOnClickListener {

            if (testeResp()) {
                if (intent.getStringExtra("modeResp") == "EditResp") {
                    responsavel = populateResponsavel(responsavel)
                    databaseHandler1.updateResponsavel(responsavel!!)
                }else{
                    responsavel = populateResponsavel(null)
                    databaseHandler1.addResponsavel(responsavel!!)
                    aposapertarsalvar()
                }
                finish()

            }
            else {
                Toast.makeText(this, "Dados não correspondem", Toast.LENGTH_SHORT).show()
            }

        }
        btnCancelResp.setOnClickListener {
            finish()
        }

    }
    private fun aposapertarsalvar(){
        val intent= Intent(this,Main_Responsavel_tela_e_registro::class.java)
        startActivity(intent)
    }
    private fun testeResp(): Boolean{

        return caixarespnomepaciente.text.toString() != ""
                && caixarespnomeresponsavel.text.toString() != ""

    }
    private fun populateResponsavel(p0: Responsavel?): Responsavel {
        val responsavel = Responsavel()
        if (p0 != null) responsavel.idResponsavel = p0!!.idResponsavel
        responsavel.nomepacienteResponsavel = caixarespnomepaciente.text.toString()
        responsavel.nomeResponsavel = caixarespnomeresponsavel.text.toString()
        responsavel.emailResponsavel = caixarespemailresponsavel.text.toString()
        responsavel.celResponsavel = caixarespcelularresponsavel.text.toString()
        responsavel.whatsResponsavel = caixarespwhatsappresponsavel.text.toString()
        responsavel.tipoResponsavel = spinnertiporesponsavel.selectedItem.toString()
        return responsavel
    }

    private var toast: Toast? = null

    fun showMessage(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    //parte da permissão para enviar sms
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            receiveMsg()

    }

    private fun receiveMsg(){
        var br = object: BroadcastReceiver(){

            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)){
                        caixarespwhatsappresponsavel.setText(sms.originatingAddress)
                        mensagemEmergencia.setText(sms.displayMessageBody)
                    }
                }

            }


        }

        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))


    }
}

