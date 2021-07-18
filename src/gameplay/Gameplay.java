package gameplay;

import entities.Apple;
import entities.SnakeBodyPart;
import graphic.Screen;

import java.util.ArrayList;
import java.util.Random;

public class Gameplay {
    private int xCoor = 10;
    private int yCoor = 10;
    private int length =  Screen.INITIAL_LENGTH;
    public boolean right = true, left = false, up = false, down = false;
    private final ArrayList<SnakeBodyPart> snakeBody;
    private final ArrayList<Apple> apples;
    private final Random r;

    public Gameplay() {
        r = new Random();
        snakeBody = new ArrayList<>();
        apples = new ArrayList<>();
    }

    public void actions() {
        if(snakeBody.size() == 0)
            createSnake();
        createApple();
        updateCoor();
        checkHitWall();
        moveSnake();
        checkApple();
    }

    //create snake body
    public void createSnake() {
        snakeBody.add(new SnakeBodyPart(xCoor, yCoor, Screen.TILE_SIZE));
    }

    //create apple
    public void createApple() {
        boolean insideSnake;
        int x, y;
        if (apples.size() == 0) {
            do {
                insideSnake = false;
                x = r.nextInt(Screen.WIDTH / Screen.TILE_SIZE - 1);
                y = r.nextInt((Screen.HEIGHT - Screen.TITLE_HEIGHT) / Screen.TILE_SIZE - 1 )
                        + Screen.TITLE_HEIGHT / Screen.TILE_SIZE;
                for (SnakeBodyPart snakeBodyPart : snakeBody) {
                    if (snakeBodyPart.getxCoor() == x && snakeBodyPart.getyCoor() == y) {
                        insideSnake = true;
                        break;
                    }
                }
            } while (insideSnake);
            apples.add(new Apple(x, y, Screen.TILE_SIZE));
        }
    }

    //change coordinate depending on movement state
    public void updateCoor() {
        if (right) {
            xCoor++;
        }
        else if (left) {
            xCoor--;
        }
        else if (up) {
            yCoor--;
        }
        else if (down) {
            yCoor++;
        }
    }

    //when snake hits the wall, teleport it to the other side
    public void checkHitWall() {
        if (xCoor < 0) {
            xCoor = Screen.WIDTH / Screen.TILE_SIZE - 1;
        }
        else if (yCoor < Screen.TITLE_HEIGHT / Screen.TILE_SIZE) {
            yCoor = Screen.HEIGHT / Screen.TILE_SIZE - 1;
        }
        else if (xCoor > Screen.WIDTH / Screen.TILE_SIZE - 1) {
            xCoor = 0;
        }
        else if (yCoor > Screen.HEIGHT / Screen.TILE_SIZE - 1) {
            yCoor = Screen.TITLE_HEIGHT / Screen.TILE_SIZE;
        }
    }

    //move the snake
    public void moveSnake() {
        //add new body part in front of the head of the snake
        createSnake();
        //remove the tail to keep the length
        if (snakeBody.size() > length) {
            snakeBody.remove(0);
        }
    }

    //when snake eats the apple, add size and remove current apple
    public void checkApple() {
        if (xCoor == apples.get(0).getxCoor() && yCoor == apples.get(0).getyCoor()) {
            length++;
            apples.remove(0);
        }
    }

    //when snake hits itself, return true so graphic.Screen can run stop()
    public boolean isSnakeHit() {
        for (int i = 0; i < snakeBody.size(); i++) {
            SnakeBodyPart snakePart = snakeBody.get(i);
            if (xCoor == snakePart.getxCoor() && yCoor == snakePart.getyCoor() && i != snakeBody.size() - 1)
                return true;
        }
        return false;
    }

    public void reset() {
        snakeBody.clear();
        apples.clear();
        xCoor = 10;
        yCoor = 10;
        length = Screen.INITIAL_LENGTH;
        right = true;
        left = false;
        up = false;
        down = false;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<SnakeBodyPart> getSnakeBody() {
        return snakeBody;
    }

    public ArrayList<Apple> getApples() {
        return apples;
    }
}
