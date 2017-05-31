package com.example.kristjan.andronoid.Game;

import android.graphics.RectF;

/**
 * Created by Kristjan on 22.04.2017.
 */

public class powerup {
    RectF rectf;
    float ballWidth = 40;
    float ballHeight = 40;
    final float DROPSPEED = 500;



    public  powerup (float x,float y) {

        rectf = new RectF();
    }
    public void update(long fps) {
        rectf.bottom = rectf.bottom + (DROPSPEED  / fps);
    }
    public RectF getRectf(){
        return rectf;
    }
}
