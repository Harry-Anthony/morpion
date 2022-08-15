import java.util.Scanner;

public class Main {
    public static void main(String[] arg) {
        System.out.println("===== ***** Hello morpion ***** ====");
        boolean isGoodChoice = false;
        String sn; 
        int n = 3;
        Scanner scanner = new Scanner(System.in);
        while(!isGoodChoice) {
            // System.out.print("Choisir la taille du jeu: ");
            System.out.println("Choisir 1 pour multiplayer: ");
            System.out.println("Choisir 2 pour jouer avec l'ordinateur: ");
            System.out.print("autre pour quitter: ");
            sn = scanner.next();
            try {
                n = Integer.parseInt(sn);
                if(n == 1 || n == 2 || n == 3) {
                    isGoodChoice = true;
                }
                // if(n >= 3) {
                //     isGoodChoice = true;
                // } 
                else {
                    // System.out.println("xxxxxx Entrer une valeur superieur a 2 xxxxxxx");
                    return;
                }
            } catch (NumberFormatException exception) {
                System.out.println("xxxxxxx Entrer une valeur de svp! xxxxxxx");
            }
        }
        new GameManager(3, n);
        scanner.close();
    }
}