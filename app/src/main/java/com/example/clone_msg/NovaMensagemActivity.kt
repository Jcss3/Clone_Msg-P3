package com.example.clone_msg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_msg.databinding.ActivityNovaMensagemBinding
import com.example.clone_msg.databinding.ActivityUltimasMensagensBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.GroupieViewHolder

class NovaMensagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovaMensagemBinding
    private val userslist = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaMensagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Selecione Contato"

        // como os elementos vao ser organizados em uma lista vertical
        binding.listaElementosRecyclerView.layoutManager = LinearLayoutManager(this)
        // decorando elementos- adicionando uma linha separando os elementos
        binding.listaElementosRecyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        getUsers()

    }

    private fun getUsers() {
        // criar um array
        //val users : MutableList<User> = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        // Read from the database
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    Log.d("NovaMensagemActivity", "User info: $it.toString()")
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        userslist.add(user)
                        Log.d("NovaMensagemActivity", "User add to list sucessfully: ${user.toString()}")
                        Log.d("NovaMensagemActivity", "Tamanho lista: ${userslist.size}")
                    }
                }
                finish()
                binding.listaElementosRecyclerView.adapter = UserAdapter(userslist,layoutInflater)
                Log.d("NovaMensagemActivity", "Acionando o Adapter com Sucesso!")
            }

        })

    }
}
