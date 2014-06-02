package com.gameplus.dpad;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;

public class Dpad {
	int _x, _y, _w, _h, state, mag, angle;
	Point controler;
	Point curpos;
	Vector2D dvec, vecR;
	

	public Dpad(int x, int y, int w, int h) {
		// TODO Auto-generated constructor stub
		_x = x;
		_y = y;
		_w = w;
		_h = h;
		controler = new Point(x, y);
		dvec = new Vector2D(x, y);
	}

	public void update(int x, int y) {
		
		// magnitute calc
		int dx = _x - x;
		int dy = _y - y;
		mag = (int) Math.sqrt((dx * dx) + (dy * dy));
		if (mag < 160)
			dvec.mag = mag;
		// rotation calc
		Vector2D vecB = new Vector2D(x, y);
		vecR = vecB.sub(dvec);
		if (x - dvec.x <= 0) {
			dvec.theta += 180 - vecR.theta - dvec.theta;
		} else {
			dvec.theta += vecR.theta - dvec.theta;
		}

		curpos = new Point(x, y);
	}

	public void draw(ShapeDrawable mDrawables, Canvas c) {
//		g.drawImage(menu.dpadBorder, _x - (_w / 2), _y - (_h / 2), null);
//		if (dvec != null)
//			dvec.drawVector(mDrawables, c,dvec);
	}
}
