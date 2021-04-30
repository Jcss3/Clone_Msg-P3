package com.example.clone_msg

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaToRowMensagemBinding
import com.example.clone_msg.databinding.UltimaMensagemRowBinding
import com.squareup.picasso.Picasso

class UltimaMensagemViewHolder(private val binding : UltimaMensagemRowBinding) : RecyclerView.ViewHolder(binding.root) {

    var username = ""
    var image = ""
    var texto = ""

    init {
        binding.root.setOnClickListener {
            //val row = it as ultimasMensagensActivity

            ultimasMensagensActivity.chatContatoUser?.username
            // Pegar o context para poder disparar o intent/activity
            // Pegar o context para poder disparar o intent/activity

            Log.d("UltimaMsgVH","Indo para tela de Conversa!")
            val context = binding.usernameTextview.context
            //Intent Explicito
            val intentExplicito = Intent(context, ConversaActivity::class.java)

            intentExplicito.putExtra("Username",ultimasMensagensActivity.chatContatoUser?.username)
            intentExplicito.putExtra("uid", ultimasMensagensActivity.chatContatoUser?.uid)
            intentExplicito.putExtra("Numero",ultimasMensagensActivity.chatContatoUser?.numero)
            intentExplicito.putExtra("Image",ultimasMensagensActivity.chatContatoUser?.fotoUrl)

            // Verificar se existe alguma activity que consegue lidar com esse intent
            if(intentExplicito.resolveActivity(context.packageManager) != null){
                context.startActivity(intentExplicito)
            }else{
                Toast.makeText(context, "Nehum app resolve esse Intent!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun bindto(mensagem: Mensagem){
        Log.d("UltimaMsgViewHolder", "Trocando Mensagens com: ${mensagem.username}")

        username = mensagem.username
        image = mensagem.foto
        texto = mensagem.texto

        // username
        binding.usernameTextview.text = mensagem.username.toString()
        Log.d("UltimaMsgViewHolder", mensagem.username)

        // mensagem enviada
        binding.mensagemTextview.text = mensagem.texto.toString()
        Log.d("UltimaMsgViewHolder", mensagem.texto)

        //imagem
        Picasso.get()
                .load(mensagem.foto)
                .into(binding.imageView)
        Log.d("UltimaMsgViewHolder", mensagem.foto)
    }
}