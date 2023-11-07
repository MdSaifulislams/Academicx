package snakex.academicx.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import snakex.academicx.com.Home.Home
import snakex.academicx.com.LogIn.LoginActivity
import snakex.academicx.com.databinding.StartBinding

class Start : AppCompatActivity() {

   private var  binding : StartBinding? =null
   var  launcherTime   = 1500
   var message : String ? = null
   var firebaseUser : FirebaseUser? = null


   override fun onCreate(savedInstanceState : Bundle?) {
      super.onCreate(savedInstanceState)
      binding = StartBinding.inflate(layoutInflater)
      setContentView(binding!!.root)

      initialise()





       object : CountDownTimer(launcherTime.toLong(),100){
         override fun onTick(p0: Long) {

         }

         override fun onFinish() {

           if(firebaseUser == null){

              startActivity(Intent(this@Start,LoginActivity::class.java))

           }else {

              startActivity(Intent(this@Start, Home::class.java))

           }

            binding!!.progressBar.visibility = View.INVISIBLE
            finish()
         }


      }.start()
   }

   private fun initialise() {
      firebaseUser =  FirebaseAuth.getInstance().getCurrentUser()
   }
}