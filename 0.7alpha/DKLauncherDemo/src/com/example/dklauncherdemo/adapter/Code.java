package com.example.dklauncherdemo.adapter;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;

/**
 * �����֤�룬�������ñ��ߣ�����ɾ��Ӧ�õĵط�
 * 
 * @author Administrator
 * 
 */
public class Code {
	private static int HEIGHT = 50;
	private static int WIDTH = 150;
	private static String string_code = "1234567890qweasdzxcrtyfghvbnmjkluiopQWEASDZXCVBNFGHRTYUIOPJKLM";
	private static Random random = new Random();
	private static int base_padding_left;
	private static final int base_padding_top = 20;
	private static final int padding_left_random = 20;
	private static final int padding_top_random = 20;
	public static String code = "";

	/**
	 * @return RGB��ɫ
	 */
	private static int mColor() {
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		return Color.rgb(red, green, blue);
	}

	private static String mCode() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append(string_code.charAt(random.nextInt(string_code
					.length())));
		}
		return buffer.toString();
	}

	private static void mDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		int color = mColor();
		paint = new Paint();
		int startX = random.nextInt(WIDTH);
		int startY = random.nextInt(HEIGHT);
		int stopX = random.nextInt(WIDTH);
		int stopY = random.nextInt(HEIGHT);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	public static Bitmap createBitmap() {
		code = mCode();
		Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setTextSize(25);
		base_padding_left = 20;
		for (int i = 0; i < code.length(); i++) {
			int color = mColor();
			paint.setColor(color);
			paint.setFakeBoldText(false);
			float skewX = random.nextInt(11) / 10;
			skewX = random.nextBoolean() ? skewX : (-skewX);
			paint.setTextSkewX(skewX);
			int padding_top = base_padding_top + random.nextInt(20);
			canvas.drawText(code.charAt(i) + "", base_padding_left,
					padding_top, paint);
			base_padding_left += padding_left_random;
		}
		mDraw(canvas, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return bitmap;
	}
}