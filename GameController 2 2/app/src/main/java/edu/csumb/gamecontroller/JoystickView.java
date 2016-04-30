package edu.csumb.gamecontroller;

<<<<<<< HEAD
=======
/**
 * Created by Josh on 11/21/2015.
 */
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
<<<<<<< HEAD
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View implements Runnable {
    // Constants
    private final double RAD = 57.2957795;
    public final static long DEFAULT_LOOP_INTERVAL = 100; // 100 ms
    public final static int FRONT = 3;
    public final static int FRONT_RIGHT = 4;
    public final static int RIGHT = 5;
    public final static int RIGHT_BOTTOM = 6;
    public final static int BOTTOM = 7;
    public final static int BOTTOM_LEFT = 8;
    public final static int LEFT = 1;
    public final static int LEFT_FRONT = 2;
    // Variables
    private OnJoystickMoveListener onJoystickMoveListener; // Listener
    private Thread thread = new Thread(this);
    private long loopInterval = DEFAULT_LOOP_INTERVAL;
    private int xPosition = 0; // Touch x position
    private int yPosition = 0; // Touch y position
    private double centerX = 0; // Center view x position
    private double centerY = 0; // Center view y position
    private Paint mainCircle;
    private Paint secondaryCircle;
    private Paint button;
    private Paint horizontalLine;
    private Paint verticalLine;
    private int joystickRadius;
    private int buttonRadius;
    private int lastAngle = 0;
    private int lastPower = 0;
=======
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View {
    public static final int INVALID_POINTER_ID = -1;

    // =========================================
    // Private Members
    // =========================================
    private final boolean D = false;
    String TAG = "JoystickView";

    private Paint dbgPaint1;
    private Paint dbgPaint2;

    private Paint bgPaint;
    private Paint handlePaint;

    private int innerPadding;
    private int bgRadius;
    private int handleRadius;
    private int movementRadius;
    private int handleInnerBoundaries;

    private JoystickMovedListener moveListener;
    private JoystickClickedListener clickListener;

    //# of pixels movement required between reporting to the listener
    private float moveResolution;

    private boolean yAxisInverted;
    private boolean autoReturnToCenter;

    //Max range of movement in user coordinate system
    public final static int CONSTRAIN_BOX = 0;
    public final static int CONSTRAIN_CIRCLE = 1;
    private int movementConstraint;
    private float movementRange;

    public final static int COORDINATE_CARTESIAN = 0;               //Regular cartesian coordinates
    public final static int COORDINATE_DIFFERENTIAL = 1;    //Uses polar rotation of 45 degrees to calc differential drive paramaters
    private int userCoordinateSystem;

    //Records touch pressure for click handling
    private float touchPressure;
    private boolean clicked;
    private float clickThreshold;

    //Last touch point in view coordinates
    private int pointerId = INVALID_POINTER_ID;
    private float touchX, touchY;

    //Last reported position in view coordinates (allows different reporting sensitivities)
    private float reportX, reportY;

    //Handle center in view coordinates
    private float handleX, handleY;

    //Center of the view in view coordinates
    private int cX, cY;

    //Size of the view in view coordinates
    private int dimX, dimY;

    //Cartesian coordinates of last touch point - joystick center is (0,0)
    private int cartX, cartY;

    //Polar coordinates of the touch point from joystick center
    private double radial;
    private double angle;

    //User coordinates of last touch point
    private int userX, userY;

    //Offset co-ordinates (used when touch events are received from parent's coordinate origin)
    private int offsetX;
    private int offsetY;

    // =========================================
    // Constructors
    // =========================================
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42

    public JoystickView(Context context) {
        super(context);
        initJoystickView();
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initJoystickView();
    }

<<<<<<< HEAD
    public JoystickView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        initJoystickView();
    }

    public void initJoystickView() {
        mainCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainCircle.setColor(Color.WHITE);
        mainCircle.setStyle(Paint.Style.FILL_AND_STROKE);

        secondaryCircle = new Paint();
        secondaryCircle.setColor(Color.GREEN);
        secondaryCircle.setStyle(Paint.Style.STROKE);

        verticalLine = new Paint();
        verticalLine.setStrokeWidth(5);
        verticalLine.setColor(Color.GREEN);

        horizontalLine = new Paint();
        horizontalLine.setStrokeWidth(2);
        horizontalLine.setColor(Color.BLACK);

        button = new Paint(Paint.ANTI_ALIAS_FLAG);
        button.setColor(Color.GREEN);
        button.setStyle(Paint.Style.FILL);
    }

    /*
    @Override
    protected void onFinishInflate() {
    }
    */

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        // before measure, get the center of view
        xPosition = (int) getWidth() / 2;
        yPosition = (int) getWidth() / 2;
        int d = Math.min(xNew, yNew);
        buttonRadius = (int) (d / 2 * 0.25);
        joystickRadius = (int) (d / 2 * 0.75);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // setting the measured values to resize the view to a certain width and
        // height
        int d = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));

        setMeasuredDimension(d, d);

=======
    public JoystickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initJoystickView();
    }

    // =========================================
    // Initialization
    // =========================================

    private void initJoystickView() {
        setFocusable(true);

        dbgPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dbgPaint1.setColor(Color.RED);
        dbgPaint1.setStrokeWidth(1);
        dbgPaint1.setStyle(Paint.Style.STROKE);

        dbgPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        dbgPaint2.setColor(Color.GREEN);
        dbgPaint2.setStrokeWidth(1);
        dbgPaint2.setStyle(Paint.Style.STROKE);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.GRAY);
        bgPaint.setStrokeWidth(1);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        handlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handlePaint.setColor(Color.DKGRAY);
        handlePaint.setStrokeWidth(1);
        handlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerPadding = 10;

        setMovementRange(10);
        setMoveResolution(1.0f);
        setClickThreshold(0.4f);
        setYAxisInverted(true);
        setUserCoordinateSystem(COORDINATE_CARTESIAN);
        setAutoReturnToCenter(true);
    }

    public void setAutoReturnToCenter(boolean autoReturnToCenter) {
        this.autoReturnToCenter = autoReturnToCenter;
    }

    public boolean isAutoReturnToCenter() {
        return autoReturnToCenter;
    }

    public void setUserCoordinateSystem(int userCoordinateSystem) {
        if (userCoordinateSystem < COORDINATE_CARTESIAN || movementConstraint > COORDINATE_DIFFERENTIAL)
            Log.e(TAG, "invalid value for userCoordinateSystem");
        else
            this.userCoordinateSystem = userCoordinateSystem;
    }

    public int getUserCoordinateSystem() {
        return userCoordinateSystem;
    }

    public void setMovementConstraint(int movementConstraint) {
        if (movementConstraint < CONSTRAIN_BOX || movementConstraint > CONSTRAIN_CIRCLE)
            Log.e(TAG, "invalid value for movementConstraint");
        else
            this.movementConstraint = movementConstraint;
    }

    public int getMovementConstraint() {
        return movementConstraint;
    }

    public boolean isYAxisInverted() {
        return yAxisInverted;
    }

    public void setYAxisInverted(boolean yAxisInverted) {
        this.yAxisInverted = yAxisInverted;
    }

    /**
     * Set the pressure sensitivity for registering a click
     * @param clickThreshold threshold 0...1.0f inclusive. 0 will cause clicks to never be reported, 1.0 is a very hard click
     */
    public void setClickThreshold(float clickThreshold) {
        if (clickThreshold < 0 || clickThreshold > 1.0f)
            Log.e(TAG, "clickThreshold must range from 0...1.0f inclusive");
        else
            this.clickThreshold = clickThreshold;
    }

    public float getClickThreshold() {
        return clickThreshold;
    }

    public void setMovementRange(float movementRange) {
        this.movementRange = movementRange;
    }

    public float getMovementRange() {
        return movementRange;
    }

    public void setMoveResolution(float moveResolution) {
        this.moveResolution = moveResolution;
    }

    public float getMoveResolution() {
        return moveResolution;
    }

    // =========================================
    // Public Methods
    // =========================================

    public void setOnJostickMovedListener(JoystickMovedListener listener) {
        this.moveListener = listener;
    }

    public void setOnJostickClickedListener(JoystickClickedListener listener) {
        this.clickListener = listener;
    }

    // =========================================
    // Drawing Functionality
    // =========================================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Here we make sure that we have a perfect circle
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int d = Math.min(getMeasuredWidth(), getMeasuredHeight());

        dimX = d;
        dimY = d;

        cX = d / 2;
        cY = d / 2;

        bgRadius = dimX/2 - innerPadding;
        handleRadius = (int)(d * 0.25);
        handleInnerBoundaries = handleRadius;
        movementRadius = Math.min(cX, cY) - handleInnerBoundaries;
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
    }

    private int measure(int measureSpec) {
        int result = 0;
<<<<<<< HEAD

        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

=======
        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
        if (specMode == MeasureSpec.UNSPECIFIED) {
            // Return a default size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
<<<<<<< HEAD
        // super.onDraw(canvas);
        centerX = (getWidth()) / 2;
        centerY = (getHeight()) / 2;

        // painting the main circle
        canvas.drawCircle((int) centerX, (int) centerY, joystickRadius,
                mainCircle);
        // painting the secondary circle
        canvas.drawCircle((int) centerX, (int) centerY, joystickRadius / 2,
                secondaryCircle);
        // paint lines
        canvas.drawLine((float) centerX, (float) centerY, (float) centerX,
                (float) (centerY - joystickRadius), verticalLine);
        canvas.drawLine((float) (centerX - joystickRadius), (float) centerY,
                (float) (centerX + joystickRadius), (float) centerY,
                horizontalLine);
        canvas.drawLine((float) centerX, (float) (centerY + joystickRadius),
                (float) centerX, (float) centerY, horizontalLine);

        // painting the move button
        canvas.drawCircle(xPosition, yPosition, buttonRadius, button);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xPosition = (int) event.getX();
        yPosition = (int) event.getY();
        double abs = Math.sqrt((xPosition - centerX) * (xPosition - centerX)
                + (yPosition - centerY) * (yPosition - centerY));
        if (abs > joystickRadius) {
            xPosition = (int) ((xPosition - centerX) * joystickRadius / abs + centerX);
            yPosition = (int) ((yPosition - centerY) * joystickRadius / abs + centerY);
        }
        invalidate();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            xPosition = (int) centerX;
            yPosition = (int) centerY;
            thread.interrupt();
            if (onJoystickMoveListener != null)
                onJoystickMoveListener.onValueChanged(xPosition, yPosition);
        }
        if (onJoystickMoveListener != null
                && event.getAction() == MotionEvent.ACTION_DOWN) {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
            thread = new Thread(this);
            thread.start();
            if (onJoystickMoveListener != null)
                onJoystickMoveListener.onValueChanged(xPosition, yPosition);
        }
        return true;
    }

    private int getAngle() {
        if (xPosition > centerX) {
            if (yPosition < centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                        / (xPosition - centerX))
                        * RAD + 90);
            } else if (yPosition > centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                        / (xPosition - centerX)) * RAD) + 90;
            } else {
                return lastAngle = 90;
            }
        } else if (xPosition < centerX) {
            if (yPosition < centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                        / (xPosition - centerX))
                        * RAD - 90);
            } else if (yPosition > centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                        / (xPosition - centerX)) * RAD) - 90;
            } else {
                return lastAngle = -90;
            }
        } else {
            if (yPosition <= centerY) {
                return lastAngle = 0;
            } else {
                if (lastAngle < 0) {
                    return lastAngle = -180;
                } else {
                    return lastAngle = 180;
                }
            }
        }
    }

    private int getPower() {
        return (int) (100 * Math.sqrt((xPosition - centerX)
                * (xPosition - centerX) + (yPosition - centerY)
                * (yPosition - centerY)) / joystickRadius);
    }

    private int getDirection() {
        if (lastPower == 0 && lastAngle == 0) {
            return 0;
        }
        int a = 0;
        if (lastAngle <= 0) {
            a = (lastAngle * -1) + 90;
        } else if (lastAngle > 0) {
            if (lastAngle <= 90) {
                a = 90 - lastAngle;
            } else {
                a = 360 - (lastAngle - 90);
            }
        }

        int direction = (int) (((a + 22) / 45) + 1);

        if (direction > 8) {
            direction = 1;
        }
        return direction;
    }

    private double getxPosition() {
        return xPosition;
    }
    private double getyPosition() {
        return yPosition;
    }

    public void setOnJoystickMoveListener(OnJoystickMoveListener listener,
                                          long repeatInterval) {
        this.onJoystickMoveListener = listener;
        this.loopInterval = repeatInterval;
    }

    public interface OnJoystickMoveListener {
        public void onValueChanged(double x, double y);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            post(new Runnable() {
                public void run() {
                    if (onJoystickMoveListener != null)
                        onJoystickMoveListener.onValueChanged(getxPosition(), getyPosition());
                }
            });
            try {
                Thread.sleep(loopInterval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
=======
        canvas.save();
        // Draw the background
        canvas.drawCircle(cX, cY, bgRadius, bgPaint);

        // Draw the handle
        handleX = touchX + cX;
        handleY = touchY + cY;
        canvas.drawCircle(handleX, handleY, handleRadius, handlePaint);

        if (D) {
            canvas.drawRect(1, 1, getMeasuredWidth()-1, getMeasuredHeight()-1, dbgPaint1);

            canvas.drawCircle(handleX, handleY, 3, dbgPaint1);

            if ( movementConstraint == CONSTRAIN_CIRCLE ) {
                canvas.drawCircle(cX, cY, this.movementRadius, dbgPaint1);
            }
            else {
                canvas.drawRect(cX-movementRadius, cY-movementRadius, cX+movementRadius, cY+movementRadius, dbgPaint1);
            }

            //Origin to touch point
            canvas.drawLine(cX, cY, handleX, handleY, dbgPaint2);

            int baseY = (int) (touchY < 0 ? cY + handleRadius : cY - handleRadius);
            canvas.drawText(String.format("%s (%.0f,%.0f)", TAG, touchX, touchY), handleX-20, baseY-7, dbgPaint2);
            canvas.drawText("("+ String.format("%.0f, %.1f", radial, angle * 57.2957795) + (char) 0x00B0 + ")", handleX-20, baseY+15, dbgPaint2);
        }

//              Log.d(TAG, String.format("touch(%f,%f)", touchX, touchY));
//              Log.d(TAG, String.format("onDraw(%.1f,%.1f)\n\n", handleX, handleY));
        canvas.restore();
    }

    // Constrain touch within a box
    private void constrainBox() {
        touchX = Math.max(Math.min(touchX, movementRadius), -movementRadius);
        touchY = Math.max(Math.min(touchY, movementRadius), -movementRadius);
    }

    // Constrain touch within a circle
    private void constrainCircle() {
        float diffX = touchX;
        float diffY = touchY;
        double radial = Math.sqrt((diffX*diffX) + (diffY*diffY));
        if ( radial > movementRadius ) {
            touchX = (int)((diffX / radial) * movementRadius);
            touchY = (int)((diffY / radial) * movementRadius);
        }
    }

    public void setPointerId(int id) {
        this.pointerId = id;
    }

    public int getPointerId() {
        return pointerId;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                return processMoveEvent(ev);
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if ( pointerId != INVALID_POINTER_ID ) {
//                              Log.d(TAG, "ACTION_UP");
                    returnHandleToCenter();
                    setPointerId(INVALID_POINTER_ID);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                if ( pointerId != INVALID_POINTER_ID ) {
                    final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = ev.getPointerId(pointerIndex);
                    if ( pointerId == this.pointerId ) {
//                                      Log.d(TAG, "ACTION_POINTER_UP: " + pointerId);
                        returnHandleToCenter();
                        setPointerId(INVALID_POINTER_ID);
                        return true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if ( pointerId == INVALID_POINTER_ID ) {
                    int x = (int) ev.getX();
                    if ( x >= offsetX && x < offsetX + dimX ) {
                        setPointerId(ev.getPointerId(0));
//                                      Log.d(TAG, "ACTION_DOWN: " + getPointerId());
                        return true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                if ( pointerId == INVALID_POINTER_ID ) {
                    final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = ev.getPointerId(pointerIndex);
                    int x = (int) ev.getX(pointerId);
                    if ( x >= offsetX && x < offsetX + dimX ) {
//                                      Log.d(TAG, "ACTION_POINTER_DOWN: " + pointerId);
                        setPointerId(pointerId);
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    private boolean processMoveEvent(MotionEvent ev) {
        if ( pointerId != INVALID_POINTER_ID ) {
            final int pointerIndex = ev.findPointerIndex(pointerId);

            // Translate touch position to center of view
            float x = ev.getX(pointerIndex);
            touchX = x - cX - offsetX;
            float y = ev.getY(pointerIndex);
            touchY = y - cY - offsetY;

//              Log.d(TAG, String.format("ACTION_MOVE: (%03.0f, %03.0f) => (%03.0f, %03.0f)", x, y, touchX, touchY));

            reportOnMoved();
            invalidate();

            touchPressure = ev.getPressure(pointerIndex);
            reportOnPressure();

            return true;
        }
        return false;
    }

    private void reportOnMoved() {
        if ( movementConstraint == CONSTRAIN_CIRCLE )
            constrainCircle();
        else
            constrainBox();

        calcUserCoordinates();

        if (moveListener != null) {
            boolean rx = Math.abs(touchX - reportX) >= moveResolution;
            boolean ry = Math.abs(touchY - reportY) >= moveResolution;
            if (rx || ry) {
                this.reportX = touchX;
                this.reportY = touchY;

//                              Log.d(TAG, String.format("moveListener.OnMoved(%d,%d)", (int)userX, (int)userY));
                moveListener.OnMoved(userX, userY);
            }
        }
    }

    private void calcUserCoordinates() {
        //First convert to cartesian coordinates
        cartX = (int)(touchX / movementRadius * movementRange);
        cartY = (int)(touchY / movementRadius * movementRange);

        radial = Math.sqrt((cartX*cartX) + (cartY*cartY));
        angle = Math.atan2(cartY, cartX);

        //Invert Y axis if requested
        if ( !yAxisInverted )
            cartY  *= -1;

        if ( userCoordinateSystem == COORDINATE_CARTESIAN ) {
            userX = cartX;
            userY = cartY;
        }
        else if ( userCoordinateSystem == COORDINATE_DIFFERENTIAL ) {
            userX = cartY + cartX / 4;
            userY = cartY - cartX / 4;

            if ( userX < -movementRange )
                userX = (int)-movementRange;
            if ( userX > movementRange )
                userX = (int)movementRange;

            if ( userY < -movementRange )
                userY = (int)-movementRange;
            if ( userY > movementRange )
                userY = (int)movementRange;
        }

    }

    //Simple pressure click
    private void reportOnPressure() {
//              Log.d(TAG, String.format("touchPressure=%.2f", this.touchPressure));
        if ( clickListener != null ) {
            if ( clicked && touchPressure < clickThreshold ) {
                clickListener.OnReleased();
                this.clicked = false;
//                              Log.d(TAG, "reset click");
                invalidate();
            }
            else if ( !clicked && touchPressure >= clickThreshold ) {
                clicked = true;
                clickListener.OnClicked();
//                              Log.d(TAG, "click");
                invalidate();
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }
        }
    }

    private void returnHandleToCenter() {
        if ( autoReturnToCenter ) {
            final int numberOfFrames = 5;
            final double intervalsX = (0 - touchX) / numberOfFrames;
            final double intervalsY = (0 - touchY) / numberOfFrames;

            for (int i = 0; i < numberOfFrames; i++) {
                final int j = i;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        touchX += intervalsX;
                        touchY += intervalsY;

                        reportOnMoved();
                        invalidate();

                        if (moveListener != null && j == numberOfFrames - 1) {
                            moveListener.OnReturnedToCenter();
                        }
                    }
                }, i * 40);
            }

            if (moveListener != null) {
                moveListener.OnReleased();
            }
        }
    }

    public void setTouchOffset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }
}
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
