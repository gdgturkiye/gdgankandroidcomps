package org.gdgankara.drawableimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DrawableImageView extends ImageView {

	private Bitmap mBitmap;
	private Bitmap pic;
	private Canvas mCanvas;
	private final Paint mPaint;
	private int a = 255;
	private int r = 192;
	private int g = 29;
	private int b = 29;
	private float width = 3;
	Matrix matrix;

	public DrawableImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
        
        init(attrs);
        
        matrix = new Matrix();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setARGB(a, r, g, b);
	}
	private void init(AttributeSet attrs) {
		final TypedArray attributes = getContext().obtainStyledAttributes(attrs,
				R.styleable.DrawableImageView, 0, 0);

        a = attributes.getInteger(R.styleable.DrawableImageView_a, 255);
        r = attributes.getInteger(R.styleable.DrawableImageView_r, 192);
        g = attributes.getInteger(R.styleable.DrawableImageView_g, 29);
        b = attributes.getInteger(R.styleable.DrawableImageView_b, 29);
        width = attributes.getInteger(R.styleable.DrawableImageView_stroke_width, 3);
	}
	public DrawableImageView(Context c, Bitmap img) {
		super(c);

		pic = img;
		matrix = new Matrix();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setARGB(a, r, g, b);

		Bitmap newBitmap = Bitmap.createBitmap(img.getWidth(), img.getHeight(),
				Bitmap.Config.RGB_565);
		
		Canvas newCanvas = new Canvas();
		newCanvas.setBitmap(newBitmap);
		newCanvas.drawRGB(0xFF, 0xFF, 0xFF );
		
		if (img != null) {
			newCanvas.drawBitmap(img, 0, 0, null);
		}
		
		mBitmap = newBitmap;
		mCanvas = newCanvas;

		this.setImageBitmap(img);
	}
	
	public void setBitmap(Bitmap img)
	{
		if(img == null)
			return;
		
		Bitmap newBitmap = Bitmap.createBitmap(img.getWidth(), img.getHeight(),
				Bitmap.Config.RGB_565);
		pic = img;
		Canvas newCanvas = new Canvas();
		newCanvas.setBitmap(newBitmap);
		newCanvas.drawRGB(0xFF, 0xFF, 0xFF );
		if (img != null) {
			newCanvas.drawBitmap(img, 0, 0, null);
		}
		mBitmap = newBitmap;
		mCanvas = newCanvas;

		this.setImageBitmap(img);
		invalidate();
	}

	public DrawableImageView(Context c, Bitmap img, int alpha, int red,
			int green, int blue) {
		this(c, img);
		setColor(alpha, red, green, blue);
	}

	public DrawableImageView(Context c, Bitmap img, int alpha, int red,
			int green, int blue, float w) {
		this(c, img, alpha, red, green, blue);
		width = w;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setWidth(float w) {
		width = w;
	}

	public void setColor(int alpha, int red, int green, int blue) {
		a = alpha;
		r = red;
		g = green;
		b = blue;
		mPaint.setARGB(a, r, g, b);
	}

	public void Undo() {
		mCanvas.drawRGB(0xFF, 0xFF, 0xFF );
		mCanvas.drawBitmap(pic, 0, 0, null);
		invalidate();
	}

	float scaleX;
	float scaleY;
	float scale;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if(mBitmap != null)
		{
			scaleX = (float) w / mBitmap.getWidth();
			scaleY = (float) h / mBitmap.getHeight();
			scale = scaleX > scaleY ? scaleY : scaleX;
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {

			matrix.postScale(scaleX, scaleY);
			canvas.drawBitmap(mBitmap, matrix, null);
		}
	}

	float lastX;
	float lastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mPaint.setStrokeWidth(width / scale);

		float curX = event.getX() / scaleX;
		float curY = event.getY() / scaleY;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mCanvas.drawCircle(curX, curY, width / 2 / scale, mPaint);
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			mCanvas.drawLine(lastX, lastY, curX, curY, mPaint);
			mCanvas.drawCircle(curX, curY, width / 2 / scale, mPaint); 
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			mCanvas.drawLine(lastX, lastY, curX, curY, mPaint);
			mCanvas.drawCircle(curX, curY, width / 2 / scale, mPaint);
			break;
		}

		}
		lastX = curX;
		lastY = curY;
		invalidate(); 

		return true;
	}
}
