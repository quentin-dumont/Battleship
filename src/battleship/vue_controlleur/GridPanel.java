package battleship.vue_controlleur;

import battleship.modele.*;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel implements Observer {

  private Grid grid;
  private int caseWidth;
  private int gridWidth;

  public static final Color SWEATBLACK = new Color(0, 0, 0, 15);
  public static final Color HARDBLACK = new Color(0, 0, 0);
  public static final Color HARDRED = new Color(204, 0, 0);
  public static final Color HARDGREEN = new Color(51, 153, 0);

  public GridPanel(Grid grid, int maxWidth) {
    this.grid = grid;
    this.caseWidth = maxWidth / this.grid.getDimension();
    this.gridWidth = maxWidth;
    //on rajoute 1 en largeur et hauteur pour pouvoir dessiner les contours de la grille
    this.setPreferredSize(new Dimension(maxWidth, maxWidth));

    this.grid.addObserver(this);
  }

  public Grid getGrid(){return this.grid;}
  public int getCaseWidth(){return this.caseWidth;}

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setFont(new Font("SansSerif", Font.BOLD, 16));

    int coordX = 0;
    int coordY = 0;

    g.setColor(HARDBLACK);
    g.drawRect(coordX, coordY, this.gridWidth-1, this.gridWidth-1);

    for (int i = 0; i < this.grid.getDimension(); i++) {
      g.setColor(SWEATBLACK);
      for (int j = 0; j < this.grid.getDimension(); j++) {
        this.paintCase(g, this.grid.getTableau()[i][j], coordX, coordY);
        coordX += this.caseWidth;
      }
      coordX = 0;
      coordY += this.caseWidth;
    }

    if (this.grid.gethasVisibleBoats()) {
      ArrayList<Boat> boatsList = this.grid.getBoatsList();
      for (int i = 0; i < boatsList.size(); i++) {
        this.paintBoat(g, boatsList.get(i));
      }
    }
  }

  private void paintCase(Graphics g, Case position, int coordX, int coordY) {
    g.setColor(SWEATBLACK);
    g.drawRect(coordX, coordY, this.caseWidth, this.caseWidth);
    switch (position.getState()) {
      case (Grid.HIT):
        g.setColor(HARDRED);
        if (position.getBoat().isSank()) {
          paintBoat(g, position.getBoat());
        }
        break;

      case (Grid.MISSED):
        g.setColor(HARDGREEN);
        break;

      default:
        return;
    }
    this.fillCenteredCircle(g,coordX+(this.caseWidth/2), coordY+(this.caseWidth/2), this.caseWidth/3);
  }

  private void fillCenteredCircle(Graphics g, int coordX, int coordY, int rayon)
  {
    int diametre = rayon*2;
    //on centre X et Y en soustrayant le rayon souhaité
    g.fillOval(coordX-rayon, coordY-rayon, diametre, diametre);
  }

  private void paintBoat(Graphics g, Boat navire) {
    Color oldColor = g.getColor();
    g.setColor(HARDBLACK);
    int longueur = navire.getLongueur();
    int[] coords = navire.getAncrage();
    boolean isVertical = navire.getIsVertical();
    // System.out.println(navire);
    // bien penser à l'inversion des coordonnées (l'ancrage en ligne correspond au Y
    // en affichage)
    if (isVertical)
      g.drawRoundRect(coords[1] * this.caseWidth, coords[0] * this.caseWidth, this.caseWidth, this.caseWidth * longueur,
          40, 40);
    else
      g.drawRoundRect(coords[1] * this.caseWidth, coords[0] * this.caseWidth, this.caseWidth * longueur, this.caseWidth,
          40, 40);
    g.setColor(oldColor);
  }

  @Override
  public void update(Observable obs, Object source) {
    // System.out.println("Ok je me mets à jour !");
    this.repaint();
  }
}
