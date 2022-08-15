public class Point {
    public int x;
    public int y;
    public String player;
    public Point(int x, int y, String p) {
        this.x = x;
        this.y = y;
        this.player = p;
    }

    public void pointDescribe() {
        System.out.println("x => "+x+" y => "+y+" player => "+player);
    }
}
