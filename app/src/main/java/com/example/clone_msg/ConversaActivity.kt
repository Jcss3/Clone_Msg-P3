package com.example.clone_msg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clone_msg.databinding.ActivityConversaBinding


class ConversaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversaBinding
    private val conversasList = arrayListOf<Conversa>()
    private val conversasList2 = arrayListOf<Conversa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra("Username")

        // como os elementos vao ser organizados em uma lista vertical
        binding.conversaRecyclerview.layoutManager = LinearLayoutManager(this)
        // decorando elementos- adicionando uma linha separando os elementos
        binding.conversaRecyclerview.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        //val adapter = GroupAdapter<GroupieViewHolder>()
        val user1 = Conversa("Ola Mundo Testtando","teste.png")
        val user2 = Conversa("Ola2 Mundo testadodaiubpkompafpjiwvqoei","teste.png")

        conversasList.add(user1)
        conversasList.add(user2)
        conversasList2.add(user1)
        conversasList2.add(user2)
        //val adapter = GroupieAdapter()

        binding.conversaRecyclerview.adapter = ConversaFromAdapter(conversasList,layoutInflater)

        binding.conversaRecyclerview.adapter = ConversaToAdapter(conversasList2,layoutInflater)

        /*val concatenated = ConcatAdapter(ConversaFromAdapter(conversasList,layoutInflater), ConversaToAdapter(conversasList,layoutInflater))
        binding.conversaRecyclerview.setAdapter(concatenated)*/

    }
}


