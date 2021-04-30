package com.example.clone_msg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ConversaFromRowMensagemBinding
import com.example.clone_msg.databinding.ConversaToRowMensagemBinding
import com.example.clone_msg.databinding.UltimaMensagemRowBinding

class MensagemAdapter (private val mensagems : ArrayList<Mensagem>, private val inflate: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return mensagems[position].id
    }
    // Criar objetos para serem exibidos na tela
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding = ConversaToRowMensagemBinding.inflate(inflate, parent, false)
            return MensagemToViewHolder(binding)
        } else if (viewType == 0) {
            val binding = ConversaFromRowMensagemBinding.inflate(inflate, parent, false)
            return MensagemFromViewHolder(binding)
        }else{
            val binding = UltimaMensagemRowBinding.inflate(inflate, parent, false)
            return UltimaMensagemViewHolder(binding)
        }
    }

    override fun getItemCount() = mensagems.size

    // ja existe um objeto criado e quero exibir um widget na tela
    override fun onBindViewHolder(holderFrom: RecyclerView.ViewHolder, position: Int) {
        if(holderFrom is MensagemToViewHolder){
            holderFrom.bindto(mensagems[position])
        }
        else if(holderFrom is MensagemFromViewHolder){
            holderFrom.bindto(mensagems[position])

        }else if(holderFrom is UltimaMensagemViewHolder){
            holderFrom.bindto(mensagems[position])
        }
    }
}