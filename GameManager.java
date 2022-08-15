import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameManager {
    public int n;
    public String[][] array;
    public ArrayList<DepthTree> allSom;
    public ArrayList<Point> gamePoint;

    public GameManager(int n, int choice) {
        this.n = n;
        this.array = new String[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                array[row][col] = ".";
            }
        }
        this.allSom = new ArrayList<DepthTree>();
        this.gamePoint = new ArrayList<Point>();
        if(choice == 1) {
            handleGame();
        } else {
            handleGameWithBot();
        }
    }

    public void handleGameWithBot() {
        boolean isNotFinish = true;
        Point point;
        int counter = 0;
        Map<String, Integer> map;
        Scanner scanner = new Scanner(System.in);
        var arr = new ArrayList<Sommet>();
        while (isNotFinish) {
            if (counter % 2 != 0) {
                System.out.println("Player 1");
                map = checkNewCoord(scanner);
                point = new Point(map.get("x"), map.get("y"), "x");
                isNotFinish = addNewPoint(point);
            } else {
                System.out.println("Player 2");
                arr.add(new Sommet(0, this.array, new Point(0, 0, "k"), this.n));
                generateListSommet(arr, "0");
                int taille = this.allSom.size();
                int numberSplit = 1;
                for (int i = taille - 1; i > 0; i--) {
                    fillScore(this.allSom.get(i - 1), this.allSom.get(i), numberSplit);
                    numberSplit++;
                }
                // System.out.println("all som size =>" + allSom.size());
                // System.out.println("score ==>");
                // printDepthScore();
                Point botPointChoice = findOptimalPoint();
                System.out.println("Bot choice point ==>");
                botPointChoice.pointDescribe();
                isNotFinish = addNewPoint(botPointChoice);
                allSom.clear();
                arr.clear();
            }
            counter++;
            if (counter == n * n) {
                return;
            }

        }
        scanner.close();
    }

    public void handleGame() {
        boolean isNotFinish = true;
        Point point;
        int counter = 0;
        Map<String, Integer> map;
        Scanner scanner = new Scanner(System.in);
        while (isNotFinish) {
            if (counter % 2 == 0) {
                System.out.println("Player 1");
            } else {
                System.out.println("Player 2");
            }
            map = checkNewCoord(scanner);
            if (counter % 2 == 0) {
                point = new Point(map.get("x"), map.get("y"), "x");
            } else {
                point = new Point(map.get("x"), map.get("y"), "0");
            }
            isNotFinish = addNewPoint(point);
            counter++;
            if (counter == n * n) {
                return;
            }
        }
        scanner.close();
    }

    public void generateListSommet(ArrayList<Sommet> lastListSommet, String player) {
        ArrayList<Sommet> newListSommet = new ArrayList<Sommet>();
        int counter = 0;
        // System.out.println("last sommet size = " + lastListSommet.size());
        for (int i = 0; i < lastListSommet.size(); i++) {
            if (lastListSommet.get(i).score == 0) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        if (lastListSommet.get(i).sommet[j][k] == ".") {
                            Sommet newSommet = lastListSommet.get(i).copyWith(new Point(j, k, player));
                            newSommet.sommet[j][k] = player;
                            if (newSommet.score == 0) {
                                boolean isWin = checkGame(new Point(j, k, player), newSommet.sommet);
                                if (isWin) {
                                    if (player == "x") {
                                        newSommet.score = -1;
                                    } else {
                                        newSommet.score = 1;
                                    }
                                }
                            }
                            newListSommet.add(newSommet);
                            counter++;
                        }
                    }
                }
            }
        }
        // System.out.println("counter = " + counter);
        // if (lastListSommet.size() == 1) {
        //     printListSommet(newListSommet);
        // }
        if (counter > 0) {
            var depth = new DepthTree(newListSommet, player);
            allSom.add(depth);
            if (player == "0") {
                generateListSommet(newListSommet, "x");
            } else {
                generateListSommet(newListSommet, "0");
            }
        }
        //  else {
        //     System.out.println("graph size " + allSom.size());
        // }
    }

    public void fillScore(DepthTree parentListSommet, DepthTree childListSommet, int numberSplit) {
        int parentSize = parentListSommet.listSommets.size();
        int childSize = childListSommet.listSommets.size();
        int fromIndex = 0;
        int toIndex = numberSplit;
        if (parentSize == childSize) {
            for (int i = 0; i < parentSize; i++) {
                parentListSommet.listSommets.get(i).score = childListSommet.listSommets.get(i).score;
            }
        } else {
            for (int i = 0; i < parentSize; i++) {
                Sommet parentSommet = parentListSommet.listSommets.get(i); // to give score
                if (parentSommet.score == 0) {
                    if (parentListSommet.player == "x") {
                        int score = min_max(false, childListSommet.listSommets.subList(fromIndex, toIndex));
                        parentSommet.score = score;
                    } else {
                        int score = min_max(true, childListSommet.listSommets.subList(fromIndex, toIndex));
                        parentSommet.score = score;
                    }
                    fromIndex += numberSplit;
                    toIndex += numberSplit;
                }
            }
        }
    }

    public void printDepthScore() {
        for (int i = 0; i < allSom.get(0).listSommets.size(); i++) {
            System.out.println(allSom.get(0).listSommets.get(i).score);
        }
    }

    public Point findOptimalPoint() {
        ArrayList<Sommet> firstItem = allSom.get(0).listSommets;
        int maxScore = firstItem.get(0).score;
        Point optPoint = firstItem.get(0).newPoint;
        for (int i = 1; i < firstItem.size(); i++) {
            if (firstItem.get(i).score > maxScore) {
                maxScore = firstItem.get(i).score;
                optPoint = firstItem.get(i).newPoint;
            }
        }
        return optPoint;
    }

    public int min_max(boolean isMin, List<Sommet> sublistSommet) {
        int score = sublistSommet.get(0).score;
        for (int i = 1; i < sublistSommet.size(); i++) {
            if (isMin) {
                // find min in sublist
                if (sublistSommet.get(i).score < score) {
                    score = sublistSommet.get(i).score;
                }
            } else {
                // find max
                if (sublistSommet.get(i).score > score) {
                    score = sublistSommet.get(i).score;
                }
            }

        }
        return score;
    }

    public void printListSommet(ArrayList<Sommet> s) {
        for (int i = 0; i < s.size(); i++) {

            printGame(s.get(i).sommet);
        }
    }

    public boolean addNewPoint(Point point) {
        this.array[point.x][point.y] = point.player;
        boolean isWin = checkGame(point, this.array);
        printGame(this.array);
        if (isWin) {
            System.out.println("Player " + point.player + " win");
        }
        return !isWin;
    }

    public boolean checkGame(Point point, String[][] arr) {
        int counterDiagSup = 0;
        int counterDiagInf = 0;
        int horCounter = 0;
        int verCounter = 0;
        for (int i = 0; i < this.n; i++) {
            if (point.x == point.y) {
                if (arr[i][i] == point.player) {
                    counterDiagSup++;
                }
            }
            if (point.y == n - point.x - 1) {
                if (arr[i][n - i - 1] == point.player) {
                    counterDiagInf++;
                }
            }
            if (arr[point.x][i] == point.player) {
                horCounter++;
            }
            if (arr[i][point.y] == point.player) {
                verCounter++;
            }
        }

        if (counterDiagSup == this.n || counterDiagInf == this.n || horCounter == this.n || verCounter == this.n) {
            return true;
        }
        return false;

    }

    public void printGame(String[][] arr) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.print("\n\n");
        }
        System.out.println("\n\n ===========xxx========== \n\n");
    }

    public Map<String, Integer> checkNewCoord(Scanner scanner) {
        boolean isGoodChoice = false;
        String sx, sy;
        int x = 0, y = 0;
        while (!isGoodChoice) {
            System.out.print("Entre la valeur de x: ");
            sx = scanner.next();
            System.out.print("Entre la valeur de y: ");
            sy = scanner.next();
            try {
                x = Integer.parseInt(sx);
                y = Integer.parseInt(sy);
                if (x <= n && x > 0 && y <= n && y > 0) {
                    if (this.array[x - 1][y - 1] == ".") {
                        isGoodChoice = true;
                    } else {
                        System.out.println("xxxxxx Valeur deja choisi xxxxxxx");
                    }
                } else {
                    System.out.println("xxxxxx Entrer une valeur entre 1 et " + this.n + " xxxxxxx");
                }
            } catch (NumberFormatException exception) {
                System.out.println("xxxxxxx Entrer une valeur de svp! xxxxxxx");
            }
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("x", x - 1);
        map.put("y", y - 1);
        return map;
    }
}
