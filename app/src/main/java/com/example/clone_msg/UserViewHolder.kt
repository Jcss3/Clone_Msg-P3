package com.example.clone_msg

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.UserRowNovaMensagemBinding
import com.squareup.picasso.Picasso

class UserViewHolder (private val binding : UserRowNovaMensagemBinding) : RecyclerView.ViewHolder(binding.root) {

    var nome : String = ""
    var numero: String = ""
    var imageUrl:String = ""
    var uid:String=""

    init {
        // listening a linha toda do contato/recycler view
        binding.btnLigar.setOnClickListener {
            // Pegar o context para poder disparar o intent/activity
            val context = binding.userNameTextView.context
            val intentImplicito = Intent()
            intentImplicito.action = Intent.ACTION_DIAL
            intentImplicito.data = Uri.parse("tel:"+numero)

            // Verificar se existe alguma activity que consegue lidar com esse intent
            if(intentImplicito.resolveActivity(context.packageManager) != null){
                context.startActivity(intentImplicito)
            }else{
                Toast.makeText(context, "Nehum app resolve esse Intent!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.root.setOnClickListener {
            // Pegar o context para poder disparar o intent/activity
            // Pegar o context para poder disparar o intent/activity
            val context = binding.userNameTextView.context
            //Intent Explicito
            val intentExplicito = Intent(context, ConversaActivity::class.java)
            intentExplicito.putExtra("Username",binding.userNameTextView.text)
            intentExplicito.putExtra("Numero",binding.btnLigar.text)
            intentExplicito.putExtra("Image",imageUrl)
            intentExplicito.putExtra("uid",uid)

            // Verificar se existe alguma activity que consegue lidar com esse intent
            if(intentExplicito.resolveActivity(context.packageManager) != null){
                context.startActivity(intentExplicito)
            }else{
                Toast.makeText(context, "Nehum app resolve esse Intent!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun bindto(user: User){
        nome = user.username
        imageUrl = user.fotoUrl
        numero = user.numero
        uid = user.uid

        //binding.btnLigar.text = user.numero
        binding.userNameTextView.text = nome
        Picasso.get()
            .load(imageUrl)
            .into(binding.fotoImageView)
    }

}