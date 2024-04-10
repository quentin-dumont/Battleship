package battleship.vue_controlleur;

import java.awt.*;
import javax.swing.*;

import battleship.modele.*;

public class VueBatailleNavale extends JPanel {
    
    private VueFinalGrid vuePlayerBleu;
    private VueFinalGrid vuePlayerRouge;

    public VueBatailleNavale(Entity playerBleu, Entity playerRouge, int totalWidth)
    {
        this.vuePlayerBleu = new VueFinalGrid(playerRouge.getGrilleAdverse(), playerBleu.toString(), totalWidth/2, Color.BLUE);
        this.vuePlayerRouge = new VueFinalGrid(playerBleu.getGrilleAdverse(), playerRouge.toString(), totalWidth/2, Color.RED);

        this.setLayout(new GridBagLayout());
        
        //mise en place des contraintes d'affichage
        GridBagConstraints gbc = new GridBagConstraints();
        //padding externe des composants :
        gbc.insets = new Insets(30, 0, 30, 30);

        this.add(vuePlayerBleu, gbc);
        this.add(vuePlayerRouge, gbc);
    }

    public VueFinalGrid getVueBleu(){return this.vuePlayerBleu;}
    public VueFinalGrid getVueRouge(){return this.vuePlayerRouge;}
}
