public class Test {
    public int score;

    public Test(int score) {
        this.score = score;
    }

    public void printScore() {
        System.out.println(this.score);
    }

    public Test copyWith() {
        return new Test(this.score);
    }
}
