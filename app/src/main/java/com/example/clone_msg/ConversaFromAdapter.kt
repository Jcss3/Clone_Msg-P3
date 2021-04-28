package com.example.clone_msg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaFromRowMensagemBinding

class ConversaFromAdapter (private val conversas : ArrayList<Conversa>, private val inflate: LayoutInflater) : RecyclerView.Adapter<ConversaFromViewHolder>() {

    // Criar objetos para serem exibidos na tela
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversaFromViewHolder {
        val binding = ConversaFromRowMensagemBinding.inflate(inflate, parent, false)
        return ConversaFromViewHolder(binding)
    }
    override fun getItemCount() = conversas.size

    // ja existe um objeto criado e quero exibir um widget na tela
    override fun onBindViewHolder(holderFrom: ConversaFromViewHolder, position: Int) {
        holderFrom.bindto(conversas[position])
    }
}