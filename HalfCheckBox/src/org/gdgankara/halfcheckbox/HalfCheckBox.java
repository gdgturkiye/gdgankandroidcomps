package org.gdgankara.halfcheckbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class HalfCheckBox extends View {

	boolean isTouchDown = false;
	Bitmap mTick;
	Bitmap mTickHalf;
	Rect mViewRect;
	Rect mCheckboxRect;
	Paint mPaint;
	int mHeight;
	int currX;
	int height;
	boolean isMovementDetected = false;
	
	public static final int NOT_CHECKED = 0;
	public static final int CHECKED = 1;
	public static final int HALF_CHECKED = 2;
	public int MARGIN=10;
	int checkState = NOT_CHECKED;

	public interface onCheckedChange {
		void onCheckClick(View v);
	}

	public HalfCheckBox(Context context) {
		super(context);
		initialize(context);
	}
	
	public HalfCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	private void initialize(Context context) {
		mTick = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.tick);
		height = mTick.getHeight();
		mTickHalf = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.tick_half);

		mViewRect = new Rect(MARGIN, MARGIN, height+MARGIN, height+MARGIN);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckBox(int checkState) {
		this.checkState = checkState;
		invalidate();
	}

	private onCheckedChange mCB = null;

	public void setChecklistener(onCheckedChange aCB) {
		mCB = aCB;
	}

	public boolean onTouchEvent(MotionEvent event) {

		int pointerX = (int) event.getX();
		int pointerY = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mViewRect.contains(pointerX, pointerY) == true)
				isTouchDown = true;
			break;
		case MotionEvent.ACTION_MOVE:
			currX = (int) event.getX();
			int deltaX = Math.abs(currX - pointerX);

			if (deltaX > ViewConfiguration.getTouchSlop()) {
				isMovementDetected = true;
			}

			break;
		case MotionEvent.ACTION_UP:
			if (isMovementDetected == false && isTouchDown == true) {
				if (checkState == NOT_CHECKED) {
					checkState = CHECKED;
				} else if (checkState == CHECKED){
					checkState = NOT_CHECKED;
				}
				if (mCB != null)
					mCB.onCheckClick(this);
				invalidate();
				isTouchDown = false;
			}
			isMovementDetected = false;
			break;
		}
		return true;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawRect(mViewRect, mPaint);
		
		if (checkState == CHECKED) 
		{
			canvas.drawBitmap(mTick, null, mViewRect, null);
		}
		else if(checkState == HALF_CHECKED)
		{
			canvas.drawBitmap(mTickHalf, null, mViewRect, null);
		}
	}
	
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {

        	result = height + 50;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }


    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);


        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = height + 50;
            if (specMode == MeasureSpec.AT_MOST) {

                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
