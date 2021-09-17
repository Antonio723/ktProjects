package br.senai.sp.jandira.imcapp20_a.dao

import android.content.Context
import android.util.Log
import java.security.AccessControlContext

class user_informations(val context: Context) {
    val dbHelper = ImcDataBase.getDatabase(context)

    fun verify_peso( id_user: String) : Boolean{
        val db = dbHelper.readableDatabase

        val campos = arrayOf(
            "id",
            "peso",
            "nivel_atividade",
            "data_pesagem")

        val filtro = "id = ?"

        val argumentos = arrayOf(id_user)

        val cursor = db.query(
            "tb_biometria",
            campos,
            filtro,
            argumentos,
            null,
            null,
            null
        )

        val linhas = cursor.count

        Log.i("XPTO", "USUARIO ${cursor.count.toString()}")

        Log.i("XPTO", "ID USUARIO ${id_user}")

        return linhas > 0
    }
}