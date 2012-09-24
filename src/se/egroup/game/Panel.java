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
	public static float mWidth;
	public static float mHeight;
	public static float blockWidth;
	public static float pixelPadding = 10;
	public static int slots = 5;
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
		deleteElement((int) event.getX(), (int) event.getY());
		return super.onTouchEvent(event);
	}

	private void addElement() {
		synchronized (mElements) {
			// mElements.add(new Element(getResources(), (int) event.getX(),
			// (int) event.getY()));
			Element e = new Element(getResources(), calculateXPosition(), 0, blockWidth);
			mElements.add(e);
			mElementNumber = mElements.size();
		}
	}

	private void deleteElement(int x, int y) {
		synchronized (mElements) {
			for (Element element : mElements) {
				if (x > element.getXPosition()
						&& x < element.getXPosition() + 72
						&& y > element.getYPosition()
						&& y < element.getYPosition() + 72) {
					mElements.remove(element);
					break;
				}
			}
		}
	}

	private int calculateXPosition() {
		Random rand = new Random();
		int x = rand.nextInt(slots);
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
		if (threadElapsed % 4000 <= 50) {
			addElement();
		}

		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Elements: "
				+ mElementNumber, 10, 10, mPaint);
	}

	public void animate(long elapsedTime) {
		synchronized (mElements) {
			for (Element element : mElements) {
				element.animate(elapsedTime, mHeight);
				closestElements = calculateClosestElements(element);
				if (!closestElements.isEmpty()) {

				}
			}
		}
	}

	private ArrayList<Element> calculateClosestElements(Element element) {
		closestElements = new ArrayList<Element>();
		// TODO Auto-generated method stub
		for (Element e : mElements) {
			if (Math.abs((double) (element.getXPosition() - e.getXPosition())) < 72
					&& Math.abs((double) (element.getYPosition() - e
							.getYPosition())) < 72) {
				closestElements.add(e);
			}
		}
		return closestElements;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!mThread.isAlive()) {
			mThread = new ViewThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}
	
	/**
	 * Called when surfaceCreated(contentHolder) is called, but never again due
	 * to no landscape mode in application.
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mWidth = width; // HTC Desire = 480
		mHeight = height; // HTC Desire = 762
		blockWidth = (mWidth - 2*pixelPadding) / slots;
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

}