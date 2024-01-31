import javax.swing.*;
public class SnakeGame
{
    public static void main(String[] args)
    {
        int boardwidth = 600;
        int boardheight = boardwidth;

        JFrame frame = new JFrame("snake");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        snakegame snakegames = new snakegame(boardwidth,boardheight);
        frame.add(snakegames);
        frame.pack();
        snakegames.requestFocus();




    }
}