package battleship.vue_controlleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import battleship.modele.*;

public class OptionsMenu extends JPanel implements ActionListener, KeyListener {

    public static final String COMPUTER = "Ordinateur";
    public static final String HUMAN = "Humain";
    public static final String[] ENTITY_TYPES = new String[] {HUMAN, COMPUTER};

    private int width;
    private int height;

    private JButton jeuTerminal;
    private JButton jeuGUI;
    private JTextField nomBleu;
    private JTextField nomRouge;
    private JTextField tailleGridBleu;
    private JTextField tailleGridRouge;
    private JComboBox<String> typeBleu;
    private JComboBox<String> typeRouge;
    

    private Entity playerBleu;
    private Entity playerRouge;

    private boolean isGUI;
    private boolean isConfirmed = false;

    public boolean getIsGUI(){return this.isGUI;}
    public boolean getIsConfirmed(){return this.isConfirmed;}
  
    public OptionsMenu(int width, int height)
    {
        this.width = width; this.height = height;
        this.setPreferredSize(new Dimension(width, height));

        JPanel optionsBleu = createOptionPanel(true);
        JPanel optionsRouge = createOptionPanel(false);
        JPanel confirmOptions = createConfirmOptions();

        this.setLayout(new BorderLayout());
        this.add(optionsBleu, BorderLayout.WEST);
        this.add(optionsRouge, BorderLayout.EAST);
        this.add(confirmOptions, BorderLayout.SOUTH);
    }

    public Entity getPlayerBleu(){return this.playerBleu;}
    public Entity getPlayerRouge(){return this.playerRouge;}

    @Override
    public void keyPressed(KeyEvent e){};
    @Override
    public void keyReleased(KeyEvent e){};
    //Cela nous sert pour les deux champs de taille de grille
    @Override
    public void keyTyped(KeyEvent e)
    {
        char touche = e.getKeyChar();
        if (((touche < '0') || (touche > '9')) && (touche != KeyEvent.VK_BACK_SPACE))
          e.consume();  // ignorer l'événement
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.isGUI = e.getSource() == this.jeuGUI ? true : false;
        String nomB = nomBleu.getText();
        String nomR = nomRouge.getText();
        int dimBleu = Integer.parseInt(tailleGridBleu.getText());
        int dimRouge = Integer.parseInt(tailleGridRouge.getText());
        dimBleu = dimBleu > Demo.TAILLE_MAX ? Demo.TAILLE_MAX : dimBleu;
        dimBleu = dimBleu < Demo.TAILLE_MIN ? Demo.TAILLE_MIN : dimBleu;
        dimRouge = dimRouge > Demo.TAILLE_MAX ? Demo.TAILLE_MAX : dimRouge;
        dimRouge = dimRouge < Demo.TAILLE_MIN ? Demo.TAILLE_MIN : dimRouge;

        /*on part du principe que si l'utilisateur choisit une grille très petite, on ne met qu'un petit bateau
          pour préserver le concept du jeu. (en réalité on le fait aussi pour éviter un bug fatal lors du placement
          aléatoire des bateaux.) */
        Integer[] boatsRouge = dimRouge > 6 ? new Integer[] {5,4,3,3,2} : new Integer[] {2};
        Integer[] boatsBleu = dimBleu > 6 ? new Integer[] {5,4,3,3,2} : new Integer[] {2};

        Grid gridBleuVisible = new Grid(dimBleu,boatsBleu,true);
        Grid gridBleuNonVisible = new Grid(dimBleu,boatsBleu,false);
        Grid gridRougeVisible = new Grid(dimRouge,boatsRouge,true);
        Grid gridRougeNonVisible = new Grid(dimRouge,boatsRouge,false);

        if(this.typeBleu.getSelectedItem() == HUMAN && this.typeRouge.getSelectedItem() == HUMAN)
        {
            if(!isGUI)
            {
                this.playerBleu = new Human(nomB, gridRougeNonVisible);
                this.playerRouge = new Human(nomR, gridBleuNonVisible);
            }
            else
            {
                this.playerBleu = new HumanMouse(nomB, gridRougeNonVisible);
                this.playerRouge = new HumanMouse(nomR, gridBleuNonVisible);
            }
        }
        else if(this.typeBleu.getSelectedItem() == HUMAN)
        {
            this.playerRouge = new Computer(nomR, gridBleuVisible);
            if(!this.isGUI)
              this.playerBleu = new Human(nomB, gridRougeNonVisible);
            else
              this.playerBleu = new HumanMouse(nomB, gridRougeNonVisible);
        }
        else if(this.typeRouge.getSelectedItem() == HUMAN)
        {
            this.playerBleu = new Computer(nomB, gridRougeVisible);
            if(!this.isGUI)
              this.playerRouge = new Human(nomR, gridBleuNonVisible);
            else
              this.playerRouge = new HumanMouse(nomR, gridBleuNonVisible);
        }
        else //les deux entités sont des ordinateurs
        {
            this.playerBleu = new Computer(nomB, gridRougeVisible);
            this.playerRouge = new Computer(nomR, gridBleuVisible);
        }
        this.isConfirmed = true;   
    }

    private JPanel createOptionPanel(boolean isBleu)
    {
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(null);
        optionPanel.setPreferredSize(new Dimension(this.width/2, this.height*5/8));

        JLabel label;
        JLabel labTaille = new JLabel("Taille :");
        JTextField nom;
        JTextField taille;
        JComboBox<String> type;

        if(isBleu)
        {
            this.nomBleu = new JTextField("Bleu");
            this.typeBleu = new JComboBox<>(ENTITY_TYPES);
            this.tailleGridBleu = new JTextField("10");
            label = new JLabel("Flotte bleue :");
            nom = this.nomBleu;
            type = this.typeBleu;
            taille = this.tailleGridBleu;
        }
        else
        {
            this.nomRouge = new JTextField("Rouge");
            this.typeRouge = new JComboBox<>(ENTITY_TYPES);
            this.tailleGridRouge = new JTextField("10");
            label = new JLabel("Flotte rouge :");
            nom = this.nomRouge;
            type = this.typeRouge; 
            taille = this.tailleGridRouge;
        }

        label.setBounds(this.width/8+20,20,120,30);
        nom.setBounds(this.width/8+20,60,120,20);
        type.setBounds(this.width/8+20,90,120,20);
        labTaille.setBounds(this.width/8+20,120,70,20);
        taille.setBounds(this.width/8+90,120,50,20);

        optionPanel.add(label); optionPanel.add(nom); optionPanel.add(type);
        optionPanel.add(labTaille); optionPanel.add(taille);
        return optionPanel;
    }

    public JPanel createConfirmOptions()
    {
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(null);
        optionPanel.setPreferredSize(new Dimension(this.width, this.height*3/8));

        JLabel select = new JLabel("Sélectionnez votre mode de jeu, et à l'abordage !");
        this.jeuTerminal = new JButton("Terminal");
        this.jeuGUI = new JButton("Interface");
        this.jeuGUI.addActionListener(this); this.jeuTerminal.addActionListener(this);

        select.setBounds(80,20,400,15);
        jeuTerminal.setBounds(90,50,150,50);
        jeuGUI.setBounds(260,50,150,50);
        
        optionPanel.add(select); optionPanel.add(jeuTerminal); optionPanel.add(jeuGUI);
        return optionPanel;
    }

}
