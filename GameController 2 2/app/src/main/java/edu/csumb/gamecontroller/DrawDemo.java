package edu.csumb.gamecontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.EdgeEffect;

/**
 * Created by brianhuynh on 3/11/16.
 */
public class DrawDemo extends View {

    public EdgeEffect leftEdge;

    public DrawDemo(Context context) {
        super(context);
        leftEdge = new EdgeEffect(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int screenWidth = canvas.getWidth();
        int screenHeight = (int) (canvas.getHeight() * 0.1);

        leftEdge.onPull(Float.valueOf("3.0"));
        leftEdge.setColor(Color.GREEN);
        leftEdge.setSize(screenWidth, screenHeight);
        boolean result = leftEdge.draw(canvas);
        Log.d("View", Boolean.toString(result));
        /*
        Rect blueRect = new Rect();
        blueRect.set(0, 0, canvas.getWidth(), canvas.getHeight() / 2);
        Rect yellowRect = new Rect();
        yellowRect.set(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
        Paint blu = new Paint();
        blu.setColor(Color.BLUE);
        blu.setStyle(Paint.Style.FILL);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
        canvas.drawRect(blueRect, blu);
        canvas.drawRect(yellowRect, yellow);
        */

    }

}
