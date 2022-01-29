package com.busy.looping.like_view_library;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class LikeView extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {
    private OnLikeChangeListener onLikeChangeListener;
    private Context context;
    private float scaleValue;
    private boolean isLiked, animateLikeToUnlike, animateUnlikeToLike;
    private ObjectAnimator objectAnimator;
    private PropertyValuesHolder pvhScaleUpX;
    private PropertyValuesHolder pvhScaleUpY;
    private int likes;


    public LikeView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LikeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LikeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;
        setOnClickListener(this);
        isLiked = false;
        likes = 0;
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart_outline));
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeView);
        animateLikeToUnlike = array.getBoolean(R.styleable.LikeView_anim_like_to_unlike, true);
        animateUnlikeToLike = array.getBoolean(R.styleable.LikeView_anim_unlike_to_like, true);
        scaleValue = array.getFloat(R.styleable.LikeView_scale_down, 0.8f);
        likes = array.getInteger(R.styleable.LikeView_likes, 0);
        array.recycle();
        if (scaleValue > 1f) scaleValue = 1f;
        if (scaleValue < 0f) scaleValue = 0f;
        pvhScaleUpX = PropertyValuesHolder.ofFloat(SCALE_X, scaleValue, 1f);
        pvhScaleUpY = PropertyValuesHolder.ofFloat(SCALE_Y, scaleValue, 1f);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhScaleUpX, pvhScaleUpY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setOnLikeChangeListener(OnLikeChangeListener onLikeChangeListener) {
        this.onLikeChangeListener = onLikeChangeListener;
    }

    public boolean isLiked() {
        return isLiked;
    }


    @Override
    public void onClick(View view) {
        objectAnimator.cancel();
        if (isLiked) {
            likes --;
            setImageResource(R.drawable.ic_heart_outline);
            if (animateLikeToUnlike)
                objectAnimator.start();
        } else {
            likes++;
            if (animateUnlikeToLike)
                objectAnimator.start();
            setImageResource(R.drawable.ic_heart_filled);
        }
        isLiked = !isLiked;
        if (onLikeChangeListener != null) {
            onLikeChangeListener.onLikeChange(isLiked);
        }
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        if (likes < 0) {
            return;
        }
        this.likes = likes;
    }
}
