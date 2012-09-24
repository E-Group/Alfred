package se.egroup.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ViewThread extends Thread {
	private Panel mPanel;
	private SurfaceHolder mHolder;
	private boolean mRun = false;
	private long mStartTime;
	private long mElapsed;
	private long threadElapsed;
	private long threadStartTime;

	public ViewThread(Panel panel) {
		mPanel = panel;
		mHolder = mPanel.getHolder();
	}

	public void setRunning(boolean run) {
		mRun = run;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		threadStartTime = System.currentTimeMillis();
		mStartTime = System.currentTimeMillis();
		while (mRun) {
			canvas = mHolder.lockCanvas();
			if (canvas != null) {
				mPanel.animate(mElapsed);
				mPanel.doDraw(mElapsed, canvas, threadElapsed);
				mElapsed = System.currentTimeMillis() - mStartTime;
				threadElapsed = System.currentTimeMillis() - threadStartTime;
				mHolder.unlockCanvasAndPost(canvas);
			}
			mStartTime = System.currentTimeMillis();
		}
	}
}