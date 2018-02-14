package com.example.steli.hw_2_papoutsakis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;


import java.util.Random;

/**
 * create a drawable view to display the face
 *
 * @Author Stelios Papoutsakis
 */

public class FaceCanvas extends SurfaceView {

    private int skinTone; // skin tone of face
    private int eyeColor; // eye color
    private int hairColor; // hair color int
    private int hairStyle; // int ued to pick between the 3 hair styles
    int cX; // center width
    int cY; // center height
    Paint paint = new Paint(); // paint object used to color face features


    /**
     * @ctors from the SurfaceView class
     */

    public FaceCanvas(Context context) {
        super(context);
        startUp();
    }

    public FaceCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        startUp();
    }

    public FaceCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startUp();

    }

    /*
called at creation of object FaceCanvas
@return void
*/
    private void startUp() {
        setWillNotDraw(false);
        randomize();
    }

    /*
randomizes the color of each feature and picks a random hairstyle
@return void
*/
    public void randomize() {
        Random random = new Random();
        int randomFactor = (random.nextInt(50) + 1);
        int randomFactor2 = (random.nextInt(200) + 1);
        int randomFactor3 = (random.nextInt(200) + 1);
        this.skinTone = Color.rgb(210 - randomFactor, 255 - randomFactor2, 255 - randomFactor3);
        this.eyeColor = Color.rgb(90 + randomFactor, 45 + randomFactor2, 45 + randomFactor3);
        this.hairColor = Color.rgb(120 + randomFactor, 20 + randomFactor2, 200 - randomFactor3);
        this.hairStyle = random.nextInt(3);

    }

    /*
    overloaded method that delegates its work to other method
    @param canvas object
    @return void
    */
    public void onDraw(Canvas canvas) {
        this.cX = (int) (getWidth() / 2.0); // center width
        this.cY = (int) (getHeight() / 2.0); // center height
        RectF Oval = new RectF(this.cX - 450, this.cY - 600, this.cX + 450, this.cY + 600);

        // delegates to other methods to draw features
        drawFace(canvas, Oval);
        drawEyes(canvas);
        drawHair(canvas, Oval);

    }

    /*
    draws the face on given canvas
    @param Canvas and Oval objects
     @return void
    */
    public void drawFace(Canvas canvas, RectF Oval) {
        // draws oval shape
        this.paint.setColor(this.skinTone);
        canvas.drawOval(Oval, this.paint);

        // draws mouth
        this.paint.setColor(Color.RED);
        canvas.drawOval(cX - 100, cY + 300, cX + 100, cY + 250, this.paint);


    }

    /*
        draws the eyes on given canvas
        @param Canvas objects
         @return void
        */
    public void drawEyes(Canvas canvas) {
        // draws the background of the eye
        this.paint.setColor(Color.WHITE);
        canvas.drawCircle(this.cX - 100, this.cY - 180, 50, this.paint);
        canvas.drawCircle(this.cX + 100, this.cY - 180, 50, this.paint);

        // draws the iris of the eye
        this.paint.setColor(eyeColor);
        canvas.drawCircle(cX - 100, cY - 180, 35, this.paint);
        canvas.drawCircle(cX + 100, cY - 180, 35, this.paint);

        // draws the pupil
        this.paint.setColor(Color.BLACK);
        canvas.drawCircle(cX - 100, cY - 180, 10, this.paint);
        canvas.drawCircle(cX + 100, cY - 180, 10, this.paint);
    }

    /*
        draws the three different hairstyles on given canvas
        @param Canvas and Oval objects
         @return void
        */
    public void drawHair(Canvas canvas, RectF Oval) {
        this.paint.setColor(this.hairColor);

        // hairstyle 1, sweep bangs style
        if (this.hairStyle == 0) {
            for (int i = 0; i < 200; i++) {

                canvas.drawLine(findX((Math.PI / 2 + .5) - i * .005, Oval), findY((Math.PI / 2 + .5) - i * .005, Oval),
                        findX(i * .005, Oval) + 50, findY(i * .005, Oval) + 150, this.paint);


            }
        }
        // hairstyle 2, balding style
        if (this.hairStyle == 1) {
            for (int i = 0; i < 151; i++) {

                int x1 = findX((3.5 / 4.0) * Math.PI - i * (.25 * Math.PI / 150), Oval);
                int y1 = findY((3.5 / 4.0) * Math.PI - i * (.25 * Math.PI / 150), Oval);
                int x2 = findX((.5 / 4.0) * Math.PI + i * (.25 * Math.PI / 150), Oval);
                int y2 = findY((.5 / 4.0) * Math.PI + i * (.25 * Math.PI / 150), Oval);
                canvas.drawLine(x1, y1, x1 + 25, y1 + 25, this.paint);
                canvas.drawLine(x2, y2, x2 - 25, y2 + 25, this.paint);

            }

        }
        // hairstyle 3, mohawk style
        if (this.hairStyle == 2) {
            for (int i = 0; i < 101; i++) {
                int x = findX((Math.PI + .3) / 2 - i * (.3 / 100), Oval);
                int y = findY((Math.PI + .3) / 2 - i * (.3 / 100), Oval);
                canvas.drawLine(x, y, x, y - 200, this.paint);
            }
        }


    }
    /*
     Date: 11 Feb 2018
     Problem: didn't know how to find a point on a oval
      Resource:https://math.stackexchange.com/questions/432902/how-to-get-the-radius-of-an-ellipse-at-a-specific-angle-by-knowing-its-semi-majo
      Solution: I used the technique used in this post to make a method to find the x and y coordinates respectivly
*/

    /* @param an oval object,  angle in radians to the points, angle 0 starts at 3 O'clock
    * @returns x coordinate on a oval
    * */
    public int findX(double angle, RectF Oval) {
        return (int) (cX + Oval.width() / 2 * Math.cos(angle));
    }

    /* @param an oval object,  angle in radians to the points, angle 0 starts at 3 O'clock,
   * @returns y coordinate on a oval
   * */
    public int findY(double angle, RectF Oval) {
        return (int) (cY + Oval.height() / 2 * Math.sin(angle + Math.PI));
    }

    // setter for skin tone
    public void setSkinTone(int skinTone) {
        this.skinTone = skinTone;
    }

    // setter for eye color
    public void setEyeColor(int eyeColor) {
        this.eyeColor = eyeColor;
    }

    // setter for hair color
    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    // setter for hairStyle
    public void setHairStyle(int hairStyle) {
        this.hairStyle = hairStyle;
    }

    public int getHairStyle() {
        return hairStyle;
    }

    // getter for skinTone int
    public int getSkinTone() {
        return skinTone;
    }

    // getter for eye color
    public int getEyeColor() {
        return eyeColor;
    }

    // getter for haircolor
    public int getHairColor() {
        return hairColor;
    }

    /*
    takes in a color value and a color select int
    0-> red changes
    1-> green changes
    2->blue changes
    also takes in a featureSelect,  0->eyes,1->hair,2->skin
    @return the int value of the new color
     */
    public int featureColorConverter(int color, int colorSelect, int featureSelect) {
        int red = 0;
        int green = 0;
        int blue = 0;
        // for eyeColor
        if (featureSelect == 0) {
            switch (colorSelect) {
                case 0:
                    green = Color.green(this.eyeColor);
                    blue = Color.blue(this.eyeColor);
                    return Color.argb(255, color, green, blue);
                case 1:
                    red = Color.red(this.eyeColor);
                    blue = Color.blue(this.eyeColor);
                    return Color.argb(255, red, color, blue);
                case 2:
                    red = Color.red(this.eyeColor);
                    green = Color.green(this.eyeColor);
                    return Color.argb(255, red, green, color);


            }
        }

        //for hairColor
        if (featureSelect == 1) {
            switch (colorSelect) {
                case 0:
                    green = Color.green(this.hairColor);
                    blue = Color.blue(this.hairColor);
                    return Color.argb(255, color, green, blue);
                case 1:
                    red = Color.red(this.hairColor);
                    blue = Color.blue(this.hairColor);
                    return Color.argb(255, red, color, blue);
                case 2:
                    red = Color.red(this.hairColor);
                    green = Color.green(this.hairColor);
                    return Color.argb(255, red, green, color);


            }
        }
        //for Skin tone
        if (featureSelect == 2) {
            switch (colorSelect) {
                case 0:
                    green = Color.green(this.skinTone);
                    blue = Color.blue(this.skinTone);
                    return Color.argb(255, color, green, blue);
                case 1:
                    red = Color.red(this.skinTone);
                    blue = Color.blue(this.skinTone);
                    return Color.argb(255, red, color, blue);
                case 2:
                    red = Color.red(this.skinTone);
                    green = Color.green(this.skinTone);
                    return Color.argb(255, red, green, color);


            }
        }
        return 0;
    }


}
