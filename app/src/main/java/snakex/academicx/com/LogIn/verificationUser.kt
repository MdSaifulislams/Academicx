package snakex.academicx.com.LogIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import snakex.academicx.com.R
import snakex.academicx.com.databinding.VerificationUserBinding

class verificationUser : Fragment() {

   var binding : VerificationUserBinding? = null
   override fun onCreateView(
      inflater : LayoutInflater, container : ViewGroup?,
      savedInstanceState : Bundle?
   ) : View? {

      binding = VerificationUserBinding.inflate(layoutInflater,container,false)
      return binding!!.root
   }


}