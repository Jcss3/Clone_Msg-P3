package com.example.clone_msg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clone_msg.databinding.ActivityConversaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class ConversaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversaBinding
    private val mensagensList = arrayListOf<Mensagem>()
    private val viewModel : ConversaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ConversaActivity", "Chegando na tela Conversa activity")
        supportActionBar?.title = intent.getStringExtra("Username")

        // como os elementos vao ser organizados em uma lista vertical
        binding.conversaRecyclerview.layoutManager = LinearLayoutManager(this)
        // decorando elementos- adicionando uma linha separando os elementos
        binding.conversaRecyclerview.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        escutandoMensagens()

        binding.btnEnviarConversa.setOnClickListener {
            enviarMsg()
            //binding.conversaRecyclerview.adapter = MensagemAdapter(mensagensList,layoutInflater)
        }

        binding.conversaRecyclerview.adapter = MensagemAdapter(mensagensList,layoutInflater)

        /*// viewModel para obeservar a lista de mensagens pode usar sem internet - não funcionou
        viewModel.Mensagens(mensagensList)
        viewModel.conversa.observe(this,
        Observer {
            mensagensList = it.
        })*/
    }

    private fun escutandoMensagens() {
        val fromId = FirebaseAuth.getInstance().uid
        val toID = intent.getStringExtra("uid")
        val ref = FirebaseDatabase.getInstance().getReference("/user-mensagens/$fromId/$toID")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                val chatMensagem = snapshot.getValue(ChatMessage::class.java)
                if(chatMensagem != null){
                    Log.d("ConversaActivity", chatMensagem.texto)

                    // Eu envio a msg id 1 , Outro Contato enviou msg id 0
                    val texto = chatMensagem.texto

                    if(chatMensagem.fromid == FirebaseAuth.getInstance().uid){
                        // App Esta enviando a msg
                        val fotourl_Fromid = ultimasMensagensActivity.userLogado?.fotoUrl.toString()
                        val meuID = 1
                        mensagensList.add(Mensagem(meuID,texto,fotourl_Fromid))
                        Log.d("ConversaActivity", "$meuID ,$texto,$fotourl_Fromid")


                    }else{
                        // App recebe a msg
                        // foto do Toid
                        val meuID = 0
                        val fotourl_Toid = intent.getStringExtra("Image").toString()
                        mensagensList.add(Mensagem(meuID,texto,fotourl_Toid))
                        Log.d("ConversaActivity", "$meuID ,$texto,$fotourl_Toid")
                    }
                }
                // carregando msg ja enviadas
                //binding.conversaRecyclerview.adapter = MensagemAdapter(mensagensList,layoutInflater)
                binding.conversaRecyclerview.scrollToPosition(MensagemAdapter(mensagensList,layoutInflater).itemCount-1)
            }
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

        })
    }

    /*d:Int, msg:String,fotoUrl:String*/
    private fun enviarMsg(){
        //msg digitada
        val textoDigitado = binding.campoTextoDigitado.text.toString()
        // signed in uid/ meu id , quando for passar para lista do adapter usar 1 como o id
        val fromId = FirebaseAuth.getInstance().uid
        // get uid from userviewholder who will receive my msg
        val toID = intent.getStringExtra("uid")
        if(fromId == null || toID == null) return
        //val ref = FirebaseDatabase.getInstance().getReference("/mensagens").push()

        //Informação de quem enviou
        val ref = FirebaseDatabase.getInstance().getReference("/user-mensagens/$fromId/$toID").push()
        //Informação de quem recebeu
        val toref = FirebaseDatabase.getInstance().getReference("/user-mensagens/$toID/$fromId").push()

        val chatmsg = ChatMessage(ref.key!!,textoDigitado,fromId,toID,System.currentTimeMillis()/1000)
       // val chatmsg2 = ChatMessage(ref.key!!,textoDigitado,toID,fromId,System.currentTimeMillis()/1000)

        ref.setValue(chatmsg).addOnSuccessListener {
            Log.d("ConversaActivity","Salvando msg no FirebaseDatabse corretamente! ID: ${ref.key}")
            // limpando o campo de texto
            binding.campoTextoDigitado.text.clear()

            binding.conversaRecyclerview.scrollToPosition(MensagemAdapter(mensagensList,layoutInflater).itemCount - 1)
        }

        toref.setValue(chatmsg).addOnSuccessListener {
            Log.d("ConversaActivity","Salvando msg no FirebaseDatabse corretamente! ID: ${toref.key}")
        }


        //ultimas mensagens / quem enviou
        val ultimasMensagensRef = FirebaseDatabase.getInstance().getReference("/ultima-mensagem/$fromId/$toID")
        ultimasMensagensRef.setValue(chatmsg).addOnSuccessListener {
            Log.d("ConversaActivity","ultima msg enviada salva com sucesso!")
        }

        //quem recebeu
        val ultimasMensagensToRef = FirebaseDatabase.getInstance().getReference("/ultima-mensagem/$toID/$fromId")
        ultimasMensagensToRef.setValue(chatmsg).addOnSuccessListener {
            Log.d("ConversaActivity","ultima msg recebida salva com sucesso!")
        }
    }

}


