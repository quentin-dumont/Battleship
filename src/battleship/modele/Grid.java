package battleship.modele;

import java.util.*;

public class Grid extends Observable {

    //etats possibles des cases
    public static final String NEUTRAL = "⬜";
    public static final String MISSED = "🟩";
    public static final String HIT = "🟥";

    //constante d'affichage des bateaux alliés
    public static final String MY_BOAT = "🟦";

    private int dimension;
    private int nbBoats;
    private final boolean hasVisibleBoats;

    /*
    On stocke les longueurs des bateaux dans des objets Integer pour
    pouvoir trier le tableau avec une méthode optimisée telle qu'Arrays.sort().
    Cette dernière ne peut pas être appliquée sur un type primitif (int)
    */
    private Integer[] boatsWidth;
    private ArrayList<Boat> boatsList;
    private Case[][] tableau;

    public Grid(int dimension, Integer[] boatsWidth, boolean hasVisibleBoats)
    {
        this.dimension = dimension;
        this.nbBoats = boatsWidth.length;
        this.hasVisibleBoats = hasVisibleBoats;

        //tri par ordre décroissant qui nous sert à placer les bateaux du plus grand au plus petit
        Arrays.sort(boatsWidth, Collections.reverseOrder());
        this.boatsWidth = boatsWidth;
        this.boatsList = new ArrayList<Boat>();

        this.tableau = new Case[dimension][dimension];
        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j < dimension; j++)
            {
                this.tableau[i][j] = new Case(null, NEUTRAL);
            }
        }
    }

    public int getDimension(){return this.dimension;}
    public Case[][] getTableau(){return this.tableau;}
    public int getNbBoats(){return this.boatsWidth.length;}
    public ArrayList<Boat> getBoatsList(){return this.boatsList;}
    public boolean gethasVisibleBoats(){return this.hasVisibleBoats;}

    public void randomBoats() throws Exception
    {
      for(int boat = 0; boat < this.nbBoats; boat++)
      {
        int longueur = this.boatsWidth[boat];
        if(longueur > this.dimension)
        {
          throw new Exception(
          "\nUn navire est trop grand pour la grille.\n"+
          "Navire de longueur "+longueur+" pour une grille de longueur "
          +this.dimension+".");
        }
        Random rd = new Random();
        boolean isVertical = rd.nextBoolean();
        int ancreX = -1;
        int ancreY = -1;

        while(ancreX == -1 || ancreY == -1)
        {
          //on choisit un ancrage aléatoire
          int ligne = rd.nextInt(this.dimension);
          int col = rd.nextInt(this.dimension);
          int begin;
          if(isVertical) begin = ligne; else begin = col;
          //si le bateau ne dépasse pas de la grille, on passe à la détection d'obstacles
          if((begin+longueur-1) < this.dimension)
          {
            boolean obstacle = false;
            for(int pos = begin; pos < (begin+longueur); pos++)
            {
              //détection obstacles sur la verticale
              if(isVertical && this.tableau[pos][col].getBoat() != null)
                obstacle = true;
              //détection obstacles sur l'horizontale
              else if(this.tableau[ligne][pos].getBoat() != null)
                obstacle = true;
            }
            //si aucun obstacle(bateau) n'est rencontré, on choisit cet ancrage
            if(!obstacle)
            {
              ancreX = ligne;
              ancreY = col;
            }
          }
        }
        this.addBoat(new Boat(longueur,ancreX,ancreY,isVertical));
      }
    }

    //Cette méthode place la même instance d'un navire sur les cases qu'il occupe
    private void addBoat(Boat navire)
    {
        int ancreX = navire.getAncrage()[0];
        int ancreY = navire.getAncrage()[1];
        int longueur = navire.getLongueur();

        this.boatsList.add(navire);

        for(int i = 0; i < longueur; i++)
        {
            //System.out.println("("+ancreX+","+ancreY+")");
            this.tableau[ancreX][ancreY].setBoat(navire);

            if(navire.getIsVertical())
              ancreX += 1;
            else
              ancreY += 1;
        }
        this.changement();
    }

    public void changement()
    {
      this.setChanged();
      this.notifyObservers();
    }

    @Override
    public String toString()
    {
        String chaine = "  ";

        for(int a = 0; a < this.dimension; a++)
        {
            chaine += a+" ";
        }
        chaine += "\n";
        for(int i = 0; i < this.dimension; i++)
        {
            chaine += i + " ";
            for(int j = 0; j < this.dimension; j++)
            {
                if(this.tableau[i][j].getBoat() != null && this.tableau[i][j].getState() != HIT)
                {
                    if(this.hasVisibleBoats)
                      chaine += MY_BOAT;
                    else
                      chaine += NEUTRAL;
                }
                else
                {
                    chaine += this.tableau[i][j].getState();
                }
            }
            chaine += "\n";
        }
        return chaine;
    }

}
