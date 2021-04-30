package com.example.clone_msg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.clone_msg.databinding.ActivityUltimasMensagensBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter

class ultimasMensagensActivity : AppCompatActivity() {

    companion object{
        var userLogado : User ?= null
        var chatContatoUser : User? = null
    }

    private lateinit var binding : ActivityUltimasMensagensBinding
    private val ultimamensagenList = arrayListOf<Mensagem>()
    private val ultimaMensagemMap = HashMap<String,ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUltimasMensagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // como os elementos vao ser organizados em uma lista vertical
        binding.recycleviewUltimamensagem.layoutManager = LinearLayoutManager(this)
        // decorando elementos- adicionando uma linha separando os elementos
        binding.recycleviewUltimamensagem.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )


        escutarUltimasMensagens()

        getUserLogado()
        // verificar se o user esta logado
        verificarSeUserIsLoggedIn()
    }

    private fun refreshRecyclerViewMensagens() {
        //ultimamensagenList.clear()
        ultimaMensagemMap.values.forEach {
            var username = "Tester"
            var fotourl = "Teste.png"

            // determianndo qual uid é do contato que enviou/recebeu a msg
            val contatoID : String
            if(it.fromid == FirebaseAuth.getInstance().uid){
                contatoID = it.toid
            }else{
                contatoID = it.fromid
            }

            // pegando o valor do uid do dataase
            val ref = FirebaseDatabase.getInstance().getReference(("/users/$contatoID"))
            ref.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatContatoUser = snapshot.getValue(User::class.java) ?: return
                    username = chatContatoUser!!.username
                    fotourl = chatContatoUser!!.fotoUrl

                    // ultimo texto enviado
                    val ultima_mensagem = it.texto
                    Log.d("UltimasMensagens","$it")

                    // identificados do adapter para ultimamensagemviewholder = 2
                    val mensagem = Mensagem(2, ultima_mensagem,fotourl,username )

                    ultimamensagenList.add(mensagem)

                    Log.d("UltimasMensagens","RefreshRecyclerViewMensagens: ${ultimamensagenList.size}")

                    binding.recycleviewUltimamensagem.adapter = MensagemAdapter(ultimamensagenList,layoutInflater)
                    Log.d("UltimasMensagens", "Acionando o Adapter ultimasMensagens dentro do RefreshRecyclerViewMensagens com Sucesso!")
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        }
    }

    private fun escutarUltimasMensagens() {
        val fromID = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/ultima-mensagem/$fromID")

        ref.addChildEventListener(object:ChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                ultimamensagenList.clear()
                val chatmessage = snapshot.getValue(ChatMessage::class.java) ?: return
                Log.d("UltimasMensagens", "Acionando onChildAdd, $chatmessage")
                ultimaMensagemMap[snapshot.key!!] = chatmessage
                refreshRecyclerViewMensagens()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                ultimamensagenList.clear()
                val chatmessage = snapshot.getValue(ChatMessage::class.java) ?: return
                Log.d("UltimasMensagens", "Acionando onChildChanged, $chatmessage")
                ultimaMensagemMap[snapshot.key!!] = chatmessage
                refreshRecyclerViewMensagens()
            }

            override fun onCancelled(error: DatabaseError) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        })
    }

    private fun setDummyRows() {
        ultimamensagenList.add(Mensagem(2,"Testanto","teste.png","Tester"))
        ultimamensagenList.add(Mensagem(2,"Testanto","teste.png","Tester2"))
        ultimamensagenList.add(Mensagem(2,"Testanto","teste.png","Tester3"))
        ultimamensagenList.add(Mensagem(2,"Testanto","teste.png","Tester4"))
        ultimamensagenList.add(Mensagem(2,"Testanto","teste.png","Tester5"))

        binding.recycleviewUltimamensagem.adapter = MensagemAdapter(ultimamensagenList,layoutInflater)
    }

    private fun getUserLogado() {
        val uid  = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object:ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                userLogado = snapshot.getValue(User::class.java)
                Log.d("UltimasMensagens","${userLogado?.username}")
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
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
                val intent = Intent(this, RegistroActivity::class.java)
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
            val intent = Intent(this, RegistroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


}