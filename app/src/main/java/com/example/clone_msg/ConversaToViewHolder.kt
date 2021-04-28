package com.example.clone_msg

import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaFromRowMensagemBinding
import com.example.clone_msg.databinding.ConversaToRowMensagemBinding
import com.squareup.picasso.Picasso

class ConversaToViewHolder (private val binding : ConversaToRowMensagemBinding) : RecyclerView.ViewHolder(binding.root) {
    var texto : String = "Texto conversa!"
    var image : String = "Alguma Imagem!"

    fun bindto(conversa: Conversa){

        binding.campoTextoConversa.text = conversa.texto

        Picasso.get()
                .load(conversa.foto)
                .into(binding.UserImageViewConversa)
    }

}