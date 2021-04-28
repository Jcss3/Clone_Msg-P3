package com.example.clone_msg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.UserRowNovaMensagemBinding

class UserAdapter(private val users : ArrayList<User>, private val inflate: LayoutInflater) : RecyclerView.Adapter<UserViewHolder>() {

    // Criar objetos para serem exibidos na tela
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserRowNovaMensagemBinding.inflate(inflate, parent, false)
        return UserViewHolder(binding)
    }
    override fun getItemCount() = users.size

    // ja existe um objeto criado e quero exibir um widget na tela
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindto(users[position])
    }
}