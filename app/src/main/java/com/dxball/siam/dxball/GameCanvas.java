package com.dxball.siam.dxball;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class GameCanvas extends View {
    int count = 0;
    int level;
    public static int life = 2;
    public static boolean gameOver;
    float brickX =0, brickY =0;
    static int score =0;
    Canvas canvas;
    boolean createGame;
    Paint paint;
    Bar bar;
    Ball ball;
    float touchPoint;
    boolean gameStart = true;
    ArrayList<Bricks> bricks =new ArrayList<Bricks>();
    Stage stage = new Stage();

    DxBallActivity db = new DxBallActivity();


    //Setting up Canvus elements.
    public GameCanvas(Context context) {
        super(context);
        paint =new Paint();
        level = 1;
        createGame = true;
        gameOver = false;
        bar = new Bar();
        ball = new Ball();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                if (ball.isballAvailable()) {
                    touchPoint = event.getX();
                    if (touchPoint < v.getWidth() / 2 && bar.getBarLeft()-20 > 0) {
                        bar.setBarLeft(bar.getBarLeft() - 10);
                        if(count <1) {
                            ball.setX(ball.getX() - 10);
                        }
                        touchPoint = -10;
                    } else if (touchPoint >= v.getWidth() / 2 && bar.getBarRight()+20 < v.getWidth()) {
                        bar.setBarLeft(bar.getBarLeft() + 10);
                        if(count <1) {
                            ball.setX(ball.getX() + 10);
                        }
                        touchPoint = -10;
                    }
                }

                if(action == MotionEvent.ACTION_UP){
                    if(count <2){
                        count +=1;
                    }
                }
                return true;
            }
        });


    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);


        if (gameStart) {
            gameStart = false;
            bar.setBar(canvas);
            ball.setBall(canvas, bar);
        }
        canvas.drawRGB(0, 0, 0);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        ball.drawBall(canvas, paint);
        paint.setColor(Color.GREEN);
        bar.drawBar(canvas, paint);

        if(count == 2){
            ball.nextPos(canvas, bar, paint);
        }
        if(count <= 1){
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            canvas.drawText("Move Bar And Tap to Start",canvas.getWidth()/2-canvas.getWidth()/2+80,canvas.getHeight()/2+canvas.getHeight()/8, paint);
        }

        this.canvas = canvas;

        brickX = canvas.getWidth() / 7;
        brickY = (canvas.getHeight() / 15) ;

        if (createGame) {
            createGame = false;
            if (level == 1) {
                stage.stage_level_one(canvas, brickX, brickY, bricks);
            }
            else if (level == 2) {
                stage.stage_level_two(canvas, brickX, brickY, bricks);
            }
            else
                gameOver = true;
        }

        for(int i = 0; i< bricks.size(); i++){
            canvas.drawRect(bricks.get(i).getLeft(), bricks.get(i).getTop(), bricks.get(i).getRight(), bricks.get(i).getBottom(), bricks.get(i).getPaint());
        }

        ball.ballBrickCollision(bricks, ball);

        if(bricks.size() == 0){
            count = -1;
            level += 1;
            createGame = true;
            gameStart = true;
        }

        if( count == -1 ){

            paint.setColor(Color.RED);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            canvas.drawText("Level 2",canvas.getWidth()/2-canvas.getWidth()/9,canvas.getHeight()/2, paint);
            canvas.drawText("Your Score: "+ score,canvas.getWidth()/2-canvas.getWidth()/5,canvas.getHeight()/2+134, paint);
            canvas.drawText("Tap To Next Level",canvas.getWidth()/2-canvas.getWidth()/3+50,canvas.getHeight()/2+300, paint);
        }

        if(ball.isballAvailable() == false){
            ball.setBallAvailable(true);
            count = 0;
            gameStart = true;
            life -= 1;
        }

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        canvas.drawText("Life: " + life, 20, 40, paint);
        canvas.drawText("Score: " + score, canvas.getWidth() - 150 , 40, paint);
        canvas.drawText("LEVEL " + level, canvas.getWidth() / 2 - 60, 40, paint);

        if(life == 0 || gameOver){
            paint.setColor(Color.BLACK);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(80);
            paint.setFakeBoldText(true);
            canvas.drawText("Game Over",canvas.getWidth()/2-canvas.getWidth()/4,canvas.getHeight()/2, paint);
            canvas.drawText("Your Score: "+ score,canvas.getWidth()/2-canvas.getWidth()/3,canvas.getHeight()/2+134, paint);



            //gameOver = false;
            level = 0;
            gameStart=true;







        }

        invalidate();


    }




    }


