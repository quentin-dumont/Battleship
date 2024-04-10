package battleship.modele;

public abstract class Entity {

    protected Grid grilleAdverse;
    protected int grilleAdverseDim;
    protected int nbBoats;
    public final String name;

    public Entity(String name, Grid grilleAdverse)
    {
        this.name = name;
        this.grilleAdverse = grilleAdverse;
        this.nbBoats = grilleAdverse.getNbBoats();
        this.grilleAdverseDim = grilleAdverse.getDimension();
    }

    public Grid getGrilleAdverse(){return this.grilleAdverse;}

    

    public boolean hasWon()
    {
        return this.nbBoats == 0;
    }

    public boolean fireOnEnnemy(int coordX, int coordY)
    {
        Case position = this.grilleAdverse.getTableau()[coordX][coordY];
        switch(position.getState())
        {
          case Grid.NEUTRAL :
            if(position.getBoat() != null)
            {
              position.setState(Grid.HIT);
              position.getBoat().touched();
              if(position.getBoat().isSank())
              {
                  this.nbBoats -= 1;
                  this.grilleAdverse.changement();
                  return true;
              }
              this.saveCoup(coordX, coordY);
            }
            else
            {
              position.setState(Grid.MISSED);
            }
          default :
            break;
        }
        this.grilleAdverse.changement();
        return false;
    }

    @Override
    public String toString(){return this.name;}

    protected void saveCoup(int x, int y){return;};
    public abstract int[] selectFire();
}
