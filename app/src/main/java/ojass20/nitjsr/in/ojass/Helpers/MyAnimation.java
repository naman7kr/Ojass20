package ojass20.nitjsr.in.ojass.Helpers;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation {

    private View view;
    private float cx, cy;           // center x,y position of circular path
    private float prevX, prevY;     // previous x,y position of image during animation
    private float r;                // radius of circle
    private float prevDx, prevDy;

    public MyAnimation(View view, float r) {
        this.view = view;
        this.r = r;
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        int cxImage = width / 2;
        int cyImage = height / 2;
        cx = 0.5f;
        cy = 0.5f;

        //Log.e("Hey", cxImage + " " + cyImage + " " + cx + " " + cy);
        // set previous position to center
        prevX = cx;
        prevY = cy;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 0) {
            t.getMatrix().setTranslate(prevDx, prevDy);
            return;
        }

        float angleDeg = (interpolatedTime * (float) (Math.acos(53.0 / 125.0) * 180 / Math.PI) + 90) % 360;
        float angleRad = (float) Math.toRadians(angleDeg);

        // r = radius, cx and cy = center point, a = angle (radians)
        float x = (float) (cx + r * Math.cos(angleRad));
        float y = (float) (cy + r * Math.sin(angleRad));


        float dx = prevX - x;
        float dy = prevY - y;

        prevX = x;
        prevY = y;

        prevDx = dx;
        prevDy = dy;


        t.getMatrix().setTranslate(dx, dy);
    }
}