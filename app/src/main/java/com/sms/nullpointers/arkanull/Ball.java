package com.sms.nullpointers.arkanull;

public class Ball {

    protected float xSpeed;
    protected float ySpeed;
    private float x;
    private float y;

    public Ball(float x, float y) {
        this.x = x;
        this.y = y;
        createSpeed();
    }

    // Create a random velocity for the ball
    protected void createSpeed() {
        int maxX = 13;
        int minX = 7;
        int maxY = -17;
        int minY = -23;
        int rangeX = maxX - minX + 1;
        int rangeY = maxY - minY + 1;

        xSpeed = (int) (Math.random() * rangeX) + minX;
        ySpeed = (int) (Math.random() * rangeY) + minY;
    }

    // Change direction according to speed
    protected void changeDirection() {
        if (xSpeed > 0 && ySpeed < 0) {
            invertxSpeed();
        } else if (xSpeed < 0 && ySpeed < 0) {
            invertySpeed();
        } else if (xSpeed < 0 && ySpeed > 0) {
            invertxSpeed();
        } else if (xSpeed > 0 && ySpeed > 0) {
            invertySpeed();
        }
    }

    // Increase speed based on level
    protected void increaseSpeed(int level) {
        xSpeed = xSpeed + (1 * level);
        ySpeed = ySpeed - (1 * level);
    }

    // Change direction depending on which wall it touched and speed
    protected void changeDirection(String wall) {
        if (xSpeed > 0 && ySpeed < 0 && wall.equals("prava")) {
            invertxSpeed();
        } else if (xSpeed > 0 && ySpeed < 0 && wall.equals("hore")) {
            invertySpeed();
        } else if (xSpeed < 0 && ySpeed < 0 && wall.equals("hore")) {
            invertySpeed();
        } else if (xSpeed < 0 && ySpeed < 0 && wall.equals("lava")) {
            invertxSpeed();
        } else if (xSpeed < 0 && ySpeed > 0 && wall.equals("lava")) {
            invertxSpeed();
        } else if (xSpeed > 0 && ySpeed > 0 && wall.equals("dole")) {
            invertySpeed();
        } else if (xSpeed > 0 && ySpeed > 0 && wall.equals("prava")) {
            invertxSpeed();
        }
    }

    // Find out if the ball is close
    private boolean isNear(float ax, float ay, float bx, float by) {
        bx += 12;
        by += 11;
        if ((Math.sqrt(Math.pow((ax + 50) - bx, 2) + Math.pow(ay - by, 2))) < 80) {
            return true;
        } else if ((Math.sqrt(Math.pow((ax + 100) - bx, 2) + Math.pow(ay - by, 2))) < 60) {
            return true;
        } else if ((Math.sqrt(Math.pow((ax + 150) - bx, 2) + Math.pow(ay - by, 2))) < 60) {
            return true;
        }
        return false;
    }

    // Find out if the ball is close to a brick
    private boolean isNearBrick(float ax, float ay, float bx, float by) {
        bx += 12;
        by += 11;
        double d = Math.sqrt(Math.pow((ax + 50) - bx, 2) + Math.pow((ay + 40) - by, 2));
        return d < 80;
    }



    // If the ball collides with the fall, it will change direction
    protected boolean ImpactPaddle(float xPaddle, float yPaddle) {
        if (isNear(xPaddle, yPaddle, getX(), getY())){
            changeDirection();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns true when detects a collision with a brick
     * If the ball collides with a brick while there isn't a power up effect, it will change direction
     * If the ball collides with a brick while there is a power up effect, it will NOT change direction
     * @param xBrick position x of the brick
     * @param yBrick position y of the brick
     * @param powerUp boolean that indicates if the power up is in effect
     * @return
     */
    protected boolean ImpactBrick(float xBrick, float yBrick, boolean powerUp) {
        if(!powerUp) {
            if (isNearBrick(xBrick, yBrick, getX(), getY())) {
                changeDirection();
                return true;
            }
        }
        else if(powerUp){
            if (isNearBrick(xBrick, yBrick, getX(), getY())) {

                return true;
            }
        }
        return false;
    }

    // Moves by the specified speed
    protected void setSpeed() {
        x = x + xSpeed;
        y = y + ySpeed;
    }

    public void invertxSpeed() {
        xSpeed = -xSpeed;
    }

    public void invertySpeed() {
        ySpeed = -ySpeed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }
}
