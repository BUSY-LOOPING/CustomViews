package com.busy.looping.strike_through_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProfileCardPainter implements Painter {

    @ColorInt
    private int color;
    private float avatarRadius, avatarMargin;
    private Context context;
    private AttributeSet attributeSet;

    @DrawableRes
    private int drawableUnderRes;

    public ProfileCardPainter(Context context, @ColorInt int color, float avatarRadius, float avatarMargin) {
        this.context = context;
        this.color = color;
        this.avatarRadius = avatarRadius;
        this.avatarMargin =avatarMargin;
    }

    /**
     * StrikedView will call this method whenever the object needs to paint
     */
    public void paint(Canvas canvas) {
        //1
        float width = canvas.getWidth();
        float height = canvas.getHeight();

        //2
        RectF shapeBounds = new RectF(0, 0, width, height - avatarRadius -avatarMargin);
//3
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
////4
//        canvas.drawRect(shapeBounds, paint);
        //1
        PointF centerAvatar = new PointF(shapeBounds.centerX(), shapeBounds.bottom + avatarMargin);
//2
        RectF avatarBounds = new RectF(centerAvatar.x - avatarRadius, centerAvatar.y - avatarRadius, centerAvatar.x + avatarRadius, centerAvatar.y + avatarRadius);

//3
//        paint.setColor(ContextCompat.getColor(context, R.color.red));
//        canvas.drawRect(avatarBounds, paint);
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
     * */
    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
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
        if (drawableUnderRes != R.color.blue) {
        }
        canvas.drawPath(curvePath, paint);
    }


    public static int dpToPx(@NonNull Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(@NonNull Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public Context getContext() {
        return context;
    }

    public AttributeSet getAttributeSet() {
        return attributeSet;
    }

    public void setAttributeSet(AttributeSet attributeSet) {
        this.attributeSet = attributeSet;
        if(attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StrikedView);
            drawableUnderRes = typedArray.getResourceId(R.styleable.StrikedView_strikeUnderBackground, R.color.blue);
            typedArray.recycle();
        }
    }
}
