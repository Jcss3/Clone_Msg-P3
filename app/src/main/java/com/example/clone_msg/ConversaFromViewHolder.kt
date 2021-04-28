package com.example.clone_msg

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaFromRowMensagemBinding
import com.example.clone_msg.databinding.UserRowNovaMensagemBinding
import com.squareup.picasso.Picasso

class ConversaFromViewHolder(private val binding : ConversaFromRowMensagemBinding) : RecyclerView.ViewHolder(binding.root) {
    var texto : String = "Texto conversa!"
    var image : String = "Alguma Imagem!"

    fun bindto(conversa: Conversa){

        binding.campoTextoConversa.text = conversa.texto

        Picasso.get()
                .load(conversa.foto)
                .into(binding.UserImageViewConversa)
    }

}