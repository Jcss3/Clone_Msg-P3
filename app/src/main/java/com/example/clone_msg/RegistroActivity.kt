package com.example.clone_msg

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clone_msg.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.net.URI
import java.util.*


class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityRegistroBinding
    private var foto : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //acesse a instância compartilhada do objeto FirebaseAuth
        // Initialize Firebase Auth
        auth = Firebase.auth

        //Botao para inserir foto
        binding.ImageViewFoto.setOnClickListener {
            Log.d("RegistroActivity","Inserindo Foto!")

            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            // Verificar se existe alguma activity que consegue lidar com esse intent
            if(chooserIntent.resolveActivity(packageManager) != null){
                startActivityForResult(chooserIntent, 0)
            }else{
                Toast.makeText(this, "Nenhum app resolve esse Intent!", Toast.LENGTH_SHORT).show()
            }

        }

        // Botoo para Criar conta.
        binding.btnRegister.setOnClickListener {
            Log.d("RegistroActivity","Registrando conta!")
            val email = binding.emailRegisterScreen.text.toString()
            val password = binding.passwordRegisterScreen.text.toString()
            registrar(email,password)
        }

        //Botao para ir para tela de login.
        binding.jaTemConta.setOnClickListener {
            Log.d("RegistroActivity","Indo para tela de Login!")
            // Iniciar a tela de login!
            val intentExplicito = Intent(this,LoginActivity::class.java)
            startActivity(intentExplicito)
        }

    }
    // retornando valor da foto selecionanda
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            // verificque qual foto foi selecionada
            Log.d("RegistroActivity","Foto foi selecionada!")

            val fotoSelecionada = data.data
            foto = fotoSelecionada
            //val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri) deprecated
            // Picasso lib para trabalhar com img
            Picasso.get()
                    .load(fotoSelecionada)
                    .into(binding.imageViewCircularFoto)
        }
    }

    // Ao inicializar sua atividade, verifique se o usuário está conectado no momento
   /* public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }*/

    //Crie uma conta enviando o endereço de e-mail e a senha do novo usuário para createUserWithEmailAndPassword.
    private fun registrar(email:String, password:String){

        if(email.isEmpty()||password.isEmpty()) {
            Log.d("RegistroActivity", "Erro ao Registrar!")
            Toast.makeText(this,"Por favor digite seu email e/ou password!",Toast.LENGTH_SHORT).show()
            return
        }

        //Cria um user no Firebase com email e senha.
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Registro", "createUserWithEmail:success")
                    /*val user = auth.currentUser
                    // atualiza o user
                    updateUI(user)*/

                    // guardar img no firebase
                    uploadImageToFirebaseStorage()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Registro", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }

    }

    private fun uploadImageToFirebaseStorage() {
        if(foto == null) return

        //O primeiro passo para acessar seu bucket do Cloud Storage é criar uma instância do FirebaseStorage:
        //val storage = Firebase.storage
        // Crie uma referência para fazer upload, download ou excluir um arquivo, além de acessar e atualizar os metadados dele
        // Create a storage reference from our app
        // sotrageRef = storage.reference

        val fileName = UUID.randomUUID().toString()
        val storageRef = Firebase.storage.reference.child("/images/$fileName")
        storageRef.putFile(foto!!)
            .addOnSuccessListener {
                Log.d("RegistroActivity", "Upload de Foto foi finalizado com sucesso! ${it.metadata?.path}")

                storageRef.downloadUrl.addOnSuccessListener {
                    // it is location/Uri of foto
                    Log.d("RegistroActivity", "Localizacao do Arquivo:  $it")

                    Log.d("RegistroActivity", "Iniciando upload de info no database!")
                    salveUserFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("RegistroActivity", "Erro ao dar Upload de foto!")
            }
    }

    private fun salveUserFirebaseDatabase(imageUrl:String) {
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        val user = User(
            uid,
            binding.usernameRegisterScreen.text.toString(),
            binding.emailRegisterScreen.text.toString(),
            binding.numberRegisterScreen.text.toString(),
            imageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegistroActivity","Info do user salvo no database!")

                Log.d("RegistroActivity","Indo para ultimasMensagensActivity")
                val intent = Intent(this,ultimasMensagensActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("RegistroActivity", "Erro ao tentar salvar info do user no database!")
            }
    }


  /*  private fun updateUI(user: FirebaseUser?) {

    }
    private fun reload() {

    }
    companion object {
        private const val TAG = "EmailPassword"
    }*/


}