package snakex.academicx.com.LogIn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import snakex.academicx.com.Adapters.LoginFragmentAdapter;
import snakex.academicx.com.databinding.LoginActivityBinding;


public class LoginActivity extends AppCompatActivity {


LoginActivityBinding binding;
FragmentManager fragmentManager;
LoginFragmentAdapter loginFragmentAdapter;


@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 binding = LoginActivityBinding.inflate(getLayoutInflater());
	 setContentView(binding.getRoot());


	 initialise();
	 setAdapter();


}


private void setAdapter() {

	 loginFragmentAdapter = new LoginFragmentAdapter(fragmentManager,1);


	 binding.viewPager.setAdapter(loginFragmentAdapter);

	 binding.tabLayout.setupWithViewPager(binding.viewPager);


}






private void initialise() {

	 fragmentManager = getSupportFragmentManager();


}
}