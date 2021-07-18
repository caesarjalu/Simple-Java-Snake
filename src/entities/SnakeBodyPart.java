package entities;

import java.awt.*;

public class SnakeBodyPart {
    private final int xCoor, yCoor, width, height;

    public SnakeBodyPart(int xCoor, int yCoor, int tileSize) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        width = tileSize;
        height = tileSize;
    }

    public void drawHead(Graphics g) {
        g.fillOval(xCoor * width, yCoor * height, width, height);
    }

    public void drawBody(Graphics g) {
        g.fillRect(xCoor * width + 2, yCoor * height + 2, width - 4, height - 4);
    }

    public void drawTail(Graphics g) {
        g.fillRect(xCoor * width + 3, yCoor * height + 3, width - 6, height - 6);
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }
}
