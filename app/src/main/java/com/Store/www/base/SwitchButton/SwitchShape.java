package com.Store.www.base.SwitchButton;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by www on 2018/12/7.
 * 开关按钮样式
 */

public class SwitchShape {
    private float x = 0, y = 0;

    private ShapeDrawable shape;

    private int color;

    private float width,height;

    public SwitchShape(ShapeDrawable s)

    {

        shape = s;

    }



    public float getX()

    {

        return x;

    }



    public void setX(float x)

    {

        this.x = x;

    }



    public float getY()

    {

        return y;

    }



    public void setY(float y)

    {

        this.y = y;

    }



    public ShapeDrawable getShape()

    {

        return shape;

    }



    public void setShape(ShapeDrawable shape)

    {

        this.shape = shape;

    }



    public int getColor() {

        return color;

    }



    public void setColor(int color) {

        this.color = color;

    }







    public float getWidth() {

        return width;

    }



    public void setWidth(float width) {

        this.width = width;

    }



    public float getHeight() {

        return height;

    }



    public void setHeight(float height) {

        this.height = height;

    }



    public void setScale(float x){

        Shape s = shape.getShape();

        shape.setBounds(0,(int)(height-height*x)/2,(int)(width*x),(int)(height*x));



        //Log.d("sqq", "width: " + width + " height:" + height);

        //s.resize(x*width, s.getHeight());

    }
}
