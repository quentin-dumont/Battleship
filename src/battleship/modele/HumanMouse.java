package battleship.modele;

public class HumanMouse extends Entity {

    private int[] coords = {-1,-1};

    public HumanMouse(String name, Grid grilleAdverse)
    {
        super(name, grilleAdverse);
    }

    
    public void setCoordX(int coordX){this.coords[0] = coordX;}
    
    public void setCoordY(int coordY){this.coords[1] = coordY;}
    
    public int getCoordX(){return this.coords[0];}
    
    public int getCoordY(){return this.coords[1];}

    //Un HumanMouse ne fait que retourner les coordonnées cliquées par l'utilisateur
    @Override
    public int[] selectFire()
    {
        return this.coords;
    }
}
