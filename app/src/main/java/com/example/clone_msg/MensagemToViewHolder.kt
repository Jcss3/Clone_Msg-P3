package com.example.clone_msg

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaToRowMensagemBinding
import com.squareup.picasso.Picasso

class MensagemToViewHolder (private val binding : ConversaToRowMensagemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindto(mensagem: Mensagem){

        binding.campoTextoConversa.text = mensagem.texto
        Log.d("MensagemTOViewHolder", mensagem.texto)

        Picasso.get()
                .load(mensagem.foto)
                .into(binding.UserImageViewConversa)
        Log.d("MensagemTOViewHolder", mensagem.foto)
    }

}