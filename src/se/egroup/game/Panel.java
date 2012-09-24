package se.egroup.game;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
	Bitmap mBitmap;
	private ViewThread mThread;
	private ArrayList<Element> mElements = new ArrayList<Element>();
	public static float mWidth;
	public static float mHeight;
	private int mElementNumber = 0;
	private Paint mPaint = new Paint();

	public Panel(Context context) {
		super(context);
		getHolder().addCallback(this);
		mThread = new ViewThread(this);
		mPaint.setColor(Color.WHITE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		deleteElement((int)event.getX(), (int)event.getY());		 
		return super.onTouchEvent(event);
	}

	private void addElement(){
		synchronized (mElements) {
			//mElements.add(new Element(getResources(), (int) event.getX(), (int) event.getY()));
			mElements.add(new Element(getResources(), calcXPos(), (int)mHeight));
			mElementNumber = mElements.size();
		}
	}

	private void deleteElement(int x, int y){
		synchronized (mElements) {
			for (Element element : mElements) {
				if(x > element.getX() && x < element.getX()+72
						&& y > element.getY() && y < element.getY()+72){
					mElements.remove(element);
					break;
				}
			}
		}
	}
	
	private int calcXPos(){
		Random rand = new Random();
		int x = rand.nextInt(5);
		String stringX = String.valueOf(x);
		Log.d("RAND", stringX);
		x *= 72; // bredden av ett block
		return x;
	}

	public void doDraw(long elapsed, Canvas canvas, long threadElapsed) {
		canvas.drawColor(Color.BLACK);
		synchronized (mElements) {
			for (Element element : mElements) {
				element.doDraw(canvas);
			}
		}

		String time = String.valueOf(threadElapsed);
		Log.d("Elapsed", time);
		if(threadElapsed % 4000 <= 50){
			addElement();
		}


		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Elements: " + mElementNumber, 10, 10, mPaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mWidth = width; // HTC Desire = 480
		mHeight = height; // HTC Desire = 762
	}

	public void animate(long elapsedTime) {
		synchronized (mElements) {
			for (Element element : mElements) {
				element.animate(elapsedTime);
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!mThread.isAlive()) {
			mThread = new ViewThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

}