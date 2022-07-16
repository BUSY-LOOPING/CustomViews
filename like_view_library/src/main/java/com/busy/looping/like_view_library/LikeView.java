package com.busy.looping.like_view_library;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.ViewDataBinding;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class LikeView extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {
    private ViewDataBinding likeViewDataBinding;

    private OnLikeChangeListener onLikeChangeListener;
    private Context context;
    private float scaleValue, repeatCount;
    private boolean isLiked, animateLikeToUnlike, animateUnlikeToLike;
    private ObjectAnimator objectAnimator;
    private PropertyValuesHolder pvhScaleUpX;
    private PropertyValuesHolder pvhScaleUpY;
    private int likes, duration, interpolatorIndex;


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
//        compositeListener.registerListener(this);
        setOnClickListener(this);
        isLiked = false;
        likes = 0;

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeView);
        isLiked = array.getBoolean(R.styleable.LikeView_liked, false);
        if (isLiked) {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart_filled));
        } else {
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart_outline));
        }
        animateLikeToUnlike = array.getBoolean(R.styleable.LikeView_anim_like_to_unlike, true);
        animateUnlikeToLike = array.getBoolean(R.styleable.LikeView_anim_unlike_to_like, true);
        scaleValue = array.getFloat(R.styleable.LikeView_scale_down, 0.8f);
        likes = array.getInteger(R.styleable.LikeView_likes, 0);
        duration = array.getInteger(R.styleable.LikeView_duration, 300);
        TypedValue typedValue = new TypedValue();
        array.getValue(R.styleable.LikeView_interpolator, typedValue);
        interpolatorIndex = typedValue.data;
        repeatCount = array.getFloat(R.styleable.LikeView_repeatCount, 1);
        array.recycle();
        if (scaleValue > 1f) scaleValue = 1f;
        if (scaleValue < 0f) scaleValue = 0f;
        pvhScaleUpX = PropertyValuesHolder.ofFloat(SCALE_X, scaleValue, 1.1f, 1f);
        pvhScaleUpY = PropertyValuesHolder.ofFloat(SCALE_Y, scaleValue, 1.1f, 1f);
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhScaleUpX, pvhScaleUpY);
        switch (interpolatorIndex) {
            case 0:
                objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
                break;
            case 1:
                objectAnimator.setInterpolator(new AnticipateInterpolator());
                break;
            case 2:
                objectAnimator.setInterpolator(new OvershootInterpolator());
                break;
            case 3:
                objectAnimator.setInterpolator(new AccelerateInterpolator());
                break;
            case 4:
                objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case 5:
                objectAnimator.setInterpolator(new BounceInterpolator());
                break;
            case 6:
                objectAnimator.setInterpolator(new CycleInterpolator(repeatCount));
                break;
            case 7:
                objectAnimator.setInterpolator(new DecelerateInterpolator());
                break;
            case 8:
                objectAnimator.setInterpolator(new LinearInterpolator());
                break;
            case 9:
                objectAnimator.setInterpolator(new FastOutLinearInInterpolator());
                break;
            case 10:
                objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
                break;
            case 11:
                objectAnimator.setInterpolator(new LinearOutSlowInInterpolator());
                break;
        }

        objectAnimator.setDuration(duration);
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
            likes--;
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

    @BindingAdapter("liked")
    public static void setLiked (LikeView likeView, boolean isLiked) {
        if (isLiked != likeView.isLiked) {
            likeView.isLiked = isLiked;
            if (isLiked) {
                likeView.setImageDrawable(ContextCompat.getDrawable(likeView.context, R.drawable.ic_heart_filled));
            } else {
                likeView.setImageDrawable(ContextCompat.getDrawable(likeView.context, R.drawable.ic_heart_outline));
            }
        }
    }

    @InverseBindingAdapter(attribute = "liked", event = "likedAttrChanged")
    public static boolean getLiked (LikeView likeView) {
        return likeView.isLiked;
    }

    public ViewDataBinding getLikeViewDataBinding() {
        return likeViewDataBinding;
    }

    public void setLikeViewDataBinding(ViewDataBinding likeViewDataBinding) {
        this.likeViewDataBinding = likeViewDataBinding;
    }
}
