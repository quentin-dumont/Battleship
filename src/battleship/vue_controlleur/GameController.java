package battleship.vue_controlleur;

import java.awt.event.*;

import battleship.modele.*;

public class GameController extends MouseAdapter {

    private GameLoop gameloop;
    private GridPanel vueGrillePlayerBleu;
    private GridPanel vueGrillePlayerRouge;
    private Entity playerBleu;
    private Entity playerRouge;

    public GameController(Entity playerBleu, Entity playerRouge, GridPanel vueGrillePlayerBleu, GridPanel vueGrillePlayerRouge)
    {
        this.vueGrillePlayerBleu = vueGrillePlayerBleu;
        this.vueGrillePlayerRouge = vueGrillePlayerRouge;
        this.playerBleu = playerBleu;
        this.playerRouge = playerRouge;

        if(this.playerBleu instanceof HumanMouse)
          this.vueGrillePlayerRouge.addMouseListener(this);
        if(this.playerRouge instanceof HumanMouse)
          this.vueGrillePlayerBleu.addMouseListener(this);

        try
        {
            this.playerBleu.getGrilleAdverse().randomBoats();
            this.playerRouge.getGrilleAdverse().randomBoats();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

        this.gameloop = new GameLoop(playerBleu, playerRouge);
        this.gameloop.start();
    }

    public GameLoop getLoop(){return this.gameloop;}

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getComponent() == this.vueGrillePlayerRouge && this.gameloop.getCurrentPlayer() == this.playerBleu)
          this.setCoords(e, this.vueGrillePlayerRouge, (HumanMouse) this.playerBleu);

        if(e.getComponent() == this.vueGrillePlayerBleu && this.gameloop.getCurrentPlayer() == this.playerRouge)
          this.setCoords(e, this.vueGrillePlayerBleu, (HumanMouse) this.playerRouge);
    }

    public void setCoords(MouseEvent e, GridPanel vueGrilleAdverse, HumanMouse tireur)
    {
      //inversion coordonnées par rapport à la grille et à l'affichage
      int coordY = e.getX()/vueGrilleAdverse.getCaseWidth();
      int coordX = e.getY()/vueGrilleAdverse.getCaseWidth();
      //System.out.println("Cliqué ! -> ("+coordX+","+coordY+")");
      tireur.setCoordX(coordX);
      tireur.setCoordY(coordY);
      this.gameloop.loadFire();
    }

    public void endGame()
    {
      //System.out.println("la partie est finie le thread est mort");
      if(this.playerBleu instanceof HumanMouse)
        this.vueGrillePlayerRouge.removeMouseListener(this);
      if(this.playerRouge instanceof HumanMouse)
        this.vueGrillePlayerBleu.removeMouseListener(this);
    }


}
