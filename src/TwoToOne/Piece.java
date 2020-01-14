package TwoToOne;

import java.awt.*;

public class Piece {
    int x, y;
    int color;
    String state;

    //need to reduce

    public Piece(int x, int y, int color, String state){
        this.x = x;
        this.y = y;
        this.color = color;
        this.state = state;
    }

    public Piece(Point p, int color, String state){
        this.x = p.x;
        this.y = p.y;
        this.color = color;
        this.state = state;
    }

    public Piece clone(){
        return new Piece(x, y, color, state);
    }
    @Override
    public String toString() {
       return "(x = " + x + "    y = " + y +
               "    color = " + color + "    state = " + state + ")\n";
    }

}
