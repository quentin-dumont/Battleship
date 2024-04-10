package battleship.vue_controlleur;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import battleship.modele.*;


public class VueCurrentPlayer extends JPanel implements Observer {
    
    private Entity playerBleu;
    private Entity playerRouge;
    private Entity currentPlayer;
    private int totalWidth;

    public VueCurrentPlayer(Entity playerBleu, Entity playerRouge, int totalWidth)
    {
        this.totalWidth = totalWidth;
        this.playerBleu = playerBleu;
        this.playerRouge = playerRouge;
        this.currentPlayer = playerRouge;
        playerBleu.getGrilleAdverse().addObserver(this);
        playerRouge.getGrilleAdverse().addObserver(this);

        this.setPreferredSize(new Dimension(totalWidth, 30));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        String text = this.currentPlayer+", c'est à vous de tirer !";
        g.setFont(new Font("Courier", Font.BOLD, 20));
        BornesPanel.drawCenteredString(g,text,new Rectangle(0,0,this.totalWidth,30));
        this.currentPlayer = this.currentPlayer == this.playerBleu ? this.playerRouge : this.playerBleu;
    }

    @Override
    public void update(Observable obs, Object source) {
        // System.out.println("Ok je me mets à jour !");
        this.repaint();
    }
}
