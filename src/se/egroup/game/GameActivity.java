package se.egroup.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        
        // F�ljde en tutorial som finns h�r:
        // http://www.droidnova.com/2d-tutorial-series-part-i,770.html
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
    }

}