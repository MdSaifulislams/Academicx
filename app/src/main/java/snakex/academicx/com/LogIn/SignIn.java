package snakex.academicx.com.LogIn;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

import snakex.academicx.com.Home.Home;
import snakex.academicx.com.Methods;
import snakex.academicx.com.databinding.SignInBinding;


public class SignIn extends Fragment {

 SignInBinding binding;

FirebaseAuth firebaseAuth;

private FirebaseUser firebaseUser;
Methods methods;

String  Email,Password;


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
	  binding = SignInBinding.inflate(getLayoutInflater(),container,false);

	 getPermissions();

	 initialise();



	 click();


	 return binding.getRoot();
}//--------------------------------------------------------------- On Create end



private void click() {

	 loginButtonClick();

}


private void loginButtonClick() {



	 binding.loginButton.setOnClickListener(v-> {

			takeDataInput();
			checkFullFillInput();

	 });

}
private void checkFullFillInput() {


	 if (Email.equals("")){
			binding.email.setError("Email Required");

	 }else if (Password.equals("")){
			binding.password.setError("Password Required");

	 }else {

			Methods.progressbarShow(binding.progressbar , binding.loginButton);

			CreateAccountVerified();
	 }




}


private void CreateAccountVerified() {

	 firebaseAuth.signInWithEmailAndPassword(Email,Password)
			 .addOnCompleteListener(task -> {
					 if (task.isSuccessful()) {

						 SuccessComplete();

					 }else {

						 Methods.progressbarHide(binding.progressbar , binding.loginButton);

						 Methods.ShowAlertDialog(getContext(),
								Objects.requireNonNull(task.getException()).getLocalizedMessage(),
								"Warning !");

					 }
			 });


}


//---------------------------------------


private void SuccessComplete() {



	 Methods.SetIntent(getContext(), Home.class);
	 getActivity().finish();
}



private void takeDataInput() {


	 Email = Objects.requireNonNull(binding.email.getText()).toString().trim();
	 Password = Objects.requireNonNull(binding.password.getText()).toString().trim();


}



private void initialise() {

	 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
	 firebaseAuth = FirebaseAuth.getInstance();


}




private void getPermissions() {


	 Dexter.withContext(getContext())
			 .withPermissions(
					 Manifest.permission.POST_NOTIFICATIONS
//			 ,Manifest.permission.READ_EXTERNAL_STORAGE
			 ,Manifest.permission.READ_MEDIA_VIDEO
			 ,Manifest.permission.READ_MEDIA_IMAGES

			 )
			 .withListener(new MultiplePermissionsListener() {

					@Override
					public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {
						 permissionToken.continuePermissionRequest();

					}

					@Override
					public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
						 if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
								ShowAlertDialog();
						 }
					}

			 }).withErrorListener(error->{

							Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();

					 }
			 ).onSameThread()
			 .check();

}


//--------------------------------

private void ShowAlertDialog() {

	 AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

	 alert.setTitle("Need Permission");
	 alert.setMessage("This app needs permission to use this feature. You can grant them in app settings.");


	 alert.setPositiveButton("Go Setting", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

				 Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				 Uri uri =  Uri.fromParts("package",getContext().getPackageName(),null);
				 intent.setData(uri);

				 startActivityForResult(intent,101);
			}
	 });


	 alert.show();

}
}