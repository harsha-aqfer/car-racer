import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class RoadFighter extends JPanel implements ActionListener {

    private static final int B_WIDTH, B_HEIGHT, DELAY, CARD_SPEED;

    static {
        B_WIDTH = 800;
        B_HEIGHT = 600;
        DELAY = 8;
        CARD_SPEED = 3;
    }

    private Image backGround, redCar, greenCar, winFlag;

    private boolean leftDirection = false, rightDirection = false;
    private boolean isRunning = true;
    private int RED_X, RED_Y;
    private int bg = 0, bgh = -B_HEIGHT, count = 0;
    private Timer timer;

    private final int[] green_car_x = new int[4];
    private final int[] green_car_y = new int[4];

    RoadFighter() {
        initBoard();
    }

    private void initBoard() {
        this.setLayout(new FlowLayout());
        this.setFocusable(true);

        this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.addKeyListener(new TAdapter());
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iir = new ImageIcon("src/main/resources/redcar.png");
        redCar = iir.getImage();
        ImageIcon iig = new ImageIcon("src/main/resources/greencar.png");
        greenCar = iig.getImage();
        ImageIcon bg = new ImageIcon("src/main/resources/racetrack.png");
        backGround = bg.getImage();
        ImageIcon w = new ImageIcon("src/main/resources/win2.png");
        winFlag = w.getImage();
    }

    private void initGame() {
        for (int i = 0; i < 4; i++) {
            int p = (int) (Math.random() * 100) % 5;
            green_car_x[i] = getXCoordinate(p);
            green_car_y[i] = (i + 1) * -150;
        }
        RED_X = 375;
        RED_Y = 500;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.drawImage(backGround, 0, bg, this);
        g.drawImage(backGround, 0, bgh, this);
        if (count == 5) g.drawImage(winFlag, 220, bgh, 350, 75, this);
        for (int i = 0; i < 4; i++) {
            g.drawImage(greenCar, green_car_x[i], green_car_y[i], this);
        }
        g.drawImage(redCar, RED_X, RED_Y, this);
    }

    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            checkCollision();
        }
        repaint();
    }

    private void move() {
        //Handle background image scrolling
        if (bg == B_HEIGHT) {
            bg = -B_HEIGHT;
            count++;
        } else bg++;
        if (bgh == B_HEIGHT) bgh = -B_HEIGHT;
        else bgh++;

        //Handle the red car coordinates based on the key event direction
        if (leftDirection) {
            RED_X -= 1;
        } else if (rightDirection) {
            RED_X += 1;
        }

        //Handle the dashing cars coordinates
        for (int i = 0; i < 4; i++) {
            if (green_car_y[i] > 600) {
                int p = (int) (Math.random() * 100) % 5;
                green_car_x[i] = getXCoordinate(p);
                green_car_y[i] = -50;
            } else {
                green_car_y[i] += CARD_SPEED;
            }
        }
    }

    private void checkCollision() {
        if (RED_X < 210 || RED_X > 540) isRunning = false;
        if (count == 5 && bgh == RED_Y) isRunning = false;
        for (int i = 0; i < 4; i++) {
//            if (Math.abs(RED_Y - dash_car_y[i]) < 46 && Math.abs(RED_X - dash_car_x[i]) <= 46) {
//                isRunning = false;
//                break;
//            }
            //Calculate the distance between the centres of red car and green car
            int distX = Math.abs(RED_X - green_car_x[i]);
            int distY = Math.abs(RED_Y - green_car_y[i]);
            if (Math.sqrt((distX * distX) + (distY * distY)) <= 50) {
            isRunning = false;
            break;
        }
    }
        if (!isRunning) {
            timer.stop();
            finish();
        }
    }

    private void finish() {
        //System.exit(ABORT);
    }

    private int getXCoordinate(int p) {
        if (p == 0) return 225;
        else if (p == 1) return 300;
        else if (p == 2) return 375;
        else if (p == 3) return 450;
        else return 525;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                leftDirection = true;
                rightDirection = false;
            }

            if (key == KeyEvent.VK_RIGHT) {
                leftDirection = false;
                rightDirection = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            leftDirection = false;
            rightDirection = false;
        }
    }
}
