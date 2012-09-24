package se.egroup.login;

import se.egroup.alfred.Main;
import se.egroup.alfred.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {

	private boolean loggedIn = false;
	
	private void checkLoginStatus(){
		if(loggedIn){
			// TODO: call for button_login instead, but with which view?
			// TODO: save the login state with onSaveInstanceState?
			Intent intent = new Intent(this, Main.class);
			startActivity(intent);
			finish();
		}
	}
	
	public void button_login(View view){
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
		finish();
	}
	
	public void button_signup(View view){
		Intent intent = new Intent(this, SignupActivity.class);
		startActivity(intent);
		finish();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLoginStatus();
    }
    
}
