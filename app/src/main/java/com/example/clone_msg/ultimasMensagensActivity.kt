package com.example.clone_msg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.clone_msg.databinding.ActivityMainBinding
import com.example.clone_msg.databinding.ActivityUltimasMensagensBinding
import com.google.firebase.auth.FirebaseAuth

class ultimasMensagensActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUltimasMensagensBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUltimasMensagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // verificar se o user esta logado
        verificarSeUserIsLoggedIn()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // switch para saber qual botao foi clicado
        when(item?.itemId){
            R.id.new_message ->{
                // desconectando
                //FirebaseAuth.getInstance().signOut()*/
                Log.d("UltimasMensagens","Indo para tela de Nova mensagem!")
                val intent = Intent(this, NovaMensagemActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_signOut ->{
                // desconectando
                FirebaseAuth.getInstance().signOut()
                Log.d("UltimasMensagens","Saindo da conta!")
                Log.d("UltimasMensagens","Indo para tela de Registrar!")
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun verificarSeUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            Log.d("UltimasMensagens","Não esta Logado na Conta!")
            Log.d("UltimasMensagens","Indo para tela de Registrar!")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


}