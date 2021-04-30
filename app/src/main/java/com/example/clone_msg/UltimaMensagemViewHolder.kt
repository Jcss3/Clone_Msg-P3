package com.example.clone_msg

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaToRowMensagemBinding
import com.example.clone_msg.databinding.UltimaMensagemRowBinding
import com.squareup.picasso.Picasso

class UltimaMensagemViewHolder(private val binding : UltimaMensagemRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindto(mensagem: Mensagem){

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