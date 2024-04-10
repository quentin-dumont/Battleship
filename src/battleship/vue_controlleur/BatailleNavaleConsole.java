package battleship.vue_controlleur;

import battleship.modele.*;

public class BatailleNavaleConsole {

  private Entity player1;
  private Entity player2;

  public BatailleNavaleConsole()
  {
    this 
    (
      //on rappelle que l'entité prend en paramètre son nom et la grille DE L'ADVERSAIRE
      new Human("Quentin", new Grid(10, new Integer[] {5,4,3,3,2}, false)), //hasVisibleBoats est à false car c'est la grille adverse 
      new Computer("Philippe(Bot)", new Grid(10, new Integer[] {5,4,3,3,2}, true)) //hasVisibleBoats est à true car c'est notre grille
    );
  }

  public BatailleNavaleConsole(Entity player1, Entity player2)
  {
    this.player1 = player1;
    this.player2 = player2;

    try
    {
      this.player1.getGrilleAdverse().randomBoats();
      this.player2.getGrilleAdverse().randomBoats();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.exit(1);
    }

    System.out.println("Commandant, j'aperçois des navires ennemis, à l'attaque !");

    while((!player1.hasWon()) && (!player2.hasWon()))
    {
        System.out.println("La Grille de "+player1+" :\n"+player2.getGrilleAdverse());
        System.out.println("La Grille de "+player2+" :\n"+player1.getGrilleAdverse());

        int[] coupP1 = player1.selectFire();
        if(player1.fireOnEnnemy(coupP1[0],coupP1[1]))
          System.out.println(player1+" ! Vous avez coulé un navire de "+player2+"!");

        int[] coupP2 = player2.selectFire();
        if(player2.fireOnEnnemy(coupP2[0],coupP2[1]))
          System.out.println(player2+" ! Vous avez coulé un navire de "+player1+"!"); 
    }

    System.out.println("La Grille de "+player1+" :\n"+player2.getGrilleAdverse());
    System.out.println("La Grille de "+player2+" :\n"+player1.getGrilleAdverse());

    Entity winner = player2.hasWon() ? player2 : player1;
    System.out.println("Félicitations "+winner+" ! Vous avez vaincu la flotte ennemie !");
  }
}
