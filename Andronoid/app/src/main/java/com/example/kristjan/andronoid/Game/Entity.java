package com.example.kristjan.andronoid.Game;

import android.graphics.RectF;

/**
 *
 */

public class Entity {

    private RectF rect;
    private int width;
    private float height;
    private float startX;
    private float startY;
    private float xBorder;
    private float speed;
    public float xPos;
    public boolean isMoving = false;
    public Entity(int screenX, int screenY) {

        width = 180;
        height = 20;


        startX = screenX / 2;
        startY = screenY - 20;
        xBorder = screenX;
        rect = new RectF(startX, startY, startX + width, startY + height);


        speed = 350;

    }

    public RectF getRectCoords() {
        return rect;
    }

    public float getStartX() {

        return rect.left;
    }
    public void reset(float screenX,float screenY){
        rect.left =   (xBorder /2) -(width/2);
        rect.right = ((xBorder /2) -(width/2))  + width;
    }

    public void update(long fps){

        if (isMoving == true) {

            rect.left =   xPos;
            rect.right = xPos + width;


        }
    }
}
