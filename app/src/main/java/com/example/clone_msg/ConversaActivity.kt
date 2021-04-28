package com.example.clone_msg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clone_msg.databinding.ActivityConversaBinding
import com.example.clone_msg.databinding.ActivityUltimasMensagensBinding

class ConversaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Conversa"



    }
}