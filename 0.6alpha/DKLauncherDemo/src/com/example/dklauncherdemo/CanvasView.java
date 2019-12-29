package com.example.dklauncherdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		System.out.println(canvas.getHeight());
		System.out.println(canvas.getWidth());
	}
}