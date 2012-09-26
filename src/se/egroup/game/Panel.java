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
	private ArrayList<Element> closestElements = new ArrayList<Element>();
	public static float screenWidth;
	public static float screenHeight;
	public static float blockPositions;
	public static float pixelPadding = 10;
	private static String[] elementCombinations = {"Square", "Horse"," Row","Finger","Zag"};
	public static int blockSlots = 5;
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
		removeElement((int) event.getX(), (int) event.getY());
		return super.onTouchEvent(event);
	}

	private void addElement() {
		synchronized (mElements) {
			// mElements.add(new Element(getResources(), (int) event.getX(),
			// (int) event.getY()));
			Element e = new Element(getResources(), calculateXPosition(), 0);
			mElements.add(e);
			mElementNumber = mElements.size();
		}
	}

	private void removeElement(int x, int y) {
		synchronized (mElements) {
			int bitmapWidth = mElements.get(0).getBitmapWidth();
			for (Element element : mElements) {
				if (x > element.getXPosition()
						&& x < element.getXPosition() + bitmapWidth
						&& y > element.getYPosition()
						&& y < element.getYPosition() + bitmapWidth) {
					mElements.remove(element);
					break;
				}
			}
		}
	}

	private int calculateXPosition() {
		Random rand = new Random();
		int x = rand.nextInt(blockSlots);
		String stringX = String.valueOf(x);
		Log.d("RAND", stringX);
		x *= blockPositions; // bredden av ett block
		return x;
	}

	public void doDraw(long elapsed, Canvas canvas, long threadElapsed) {
		canvas.drawColor(Color.BLACK);
		boolean everyoneHasStopped = false;
		synchronized (mElements) {
			for (Element element : mElements) {
				element.doDraw(canvas);
				everyoneHasStopped = element.isMoving();
				Log.d("stopped", ""+everyoneHasStopped);
			}
			if(everyoneHasStopped){
				addElement();
			}
		}
		
		String time = String.valueOf(threadElapsed);
		

		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Elements: "
				+ mElementNumber, 10, 10, mPaint);
	}

	public void animate(long elapsedTime) {
		synchronized (mElements) {
			for (Element element : mElements) {
				element.animate(elapsedTime, screenHeight, mElements);
				
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!mThread.isAlive()) {
			mThread = new ViewThread(this);
			mThread.setRunning(true);
			mThread.start();
			addElement();
		}
	}
	
	/**
	 * Called when surfaceCreated(contentHolder) is called, but never again due
	 * to no landscape mode in application.
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		screenWidth = width; // HTC Desire = 480
		screenHeight = height; // HTC Desire = 762
		blockPositions = (screenWidth - 2*pixelPadding) / blockSlots;
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

}