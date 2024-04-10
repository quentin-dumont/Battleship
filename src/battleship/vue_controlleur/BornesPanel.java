package battleship.vue_controlleur;

import battleship.modele.*;

import javax.swing.*;
import java.awt.*;

public class BornesPanel extends JPanel {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int TITLE = 2;

    private static final String[] LETTERS = { 
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" };

    private Color couleur;
    private String playerName;
    private Grid grid;
    private int caseWidth;
    private int gridWidth;
    private int etat;

    public BornesPanel(Grid playerGrid, String playerName, int gridWidth, int etat, Color couleur) {

        this.couleur = couleur;
        this.playerName = playerName;
        this.grid = playerGrid;
        this.gridWidth = gridWidth;
        this.caseWidth = gridWidth / this.grid.getDimension();
        this.etat = etat;
        if(this.etat == HORIZONTAL || this.etat == TITLE)
          this.setPreferredSize(new Dimension(gridWidth+this.caseWidth, this.caseWidth));
        else
          this.setPreferredSize(new Dimension(this.caseWidth, gridWidth));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.couleur);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));

        int coordX; int coordY;

        if(this.etat == HORIZONTAL || this.etat == TITLE)
        {
            coordX = this.caseWidth;
            coordY = 0;
        }
        else
        {
            coordX = 0;
            coordY = 0;
        }
        
        String car = "";
        if(this.etat == HORIZONTAL || this.etat == VERTICAL)
        {
          for(int l = 0; l < this.grid.getDimension(); l++)
          {
              if(this.etat == HORIZONTAL)
                car = (l+1)+"";
              else
                car = LETTERS[l%26]; //modulo à 26 (et pas 27 car index à 0) pour éviter de chercher une lettre qui n'existe pas :)

              drawCenteredString(g, car, new Rectangle(coordX, coordY, this.caseWidth, this.caseWidth));
              if(this.etat == HORIZONTAL)
                coordX += this.caseWidth;
              else
                coordY += this.caseWidth;
          }
        }
        else //(case : TITLE)
        {
          g.setFont(new Font("Courier", Font.BOLD, 25));
          String name = "Flotte de "+this.playerName; 
          drawCenteredString(g, name, new Rectangle(coordX, coordY, this.gridWidth, this.caseWidth));
        }
        
    }

    public static void drawCenteredString(Graphics g, String text, Rectangle rect) {
      FontMetrics metrics = g.getFontMetrics(g.getFont());
      int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
      int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
      g.drawString(text, x, y);
    }

}
