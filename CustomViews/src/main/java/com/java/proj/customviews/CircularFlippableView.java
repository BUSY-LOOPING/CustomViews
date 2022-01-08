package com.java.proj.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;


public class CircularFlippableView extends View implements View.OnClickListener {
    private Paint paint;
    private int radius = 0;
    private RectF rectF;
    private final int RADIUS_DEFAULT = 50;
    private float degree = 0.0f;
    private int width;
    private int height;
    private final Handler handler = new Handler();
    private Drawable flippedDrawable;
    private Bitmap flippedBitmap;
    private RoundedBitmapDrawable roundedBitmapDrawable;

    public CircularFlippableView(Context context) {
        super(context);
        setOnClickListener(this);
        init(null);
    }

    public CircularFlippableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        init(attrs);
    }

    public CircularFlippableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        init(attrs);
    }

    public CircularFlippableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnClickListener(this);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 0;
        int desiredHeight = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        int diameter = (int) radius * 2;
        if (diameter > height) {
            height = diameter;
        }
        if (diameter > width) {
            width = diameter;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (degree > 0) {
            setRotationY(degree);
            if (degree >= 90) {
                if (roundedBitmapDrawable != null) {
                    roundedBitmapDrawable.setBounds(0, 0, width, height);
                    roundedBitmapDrawable.draw(canvas);
                }
            }
        }
        canvas.drawOval(rectF, paint);
    }

    private void init(@Nullable AttributeSet attributeSet) {
        rectF = new RectF();
        rectF.top = 0;
        rectF.left = 0;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (attributeSet == null)
            return;
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircularFlippableView);
        radius = typedArray.getDimensionPixelSize(R.styleable.CircularFlippableView_radius, RADIUS_DEFAULT);
        flippedDrawable = typedArray.getDrawable(R.styleable.CircularFlippableView_flippedSrc);
        if (flippedDrawable != null) {
            flippedBitmap = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.CircularFlippableView_flippedSrc, R.color.white));
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), flippedBitmap);
            roundedBitmapDrawable.setCornerRadius(radius);
            roundedBitmapDrawable.setAntiAlias(true);
        }

        typedArray.recycle();
        int diameter = radius * 2;
        rectF.bottom = diameter;
        rectF.right = diameter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean callOnClick() {
        return super.callOnClick();
    }

    @Override
    public void onClick(View v) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                degree += 10;
                postInvalidate();
//                invalidate();
                if (degree < 180) {
                    handler.post(this);
                } else {
                    degree = 0;
                }
            }
        });
    }
}

