package com.mb_lab.mbviewlib.ImageViewManagment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final int borderWidth = 3; // Border width in pixels
    private Paint borderPaint;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialize paint for drawing border
        borderPaint = new Paint();
        borderPaint.setColor(Color.YELLOW);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the circular image
        Path clipPath = new Path();
        RectF rect = new RectF(borderWidth, borderWidth, this.getWidth() - borderWidth, this.getHeight() - borderWidth);
        clipPath.addRoundRect(rect, this.getWidth() / 2, this.getHeight() / 2, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);

        // Draw the border around the image
        canvas.drawRoundRect(rect, this.getWidth() / 2, this.getHeight() / 2, borderPaint);
    }
}