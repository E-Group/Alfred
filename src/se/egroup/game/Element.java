package se.egroup.game;

import java.util.Random;

import se.egroup.alfred.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Element {
    private int mX;
    private int mY;
    private Bitmap mBitmap;
    private int mSpeedX;
    private int mSpeedY;
     
    public Element(Resources res, int x, int y) {
        Random rand = new Random();
        mBitmap = BitmapFactory.decodeResource(res, R.drawable.box_blue);
        //mX = x - mBitmap.getWidth() / 2;
        //mY = y - mBitmap.getHeight() / 2;
        //mSpeedX = rand.nextInt(7) - 3;
        //mSpeedY = rand.nextInt(7) - 3;
        mX = x;
        mY = y;
        mSpeedX = 0;
        mSpeedY = -3;
    }
 
    public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public void animate(long elapsedTime) {
        mX += mSpeedX * (elapsedTime / 20f);
        mY += mSpeedY * (elapsedTime / 20f);
        checkBorders();
    }
    
    private void checkBorders() {
        if (mX <= 0) {
            mSpeedX = -mSpeedX;
            mX = 0;
        } else if (mX + mBitmap.getWidth() >= Panel.mWidth) {
            mSpeedX = -mSpeedX;
            mX = (int) (Panel.mWidth - mBitmap.getWidth());
        }
        if (mY <= 0) {
            mY = 0;
           // mSpeedY = -mSpeedY;
            mSpeedY = 0;
        }
        if (mY + mBitmap.getHeight() >= Panel.mHeight) {
            mSpeedY = -mSpeedY;
            mY = (int) (Panel.mHeight - mBitmap.getHeight());
        }
    }
    
    public void doDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mX, mY, null);
    }
}