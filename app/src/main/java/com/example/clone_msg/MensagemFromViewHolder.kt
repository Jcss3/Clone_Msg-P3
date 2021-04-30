package com.example.clone_msg

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaFromRowMensagemBinding
import com.squareup.picasso.Picasso

class MensagemFromViewHolder(private val binding : ConversaFromRowMensagemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindto(mensagem: Mensagem){

        binding.campoTextoConversa.text = mensagem.texto
        Log.d("MensagemFromViewHolder", mensagem.texto)

        Picasso.get()
                .load(mensagem.foto)
                .into(binding.UserImageViewConversa)
        Log.d("MensagemFromViewHolder", mensagem.foto)
    }

}