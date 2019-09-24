package com.e.glassofdavinciforjava.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class CustomView extends View {

    //드레그 모드인지 핀치 줌 모드인지 구분
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    //드레그시 좌표 저장
    int posX1=0;
    int posX2=0;
    int posY1=0;
    int posY2=0;

    //핀치시 두 좌표간 거리 저장
    float oldDist = 1f;
    float newDist = 1f;

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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int act = event.getAction();

        switch (act & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                posX1 = (int)event.getX();
                posY1 = (int)event.getY();
                Log.d("Zoom","Mode = DRAG");
                mode = DRAG;
                break;

            case MotionEvent.ACTION_MOVE:
                if(mode==DRAG){
                    if(Math.abs(posX2-posX1)>20 || Math.abs(posY2-posY1)>20) {
                        posX1 = posX2;
                        posY1 = posY2;
                        Log.d("Zoom","dragging");
                    }
                } else if (mode == ZOOM) {
                    newDist = spacing(event);

                    Log.d("zoom", "newDist=" + newDist);
                    Log.d("zoom", "oldDist=" + oldDist);

                    if(newDist - oldDist > 20) {
                        oldDist = newDist;

                        Log.d("zoom","pinch zoom in");
                    } else if (oldDist - newDist > 20) {
                        oldDist = newDist;

                        Log.d("zoom","pinch zoom out");
                    }

                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;

                newDist = spacing(event);
                oldDist = spacing(event);

                Log.d("zoom", "newDist=" + newDist);
                Log.d("zoom", "oldDist=" + oldDist);
                Log.d("zoom", "mode=ZOOM");
                break;

            case MotionEvent.ACTION_CANCEL:
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private float spacing(MotionEvent event){
        float x = event.getX(0)-event.getX(1);
        float y = event.getY(0)-event.getY(1);
        return (float) Math.sqrt(x * x+ y * y);
    }
}
