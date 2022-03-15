package com.example.mednow.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.banco_de_dados_estudo.model.Paciente
import com.example.mednow.model.Enfermeiro
import com.example.mednow.model.Medicamento
import com.example.mednow.model.Responsavel

class DatabaseHandler(ctx: Context): SQLiteOpenHelper(ctx, DB_NOME,null, DB_VERSAO) {

 override fun onCreate(p0: SQLiteDatabase?) {

val CREATE_TABLE_PACIENTE = "CREATE TABLE $TABELA_NOME($IDPACIENTE INTEGER PRIMARY KEY, $NOMEPACIENTE TEXT, $IDADEPACIENTE TEXT, " +
"$SEXOPACIENTE TEXT, $ENDERECOPACIENTE TEXT, $NUMERODARESIDENCIAPACIENTE TEXT," +
" $COMPLEMENTOPACIENTE TEXT, $CIDADEPACIENTE TEXT, $ESTADOPACIENTE TEXT, $CEPPACIENTE TEXT," +
"$PLANODESAUDEPACIENTE TEXT, $TELEFONEPACIENTE TEXT, $ESTADOCIVILPACIENTE TEXT," +
"$RGPACIENTE TEXT, $CPFPACIENTE TEXT);"
p0?.execSQL(CREATE_TABLE_PACIENTE)


val CREATE_TABLE_MEDICAMENTO = "CREATE TABLE $TABELA_NOME_MEDICAMENTO($IDMEDICAMENTO INTEGER PRIMARY KEY," +
" $NOMEPACIENTEMEDICAMENTO TEXT, $NOMEMED  TEXT,$DATAVALIDADEMED TEXT," +
" $DESCMED TEXT,  $QTDTOTALMED TEXT,  $UNIDADEQTDTOTALMED TEXT,  $TIPOLOCALMED TEXT," +
" $EMBALAGEMMED TEXT,  $HORAINICIALMED TEXT,  $NUMVEZESMED TEXT," +
" $DOSAGEMMED TEXT, $UNIDADEDOSAGEMMED TEXT, $IMAGEMMED TEXT );"
 p0?.execSQL(CREATE_TABLE_MEDICAMENTO)


val CREATE_TABLE_RESPONSAVEL = "CREATE TABLE $TABELA_NOME_RESPONSAVEL($IDRESPONSAVEL INTEGER PRIMARY KEY," +
" $NOMEPACIENTERESPONSAVEL TEXT, $NOMERESPONSAVEL  TEXT, $EMAILRESPONSAVEL TEXT, " +
        "$CELRESPONSAVEL TEXT, $WHATSRESPONSAVEL TEXT, $TIPORESPONSAVEL TEXT);"
 p0?.execSQL(CREATE_TABLE_RESPONSAVEL)

 val CREATE_TABLE_ENFERMEIRO = "CREATE TABLE $TABELA_NOME_ENFERMEIRO($IDENFERMEIRO INTEGER PRIMARY KEY," +
         " $NOMEENFERMEIRO TEXT, $EMAILENFERMEIRO  TEXT, $CORENENFERMEIRO TEXT, " +
         "$SENHAENFERMEIRO TEXT);"
 p0?.execSQL(CREATE_TABLE_ENFERMEIRO)

}


override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
val DROP_TABLE_PACIENTE = "DROP TABLE IF EXISTS $TABELA_NOME;"
 p0?.execSQL(DROP_TABLE_PACIENTE)
 onCreate(p0)

val DROP_TABLE_MEDICAMENTO = "DROP TABLE IF EXISTS $TABELA_NOME_MEDICAMENTO;"
p0?.execSQL(DROP_TABLE_MEDICAMENTO)
onCreate(p0)

 val DROP_TABLE_RESPONSAVEL = "DROP TABLE IF EXISTS $TABELA_NOME_RESPONSAVEL;"
 p0?.execSQL(DROP_TABLE_RESPONSAVEL)
 onCreate(p0)

 val DROP_TABLE_ENFERMEIRO = "DROP TABLE IF EXISTS $TABELA_NOME_ENFERMEIRO;"
 p0?.execSQL(DROP_TABLE_ENFERMEIRO)
 onCreate(p0)
}

 /**
  * Parte do Paciente
  */
fun addPaciente(paciente: Paciente) {
val p0 = writableDatabase
val values = ContentValues().apply {
put(NOMEPACIENTE, paciente.nomePaciente)
put(IDADEPACIENTE, paciente.idadePaciente)
put(SEXOPACIENTE, paciente.sexoPaciente)
put(ENDERECOPACIENTE, paciente.enderecoPaciente)
put(NUMERODARESIDENCIAPACIENTE, paciente.numerodaresidenciaPaciente)
put(COMPLEMENTOPACIENTE, paciente.complementoPaciente)
put(CIDADEPACIENTE, paciente.cidadePaciente)
put(ESTADOPACIENTE, paciente.estadoPaciente)
put(CEPPACIENTE, paciente.cepPaciente)
put(PLANODESAUDEPACIENTE, paciente.planodesaudePaciente)
put(TELEFONEPACIENTE, paciente.telefonePaciente)
put(ESTADOCIVILPACIENTE, paciente.estadocivilPaciente)
put(RGPACIENTE, paciente.rgPaciente)
put(CPFPACIENTE, paciente.cpfPaciente)
}
p0.insert(TABELA_NOME, null, values)
}

fun getPaciente(id: Int ): Paciente{
val p0 = readableDatabase
val  selectQuery = "SELECT * FROM $TABELA_NOME WHERE $IDPACIENTE= $id;"
val cursor = p0.rawQuery(selectQuery, null)
cursor?.moveToFirst()
val paciente = populatePaciente(cursor)
cursor.close()
return paciente
}

fun getPacienteList(): ArrayList<Paciente> {
val pacienteList = ArrayList<Paciente>()
val p0 = readableDatabase
val selectQuery = "SELECT * FROM $TABELA_NOME ORDER BY $NOMEPACIENTE;"
val cursor = p0.rawQuery(selectQuery, null)
if (cursor != null) {
if (cursor.moveToFirst()) {
do {
val person = populatePaciente(cursor)
pacienteList.add(person)
} while (cursor.moveToNext())
}
}
cursor.close()
return pacienteList
}

fun updatePaciente(paciente: Paciente){
val p0= writableDatabase
val values = ContentValues().apply {
put(NOMEPACIENTE, paciente.nomePaciente)
put(IDADEPACIENTE, paciente.idadePaciente)
put(SEXOPACIENTE, paciente.sexoPaciente)
put(ENDERECOPACIENTE,paciente.enderecoPaciente)
put(NUMERODARESIDENCIAPACIENTE, paciente.numerodaresidenciaPaciente)
put(COMPLEMENTOPACIENTE, paciente.complementoPaciente)
put(CIDADEPACIENTE, paciente.cidadePaciente)
put(ESTADOPACIENTE, paciente.estadoPaciente)
put(CEPPACIENTE, paciente.cepPaciente)
put(PLANODESAUDEPACIENTE, paciente.planodesaudePaciente)
put(TELEFONEPACIENTE, paciente.telefonePaciente)
put(ESTADOCIVILPACIENTE, paciente.estadocivilPaciente)
put(RGPACIENTE, paciente.rgPaciente)
put(CPFPACIENTE, paciente.cpfPaciente)
}
p0.update(TABELA_NOME, values,"$IDPACIENTE=?", arrayOf(paciente.idPaciente.toString()))
}

fun delPaciente(id: Int){
val p0= writableDatabase
p0.delete(TABELA_NOME,"$IDPACIENTE=?", arrayOf(id.toString()))
}

fun searchPaciente(str: String) : ArrayList<Paciente>{
val pacienteList = ArrayList<Paciente>()
val p0 = readableDatabase
val selectQuery = "SELECT * FROM $TABELA_NOME WHERE $NOMEPACIENTE LIKE '%$str%' OR $ENDERECOPACIENTE LIKE '%$str%' ORDER BY $NOMEPACIENTE;"
val cursor = p0.rawQuery(selectQuery, null)
if (cursor != null) {
if (cursor.moveToFirst()) {
do {
val paciente = populatePaciente(cursor)
pacienteList.add(paciente)
} while (cursor.moveToNext())
}
}
cursor.close()
return pacienteList
}

@SuppressLint("Range")
fun populatePaciente(cursor: Cursor):Paciente{
val paciente =Paciente()
paciente.idPaciente = cursor.getInt(cursor.getColumnIndex(IDPACIENTE))
paciente.nomePaciente = cursor.getString(cursor.getColumnIndex(NOMEPACIENTE))
paciente.idadePaciente = cursor.getString(cursor.getColumnIndex(IDADEPACIENTE))
paciente.sexoPaciente = cursor.getString(cursor.getColumnIndex(SEXOPACIENTE))
paciente.enderecoPaciente=cursor.getString(cursor.getColumnIndex(ENDERECOPACIENTE))
paciente.numerodaresidenciaPaciente=cursor.getString(cursor.getColumnIndex(NUMERODARESIDENCIAPACIENTE))
paciente.complementoPaciente=cursor.getString(cursor.getColumnIndex(COMPLEMENTOPACIENTE))
paciente.cidadePaciente= cursor.getString(cursor.getColumnIndex(CIDADEPACIENTE))
paciente.estadoPaciente=cursor.getString(cursor.getColumnIndex(ESTADOPACIENTE))
paciente.cepPaciente=cursor.getString(cursor.getColumnIndex(CEPPACIENTE))
paciente.planodesaudePaciente=cursor.getString(cursor.getColumnIndex(PLANODESAUDEPACIENTE))
paciente.telefonePaciente=cursor.getString(cursor.getColumnIndex(TELEFONEPACIENTE))
paciente.estadocivilPaciente=cursor.getString(cursor.getColumnIndex(ESTADOCIVILPACIENTE))
paciente.rgPaciente=cursor.getString(cursor.getColumnIndex(RGPACIENTE))
paciente.cpfPaciente=cursor.getString(cursor.getColumnIndex(CPFPACIENTE))
return paciente
}

 /**
  * Parte do Medicamento
  */

fun addMedicamento(medicamento: Medicamento) {
val p0 = writableDatabase
val values = ContentValues().apply {
put(NOMEPACIENTEMEDICAMENTO, medicamento.nomepacienteMedicamento)
put(NOMEMED, medicamento.nomeMed)
put(DATAVALIDADEMED, medicamento.dataValidadeMed)
put(DESCMED, medicamento.descMed)
put(QTDTOTALMED, medicamento.qtdTotalMed)
put(UNIDADEQTDTOTALMED, medicamento.unidadeqtdtotalMed)
put(TIPOLOCALMED, medicamento.tipoLocalMed)
put(EMBALAGEMMED, medicamento.embalagemMed)
put(HORAINICIALMED, medicamento.horaInicialMed)
put(NUMVEZESMED, medicamento.numVezesMed)
put(DOSAGEMMED, medicamento.dosagemMed)
put(UNIDADEDOSAGEMMED, medicamento.unidadedosagemMed)
//put(IMAGEMMED, medicamento.imagemMed)
}
p0.insert(DatabaseHandler.TABELA_NOME_MEDICAMENTO, null, values)
}

fun getMedicamento(id: Int ): Medicamento {
val p0 = readableDatabase
val  selectQuery = "SELECT * FROM $TABELA_NOME_MEDICAMENTO WHERE $IDMEDICAMENTO= $id;"
val cursor = p0.rawQuery(selectQuery, null)
cursor?.moveToFirst()
val medicamento = populateMedicamento(cursor)
cursor.close()
return medicamento
}


fun getMedicamentoList(): ArrayList<Medicamento> {
val medicamentoList = ArrayList<Medicamento>()
val p0 = readableDatabase
val selectQuery = "SELECT * FROM $TABELA_NOME_MEDICAMENTO ORDER BY $NOMEMED;"
val cursor = p0.rawQuery(selectQuery, null)
if (cursor != null) {
if (cursor.moveToFirst()) {
do {
val medicamento = populateMedicamento(cursor)
medicamentoList.add(medicamento)
} while (cursor.moveToNext())
}
}
cursor.close()
return medicamentoList
}


fun updateMedicamento(medicamento: Medicamento){
val p0= writableDatabase
val values = ContentValues().apply {
put(NOMEPACIENTEMEDICAMENTO, medicamento.nomepacienteMedicamento)
put(NOMEMED, medicamento.nomeMed)
put(DATAVALIDADEMED, medicamento.dataValidadeMed)
put(DESCMED, medicamento.descMed)
put(QTDTOTALMED, medicamento.qtdTotalMed)
put(UNIDADEQTDTOTALMED,medicamento.unidadeqtdtotalMed)
put(TIPOLOCALMED, medicamento.tipoLocalMed)
put(EMBALAGEMMED, medicamento.embalagemMed)
put(HORAINICIALMED, medicamento.horaInicialMed)
put(NUMVEZESMED, medicamento.numVezesMed)
put(DOSAGEMMED, medicamento.dosagemMed)
put(UNIDADEDOSAGEMMED, medicamento.unidadedosagemMed)
//put(DatabaseHandler1.IMAGEMMED, medicamento.unidadedosagemMed)
}
p0.update(TABELA_NOME_MEDICAMENTO, values,"$IDMEDICAMENTO=?", arrayOf(medicamento.idMedicamento.toString()))
}


fun delMedicamento(id: Int){
val p0= writableDatabase
p0.delete(TABELA_NOME_MEDICAMENTO,"$IDMEDICAMENTO=?", arrayOf(id.toString()))
}


fun searchMedicamento(str: String) : ArrayList<Medicamento>{
val medicamentoList = ArrayList<Medicamento>()
val p0=readableDatabase
val selectQuery = "SELECT * FROM $TABELA_NOME_MEDICAMENTO WHERE $NOMEPACIENTEMEDICAMENTO LIKE '%$str%' OR $NOMEMED LIKE '%$str%' ORDER BY $NOMEPACIENTEMEDICAMENTO;"
val cursor = p0.rawQuery(selectQuery, null)
if (cursor != null) {
if (cursor.moveToFirst()) {
do {
val medicamento = populateMedicamento(cursor)
medicamentoList.add(medicamento)
} while (cursor.moveToNext())
}
}
cursor.close()
return medicamentoList
}


@SuppressLint("Range")
fun populateMedicamento(cursor: Cursor):Medicamento{
val medicamento =Medicamento()
medicamento.idMedicamento = cursor.getInt(cursor.getColumnIndex(IDMEDICAMENTO))
medicamento.nomepacienteMedicamento = cursor.getString(cursor.getColumnIndex(NOMEPACIENTEMEDICAMENTO))
medicamento.nomeMed= cursor.getString(cursor.getColumnIndex(NOMEMED))
medicamento.dataValidadeMed=cursor.getString(cursor.getColumnIndex(DATAVALIDADEMED))
medicamento.descMed=cursor.getString(cursor.getColumnIndex(DESCMED))
medicamento.qtdTotalMed=cursor.getString(cursor.getColumnIndex(QTDTOTALMED))
medicamento.unidadeqtdtotalMed=cursor.getString(cursor.getColumnIndex(UNIDADEQTDTOTALMED))
medicamento.tipoLocalMed=cursor.getString(cursor.getColumnIndex(TIPOLOCALMED))
medicamento.embalagemMed=cursor.getString(cursor.getColumnIndex(EMBALAGEMMED))
medicamento.horaInicialMed=cursor.getString(cursor.getColumnIndex(HORAINICIALMED))
medicamento.numVezesMed=cursor.getString(cursor.getColumnIndex(NUMVEZESMED))
medicamento.dosagemMed=cursor.getString(cursor.getColumnIndex(DOSAGEMMED))
medicamento.unidadedosagemMed=cursor.getString(cursor.getColumnIndex(UNIDADEDOSAGEMMED))
//medicamento.imagemMed=cursor.getString(cursor.getColumnIndex(IMAGEMMED))
return medicamento
}

 /**
  * Parte do Responsável
  */

 fun addResponsavel(responsavel: Responsavel) {
  val p0 = writableDatabase
  val values = ContentValues().apply {
   put(NOMEPACIENTERESPONSAVEL, responsavel.nomepacienteResponsavel)
   put(NOMERESPONSAVEL, responsavel.nomeResponsavel)
   put(EMAILRESPONSAVEL, responsavel.emailResponsavel)
   put(CELRESPONSAVEL, responsavel.celResponsavel)
   put(WHATSRESPONSAVEL, responsavel.whatsResponsavel)
   put(TIPORESPONSAVEL, responsavel.tipoResponsavel)
  }
  p0.insert(DatabaseHandler.TABELA_NOME_RESPONSAVEL, null, values)
 }


 fun getResponsavel(id: Int ): Responsavel {
  val p0 = readableDatabase
  val  selectQuery = "SELECT * FROM $TABELA_NOME_RESPONSAVEL WHERE $IDRESPONSAVEL= $id;"
  val cursor = p0.rawQuery(selectQuery, null)
  cursor?.moveToFirst()
  val responsavel= populateResponsavel(cursor)
  cursor.close()
  return responsavel
 }


 fun getResponsavelList(): ArrayList<Responsavel> {
  val responsavelList = ArrayList<Responsavel>()
  val p0 = readableDatabase
  val selectQuery = "SELECT * FROM $TABELA_NOME_RESPONSAVEL ORDER BY $NOMERESPONSAVEL;"
  val cursor = p0.rawQuery(selectQuery, null)
  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     val responsavel = populateResponsavel(cursor)
     responsavelList.add(responsavel)
    } while (cursor.moveToNext())
   }
  }
  cursor.close()
  return responsavelList
 }


 fun updateResponsavel(responsavel: Responsavel){
  val p0= writableDatabase
  val values = ContentValues().apply {
   put(NOMEPACIENTERESPONSAVEL, responsavel.nomepacienteResponsavel)
   put(NOMERESPONSAVEL, responsavel.nomeResponsavel)
   put(EMAILRESPONSAVEL, responsavel.emailResponsavel)
   put(CELRESPONSAVEL, responsavel.celResponsavel)
   put(WHATSRESPONSAVEL, responsavel.whatsResponsavel)
   put(TIPORESPONSAVEL, responsavel.tipoResponsavel)
  }
  p0.update(TABELA_NOME_RESPONSAVEL, values,"$IDRESPONSAVEL=?", arrayOf(responsavel.idResponsavel.toString()))
 }


 fun delResponsavel(id: Int){
  val p0= writableDatabase
  p0.delete(TABELA_NOME_RESPONSAVEL,"$IDRESPONSAVEL=?", arrayOf(id.toString()))
 }

 fun searchResponsavel(str: String) : ArrayList<Responsavel>{
  val responsavelList = ArrayList<Responsavel>()
  val p0=readableDatabase
  val selectQuery = "SELECT * FROM $TABELA_NOME_RESPONSAVEL WHERE $NOMEPACIENTERESPONSAVEL LIKE '%$str%' OR $NOMERESPONSAVEL LIKE '%$str%' ORDER BY $NOMEPACIENTERESPONSAVEL;"
  val cursor = p0.rawQuery(selectQuery, null)
  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     val responsavel = populateResponsavel(cursor)
     responsavelList.add(responsavel)
    } while (cursor.moveToNext())
   }
  }
  cursor.close()
  return responsavelList
 }


 @SuppressLint("Range")
 fun populateResponsavel(cursor: Cursor):Responsavel{
  val responsavel =Responsavel()
  responsavel.idResponsavel = cursor.getInt(cursor.getColumnIndex(IDRESPONSAVEL))
  responsavel.nomepacienteResponsavel = cursor.getString(cursor.getColumnIndex(NOMEPACIENTERESPONSAVEL))
  responsavel.nomeResponsavel= cursor.getString(cursor.getColumnIndex(NOMERESPONSAVEL))
  responsavel.emailResponsavel=cursor.getString(cursor.getColumnIndex(EMAILRESPONSAVEL))
  responsavel.celResponsavel=cursor.getString(cursor.getColumnIndex(CELRESPONSAVEL))
  responsavel.whatsResponsavel=cursor.getString(cursor.getColumnIndex(WHATSRESPONSAVEL))
  responsavel.tipoResponsavel=cursor.getString(cursor.getColumnIndex(TIPORESPONSAVEL))
  return responsavel
 }

 /**
  * Parte do Enfermeiro
  * */

 fun getName(): Cursor? {
  val db = this.readableDatabase
  return db.rawQuery("SELECT * FROM " + TABELA_NOME_ENFERMEIRO, null)

 }
 // Adiciona os dados no banco de dados
 fun addUser(enfermeiro: Enfermeiro) {
  val db = this.writableDatabase
  val values = ContentValues()
  values.put(NOMEENFERMEIRO, enfermeiro.name)
  values.put(EMAILENFERMEIRO, enfermeiro.email)
  values.put(CORENENFERMEIRO, enfermeiro.coren)
  values.put(SENHAENFERMEIRO, enfermeiro.password)
  db.insert(TABELA_NOME_ENFERMEIRO, null, values)
  db.close()
 }

 fun getAllUser(): ArrayList<Enfermeiro> {
  val columns = arrayOf(IDENFERMEIRO, EMAILENFERMEIRO, NOMEENFERMEIRO, CORENENFERMEIRO, SENHAENFERMEIRO)
  val sortOrder = "$NOMEENFERMEIRO ASC"
  val userList = ArrayList<Enfermeiro>()
  val db = this.readableDatabase
  val cursor = db.query(
   TABELA_NOME_ENFERMEIRO, //Table to query
   columns,            //columns to return
   null,     //columns for the WHERE clause
   null,  //The values for the WHERE clause
   null,      //group the rows
   null,       //filter by row groups
   sortOrder)         //The sort order
  if (cursor.moveToFirst()) {
   do {
    val user = Enfermeiro(id = cursor.getString(cursor. getColumnIndexOrThrow(
     IDENFERMEIRO
    )).toInt(),
     name = cursor.getString(cursor.getColumnIndexOrThrow(NOMEENFERMEIRO)),
     email = cursor.getString(cursor.getColumnIndexOrThrow(EMAILENFERMEIRO)),
     password = cursor.getString(cursor.getColumnIndexOrThrow(SENHAENFERMEIRO)),
     coren = cursor.getString(cursor.getColumnIndexOrThrow(CORENENFERMEIRO)))
    userList.add(user)
   } while (cursor.moveToNext())
  }
  cursor.close()
  db.close()
  return userList
 }

 fun updateUser(enfermeiro: Enfermeiro) {
  val db = this.writableDatabase
  val values = ContentValues()
  values.put(NOMEENFERMEIRO, enfermeiro.name)
  values.put(EMAILENFERMEIRO, enfermeiro.email)
  values.put(CORENENFERMEIRO, enfermeiro.coren)
  values.put(SENHAENFERMEIRO, enfermeiro.password)
  db.update(
   TABELA_NOME_ENFERMEIRO, values, "$IDENFERMEIRO = ?",
   arrayOf(enfermeiro.id.toString()))
  db.close()
 }

 fun deleteUser(enfermeiro: Enfermeiro) {
  val db = this.writableDatabase
  db.delete(
   TABELA_NOME_ENFERMEIRO, "$IDENFERMEIRO = ?",
   arrayOf(enfermeiro.id.toString()))
  db.close()
 }

 fun updatePassword(email: String, password: String?) {
  val db = this.writableDatabase
  val values = ContentValues()
  values.put(SENHAENFERMEIRO, password)
  db.update(TABELA_NOME_ENFERMEIRO, values, "$EMAILENFERMEIRO = ?", arrayOf(email))
  db.close()
 }

 fun checkUser(email: String): Boolean {
  val columns = arrayOf(IDENFERMEIRO)
  val db = this.readableDatabase
  val selection = "$EMAILENFERMEIRO = ?"
  val selectionArgs = arrayOf(email)
  val cursor = db.query(
   TABELA_NOME_ENFERMEIRO, //Table to query
   columns,        //columns to return
   selection,      //columns for the WHERE clause
   selectionArgs,  //The values for the WHERE clause
   null,  //group the rows
   null,   //filter by row groups
   null)  //The sort order
  val cursorCount = cursor.count
  cursor.close()
  db.close()
  if (cursorCount > 0) {
   return true
  }
  return false
 }
 /**
  * Verifica se o usuario existe
  *
  * @param email
  * @param password
  * @return true/false
  */
 fun checkUser(email: String, password: String): Boolean {
  val columns = arrayOf(IDENFERMEIRO)
  val db = this.readableDatabase
  val selection = "$EMAILENFERMEIRO = ? AND $SENHAENFERMEIRO = ?"
  val selectionArgs = arrayOf(email, password)
  val cursor = db.query(
   TABELA_NOME_ENFERMEIRO, //Table to query
   columns, //columns to return
   selection, //columns for the WHERE clause
   selectionArgs, //The values for the WHERE clause
   null,  //group the rows
   null, //filter by row groups
   null) //The sort order
  val cursorCount = cursor.count
  cursor.close()
  db.close()
  if (cursorCount > 0)
   return true
  return false
 }


 //Tabela Paciente
 companion object {
  //Tabela Paciente
  private val DB_VERSAO = 1
  private val DB_NOME = "MED.NOW"
  private val TABELA_NOME = "Paciente"
  private val IDPACIENTE = "IdPaciente"
  private val NOMEPACIENTE = "NomePaciente"
  private val IDADEPACIENTE = "IdadePaciente"
  private val SEXOPACIENTE = "SexoPaciente"
  private val ENDERECOPACIENTE = "EnderecoPaciente"
  private val NUMERODARESIDENCIAPACIENTE = "NumerodaresidenciaPaciente"
  private val COMPLEMENTOPACIENTE = "ComplementoPaciente"
  private val CIDADEPACIENTE = "CidadePaciente"
  private val ESTADOPACIENTE = "EstadoPaciente"
  private val CEPPACIENTE = "CepPaciente"
  private val PLANODESAUDEPACIENTE = "PlanodesaudePaciente"
  private val TELEFONEPACIENTE = "TelefonePaciente"
  private val ESTADOCIVILPACIENTE = "EstadocivilPaciente"
  private val RGPACIENTE = "RgPaciente"
  private val CPFPACIENTE = "CpfPaciente"

  //Tabela Medicamentos
  private val TABELA_NOME_MEDICAMENTO="Medicamento"
  private val IDMEDICAMENTO="IdMedicamento"
  private val NOMEPACIENTEMEDICAMENTO="NomepacienteMedicamento"
  private val NOMEMED="NomeMed"
  private val DATAVALIDADEMED="DatavalidadeMed"
  private val DESCMED="DescMed"
  private val QTDTOTALMED="QnttotalMed"
  private val UNIDADEQTDTOTALMED="UnidadeMed"
  private val TIPOLOCALMED="TipolocalMed"
  private val EMBALAGEMMED="EmbalagemMed"
  private val HORAINICIALMED="HorainicialMed"
  private val NUMVEZESMED="NumerdodevezesMed"
  private val DOSAGEMMED="DosagemMed"
  private val UNIDADEDOSAGEMMED="UnidadedosagemMed"
  private val IMAGEMMED="ImagemMed"

  //Tabela Responsavel
  private val TABELA_NOME_RESPONSAVEL="Responsavel"
  private val IDRESPONSAVEL="IdResponsavel"
  private val NOMEPACIENTERESPONSAVEL="NomepacienteResponsavel"
  private val NOMERESPONSAVEL="NomeResponsavel"
  private val EMAILRESPONSAVEL="EmailResponsavel"
  private val CELRESPONSAVEL="CelResponsavel"
  private val WHATSRESPONSAVEL="WhatsResponsavel"
  private val TIPORESPONSAVEL="TipoResponsavel"

  //Tabela Enfermeiro
  private val TABELA_NOME_ENFERMEIRO = "enfermeiro"
  private val IDENFERMEIRO = "IdEnfermeiro"
  private val NOMEENFERMEIRO = "NomeEnfermeiro"
  private val EMAILENFERMEIRO = "EmailEnfermeiro"
  private val SENHAENFERMEIRO = "SenhaEnfermeiro"
  private val CORENENFERMEIRO = "CorenEnfermeiro"

 }
}
