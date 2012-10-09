package se.egroup.alfred;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import se.egroup.game.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * The very beginning of a cool app
 * 
 * @author Lalle
 * @author Emil
 * 
 */
public class Main extends Activity {
	
	// TODO: Kanske strukturera om login funktionen, så att man
	// kan spela spelet utan att logga in, men man måste skapa
	// konto om man ska spela mot polare?
	// källa: http://developer.android.com/training/id-auth/identify.html
	
	/**
	 * Called when the user clicks the New Game button
	 * @param view - The view that was clicked
	 */
	public void button_new(View view){
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Called when the user clicks the Friends button
	 * @param view - The view that was clicked
	 */
	public void button_friends(View view){
		Intent intent = new Intent(this, FriendsActivity.class);
		startActivity(intent);
		
//		DefaultHttpClient hc=new DefaultHttpClient();
//		ResponseHandler <String> res=new BasicResponseHandler();
//		HttpPost postMethod=new HttpPost("http://localhost:8888/alfredserver");
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
//		nameValuePairs.add(new BasicNameValuePair("value1", "hejhej"));  
//		try {
//			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		try {
//			String response=hc.execute(postMethod,res);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}
	
	/**
	 * Called when the user clicks the Settings button
	 * @param view - The view that was clicked
	 */
	public void button_settings(View view){
		// TODO: change to a action bar?
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Called when the user clicks the About button
	 * @param view - The view that was clicked
	 */
	public void button_about(View view){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// The activity is being created.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public void onStart(){
		// The activity is about to become visible.
		super.onStart();
	}

	@Override
	public void onResume(){
		// The activity has become visible (it is now "resumed").
		super.onResume();
	}

	@Override
	public void onPause(){
		// Another activity is taking focus (this activity is about to be "paused").
		super.onPause();
	}

	@Override
	public void onStop(){
		// The activity is no longer visible (it is now "stopped")
		super.onStop();
	}

	@Override
	public void onDestroy(){
		// The activity is about to be destroyed.
		super.onDestroy();
	}

}
