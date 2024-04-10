package battleship.modele;

import java.util.*;

public class Computer extends Entity {

    private ArrayList<int[]> coupsPossibles;
    private int nbCoupsAleatoires = 0;
    private int[] memoireCoup;
    private int[] memoireDirection;
    private int[][] strategyIA;
    private int indexStrategy;

    public Computer(String name, Grid grilleAdverse)
    {
        super(name, grilleAdverse);

        //Préparation des tirs aléatoires de l'IA
        this.coupsPossibles = new ArrayList<int[]>();
        for(int i = 0; i < grilleAdverse.getDimension(); i++)
        {
            for(int j = 0; j < grilleAdverse.getDimension(); j++)
            {
                this.coupsPossibles.add(new int[] {i,j});
            }
        }
        Collections.shuffle(this.coupsPossibles);

        this.nbCoupsAleatoires = 0;

        //initialisation de la stratégie IA
        this.memoireCoup = new int[] {-1,-1};
        this.strategyIA = new int[][] {{-1,0},{1,0},{0,1},{0,-1}}; //Haut puis bas puis droite puis gauche
        this.memoireDirection = new int[] {0,0};
        this.indexStrategy = 0;
    }

    //on redéfinit la sauvegarde du coup pour modifier le comportement final
    //de fireOnEnnemy lorsque cette dernière méthode est appelée sur une instance de Computer
    @Override
    protected void saveCoup(int x, int y)
    {
        if(this.memoireCoup[0] == -1)
        {
            //System.out.println("je sauvegarde le coup");
            this.memoireCoup[0] = x;
            this.memoireCoup[1] = y;
        }
    }

    private boolean isAlreadyFired(int coordX, int coordY)
    {
      Case position = this.grilleAdverse.getTableau()[coordX][coordY];
      return position.getState() != Grid.NEUTRAL;
    }

    @Override
    public int[] selectFire()
    {
      int coordX = Integer.MAX_VALUE;
      int coordY = Integer.MAX_VALUE;

      if(this.memoireCoup[0] != -1)
      {
        //Si le bateau que l'on visait a été coulé, on réinitialise la mémoire
        if(this.grilleAdverse.getTableau()[this.memoireCoup[0]][this.memoireCoup[1]].getBoat().isSank())
        {
          this.memoireCoup[0] = -1;
          this.memoireCoup[1] = -1;
          this.memoireDirection[0] = 0;
          this.memoireDirection[1] = 0;
          this.indexStrategy = 0;
        }
        else
        {
          do //Tant qu'on tombe sur une case où un bateau a déjà été touché
          {
            //On vérifie que les coordonnées de tir soient valides
            do
            {
              coordX = this.memoireCoup[0] + this.strategyIA[indexStrategy][0] + this.memoireDirection[0];
              coordY = this.memoireCoup[1] + this.strategyIA[indexStrategy][1] + this.memoireDirection[1];
              //on change de stratégie si les coordonnées ne sont pas valides
              if(coordX >= this.grilleAdverseDim || coordX < 0 || coordY >= this.grilleAdverseDim || coordY < 0)
              {
                this.memoireDirection[0] = 0;
                this.memoireDirection[1] = 0;
                indexStrategy += 1;
              }
              else
                break;
            } while(true);
            //System.out.println("on tente en ("+(coordX)+","+coordY+")");

            /*Les deux lignes ci-dessous permettent de tirer une case plus loin dans la même direction
              au prochain tour. */
            this.memoireDirection[0] += this.strategyIA[indexStrategy][0];
            this.memoireDirection[1] += this.strategyIA[indexStrategy][1];

          } while(this.grilleAdverse.getTableau()[coordX][coordY].getState() == Grid.HIT || this.grilleAdverse.getTableau()[coordX][coordY].getState() == Grid.MISSED);

          //Si la case ne contient pas de bateau (autrement dit, si le tir est manqué)
          if(this.grilleAdverse.getTableau()[coordX][coordY].getBoat() == null)
          {
            /*On change de stratégie pour le prochain tour
            et on réinitialise les compteurs assignés à la direction*/
            indexStrategy += 1;
            this.memoireDirection[0] = 0;
            this.memoireDirection[1] = 0;
          }
          //On retourne les coordonnées définies par la stratégie
          return new int[] {coordX, coordY};
        }
      }
      //Si memoireCoup est vide, on tire aléatoirement
      do
      {
        coordX = this.coupsPossibles.get(this.nbCoupsAleatoires)[0];
        coordY = this.coupsPossibles.get(this.nbCoupsAleatoires)[1];
        this.nbCoupsAleatoires += 1;
      } while(this.isAlreadyFired(coordX, coordY));
      return new int[] {coordX, coordY};
    }
}
