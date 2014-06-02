package com.gameplus.dpad;


import com.gameplus.dpad.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private Bitmap rocket,dpad,dpadBorder;
	Dpad _dpad;
	Vector2D player=new Vector2D(400,400,"player",90.0f,0.0f);
	private double angle;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private Matrix matrix;
	private boolean dragStopped;
	
	public GameView(Context context) {
		super(context);

		
		holder = getHolder();
		gameLoopThread = new GameLoopThread(this);
		holder.addCallback(new Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}

			public void surfaceCreated(SurfaceHolder holder) {
				try{
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
				}catch (Exception e) {
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				// TODO Auto-generated method stub

			}
		});
		rocket=BitmapFactory.decodeResource(getResources(), R.drawable.rocket);
		dpad=BitmapFactory.decodeResource(getResources(), R.drawable.dpad);
		dpadBorder=BitmapFactory.decodeResource(getResources(), R.drawable.dpad_border);
		matrix=new Matrix();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas c) {
		if(c==null)
			return;
		c.drawColor(Color.BLACK);
		int width = c.getWidth();
		int height = c.getHeight();

		if (player.x > width)
			player.x = 0;
		else if (player.x < 0)
			player.x = width;
		if (player.y > height)
			player.y = 0;
		else if (player.y < 0)
			player.y = height;
		if(_dpad!=null){
			player.move(-_dpad.dvec.mag/20);
		}

		matrix.preTranslate(player.x,player.y);
		matrix.preRotate(player.theta);
		matrix.preTranslate(-15, -16);
		c.drawBitmap(rocket, matrix, null);
		matrix.reset();
		if(_dpad!=null){
			matrix.preTranslate(_dpad._x - (_dpad._w / 2),_dpad._y - (_dpad._h / 2));
			c.drawBitmap(dpadBorder, matrix, null);
			matrix.reset();

			if(_dpad.dvec!=null){
			int endx, endy, w, h;
			endy = (int) (_dpad.dvec.y + _dpad.dvec.mag * Math.sin(Math.toRadians(_dpad.dvec.theta)));
			endx = (int) (_dpad.dvec.x + _dpad.dvec.mag * Math.cos(Math.toRadians(_dpad.dvec.theta)));
			w = h = 40;
			matrix.preTranslate(endx - (w / 2),endy - (h / 2));
			}
			c.drawBitmap(dpad, matrix, null);
			matrix.reset();
			if(dragStopped)
				_dpad=null;
		}
	}

	public void setMouseLocation(float mx, float my) {
		dragStopped=false;
		_dpad = new Dpad((int)mx,(int)my, 50, 50);
		angle=_dpad.dvec.theta;
	}

	public void drag(float mx, float my) {
		if(_dpad!=null){
			_dpad.update((int)mx,(int)my);
			player.theta=(float) (180+_dpad.dvec.theta-angle);
		}
	}
	
	public void destroy() {
	}

	public void dragStop(float mx, float my) {
		dragStopped=true;
	}
	
}
