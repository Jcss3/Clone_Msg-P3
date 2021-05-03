package com.example.clone_msg

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConversaViewModel : ViewModel(){
    val conversa : MutableLiveData<ArrayList<Mensagem>> by lazy(){
        MutableLiveData<ArrayList<Mensagem>>()
    }

    fun Mensagens(mensagensLista: ArrayList<Mensagem>){
        var listasalva: ArrayList<Mensagem> ?= null

        mensagensLista.forEach {
            listasalva?.add(it)
        }

        conversa.postValue(listasalva)
    }

}