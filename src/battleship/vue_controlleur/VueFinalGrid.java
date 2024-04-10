package battleship.vue_controlleur;

import battleship.modele.*;

import javax.swing.*;
import java.awt.*;

public class VueFinalGrid extends JPanel {

    private GridPanel gridPanel;

    public VueFinalGrid(Grid playerGrid, String playerName, int gridWidth, Color couleur)
    {
        this.gridPanel = new GridPanel(playerGrid, gridWidth);
        BornesPanel horizon = new BornesPanel(playerGrid, playerName, gridWidth, BornesPanel.HORIZONTAL, couleur);
        BornesPanel vertical = new BornesPanel(playerGrid, playerName, gridWidth, BornesPanel.VERTICAL, couleur);
        BornesPanel title = new BornesPanel(playerGrid, playerName, gridWidth, BornesPanel.TITLE, couleur);

        this.setLayout(new BorderLayout());
        this.add(gridPanel, BorderLayout.CENTER);
        this.add(horizon, BorderLayout.NORTH);
        this.add(vertical, BorderLayout.WEST);
        this.add(title, BorderLayout.SOUTH);
    }

    public GridPanel getGridPanel(){return this.gridPanel;}
}
