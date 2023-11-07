package snakex.academicx.com.LogIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import snakex.academicx.com.databinding.VerficationUserBinding


class VerificationUser : Fragment() {

   var binding : VerficationUserBinding ? = null

   override fun onCreateView(
      inflater : LayoutInflater, container : ViewGroup?,
      savedInstanceState : Bundle?
   ) : View? {

      binding = VerficationUserBinding.inflate(layoutInflater,container,false)

      return binding!!.root
   }

   }
