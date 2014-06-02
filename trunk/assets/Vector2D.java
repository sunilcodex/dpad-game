package com.gameplus.dpad;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;


/**
 * @author murugan.kannan
 * 
 */
public class Vector2D {

	float x, y, theta = 0.0f, mag = 0.0f;
	Vector2D unit;
	String name;
	Vector2D beg, end;

	public Vector2D(float x, float y, String name, float angle, float mag) {
		this.name = name;
		this.x = x;
		this.y = y;
		theta = angle;
		this.mag = mag;
	}

	public Vector2D() {
	}

	public Vector2D(float x2, float y2) {
		x = x2;
		y = y2;
	}

	public double dot(Vector2D vec2) {
		return this.x * vec2.x + this.y * vec2.y;
	}

	public Vector2D add(Vector2D vec2) {
		Vector2D res = new Vector2D();
		res.x = this.x + vec2.x;
		res.y = this.y + vec2.y;
		return res;
	}

	public Vector2D sub(Vector2D vec2) {
		Vector2D res = new Vector2D();
		res.x = this.x - vec2.x;
		res.y = this.y - vec2.y;
		res.theta =(float) res.angle();
		res.name = vec2.name + this.name;
		res.beg = vec2;
		res.end = this;
		return res;
	}

	public double angle() {
		double mod = mod();
		if (mod != 0) {
			double val = y / mod;
			theta = (float) Math.toDegrees(Math.asin(val));
		} else
			theta = 0.0f;
		return theta;
	}

	public double mod() {

		return android.util.FloatMath.sqrt(this.x * this.x + this.y * this.y);
	}

	public void changeMod(float speed) {

		mag += speed;

	}

	public void changeAngle(double speed) {
		theta += speed;
	}

	public void print() {
		System.out.println(x + "i + " + y + "j");
		System.out.println(theta);
		System.out.println(mag);

	}

	public void move(double speed) {
		y =(float) (y + speed * Math.sin(Math.toRadians(theta)));
		x =(float) (x + speed * Math.cos(Math.toRadians(theta)));
	}

	public void changey(double speed) {
		y += speed;

	}

	public void changex(double speed) {
		x += speed;

	}

	public void drawVector(ShapeDrawable mDrawables, Canvas c) {
		int r_width = 50;
		int r_height = 10;
		c.rotate(theta, x, y);
		mDrawables.setBounds((int) x, (int) y, (int) x + r_width, (int) y + r_height);
		mDrawables.draw(c);
		c.rotate(-theta, x, y);
		

	}

	public void setX(float f) {
		x=f;
		
	}

	public void setY(float f) {
		y=f;
		
	}
}
