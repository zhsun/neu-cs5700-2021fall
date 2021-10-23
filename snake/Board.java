package snake.app;

import static snake.app.Config.BOARD_SIZE;
import static snake.app.Config.UNIT_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel {
  private static final Image DOT = new ImageIcon("snake/images/dot.png").getImage();
  private static final Image APPLE = new ImageIcon("snake/images/apple.png").getImage();
  private static final Image HEAD = new ImageIcon("snake/images/head.png").getImage();

  public Board() {
    int size = UNIT_SIZE * BOARD_SIZE;
    setPreferredSize(new Dimension(size, size));
    setBorder(BorderFactory.createLineBorder(Color.BLUE));
    setBackground(Color.BLACK);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    renderApple(g);
    renderSnake(g);
    if (GameState.get().isGameOver()) {
      renderGameOver(g);
    }
  }

  private void renderApple(Graphics g) {
    Position p = GameState.get().getApplePosition();
    render(g, APPLE, p);
  }

  private void renderSnake(Graphics g) {
    GameState.get()
        .getSnakePosition()
        .findFirst()
        .ifPresent(p -> render(g, HEAD, p));
    GameState.get()
        .getSnakePosition()
        .skip(1)
        .forEach(p -> render(g, DOT, p));
  }

  private void renderGameOver(Graphics g) {
    g.setColor(Color.RED);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
    g.drawString("Game Over", 50, 50);
  }

  private void render(Graphics g, Image image, Position p) {
    g.drawImage(image, p.getCol() * UNIT_SIZE, p.getRow() * UNIT_SIZE, this);
  }
}
