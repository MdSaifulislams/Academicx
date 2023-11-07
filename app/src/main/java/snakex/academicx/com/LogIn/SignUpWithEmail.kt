package snakex.academicx.com.LogIn

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import snakex.academicx.com.Home.Home
import snakex.academicx.com.Methods
import snakex.academicx.com.databinding.SignUpWithEmailBinding
import java.util.Objects


class SignUpWithEmail : Fragment() {

 private var binding : SignUpWithEmailBinding ? =null

    private var firebaseAuth : FirebaseAuth? = null
    private var firebaseUser : FirebaseUser? = null
    private  var databaseReference : DatabaseReference? = null
    private var storageReference : StorageReference? = null

   private  var UserId : String? = null
   private  var Name: String? = null
   private  var Email: String? = null
   private var Password: String? = null
   private var ProfilePhotoUrl:String? = "ProfilePhotoUrl"
   private  var ProfilePhotoUri : Uri? = null
   private  var UploadCompleteProfilePhoto = true


   override fun onCreateView(
      inflater : LayoutInflater, container : ViewGroup?,
      savedInstanceState : Bundle?
   ) : View? {
      binding = SignUpWithEmailBinding.inflate(layoutInflater, container, false)

      initialise()
      click()
      AddProfilePhoto()



      return binding!!.root
   }

   private fun click() {
//      CreateAccountButtonClick()

 var email = "mdsaifulislam908077@gmail.com"

//    firebaseAuth!!.sendSignInLinkToEmail(email,"saiful").addOnCompleteListener(
//       { task ->
//
//       }
//    )


      firebaseAuth!!.signInWithEmailLink(email,email)
         .addOnCompleteListener { task ->
            if (task.isSuccessful) {
               Toast.makeText(context, "sus", Toast.LENGTH_SHORT).show()

            } else {
               Toast.makeText(context, "ff", Toast.LENGTH_SHORT).show()
            }

         }
   }


   private fun CreateAccountButtonClick() {
      binding!!.CreateAccountButton.setOnClickListener { v ->
         takeDataInput()
         checkFullFillInput()
      }
   }


   private fun AddProfilePhoto() {
      binding!!.EditButton.setOnClickListener { view ->
         val intent = Intent(Intent.ACTION_GET_CONTENT)
         intent.type = "image/*"
         startActivityForResult(intent, 111)
      }
   }


   override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      if (resultCode == Activity.RESULT_OK) {
         if (data != null) {
            if (requestCode == 111) {
               ProfilePhotoUri = data.data
               binding!!.ProfilePhoto.setImageURI(ProfilePhotoUri)
            }
         }
      }
   }

//----------------


//---------------------------


   //----------------
   //---------------------------
   private fun takeDataInput() {
      Name = Objects.requireNonNull(binding!!.phoneNumber.getText()).toString().trim { it <= ' ' }
      Email = Objects.requireNonNull(binding!!.email.text).toString().trim { it <= ' ' }
      Password = Objects.requireNonNull(binding!!.password.text).toString().trim { it <= ' ' }
   }


   private fun checkFullFillInput() {
      if (Name == "") {
         binding!!.phoneNumber.setError("Name Required")
      } else if (Email == "") {
         binding!!.email.error = "Email Required"
      } else if (Password == "") {
         binding!!.password.error = "Password Required"
      } else {
         ShowProgressBar()
         CreateAccountVerified()
      }
   }


   private fun CreateAccountVerified() {
      firebaseAuth!!.createUserWithEmailAndPassword(Email!!, Password!!)
         .addOnSuccessListener { authResult : AuthResult? ->
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            initialise()
            getData()
            if (ProfilePhotoUri != null) {
               UploadCompleteProfilePhoto = false
               setProfilePhotoDataBase()
               CheckPhotoUpload()
            } else {
               SuccessComplete()
            }
         }
         .addOnFailureListener { e : Exception ->
            Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
            hideProgressBar()
         }

//
//      firebaseAuth!!.signInWithEmailLink("mdsaifulislam908077@gmail.com","mdsaifulislam908077@gmail.com")
//         .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//               Toast.makeText(context, "sus", Toast.LENGTH_SHORT).show()
//
//            } else {
//               Toast.makeText(context, "ff", Toast.LENGTH_SHORT).show()
//            }
//
//      }
   }


   private fun setProfilePhotoDataBase() {
      storageReference =
         FirebaseStorage.getInstance().getReference("user").child(UserId!!).child("ProfilePhoto")
      storageReference!!.putFile(ProfilePhotoUri!!)
         .addOnSuccessListener { taskSnapshot : UploadTask.TaskSnapshot? ->
            storageReference!!.downloadUrl.addOnSuccessListener { uri : Uri ->
               ProfilePhotoUrl = uri.toString()
               UploadCompleteProfilePhoto = true
               CheckPhotoUpload()
            }.addOnFailureListener { e : Exception ->
               Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
               hideProgressBar()
            }
         }.addOnFailureListener { e : Exception ->
            Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
            hideProgressBar()
         }
   }


   private fun CheckPhotoUpload() {
      if (!UploadCompleteProfilePhoto) {
      } else {
         SuccessComplete()
      }
   }

   private fun SuccessComplete() {
      val userData : MutableMap<String, Any> = HashMap()
      userData["UserId"] = UserId!!
      userData["ProfilePhotoUrl"] = ProfilePhotoUrl!!
      userData["CoverPhotoUrl"] = "CoverPhotoUrl"
      userData["Name"] = Name!!
      userData["userEmail"] = Email!!
      userData["userPassword"] = Password!!
      databaseReference!!.child(UserId!!).setValue(userData)
         .addOnCompleteListener { task : Task<Void?> ->
            if (task.isSuccessful) {
               hideProgressBar()
               Log.i("TAG", " 252 ")
               startActivity(Intent(context, Home::class.java))
               requireActivity().finish()
            } else {
               Methods.ShowAlertDialog(
                  context,
                  task.exception!!.localizedMessage,
                  "Warning !"
               )
               hideProgressBar()
               Toast.makeText(context, "us", Toast.LENGTH_SHORT).show()
            }
         }
   }

   private fun getData() {
      UserId = firebaseUser!!.uid
   }


//------------------------------------------------------------------

   //------------------------------------------------------------------
   private fun initialise() {
      firebaseAuth = FirebaseAuth.getInstance()
      firebaseUser = FirebaseAuth.getInstance().currentUser
      databaseReference = FirebaseDatabase.getInstance().getReference("user")
   }


   private fun ShowProgressBar() {
      Methods.progressbarShow(binding!!.progressbar, binding!!.CreateAccountButton)
   }

//-----------------------------------------------


   //-----------------------------------------------
   private fun hideProgressBar() {
      Methods.progressbarHide(binding!!.progressbar, binding!!.CreateAccountButton)
   }


}