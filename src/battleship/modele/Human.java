package battleship.modele;

import java.util.*;

public class Human extends Entity {

    private Scanner scanner;

    public Human(String name, Grid grilleAdverse)
    {
        super(name, grilleAdverse);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int[] selectFire()
    {
        int coordX, coordY;
        do
        {
            System.out.println("Sur quelle ligne voulez-vous tirer capitaine "+this.name+" ?");
            String str = scanner.next();
            coordX = isInt(str) ? Integer.parseInt(str) : -1;
        } while (coordX < 0 || coordX >= this.grilleAdverseDim);

        do
        {
            System.out.println("Et sur quelle colonne ?");
            String str = scanner.next();
            coordY = isInt(str) ? Integer.parseInt(str) : -1;
        } while (coordY < 0 || coordY >= this.grilleAdverseDim);

        return new int[] {coordX, coordY};
    }

    private static boolean isInt(String str)
    {
        try
        {
          Integer.parseInt(str);
          return true;
        }
        catch(NumberFormatException e)
        {
          return false;
        }
    }

    //on doit redéfinir cette méthode pour éviter une fuite de données à cause du Scanner
    @Override
    public boolean hasWon()
    {
        if(this.nbBoats == 0)
        {
            scanner.close();
            return true;
        }
        return false;
    }
}
