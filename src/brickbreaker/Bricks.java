package brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bricks {
    public int AnchoLadrillo;
    public int AlturaLadrillo;
    public int mapa[][];

    public Bricks(int fil, int col) {
        mapa = new int[fil][col];

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                mapa[i][j] = 1;
            }
        }
        AnchoLadrillo = 540 / col;
        AlturaLadrillo = 150 / fil;
    }
    public void dibujar(Graphics2D graphics2d, Color colorName) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (mapa[i][j] > 0) {
                    graphics2d.setColor(colorName);
                    graphics2d.fillRect(j * AnchoLadrillo + 80, i * AlturaLadrillo + 50, AnchoLadrillo, AlturaLadrillo);

                    graphics2d.setStroke(new BasicStroke(4));
                    graphics2d.setColor(Color.black);
                    graphics2d.drawRect(j * AnchoLadrillo + 80, i * AlturaLadrillo + 50, AnchoLadrillo, AlturaLadrillo);
                }
            }
        }
    }
    public void setBrickValue(int valor, int fil, int col) {
        mapa[fil][col] = valor;
    }
}