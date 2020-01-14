package TwoToOne;

import java.awt.*;

public class Player {

    private Piece piece;
    private Piece chosenPiece;

    public Player(int color){
        piece = new Piece(-1, -1, color, "UnChosen");
    }

    public Piece getPiece(){
        return piece;
    }

    public void setUnChosen(){
        piece.state = "UnChosen";
    }

    public void move(Board board, Point p){

        Point pos = board.calcPos(p);

        //Wrong position
        if (pos.x == -1){
            //nothing to do
            return; }
        //has not choose
        if (piece.state.equals("UnChosen")){

            //choose his own piece
            if (board.getColor(pos) == piece.color){
                piece = new Piece(pos, piece.color, "Chosen");
                board.setChosenPiece(piece);
            }
            //choose wrong position
            else{
                // nothing to do
            }
        }
        //already choose
        else{
            //choose again
            if (board.getColor(pos) == piece.color){
                piece = new Piece(pos, piece.color, "ChosenAgain");
                board.setChosenPiece(piece);
            }
            //can move
            else if (board.canMove(piece, pos)){
                piece.state = "Moved";
            }
            else{
                //nothing to do
            }
        }
        System.out.println(piece.toString());
    }
}
