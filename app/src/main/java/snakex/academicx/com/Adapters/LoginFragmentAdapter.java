package snakex.academicx.com.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import snakex.academicx.com.LogIn.SignUp;
import snakex.academicx.com.LogIn.SignIn;
import snakex.academicx.com.Methods;



public class LoginFragmentAdapter extends FragmentPagerAdapter {

String[] FragmentName = Methods.FragmentName;


public LoginFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
	 super(fm, behavior);
}

//--------------------------------------------------

@NonNull
@Override
public Fragment getItem(int position) {

	 switch (position){

			case  0 :
			  return new SignIn();


			case  1 :
			  return new SignUp();


	 }

	 return null;
}



//--------------------------------------------------



@Override
public int getCount() {
	 return FragmentName.length;
}



//--------------------------------------------------


@Nullable
@Override
public CharSequence getPageTitle(int position) {
	 return FragmentName[position];
}
}
