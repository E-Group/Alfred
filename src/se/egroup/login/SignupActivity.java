package se.egroup.login;

import se.egroup.alfred.Main;
import se.egroup.alfred.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void button_signup(View view){
    	Intent intent = new Intent(this, Main.class);
		startActivity(intent);
		finish();
    }
    
}
