package se.egroup.game;

import java.util.Random;

import se.egroup.alfred.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Element {
    private int xPosition;
    private int yPosition;
    private Bitmap mBitmap;
    private int xSpeed;
    private int ySpeed;
     
    public Element(Resources res, int x, int y, float blockWidth) {
        Random rand = new Random();
        mBitmap = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.box_blue), blockWidth);
        //xPosition = x - mBitmap.getWidth() / 2;
        //yPosition = y - mBitmap.getHeight() / 2;
        //xSpeed = rand.nextInt(7) - 3;
        //ySpeed = rand.nextInt(7) - 3;
        xPosition = x;
        yPosition = y;
        xSpeed = 0;
        ySpeed = -3;
    }
 
    public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void animate(long elapsedTime, float mHeight) {
        xPosition += xSpeed * (elapsedTime / 20f);
        yPosition -= ySpeed * (elapsedTime / 20f);
        checkBorders(mHeight);
    }
    
    private void checkBorders(final float mHeight) {
        if (xPosition <= 0) {
            xSpeed = -xSpeed;
            xPosition = 0;
        } else if (xPosition + mBitmap.getWidth() >= Panel.mWidth) {
            xSpeed = -xSpeed;
            xPosition = (int) (Panel.mWidth - mBitmap.getWidth());
        }
        if (yPosition >= mHeight) {
            yPosition = (int) mHeight;
           // ySpeed = -ySpeed;
            ySpeed = 0;
        }
        if (yPosition + mBitmap.getHeight() >= Panel.mHeight) {
            ySpeed = 0;
            yPosition = (int) (Panel.mHeight - mBitmap.getHeight());
        }
    }
    
    public void doDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, xPosition, yPosition, null);
    }
    
    public Bitmap getResizedBitmap(Bitmap bm, float newDimension) {

    	int width = bm.getWidth();
    	int height = bm.getHeight();

    	float scaleWidth = newDimension;
    	float scaleHeight = newDimension;

    	// create a matrix for the manipulation
    	Matrix matrix = new Matrix();

    	// resize the bit map
    	matrix.postScale(scaleWidth, scaleHeight);

    	// recreate the new Bitmap

    	Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

    	return resizedBitmap;

    	}

}