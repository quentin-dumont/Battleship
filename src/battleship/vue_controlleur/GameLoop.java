package battleship.vue_controlleur;

import battleship.modele.*;

public class GameLoop extends Thread {

  private Entity playerBleu;
  private Entity playerRouge;
  private Entity currentPlayer;
  private boolean isLoaded;
  private boolean isOver;

  public GameLoop(Entity playerBleu, Entity playerRouge)
  {
    this.playerBleu = playerBleu;
    this.playerRouge = playerRouge;
    this.currentPlayer = playerBleu;
    this.isLoaded = false;
    this.isOver = false;
  }

  public Entity getPlayerBleu(){return this.playerBleu;}
  public Entity getPlayerRouge(){return this.playerRouge;}
  public Entity getCurrentPlayer(){return this.currentPlayer;}
  public boolean getIsOver(){return this.isOver;}

  @Override
  public void run()
  {
    while(true)
    {
      this.waitClick(this.playerBleu);
      int[] tirPlayerBleu = this.playerBleu.selectFire();
      this.playerBleu.fireOnEnnemy(tirPlayerBleu[0], tirPlayerBleu[1]);
      dormir(200);

      if(this.playerBleu.hasWon())
        break;

      this.currentPlayer = this.playerRouge;

      this.waitClick(this.playerRouge);
      int[] tirPlayerRouge = this.playerRouge.selectFire();
      this.playerRouge.fireOnEnnemy(tirPlayerRouge[0], tirPlayerRouge[1]);
      dormir(200);

      if(this.playerRouge.hasWon())
        break;

      this.currentPlayer = this.playerBleu;
    }
    this.isOver = true;
    interrupt();
  }

  private void attendre()
  {
    try {
      this.wait();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void dormir(int time)
  {
    try {
      Thread.sleep(time);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private synchronized void waitClick(Entity player)
  {
    if(player instanceof HumanMouse)
    {
      while(!this.isLoaded)
      {
        attendre();
      }
      this.isLoaded = false;
    }
  }

  /*cette méthode est appelée de l'extérieur par GameController, et
  permet de réveiller la boucle de jeu (GameLoop)*/
  public synchronized void loadFire()
  {
    this.isLoaded = true;
    this.notifyAll();
  }


}
