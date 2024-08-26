package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Physics extends JPanel implements KeyListener, ActionListener {  

    private boolean play = false;
    public int score = 0;

    private int totalLadrillos = 40;

    private Timer timer;
    private int delay = 9;

    private int playerX = 300;

    private int bolaPosX = 290;
    private int bolaPosY = 350;
    private int bolaDirX = getRandomNumX();
    private int bolaDirY = getRandomNumY();

    private Bricks mapPlay;

    public Physics() {
        mapPlay = new Bricks(4, 10);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(1, 1, 692, 592);
        mapPlay.dibujar((Graphics2D) graphics, Color.black);
        graphics.setColor(Color.black);
        graphics.fillRect(playerX, 550, 100, 8);

        if (play == false) {
            graphics.setColor(Color.black);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("ENTER", 203, 350);

        } else { 
            graphics.setColor(Color.RED);
            graphics.fillOval(bolaPosX, bolaPosY, 20, 20);
        }
        
        if (totalLadrillos <= 0) {
            play = false;
            bolaDirX = 0;
            bolaDirY = 0;

            graphics.setColor(Color.black);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("GANASTE", 230, 300);

            graphics.setColor(Color.black);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("ENTER", 230, 330);

            graphics.setColor(Color.black);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Score: " + score, 490, 30);

            mapPlay.dibujar((Graphics2D) graphics, Color.BLACK);

            graphics.setColor(Color.black);
            graphics.fillRect(playerX, 550, 100, 8);

            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("ENTER", 203, 350);
        }

        if (bolaPosY > 570) { 
            play = false;
            bolaDirX = 0;
            bolaDirY = 0;

            graphics.setColor(Color.black);
            graphics.fillOval(bolaPosX, bolaPosY, 23, 23);

            graphics.setColor(Color.black);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("PERDISTE ", 240, 300);

            graphics.setColor(Color.black);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Score: " + score, 490, 30);

            mapPlay.dibujar((Graphics2D) graphics, Color.BLACK);

            graphics.setColor(Color.black);
            graphics.fillRect(playerX, 550, 100, 8);

        }
        graphics.dispose();
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                    if (playerX >= 600) {
                        playerX = 600;
                    } else {
                        moveRight();
                    }
                break;
                
            case KeyEvent.VK_LEFT:
                    if (playerX < 10) {
                        playerX = 10;
                    } else {
                        moveLeft();
                    }
                break;
                
        }
       
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                playerX = 310;
                bolaPosX = 290;
                bolaPosY = 350;
                bolaDirX = getRandomNumX();
                bolaDirY = getRandomNumY();
                totalLadrillos = 40;

                mapPlay = new Bricks(4, 10);
                score = 0;
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();

        if (play) {
            if (new Rectangle(bolaPosX, bolaPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 80))) {
                bolaDirY = -bolaDirY;
            }

            A:
            for (int i = 0; i < mapPlay.mapa.length; i++) {
                for (int j = 0; j < mapPlay.mapa[0].length; j++) {
                    if (mapPlay.mapa[i][j] > 0) {
                        int brickX = j * mapPlay.AnchoLadrillo + 80;
                        int brickY = i * mapPlay.AlturaLadrillo + 50;
                        int brickWidth = mapPlay.AnchoLadrillo;
                        int brickHeight = mapPlay.AlturaLadrillo;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(bolaPosX, bolaPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            mapPlay.setBrickValue(0, i, j);
                            totalLadrillos--;
                            score += 1;

                            if (bolaPosX + 19 <= brickRect.x || bolaPosX + 1 >= brickRect.x + brickRect.width) {
                                bolaDirX = -bolaDirX;
                            } else {
                                bolaDirY = -bolaDirY;
                            }
                            break A;
                        }
                    }
                }
            }

            bolaPosX += bolaDirX;
            bolaPosY += bolaDirY;

            if (bolaPosX < 0) {  
                bolaDirX = -bolaDirX;
            }
            if (bolaPosY < 0) { 
                bolaDirY = -bolaDirY;
            }
            if (bolaPosX > 670) { 
                bolaDirX = -bolaDirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    public int getRandomNumY() {
        Random random = new Random();
        int max = -1;
        int min = -4;
        int randomNumber = min + random.nextInt(max - min + 1);
        return randomNumber;
    }
    
    public int getRandomNumX() {
        Random random = new Random();
        int max = -1;
        int min = -4;
        int randomNumber = min + random.nextInt(max - min + 1);
        return randomNumber;
    }
}