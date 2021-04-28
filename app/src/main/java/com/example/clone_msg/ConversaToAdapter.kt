package com.example.clone_msg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaFromRowMensagemBinding
import com.example.clone_msg.databinding.ConversaToRowMensagemBinding

class ConversaToAdapter (private val conversas : ArrayList<Conversa>, private val inflate: LayoutInflater) : RecyclerView.Adapter<ConversaToViewHolder>() {

    // Criar objetos para serem exibidos na tela
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversaToViewHolder {
        val binding = ConversaToRowMensagemBinding.inflate(inflate, parent, false)
        return ConversaToViewHolder(binding)
    }
    override fun getItemCount() = conversas.size

    // ja existe um objeto criado e quero exibir um widget na tela
    override fun onBindViewHolder(holderFrom: ConversaToViewHolder, position: Int) {
        holderFrom.bindto(conversas[position])
    }
}