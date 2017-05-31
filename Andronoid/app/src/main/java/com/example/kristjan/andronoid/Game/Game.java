package com.example.kristjan.andronoid.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.kristjan.andronoid.R;

import java.io.IOException;

public class  Game extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    int screenX;
    int screenY;
    float xPos;
    private String name = null;

    public static int scoreTotal = 0;

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_game);
       gameView = new GameView(this);
        setContentView(gameView);





    }


    class GameView extends SurfaceView implements Runnable {

        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;

        boolean paused = true;

        long fps;
        private long timeThisFrame;
        int screenX;
        int screenY;
        int count = 3;

        //initsialirisuerm paddle
        Entity paddle;
        Ball ball;
        powerup powerup;
        //initsialirisuerm ball
        //initsialirisuerm bricks
        Brick[] bricks = new Brick[200];
        int numBricks = 0;
        // The score
        int score = 0;

        // Lives
        int lives = 3;

        Canvas canvas;

        Paint paint;

        long startTime;
        long timer;


        public GameView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);
            paint = new Paint();
            ourHolder = getHolder();

            // Get a Display object to access screen details
            Display display = getWindowManager().getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            //sozdaem paddle
            paddle = new Entity(screenX,screenY);
            ball = new Ball(screenX,screenY);
            powerup = new powerup(screenX,screenY);

            //sozdaem ball
            //sozdaem bricks
            createBricksAndRestart();


        }
        public void createBricksAndRestart() {

            //if (count <= 3){
            startTime = 0;
            timer = 0;
            // Put the ball back to the start

            ball.reset(screenX, screenY);
            paddle.reset(screenX, screenY);
            int brickWidth = screenX / 8;
            int brickHeight = screenY / 10;

            numBricks = 0;
            for (int column = 0; column < 8; column++) {
                for (int row = 0; row < 3; row++) {
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numBricks++;

                }
            }
            // if game over reset scores and lives
            if (lives == 0) {
                score = 0;
                lives = 3;

            }
        }

        @Override
        public void run() {
            while (playing) {
                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();
                // Update the frame
                if(!paused){

                    update();
                }
                // Draw the frame
                draw();
                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.


                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                    timer++;

                }

            }

        }
        // Everything that needs to be updated goes in here
        // Movement, collision detection etc.
        public void update() {

            // Move the paddle if required
            paddle.xPos = xPos;

           paddle.update(fps);

           ball.update(fps);
            powerup.update(fps);

            int trans;
            int trans2;
            //trans = numBricks / 3;
            //trans2 = numBricks - trans;
            //int poweredBrickCount = 0;

            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                        bricks[i].setInvisible();
                        ball.reverseYVelocity();
                        score = score + 10;
                        saveHighScore(score);


                    }
                }
            }


            // Check for ball colliding with paddle
            if (RectF.intersects(paddle.getRectCoords(), ball.getRect())) {
                ball.setRandomXVelocity();
                ball.reverseYVelocity();
                ball.clearObstacleY(paddle.getRectCoords().top - 2);

            }
            // if the ball hits bottom side
            if (ball.getRect().bottom > screenY) {
                //ball.reset(screenX, screenY - 50);
                ball.reverseXVelocity();
                ball.clearObstacleY(screenY - 2);

                // Lose a life
                //lives--;

                if (lives == 0) {
                    paused = true;
                    createBricksAndRestart();
                }
            }

            if (ball.getRect().top < 0)

            {
                ball.reverseYVelocity();
                ball.clearObstacleY(12);

            }
            // If the ball hits left wall bounce
            if (ball.getRect().left < 0)
            {
                ball.reverseXVelocity();
                ball.clearObstacleX(2);
            }

            // If the ball hits right wall bounce
            if (ball.getRect().right > screenX - 10) {
                ball.reverseXVelocity();
                ball.clearObstacleX(screenX - 22);

            }

            if (paddle.getRectCoords().right > screenX - 5 || paddle.getRectCoords().left < 5) {
                paddle.isMoving = false;
            } else {
                paddle.isMoving = true;
            }



            if (score == numBricks * 10) {
                paused = true;
                count++;
                createBricksAndRestart();
            }

        }

        // Draw the newly updated scene
        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.argb(255, 26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 255, 255, 255));

                // Draw the paddle
                canvas.drawRect(paddle.getRectCoords(), paint);

                // Draw the ball
                canvas.drawRect(ball.getRect(), paint);

                // Change the brush color for drawing


                paint.setColor(Color.argb(255, 249, 129, 0));
                int trans;
                int trans2;
                trans = numBricks / 3;
                trans2 = numBricks - trans;

                // Draw the bricks if visible
                //orange
                for (int i = 0; i < trans + 1 ; i++) {
                    if (bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }
                //black
                paint.setColor(Color.argb(150,000, 000, 000));
                // Draw the bricks if visible
                for (int i = trans + 1 ; i < trans2 - 1; i++) {
                    if (bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }
                //red
                paint.setColor(Color.argb(255, 255, 000, 000));
                // Draw the bricks if visible
                for (int i = trans2 -1 ; i < numBricks; i++) {
                    if (bricks[i].getVisibility()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 255, 255, 255));

                // Draw the score
                paint.setTextSize(40);
                canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);
                canvas.drawText("FPS: " + fps,screenX - 150, 50, paint);
                canvas.drawText("Timer: " + timer,screenX - 350, 50, paint);
                // Has the player cleared the screen?
               // if (score == numBricks * 10) {
                  //  paint.setTextSize(90);
                  //  canvas.drawText("CLEARED!", 10, screenY / 2, paint);
               // }

                // Has the player lost?
               //if (lives <= 0) {
                   // paint.setTextSize(90);
                   // canvas.drawText("YOU HAVE LOST!", 10, screenY / 2, paint);

                //}

                // Draw everything to the screen
                paint.setColor(Color.argb(255, 255, 255, 255));

                // Draw the paddle
                canvas.drawRect(powerup.getRectf(), paint);

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        // If SimpleGameEngine Activity is started then
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            float xCoord = motionEvent.getX();
            xPos = xCoord - 90;

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_MOVE:
                    paused = false;
                    paddle.isMoving = true;


                    break;


                case MotionEvent.ACTION_UP:
                    paddle.isMoving = false;


                    break;
            }
            return true;
        }
        public void saveHighScore(int score) {
            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            int highScore = 0;
            if (getIntent().hasExtra("name")) {
                name = getIntent().getExtras().getString("name");
                highScore = sharedPreferences.getInt(name + "score", 0);
            }

            if (score > highScore) {
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(name + "score", score);
                editor.apply();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();

    }


}
