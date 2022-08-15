
public class Sommet {
    public String[][] sommet;
    public int n;
    public int score;
    public Point newPoint;

    public Sommet(int sc, String[][] s, Point newPoint, int taille) {
        this.score = sc;
        this.sommet = s;
        this.newPoint = newPoint;
        this.n = taille;
    }

    public void setScoreSommet(int newScore) {
        this.score = newScore;
    }

    public Sommet copyWith(Point newPoint) {
        String[][] s = new String[this.n][this.n];
        int newScore = score;
        for(int i = 0; i< this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                s[i][j] = sommet[i][j];
            }
        }
        return new Sommet(newScore, s, newPoint, this.n);
    }
}

