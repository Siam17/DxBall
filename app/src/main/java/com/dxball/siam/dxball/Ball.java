package com.dxball.siam.dxball;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

class Ball {

    private boolean _ballAvailable = true;
    private float x,y,radius=20;
    private float xStep = 5;
    private float yStep = -5;

    public boolean isballAvailable() {
        return _ballAvailable;
    }

    public void setBallAvailable(boolean _ballAvailable) {
        this._ballAvailable = _ballAvailable;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getRadius() { return radius; }

    public float getxStep() {
        return xStep;
    }

    public float getyStep() {
        return yStep;
    }

    public void setxStep(float xStep) { this.xStep = xStep; }

    public void setyStep(float yStep) { this.yStep = yStep; }

    public void setRadius(float radius) { this.radius = radius; }

    public void setBall(Canvas canvas, Bar bar) {
        float barMid = (bar.getBarRight()-bar.getBarLeft())/2;
        x = bar.getBarLeft()+barMid;
        y = bar.getBarTop()-radius;
        xStep=5;
        yStep=-5;
    }

    public void drawBall(Canvas canvas, Paint paint){
        canvas.drawCircle(x,y,radius,paint);
    }

    public void nextPos(Canvas canvas, Bar bar, Paint paint) {
        if (x + radius >= canvas.getWidth() || (x + radius == bar.getBarLeft() && y >= bar.getBarTop() && y <= bar.getBarBottom()) && xStep > 0) {
            xStep = -xStep;
        }
        else if (y <= radius) { yStep = -yStep; }
        else if (x <= radius) { xStep = -xStep; }
        else if (y + radius > bar.getBarTop() && x > bar.getBarLeft() && x < bar.getBarRight()) { yStep = -yStep; }
        else if (y > bar.getBarBottom() && y <= canvas.getHeight()) {
            xStep = 0;
            yStep = 0;
            _ballAvailable = false;
        }else if(x+radius == bar.getBarLeft()-20 && y>=bar.getBarTop()){
            xStep = - xStep;
        }else if(x+radius == bar.getBarRight() +20 && y >= bar.getBarTop()){
            xStep = - xStep;
        }

        x += xStep;
        y += yStep;
    }

    public void ballBrickCollision(ArrayList<Bricks> br, Ball ball){
        for(int i=0;i<br.size();i++) {
            if (((ball.getY() - ball.getRadius()) <= br.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= br.get(i).getTop()) && ((ball.getX()) >= br.get(i).getLeft()) && ((ball.getX()) <= br.get(i).getRight())) {
                br.remove(i);
                GameCanvas.score +=10;
                ball.setyStep(-(ball.getyStep()));
            }
            else if(((ball.getY()) <= br.get(i).getBottom()) && ((ball.getY()) >= br.get(i).getTop()) && ((ball.getX() + ball.getRadius()) >= br.get(i).getLeft()) && ((ball.getX() - ball.getRadius()) <= br.get(i).getRight())) {
                br.remove(i);
                GameCanvas.score +=10;
                ball.setxStep(-(ball.getxStep()));
            }
        }
    }
}
