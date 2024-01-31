import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends JFrame {
    private static final int TILE_SIZE = 20;
    private static final int GRID_SIZE = 20;

    private int[][] grid;
    private int snakeLength;
    private int[] snakeX, snakeY;
    private int direction; // 0: up, 1: right, 2: down, 3: left

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        grid = new int[GRID_SIZE][GRID_SIZE];
        snakeX = new int[GRID_SIZE * GRID_SIZE];
        snakeY = new int[GRID_SIZE * GRID_SIZE];
        snakeLength = 1;

        direction = 1; // initially moving right

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                checkCollision();
                checkApple();
                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (direction != 2) direction = 0;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 3) direction = 1;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 0) direction = 2;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (direction != 1) direction = 3;
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        setFocusable(true);
    }

    private void move() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 0:
                snakeY[0] -= 1;
                break;
            case 1:
                snakeX[0] += 1;
                break;
            case 2:
                snakeY[0] += 1;
                break;
            case 3:
                snakeX[0] -= 1;
                break;
        }
    }

    private void checkCollision() {
        if (snakeX[0] < 0 || snakeX[0] >= GRID_SIZE || snakeY[0] < 0 || snakeY[0] >= GRID_SIZE) {
            gameOver();
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                gameOver();
            }
        }
    }

    private void checkApple() {
        // For simplicity, randomly spawn an "apple" in an empty cell
        int appleX, appleY;
        do {
            appleX = (int) (Math.random() * GRID_SIZE);
            appleY = (int) (Math.random() * GRID_SIZE);
        } while (grid[appleX][appleY] != 0);

        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            snakeLength++;
        }

        grid[appleX][appleY] = 1;
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < snakeLength; i++) {
            g.setColor(i == 0 ? Color.GREEN : Color.RED);
            g.fillRect(snakeX[i] * TILE_SIZE, snakeY[i] * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        g.setColor(Color.BLUE);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 1) {
                    g.fillOval(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        Toolkit.getDefaultToolkit().sync(); // Synchronize the display
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}
