package se.egroup.alfred;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * The very beginning of a cool app
 * 
 * @author Lalle
 * @author Emil	
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
