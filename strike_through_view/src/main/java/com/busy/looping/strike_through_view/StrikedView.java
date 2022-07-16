package com.busy.looping.strike_through_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Type;

public class StrikedView extends View {
    private Context context;
    private int width, height;
    @ColorInt
    private int color;
    private float avatarRadius, avatarMargin;
    private RectF shapeBounds, avatarBounds;
    private Paint paint;
    private PointF centerAvatar;
//    private ProfileCardPainter painter;

//    public StrikedView(Context context, int width, int height, ProfileCardPainter painter) {
//        super(context);
//        init(context, width, height, painter, null);
//    }

//    public StrikedView(Context context, int width, int height, ProfileCardPainter painter, AttributeSet attributeSet) {
//        super(context);
//        init(context, width, height, painter, attributeSet);
//    }

    private void init(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                StrikedView.this.context = context;
                StrikedView.this.width = getWidth();
                StrikedView.this.height = getHeight();
                StrikedView.this.avatarRadius = 100;
                StrikedView.this.avatarMargin = 0;
                StrikedView.this.color = ContextCompat.getColor(context, R.color.red);
                shapeBounds = new RectF(0, 0, width, height - avatarRadius - avatarMargin);

                if (attributeSet != null) {
                    TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StrikedView);
                    StrikedView.this.avatarRadius = typedArray.getInt(R.styleable.StrikedView_avatarRadius, 55);
                    StrikedView.this.color = typedArray.getColor(R.styleable.StrikedView_strikeUnderColor, ContextCompat.getColor(context, R.color.blue));
                    typedArray.recycle();
                    Log.d("mylog", "avatarRadius: " + avatarRadius);
                }
                //1
                centerAvatar = new PointF(shapeBounds.centerX(), shapeBounds.bottom + avatarMargin);
//2
                avatarBounds = new RectF(centerAvatar.x - avatarRadius, centerAvatar.y - avatarRadius, centerAvatar.x + avatarRadius, centerAvatar.y + avatarRadius);
                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        this.painter = painter;
//        if (painter != null) {
//            painter.setAttributeSet(attributeSet);
//        }
                paint.setColor(color);
                postInvalidate();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

    }

    public StrikedView(Context context) {
        super(context);
        init(context, null);
    }

    public StrikedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StrikedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public StrikedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

//    public void setPainter(ProfileCardPainter painter) {
//        this.painter = painter;
//        if (painter != null)
//            postInvalidate();
//    }
//
//    @Nullable
//    public ProfileCardPainter getPainter() {
//        return painter;
//    }

    @Override
    public void onDraw(Canvas canvas) {
        drawBackground(canvas, shapeBounds, avatarBounds);
        drawCurvedShape(canvas, shapeBounds, avatarBounds);
        Log.d("mylog", "paint: ");
    }

    public void drawBackground(Canvas canvas, RectF bounds, RectF avatarBounds) {
        //1
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setDither(true);

        //2
        Path path = new Path();
        // 3
        path.moveTo(bounds.left, bounds.top);
        // 4
        path.lineTo(bounds.left, bounds.top + bounds.height());
        // 5
        path.lineTo(bounds.left, avatarBounds.centerY());
        // 6
        path.arcTo(avatarBounds, -180f, 180f, false);
        // 7
        path.lineTo(bounds.right, avatarBounds.centerY());
        // 8
        path.lineTo(bounds.right, bounds.bottom - bounds.height());

        path.close();

//        val backgroundPath = new Path() .apply {
//            // 3
//            moveTo(bounds.left, bounds.top)
//            // 4
//            lineTo(bounds.bottomLeft.x, bounds.bottomLeft.y)
//            // 5
//            lineTo(avatarBounds.centerLeft.x, avatarBounds.centerLeft.y)
//            // 6
//            arcTo(avatarBounds, -180f, 180f, false)
//            // 7
//            lineTo(bounds.bottomRight.x, bounds.bottomRight.y)
//            // 8
//            lineTo(bounds.topRight.x, bounds.topRight.y)
//            // 9
//            close()
//        }

        //10
        canvas.drawPath(path, paint);
    }

    /**
     * You will want to use a factor less than 1.0f to darken. try 0.8f
     */
    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    private void drawCurvedShape(Canvas canvas, RectF bounds, RectF avatarBounds) {
        //1
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(manipulateColor(color, 0.8f));
        paint.setDither(true);

        //2
        PointF handlePoint = new PointF(bounds.left + (bounds.width() * 0.25f), bounds.top);
        Path curvePath = new Path();


        curvePath.moveTo(bounds.left, bounds.top + bounds.height());
        //5
        curvePath.lineTo(avatarBounds.centerX() - avatarRadius, avatarBounds.centerY());
        //6
        curvePath.arcTo(avatarBounds, -180f, 180f, false);
        //7
        curvePath.lineTo(bounds.right, bounds.bottom);
//        //8
        curvePath.lineTo(bounds.right, bounds.bottom - bounds.height());
//        //9
        curvePath.quadTo(handlePoint.x, handlePoint.y, bounds.left, bounds.top + bounds.height());
//        //10
        curvePath.close();

        //11
        canvas.drawPath(curvePath, paint);
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public float getAvatarRadius() {
        return avatarRadius;
    }

    public void setAvatarRadius(float avatarRadius) {
        this.avatarRadius = avatarRadius;
    }

    public float getAvatarMargin() {
        return avatarMargin;
    }

    public void setAvatarMargin(float avatarMargin) {
        this.avatarMargin = avatarMargin;
    }
}
