package battleship.modele;

public class Case {

  private String etat;
  private Boat bateau = null;

  public Case(Boat bateau, String etat)
  {
    this.bateau = bateau;
    this.etat = etat;
  }

  public Boat getBoat(){return this.bateau;}
  public String getState(){return this.etat;}

  public void setBoat(Boat navire){this.bateau = navire;}
  public void setState(String etat){this.etat = etat;}

}
