package com.gameplus.dpad;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Coins {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
	int x,y;
	boolean iscollected;
	long anim=0;
    private int xSpeed = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0,currentFrame1=0;
    private int width;
    private int height;
static int count=0;

	public Coins(int _x, int _y) {
		x=_x;
		y=_y;
	}


	public Coins(int _x, int _y, GameView gameView, Bitmap bmp) {
		x=_x;
		y=_y;
		count++;
		currentFrame=count;
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
	}


	public void update() {
		// TODO Auto-generated method stub
		
		if(anim==0)
			anim=System.currentTimeMillis();
		else if(System.currentTimeMillis()-anim>100){
			anim=System.currentTimeMillis();
		 if (x > gameView.getWidth() - width - xSpeed) {
             xSpeed = -5;
      }
      if (x + xSpeed < 0) {
             xSpeed = 5;
      }
      x = x + xSpeed;
      if(currentFrame+1>=BMP_COLUMNS){
    	  currentFrame1=++currentFrame1%(BMP_ROWS-1);
      }
    	  
      currentFrame = ++currentFrame % BMP_COLUMNS;
      
		}
}

public void draw(Canvas canvas) {
      update();
      int srcX = currentFrame * width;
      int srcY = currentFrame1 * height;
      Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
      Rect dst = new Rect(x, y, x + width/2, y + height/2);
      canvas.drawBitmap(bmp, src, dst, null);
      if(currentFrame1>=BMP_ROWS){
    	  currentFrame=0;
    	  currentFrame1=0;
      }
}


public void isColliding(int _x, int _y, int w, int h) {
	// TODO Auto-generated method stub
	
	
}
}
