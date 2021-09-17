package br.senai.sp.jandira.imcapp20_a.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.UsuarioDao
import br.senai.sp.jandira.imcapp20_a.dao.user_informations
import br.senai.sp.jandira.imcapp20_a.utils.converterBase64ParaBitmap
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_dash_board.*
import org.jetbrains.anko.toast

class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val dados = getSharedPreferences("tb_usuario", Context.MODE_PRIVATE)
        val id_user = dados.getString("id", "")
        val user_biometric_informations = user_informations(this).verify_peso(id_user!!)
        if (user_biometric_informations == false){
            criarAlertDialog()
        }

        preencherDashBoard()

        tv_logout.setOnClickListener {
            val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
            val editor = dados.edit()
            editor.putBoolean("lembrar", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun criarAlertDialog() {
        val dialog = AlertDialog.Builder(this)

        dialog.setMessage("\nVocê ainda não concluiu o seu cadastro. Vamos fazer isso agora?\n")
            .setCancelable(false)
            .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Continuar", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Não", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })

        val alert = dialog.create()

        alert.setTitle("\nPrecisamos de mais informações\n\n")

        alert.show()
    }

    private fun preencherDashBoard() {
        val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)

        tv_profile_name.text = dados.getString("nome", "")
        tv_profile_occupation.text = dados.getString("profissao", "")
        tv_weight.text = dados.getInt("peso", 0).toString()
        tv_age.text = dados.getString("idade", "")

        val imagemBase64 = dados.getString("foto", "")
        val imagemBitmap = converterBase64ParaBitmap(imagemBase64)

        iv_profile.setImageBitmap(imagemBitmap)

        if (dados.getInt("peso", 0) == 0) {
            criarAlertDialog()
        }

        // *** Colocar foto do Github no ImageView
//        val url = "https://avatars.githubusercontent.com/u/14265058?v=4"
//        Glide.with(this).load(url).into(iv_profile)

    }
}