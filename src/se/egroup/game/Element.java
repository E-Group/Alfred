package se.egroup.game;

import java.util.ArrayList;

import se.egroup.alfred.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Element {
	private int xPosition;
	private int yPosition;
	private Bitmap mBitmap;
	private int xSpeed;
	private int ySpeed;
	private int bitmapWidth;
	
	private char letter;
	private Paint paint = new Paint();

	public Element(Resources res, int x, int y) {
		mBitmap = BitmapFactory.decodeResource(res, R.drawable.box_blue);
		bitmapWidth = mBitmap.getWidth();
		xPosition = x;
		yPosition = y;
		xSpeed = 0;
		ySpeed = -15;
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		initiateLetter();
	}
	
	private void initiateLetter(){
		Alphabet alphabet = new Alphabet();
		letter = alphabet.getNewLetter();
	}

	/**
	 * Returns the elements letter
	 * @return - the letter
	 */
	public char getLetter(){
		return letter;
	}
	
	/**
	 * @return the bitmapWidth
	 */
	 public int getBitmapWidth() {
		 return bitmapWidth;
	 }

	 /**
	  * @param bitmapWidth the bitmapWidth to set
	  */
	 public void setBitmapWidth(int bitmapWidth) {
		 this.bitmapWidth = bitmapWidth;
	 }

	 public int getXPosition() {
		 return xPosition;
	 }

	 public int getYPosition() {
		 return yPosition;
	 }

	 /**
	  * @return the ySpeed
	  */
	 public boolean isMoving() {
		 return ySpeed != 0;
	 }

	 public boolean animate(long elapsedTime, float mHeight, ArrayList<Element> elements) {
		 xPosition += xSpeed * (elapsedTime / 20f);
		 yPosition -= ySpeed * (elapsedTime / 20f);
		 checkBorders(mHeight, elements);
		 checkCollisions(this, elements);
		 checkFloor(this, elements);
		 return checkLose();
	 }
	 
	 private boolean checkLose(){
		 if(ySpeed == 0 && yPosition <= 10){
			 Log.d("GAME", "Torsk");
			 Log.d("GAME", "yPos: "+yPosition);
			 return false;
		 }
		 return true;
	 }

	 // TODO: write javadoc
	 /**
	  * @param mHeight
	  * @param elements
	  */
	 private void checkBorders(final float mHeight, ArrayList<Element> elements) {
		 // TODO: Dom här sasterna gör väl samma sak?
		 if (yPosition >= mHeight) {
			 yPosition = (int) mHeight;
			 // ySpeed = -ySpeed;
			 ySpeed = 0;
		 }
		 if (yPosition + mBitmap.getHeight() >= Panel.screenHeight) {
			 ySpeed = 0;
			 yPosition = (int) (Panel.screenHeight - mBitmap.getHeight());
		 }
		 
	 }

	 /**
	  * Checks if the element has collide with any other element in the column
	  * @param element - The element
	  * @param elements - All elements
	  */
	 private void checkCollisions(Element element, ArrayList<Element> elements) {
		 synchronized (elements) {
			 for(Element e: elements){
				 if(element.xPosition == e.getXPosition() && 
					element.yPosition + element.bitmapWidth >= e.yPosition + element.ySpeed &&
					element.yPosition + element.bitmapWidth <= e.yPosition - element.ySpeed){

					 element.ySpeed = 0;
					 element.yPosition = e.yPosition - element.bitmapWidth;
					 break;
				 }

			 }
		 }
	 }

	 /**
	  * Function that checks if a element has lost its floor
	  * Kanske ska komma på ett bättre namn :P
	  * @param element
	  * @param elements
	  */
	 private void checkFloor(Element element, ArrayList<Element> elements){
		 if(element.ySpeed != 0 || 
			element.yPosition == (int) (Panel.screenHeight - mBitmap.getHeight())){
			 return;
		 }
		 synchronized (elements) {
			 for(Element e: elements){
				 if(element.xPosition == e.getXPosition() && 
					element.yPosition + element.bitmapWidth == e.yPosition){
					 return;
				 }

			 }
		 }
		 // Still going -> no element under -> turn on speed
		 // TODO: create static int fallingSpeed = -15?
		 element.ySpeed = -15;
	 }

	 public void doDraw(Canvas canvas) {
		 canvas.drawBitmap(mBitmap, xPosition, yPosition, null);
		 // TODO: make the positioning dynamic
		 canvas.drawText(Character.toString(letter), xPosition + 35, yPosition + 70, paint);
	 }
}