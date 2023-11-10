package snakex.academicx.com.LogIn

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
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
import snakex.academicx.com.R
import snakex.academicx.com.databinding.SignUpWithEmailBinding
import java.io.File
import java.util.Objects


class SignUpWithEmail : Fragment() {

 private var binding : SignUpWithEmailBinding ? =null

    private var firebaseAuth : FirebaseAuth? = null
    private var firebaseUser : FirebaseUser? = null
    private  var databaseReference : DatabaseReference? = null
    private var storageReference : StorageReference? = null

   private  var Name: String? = null
   var RollNumber: String? = null
   var RegistrationNumber: String? = null
   var ClassName: String? = null
   var Section: String? = null
   var AcademicName: String? = null
   var PhoneNumber: String? = null

   private  var UserId : String? = null
   private  var Email: String? = null
   private var Password: String? = null
   private var ProfilePhotoUrl:String? = "ProfilePhotoUrl"
   private var CompromiseProfilePhotoUrl:String? = "CompromiseProfilePhotoUrl"
   private  var ProfilePhotoUri : Uri? = null
   private  var CompromiseProfilePhotoUri : Uri? = null
   private  var UploadCompletePhoto = true

   var contextt : SignUpWithEmail = this

   override fun onCreateView(
      inflater : LayoutInflater, container : ViewGroup?,
      savedInstanceState : Bundle?
   ) : View? {
      binding = SignUpWithEmailBinding.inflate(layoutInflater, container, false)

      getBundleData()
      initialise()
      click()
      AddProfilePhoto()



      return binding!!.root
   }

   private fun getBundleData() {
      val bundle = this.arguments

      Name = bundle!!.getString("Name")
      RollNumber = bundle.getString("RollNumber")
      RegistrationNumber = bundle.getString("RegistrationNumber")
      ClassName = bundle.getString("ClassName")
      Section = bundle.getString("Section")
      AcademicName = bundle.getString("AcademicName")
      PhoneNumber = bundle.getString("PhoneNumber")

   }

   private fun click() {
      CreateAccountButtonClick()

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

             var file = File(ProfilePhotoUri.toString())

               binding!!.ProfilePhoto.setImageURI(file.toUri())

//               val cl = Compressor(requireContext()).compressToBitmap(data.data!!.toFile())
//               binding!!.ProfilePhoto.setImageBitmap(cl)

//               Toast.makeText(context, ""+file.toString(), Toast.LENGTH_SHORT
//               ).show()



//               binding!!.ProfilePhoto.setImageURI(ss)
//               val fileUri = getFilePathFromUri(data.data, requireContext())
//               val compressedImageFile = Compressor.compress(requireContext(), File(fileUri.path)){
//                  quality(50) // combine with compressor constraint
//                  format(Bitmap.CompressFormat.JPEG)
            }
         }
      }
   }


//----------------



   private  fun takeDataInput() {
      Email = Objects.requireNonNull(binding!!.email.text).toString().trim { it <= ' ' }
      Password = Objects.requireNonNull(binding!!.password.text).toString().trim { it <= ' ' }

   }


   private fun checkFullFillInput() {
      if (Email!!.equals("")) {
         binding!!.emailLayout.helperText = "Email Required"
      } else if (Password!!.equals("")) {
         binding!!.passwordLayout.helperText = "Password Required"
      } else {
         ShowProgressBar()
         CreateAccountVerified()
      }
   }


   private fun CreateAccountVerified() {
      firebaseAuth!!.createUserWithEmailAndPassword(Email!!, Password!!)
         .addOnSuccessListener { authResult : AuthResult? ->


            initialise()
            getData()
            if (ProfilePhotoUri != null) {
               UploadCompletePhoto = false
               setProfilePhotoDataBase()

            } else {
               SuccessComplete()
            }
         }
         .addOnFailureListener { e : Exception ->
            Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
            hideProgressBar()
         }


   }


   private fun setProfilePhotoDataBase() {
      storageReference =
         FirebaseStorage.getInstance().getReference("UserProfilePhoto").child(UserId!!).child("ProfilePhoto")
      storageReference!!.putFile(ProfilePhotoUri!!)
         .addOnSuccessListener { taskSnapshot : UploadTask.TaskSnapshot? ->
            storageReference!!.downloadUrl.addOnSuccessListener { uri : Uri ->
               ProfilePhotoUrl = uri.toString()


               setCompromiseProfilePhotoDataBase()
               SuccessComplete()
            }.addOnFailureListener { e : Exception ->
               Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
               hideProgressBar()
            }
         }.addOnFailureListener { e : Exception ->
            Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
            hideProgressBar()
         }
   }


   private fun setCompromiseProfilePhotoDataBase() {
      storageReference =
         FirebaseStorage.getInstance().getReference("UserProfilePhoto").child(UserId!!).child("CompromiseProfilePhoto")
      storageReference!!.putFile(CompromiseProfilePhotoUri!!)
         .addOnSuccessListener { taskSnapshot : UploadTask.TaskSnapshot? ->
            storageReference!!.downloadUrl.addOnSuccessListener { uri : Uri ->
               CompromiseProfilePhotoUrl = uri.toString()
               UploadCompletePhoto = true

               SuccessComplete()

            }.addOnFailureListener { e : Exception ->
               Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
               hideProgressBar()
            }
         }.addOnFailureListener { e : Exception ->
            Methods.ShowAlertDialog(context, e.localizedMessage, "Warning !")
            hideProgressBar()
         }
   }




   private fun SuccessComplete() {

      val userData : MutableMap<String, Any> = HashMap()
      userData["UserId"] = UserId!!
      userData["ProfilePhotoUrl"] = ProfilePhotoUrl!!
      userData["CompromiseProfilePhotoUrl"] = CompromiseProfilePhotoUrl!!
      userData["CoverPhotoUrl"] = "CoverPhotoUrl"
      userData["Name"] = Name!!
      userData["RollNumber"] = RollNumber!!
      userData["RegistrationNumber"] = RegistrationNumber!!
      userData["ClassName"] = ClassName!!
      userData["Section"] = Section!!
      userData["AcademicName"] = AcademicName!!
      userData["PhoneNumber"] = PhoneNumber!!
      userData["Name"] = Name!!
      userData["Email"] = Email!!
      userData["Password"] = Password!!

      databaseReference!!.child(UserId!!).setValue(userData)
         .addOnCompleteListener { task : Task<Void?> ->
            if (task.isSuccessful) {

               hideProgressBar()
               Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
               startActivity(Intent(context, Home::class.java))
               requireActivity().finish()


               val fragmentTransaction = requireFragmentManager().beginTransaction()

               fragmentTransaction.replace(R.id.sign_up_with_email,  verificationUser()).addToBackStack(null).commit()


            } else {

               Methods.ShowAlertDialog(context, task.exception!!.localizedMessage, "Warning !")

               hideProgressBar()

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