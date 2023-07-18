import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    //intialize the dimension of board
    int B_HEIGHT = 400;
    int B_WIDTH = 400;
    int MAX_DOTS = 1600; // 400/10*400/10
    int DOT_SIZE = 10; // dot size of snake
    int DOTS; //NO. of dots of snake
    int x[] = new int[MAX_DOTS]; //declaration of coordinates of snake
    int y[] = new int[MAX_DOTS];

    int apple_x;
    int apple_y;

    Image body, head, apple; //objects

    Timer timer; //Timer object--> make snake move
    int DELAY = 200; //after 200msec timer get incremented means snake moves 200ms per DOT

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;

    Board() { //constructor of Board

        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true); //perticular board to be focusable

        setPreferredSize(new Dimension(B_HEIGHT, B_WIDTH)); //inside the board i am going set the
        // preferredsize and create a new dimesnion provide height and width of board

        setBackground(Color.DARK_GRAY); //background color of board
        intiGame(); //call this method to constructor
        loadImages();

    }

    public void intiGame() { //intialize the snake
        DOTS = 3; //intialize the DOTS of snake
        //intialize snake's position
        x[0] = 50;
        y[0] = 50;
        for (int i = 0; i < DOTS; i++) {
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }

        locateApple();
        timer = new Timer(DELAY, this); // "this" is actionlistner, board class is behave as an actionlistner and implement actionlistner
        timer.start();
    }

    // load images form resources folder to Image objects
    public void loadImages() {
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage(); //we get the Image from bodyIcon to body

        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();

        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

    //draw images at snake and apple position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }

    //draw image
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this); //this --> we need observer is gping to be board object

            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        } else {
            gameOver(g);
            timer.stop();
        }
    }

    //randomizing the position of apple
    public void locateApple() {
        apple_x = ((int) (Math.random() * 39)) * DOT_SIZE;
        apple_y = ((int) (Math.random() * 39)) * DOT_SIZE;
    }

    //CHECK collision with body and border
    public void checkCollision() {
        //check collision with body
        for (int i = 1; i < DOTS; i++) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        //check collision with border
        if (x[0] < 0) {
            inGame = false;
        }
        if (x[0] >= B_WIDTH) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }
    }

    //Display Gameover message
    public void gameOver(Graphics g) {
        String msg = "Game Over";
        String remsg="Press Space to Resart the Game";
        int score = (DOTS - 3) * 100;
        String scoremsg = "score:" + Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fontMetrics.stringWidth(msg)) / 2, B_HEIGHT / 4);
        g.drawString(scoremsg, (B_WIDTH - fontMetrics.stringWidth(msg)) / 2, 3 * (B_HEIGHT / 4));
        g.drawString(remsg, B_WIDTH/4, B_HEIGHT / 2);


    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) { //whenever actionevent is generated we need to move function
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint(); //call the repeating the function

    }

    public void move() {
        for (int i = DOTS - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }


    //make snake eat food
    public void checkApple() {
        if (apple_x == x[0] && apple_y == y[0]) {
            DOTS++; //size of snake will be incraese
            locateApple(); //apple position randomize again
        }
    }

    //implement controls
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {

            int key = keyEvent.getKeyCode();
            if(inGame==false) {
                if (key == keyEvent.VK_SPACE) {
                    DOTS = 3;
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                    rightDirection = false;
                    //move();
                    inGame = true;
                    intiGame();
                    timer.start();

                }
            }
                if (key == keyEvent.VK_LEFT && !rightDirection) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if (key == keyEvent.VK_RIGHT && !leftDirection) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if (key == keyEvent.VK_UP && !downDirection) {
                    leftDirection = false;
                    upDirection = true;
                    rightDirection = false;
                }

                if (key == keyEvent.VK_DOWN && !upDirection) {
                    leftDirection = false;
                    rightDirection = false;
                    downDirection = true;
                }

        }
    }
}




