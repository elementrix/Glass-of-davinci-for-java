package com.e.glassofdavinciforjava.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class CustomView extends View {

    private ShapeDrawable mTrapezoid;

    public CustomView(Context context) {
        super(context);

        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        Path path = new Path();
        path.moveTo(100.0f,60.0f);
        path.lineTo(300.f,60.0f);
        path.lineTo(270.0f,140.0f);
        path.lineTo(130.0f,140.0f);
        path.lineTo(100.0f,60.0f);

        mTrapezoid = new ShapeDrawable(new PathShape(path, 400.0f, 200.0f));
        mTrapezoid.getPaint().setStyle(Paint.Style.STROKE);
        mTrapezoid.getPaint().setStrokeWidth(1.0f);
        mTrapezoid.getPaint().setColor(Color.BLACK);
        //mTrapezoid.setBounds(0, 0, 200, 100);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        mTrapezoid.setBounds(0, 0, w, h);

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        mTrapezoid.draw(canvas);
    }
}
