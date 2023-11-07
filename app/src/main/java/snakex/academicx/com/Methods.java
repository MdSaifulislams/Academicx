package snakex.academicx.com;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.Nullable;


public class Methods {


public static String[] FragmentName = {"Sign In","Sign Up"};


public static String[] HomeFragmentName = {"Home","Contacts","Profile"};

//-------------------------


public  Methods getInstance(){
	 return new Methods();
}


//---------------------------



public static void SetIntent(Context context, Class<?> targetedContext){

	 context.startActivity(new Intent(context,targetedContext));

}


//---------------------------------------


public static  void Picasso(String Url ){



}


//---------------------------------------


public static  void FragmentToFragment(FragmentActivity FragmentActivity){

	 FragmentTransaction fragmentTransaction = FragmentActivity.getSupportFragmentManager().beginTransaction();


}

//---------------------------------------



public  static  void ShowAlertDialog(Context context,String localizedMessage,String AlertTitle){

	 AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

	 alertDialog.setTitle(AlertTitle);
	 alertDialog.setMessage(localizedMessage);
	 alertDialog.setPositiveButton("Try again", (dialogInterface, i) -> {

	 });


	 AlertDialog alertDialog1 = alertDialog.create();

	 alertDialog1.show();

}

//------------------------



public static void progressbarHide(ProgressBar HideProgressBar ,@Nullable Button ShowButton) {
	 if (ShowButton!=null) {
			ShowButton.setVisibility(View.VISIBLE);
	 }
	 HideProgressBar.setVisibility(View.GONE);
}


//----------------------------------------


public static void progressbarShow(ProgressBar ShowProgressBar ,@Nullable Button HideButton) {

	 if (HideButton!=null) {
			HideButton.setVisibility(View.INVISIBLE);
	 }
	 ShowProgressBar.setVisibility(View.VISIBLE);
}



//

//----------------------------------------

public static void SetError(EditText ErrorButton , String ErrorText){


	 ErrorButton.setError(ErrorText);
}




//----------------------------------------


public static void GoCallIntent(Context context, String Number){


	 if (ActivityCompat.checkSelfPermission(context,
			 Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

			Intent intentDial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Number));
			context.startActivity(intentDial);
	 }



}



//

public static void GoMessageIntent(Context context, String Number){

	 if (ActivityCompat.checkSelfPermission(context,
			 Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+Number));
			//	 intent.putExtra("sms_body", message);
			context.startActivity(intent);

	 }



}



}
