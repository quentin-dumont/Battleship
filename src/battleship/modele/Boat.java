package battleship.modele;

public class Boat {

  private final int longueur,ancrageX, ancrageY;
  private final boolean isVertical;

  private int touches = 0; //compteur de touches

  public Boat(int longueur, int ancrageX, int ancrageY, boolean isVertical)
  {
    this.longueur = longueur;
    this.isVertical = isVertical;
    this.ancrageX = ancrageX;
    this.ancrageY = ancrageY;
  }

  public int getLongueur(){return this.longueur;}
  public int getTouches(){return this.touches;}
  public boolean getIsVertical(){return this.isVertical;}

  public int[] getAncrage()
  {
    int[] ancrage = {this.ancrageX, this.ancrageY};
    return ancrage;
  }

  //incrémente le compteur de touches
  public void touched()
  {
    this.touches += 1;
  }

  //fonction booléenne qui indique si le bateau est coulé
  public boolean isSank()
  {
    return (this.touches == this.longueur);
  }

  @Override
  public String toString()
  {
    return "("+this.ancrageX+","+this.ancrageY+") / Longueur = "+this.longueur+" / Vertical = "+this.isVertical+"\n";
  }


}
