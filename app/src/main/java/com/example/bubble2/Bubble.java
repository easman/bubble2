package com.example.bubble2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Bubble extends FrameLayout implements View.OnClickListener {

    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#0A70F5");
    private static final int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_PADDING = 16;
    private static final int DEFAULT_OFFSET = 0;

    private static WeakReference<Bubble> reference;

    private int mRadius;
    private FrameLayout.LayoutParams mParams;

    private View mAnchorView;
    private ViewGroup mContentView;

    /**
     * Triangle
     */
    private int mOffset;
    private Point mDatumPoint;

    private Paint mBorderPaint;
    private Path mPath;
    private RectF mRect;

    public static Bubble makeBubble(Activity activity, View anchorView, String text, Drawable drawable) {
        return new Bubble(activity, anchorView, text, drawable);
    }

    private Bubble(Activity activity, View anchorView, String text, Drawable drawable) {
        super(activity);
        init(activity, anchorView, text, drawable);
    }

    private void init(Activity activity, View anchorView, String text, Drawable drawable) {
        mContentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);

        mParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        mParams.gravity = Gravity.BOTTOM;
        setLayoutParams(mParams);

        int padding = dp2px(DEFAULT_PADDING);
        setPadding(padding, 0, padding, padding);

        initViews(activity, text, drawable);

        mAnchorView = anchorView;

        mRadius = dp2px(DEFAULT_RADIUS);
        mOffset = dp2px(DEFAULT_OFFSET);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(DEFAULT_BACKGROUND_COLOR);

        mPath = new Path();
        mRect = new RectF();
        mDatumPoint = new Point();

        setWillNotDraw(false);
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void initViews(Context context, String text, Drawable drawable) {

        View view = LayoutInflater.from(context).inflate(R.layout.bubble_layout, this);
        ImageView bubbleImage = view.findViewById(R.id.bubble_image);
        if (drawable != null) {
            bubbleImage.setImageDrawable(drawable);
            bubbleImage.setVisibility(VISIBLE);
        }
        TextView bubbleText = view.findViewById(R.id.bubble_text);
        bubbleText.setText(text);
        ImageView closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangle(canvas);
    }

    private void drawTriangle(Canvas canvas) {
        int triangularLength = getPaddingBottom();
        if (triangularLength == 0) {
            return;
        }

        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CCW);
        mPath.moveTo(mDatumPoint.x + triangularLength / 2, mDatumPoint.y);
        mPath.lineTo(mDatumPoint.x, mDatumPoint.y + triangularLength / 2);
        mPath.lineTo(mDatumPoint.x - triangularLength / 2, mDatumPoint.y);
        mPath.close();
        canvas.drawPath(mPath, mBorderPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = w - getPaddingRight();
        mRect.bottom = h - getPaddingBottom();

        mDatumPoint.x = w / 2;
        mDatumPoint.y = h - getPaddingBottom();

        if (mOffset != 0) {
            applyOffset();
        }
    }

    private void setPosition(int w, int h) {
        int[] l = new int[2];
        mAnchorView.getLocationInWindow(l);
        int aw = mAnchorView.getMeasuredWidth();
        Point anchor = new Point(l[0] + aw / 2, l[1]);

        int delta = getScreenHeight() - mContentView.getMeasuredHeight();
        mParams.leftMargin = anchor.x - w / 2;
        mParams.topMargin = anchor.y - delta - h;
        setLayoutParams(mParams);
    }


    public void setTriangleOffset(int offset) {
        this.mOffset = offset;
        applyOffset();
        invalidate();
    }

    private void applyOffset() {
        mDatumPoint.x += mOffset;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_button) {
            close();
        }
    }

    public void show() {
        if (reference != null && reference.get() != null) {
            reference.get().close();
        }
        mContentView.addView(this);
        reference = new WeakReference<>(this);
    }

    public void close() {
        mContentView.removeView(this);
        reference.clear();
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private int dp2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void move() {
        mParams.topMargin += 200;
        setLayoutParams(mParams);
    }
}
