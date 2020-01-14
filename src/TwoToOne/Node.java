package TwoToOne;

import java.util.ArrayList;

public class Node {

    public Piece piece;
    public double c = 0.5;
    public int Q;
    public int N;
    public int[][] map;
    public Node parent;
    public ArrayList<Node> childern;

    public Node(int x, int y, int color, String state, int[][] map){
        piece = new Piece(x, y, color, state);
        this.map = map.clone();
    }

    public double UTC(){
        return (double) Q / N + Math.sqrt(Math.log(parent.N) / (double)N);
    }
}
