import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame {
    Board board = new Board(); //object of Board class
    SnakeGame(){  //constructor of SnakeGame class
        add(board); // added board to frame
        pack(); //packs the particular parent class to children class
        setResizable(false);//resize of JFrame is not possible
        setVisible(true); //particular JFrame will be visible

        setTitle("Snake Game");



    }
    public static void main(String[] args){

        SnakeGame snakeGame= new SnakeGame(); // //intialize sanke game object


    }
}