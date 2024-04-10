package battleship.vue_controlleur;

import battleship.modele.*;

import javax.swing.*;
import java.awt.*;


public class BatailleNavaleGUI extends JFrame {

  public BatailleNavaleGUI()
  {
    this(1000);
  }

  public BatailleNavaleGUI(int totalWidth)
  {
    super("Bataille Navale !");

    Container cp = this.getContentPane();
    cp.setLayout(new BorderLayout());

    OptionsMenu options = new OptionsMenu(500,300);
    cp.add(options, BorderLayout.CENTER);

    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    while(!options.getIsConfirmed())
    {
      try {
        Thread.sleep(1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    Entity playerBleu = options.getPlayerBleu();
    Entity playerRouge = options.getPlayerRouge();
    if(!options.getIsGUI())
    {
      //lancement d'une partie en terminal
      this.dispose();
      new BatailleNavaleConsole(playerBleu, playerRouge);
    }
    else
    {
      cp.removeAll();
      cp.setLayout(new BorderLayout());

      //lancement d'une partie en interface
      //affichage
      VueBatailleNavale vueGame = new VueBatailleNavale(playerBleu, playerRouge, totalWidth);
      cp.add(vueGame, BorderLayout.CENTER);
      this.pack();
      VueCurrentPlayer arbitre = new VueCurrentPlayer(playerBleu, playerRouge, cp.getSize().width);
      cp.add(arbitre, BorderLayout.SOUTH);
      this.pack();
      this.setLocationRelativeTo(null);

      //puis lancement de la boucle de jeu
      GameController controleur = new GameController(playerBleu, playerRouge, vueGame.getVueBleu().getGridPanel(), vueGame.getVueRouge().getGridPanel());
      while(!controleur.getLoop().getIsOver())
      {
      try {
          Thread.sleep(1);
      } catch (Exception e) {
          e.printStackTrace();
      }
      }
      controleur.endGame();
    }
      

    String conclusion = "";
    String winner = "";
    if(playerBleu.hasWon())
      winner = playerBleu.toString();
    else
      winner = playerRouge.toString();
    conclusion = "Félicitations "+winner+" ! Vous avez coulé la flotte ennemie !\n"+
                "Voulez-vous rejouer ?";

    //boîte de dialogue de fin de partie qui affiche le gagnant et demande si on veut rejouer
    int retour = JOptionPane.showConfirmDialog(this, conclusion, "Partie terminée", JOptionPane.YES_NO_OPTION);
    this.dispose();
    if(retour == JOptionPane.OK_OPTION)
    {
      new BatailleNavaleGUI();
    }
    //System.out.println("coucou");

  }


}
