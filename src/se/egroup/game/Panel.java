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
	private Paint mPaint = new Paint();
	private boolean successfulDraw = false;

	private ArrayList<Float> lineXPositions = new ArrayList<Float>();
	private ArrayList<Float> lineYPositions = new ArrayList<Float>();
	private ArrayList<Element> activeElements = new ArrayList<Element>();

	// Variable that choose the soultion for deleting elements
	// Solution == 1: 	Adds element to an ArrayList when they are drawn on,
	//					deletes them from mElements when releasing the screen.
	// Solution == 2: 	Deletes all the elements on the line when releasing
	// 					the screen. May reduce performance due to deep loop.
	//
	// Den andra lösningen känns mer responsive när man spelar, för man kanske
	// drar över ett element en millisek innan den landar, men då tas den inte
	// bort i första lösningen. Men det känns som att spelet laggar ibland med
	// den lösningen.. Vad tykcer du?
	//
	private static final int SOLUTION = 2;
	
	private Context context;
	public Panel(Context context) {
		super(context);
		this.context = context;
		getHolder().addCallback(this);
		mThread = new ViewThread(this);
		mPaint.setColor(Color.WHITE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		successfulDraw = false;
		
		//removeElement((int) event.getX(), (int) event.getY());
		if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
			Log.d("TOUCH", "DOWN:"+event.getX()+":"+event.getY());
			lineXPositions.clear(); // Kan flyttas till i slutet av ACTION_UP om
			lineYPositions.clear(); // man vill att strecket ska försvninna direkt.
			lineXPositions.add(event.getX());
			lineYPositions.add(event.getY());
			if(SOLUTION == 1){
				checkElement(event.getX(), event.getY());
			}
		}
		if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
			Log.d("TOUCH", "MOVE:"+event.getX()+":"+event.getY());
			lineXPositions.add(event.getX());
			lineYPositions.add(event.getY());
			if(SOLUTION == 1){
				checkElement(event.getX(), event.getY());
			}
		}
		if(event.getActionMasked() == MotionEvent.ACTION_UP){
			Log.d("TOUCH", "UP:"+event.getX()+":"+event.getY());
			Log.d("LINE", "linePositions:"+lineXPositions.size());
			lineXPositions.add(event.getX());
			lineYPositions.add(event.getY());
			if(SOLUTION == 1){
				removeElementsInArrayList();
			}else if(SOLUTION == 2){
				removeElementsOnLine();
			}
		}
		//return super.onTouchEvent(event);
		return true;
	}

	/**
	 * Function that should be used together with checkElement.
	 * Deletes all elements i activeElements.
	 */
	private void removeElementsInArrayList(){
		synchronized (mElements) {
			synchronized (activeElements) {
				if(mElements.isEmpty()||activeElements.isEmpty())return;
				for(Element e : activeElements){
					mElements.remove(e);
				}
				activeElements.clear();
			}
		}
	}

	/**
	 * Function that adds not moving element to a arraylist as soon as
	 * they are drawn on. Avoids high calculations at the ACTION_UP.
	 * This could be a good solution because falling elements should not
	 * be allowed to be drawn on.
	 * @param x - x position
	 * @param y - y position
	 */
	private void checkElement(float x, float y){
		synchronized (mElements) {
			int bitmapWidth = mElements.get(0).getBitmapWidth();
			for (Element element : mElements) {
				synchronized (activeElements) {
					if (activeElements.contains(element)||element.isMoving()) continue;
					if (x > element.getXPosition()
							&& x < element.getXPosition() + bitmapWidth
							&& y > element.getYPosition()
							&& y < element.getYPosition() + bitmapWidth) {
						activeElements.add(element);
						break;
					}
				}
			}
		}
	}

	/**
	 * Function that adds an element
	 */
	private void addElement() {
		synchronized (mElements) {
			Element e = new Element(getResources(), calculateXPosition(), -72);
			mElements.add(e);
		}
	}

	/**
	 * Remove all elements that is on the line
	 * The function may have bad performance due the deep loops
	 * Requires testing..
	 * @param x - x position
	 * @param y - y position
	 */
	private void removeElementsOnLine() {
		
		// a arraylist that contains all the elements who has been drawn on
		ArrayList<Element> inLine = new ArrayList<Element>();
		
		synchronized (mElements) {
			int bitmapWidth = mElements.get(0).getBitmapWidth();
			if(mElements.isEmpty())return;
			synchronized (lineXPositions) {
				synchronized (lineYPositions) {
					for (int i = 0; i < lineXPositions.size()-1; i++) {
						for (Element element : mElements) {
							if (lineXPositions.get(i) > element.getXPosition()
									&& lineXPositions.get(i) < element.getXPosition() + bitmapWidth
									&& lineYPositions.get(i) > element.getYPosition()
									&& lineYPositions.get(i) < element.getYPosition() + bitmapWidth) {
								//mElements.remove(element);
								if(!inLine.contains(element)){
									inLine.add(element);
								}
								break;
							}
						}
					}
					
					approveDraw(inLine);
					
				}
			}
			
		}
	}
	
	/**
	 * Function that decides if a draw is approved or not
	 * @param inLine - the arraylist of elements in the draw
	 */
	private void approveDraw(ArrayList<Element> inLine){
		// A successful draw should include at least two elements
		Log.d("GAME", "size:"+inLine.size());
		if(inLine.size() < GameSettings.MINIMUM_BLOCKS_IN_A_ROW){
			return;
		}
		
		// The algorithm checks if the letters are the same in the row
		char lastLetter = 0;
		for (Element element : inLine) {
			if(element.getLetter() != lastLetter && lastLetter != 0){
				return;
			}
			lastLetter = element.getLetter();
		}
		
		for (Element element : inLine) {
			mElements.remove(element);
		}
		
		successfulDraw = true;
	}

	/**
	 * Function that randomizes a x position to the new elements
	 * TODO: resizing does not work?
	 * @return
	 */
	private int calculateXPosition() {
		Random rand = new Random();
		int x = rand.nextInt(GameSettings.BLOCK_SLOTS);
		String stringX = String.valueOf(x);
		Log.d("RAND", stringX);
		x *= blockPositions; // bredden av ett block
		return x;
	}

	public void doDraw(long elapsed, Canvas canvas, long threadElapsed) {
		canvas.drawColor(Color.BLACK);
		boolean lastElementIsMoving = true;
		synchronized (mElements) {
			for (Element element : mElements) {
				element.doDraw(canvas);
				lastElementIsMoving = element.isMoving();
				Log.d("stopped", ""+lastElementIsMoving);
			}
			if(!lastElementIsMoving||mElements.isEmpty()){
				addElement();
			}
		}
		
		// Draw
		mPaint.setColor(Color.RED);
		if(successfulDraw){
			mPaint.setColor(Color.GREEN);
		}
		mPaint.setStrokeWidth(15);
		synchronized (lineXPositions) {
			synchronized (lineYPositions) {
				for (int i = 0; i < lineXPositions.size()-1; i++) {
					canvas.drawLine(lineXPositions.get(i), lineYPositions.get(i),
							lineXPositions.get(i+1), lineYPositions.get(i+1), mPaint);
				}
			}
		}

		// TODO: Use threadElapsed to increase the difficulty of the game
		//String time = String.valueOf(threadElapsed);

		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Elements: "
				+ mElements.size(), 10, 10, mPaint);
	}

	/**
	 * Calls every elements .animate
	 * @param elapsedTime
	 */
	public void animate(long elapsedTime) {
		synchronized (mElements) {
			for (Element element : mElements) {
				boolean living = element.animate(elapsedTime, screenHeight, mElements);
				if (!living){
					// TODO: end the game
					// typ byta context till gameactivity eller något..
				}
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
		blockPositions = (screenWidth - 2*pixelPadding) / GameSettings.BLOCK_SLOTS;
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

}