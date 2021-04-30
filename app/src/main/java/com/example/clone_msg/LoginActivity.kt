package com.example.clone_msg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.clone_msg.databinding.ActivityLoginBinding
import com.example.clone_msg.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //acesse a instância compartilhada do objeto FirebaseAuth
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            val email = binding.emailLoginScreen.text.toString()
            val password = binding.passwordLoginScreen.text.toString()
            login(email,password)
        }

        binding.backToRegister.setOnClickListener {
            // Voltando para tela de registrar!
            /*val intent = Intent(this,RegistroActivity::class.java)
            startActivity(intent)*/
            finish()
        }
    }
    /*public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }*/
    // Metodo para logar no app.
    private fun login(email:String,password:String){
        //verificar se falta digitar algo.
        if(email.isEmpty()||password.isEmpty()) {
            Toast.makeText(this,"Por favor digite seu email e/ou password!",Toast.LENGTH_SHORT).show()
            return
        }
        //login
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    //val user = auth.currentUser
                    //updateUI(user)

                    Log.d("Login","Indo para ultimasMensagensActivity")
                    val intent = Intent(this,ultimasMensagensActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

   /* private fun updateUI(user: FirebaseUser?) {

    }
    private fun reload() {

    }
    companion object {
        private const val TAG = "login"
    }*/
}