package se.egroup.alfred;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * Test Test Test
 * TEST AV EMIL
 * 
 * @author Lalle
 * 
 */
public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// The activity is being created.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public void onStart(){
    	super.onStart();
        // The activity is about to become visible.
    }
    
    @Override
    public void onResume(){
    	super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    
    @Override
    public void onPause(){
    	super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    
    @Override
    public void onStop(){
    	super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
        // The activity is about to be destroyed.
    }
    
}