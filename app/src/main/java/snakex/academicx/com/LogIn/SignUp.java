package snakex.academicx.com.LogIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import snakex.academicx.com.R;
import snakex.academicx.com.databinding.SignUpBinding;


public class SignUp extends Fragment {

SignUpBinding binding;


DatabaseReference databaseReference;

String  Name, RollNumber, RegistrationNumber,ClassName,Section, AcademicName,PhoneNumber;

List<String> classNameList;
List<String> organizationNameList;



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
												 Bundle savedInstanceState) {
  binding = SignUpBinding.inflate(getLayoutInflater(),container,false);

	 initialise();
	 setSectionSpinner();
	 getClassNameData();
	 getOrganizationData();
	 click();



	 return binding.getRoot();
}//------------------------------------- Oncreate end



private void setSectionSpinner() {

	 String[] sectionName ={ "A" ,"B","C","D","E","F","G","H","I","J"};

	 ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
			 android.R.layout.simple_spinner_item,sectionName );
	 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	 binding.section.setAdapter(adapter);
   binding.section.setPrompt("Select Your Section");



	 binding.section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				   Section = sectionName[i];
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
	 });

}


private void getOrganizationData() {

	 databaseReference = FirebaseDatabase.getInstance().getReference("Organization Name");

	 databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				 if (snapshot!=null){

						for (DataSnapshot dataSnapshot : snapshot.getChildren()){

							 organizationNameList.add(dataSnapshot.getValue().toString());

						}


						ArrayAdapter<String> academicNameAdapter = new ArrayAdapter<>(getContext(),
								android.R.layout.simple_dropdown_item_1line,organizationNameList );

						binding.academic.setThreshold(1);
						binding.academic.setAdapter(academicNameAdapter);
				 }
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			}
	 });

}

private void getClassNameData() {

	 databaseReference = FirebaseDatabase.getInstance().getReference("Class Name");

	 			 databaseReference.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot snapshot) {

						 if (snapshot!=null){

								for (DataSnapshot dataSnapshot : snapshot.getChildren()){

									 classNameList.add(dataSnapshot.getValue().toString());
								}


								ArrayAdapter<String> classNameAdapter = new ArrayAdapter<>(getContext(),
										android.R.layout.simple_dropdown_item_1line,classNameList );

								binding.className.setThreshold(1);
								binding.className.setAdapter(classNameAdapter);
						 }
					}

					@Override
					public void onCancelled(@NonNull DatabaseError error) {

					}
			 });


}




private void click() {

	 binding.Button.setOnClickListener(v -> {


			takeDataInput();
			checkFullFillInput();


	 });
}







private void takeDataInput() {

	 Name = Objects.requireNonNull(binding.name.getText()).toString().trim();
	 RollNumber = Objects.requireNonNull(binding.roll.getText()).toString().trim();
	 RegistrationNumber = Objects.requireNonNull(binding.registrationNumber.getText()).toString().trim();
	 ClassName  = Objects.requireNonNull(binding.className.getText()).toString().trim();
	 AcademicName = Objects.requireNonNull(binding.academic.getText()).toString().trim();
   PhoneNumber = binding.phoneNumber.getText().toString().trim();

}



private void checkFullFillInput() {


	 if (Name.equals("")) {

			binding.nameLayout.setHelperText("Name Required!");

	 } else if (RollNumber.equals("")) {
			binding.rollLayout.setHelperText("Roll Number Required!");

	 } else if (RegistrationNumber.equals("")) {
			binding.registrationNumberLayout.setHelperText("Registration Number Required!");

 } else if (ClassName.equals("")) {
			binding.classNameLayout.setHelperText("Class Name Required!");



	 } else if (AcademicName.equals("")) {
			binding.academicLayout.setHelperText("Academic Required!");

	 }else if (PhoneNumber.equals("")){
			binding.phoneNumberLayout.setHelperText("Phone Number Required!");
	 } else {

			GoSignUpWithEmail();

	 }


}

private void GoSignUpWithEmail() {

	 Bundle bundle = new Bundle();

	 bundle.putString("Name",Name);
	 bundle.putString("RollNumber",RollNumber);
	 bundle.putString("RegistrationNumber",RegistrationNumber);
	 bundle.putString("ClassName",ClassName);
	 bundle.putString("Section",Section);
	 bundle.putString("AcademicName",AcademicName);
	 bundle.putString("PhoneNumber",PhoneNumber);

	 Fragment fragment = new SignUpWithEmail();
	 fragment.setArguments(bundle);

	 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

	 fragmentTransaction.replace(R.id.sign_up,fragment).addToBackStack(null).commit();

}


private void initialise () {

	 classNameList = new ArrayList<>();
	 organizationNameList = new ArrayList<>();

	 }



}