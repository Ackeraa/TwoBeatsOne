package TwoToOne;

import javax.swing.*;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;


public class Board extends JPanel implements BoardConstants{

    //chosen piece
    private Piece chosenPiece;

    //number of live Piece
    private int[] pieceNum;

    //board map
    private int[][] map;

    //position of pieces

    public Board(){
        map = new int[ROWS][ROWS];
        pieceNum = new int[2];

        pieceNum[0] = ROWS;
        pieceNum[1] = ROWS;

        for (int i = 0; i < ROWS; i++){
            map[i][0] = 0;
        }
        for (int i = 0; i < ROWS; i++){
            map[i][ROWS - 1] = 1;
        }
        for (int i = 0; i < ROWS; i++){
            for (int j = 1; j < ROWS - 1; j++){
                map[i][j] = -1;
            }
        }

/*        pieceNum[0] = 2;
        pieceNum[1] = 2;
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < ROWS; j++){
                map[i][j] = -1;
            }
        }
        map[0][0] = 0; map[2][0] = 0;
        map[0][2] = 1; map[1][1] = 1;*/
    }

    public void reset(){
        map = new int[ROWS][ROWS];
        pieceNum = new int[2];

        pieceNum[0] = ROWS;
        pieceNum[1] = ROWS;

        for (int i = 0; i < ROWS; i++){
            map[i][0] = 0;
        }
        for (int i = 0; i < ROWS; i++){
            map[i][ROWS - 1] = 1;
        }
        for (int i = 0; i < ROWS; i++){
            for (int j = 1; j < ROWS - 1; j++){
                map[i][j] = -1;
            }
        }

      /*  pieceNum[0] = 2;
        pieceNum[1] = 2;
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < ROWS; j++){
                map[i][j] = -1;
            }
        }
        map[0][0] = 0; map[2][0] = 0;
        map[0][2] = 1; map[1][1] = 1;*/

    }
    public int getColor(Point p) {
        return map[p.x][p.y];
    }

    public int[][] getMap(){
        int[][] mapp = new int[ROWS][ROWS];
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < ROWS; j++){
                mapp[i][j] = map[i][j];
            }
        }
        return mapp;
    }

    public int[] getPieceNum(){
        int [] pieceNum1 = new int[2];
        pieceNum1[0] = pieceNum[0];
        pieceNum1[1] = pieceNum[1];
        return pieceNum;
    }

    public void setChosenPiece(Piece chosenPiece) {
        this.chosenPiece = chosenPiece;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(new Color(30, 34, 255));

        //draw board
        for (int i = 0; i < ROWS; i++){
            g.drawLine(TOP_LEFT_X, TOP_LEFT_Y + i * SPAN_Y, BOARD_WIDTH + TOP_LEFT_X, TOP_LEFT_Y + i * SPAN_Y);
        }

        for (int i = 0; i < ROWS; i++){
            g.drawLine(TOP_LEFT_X + i * SPAN_X, TOP_LEFT_Y, TOP_LEFT_X + i * SPAN_X, BOARD_HEIGHT + TOP_LEFT_Y);
        }

        //draw piece
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < ROWS; j++){
                if (map[i][j] == 0) {
                    Point p = calcCor(new Point(i, j));
                    g.drawImage(WHITE_PIECE, p.x - PIECE_SIZE / 2, p.y - PIECE_SIZE / 2, PIECE_SIZE, PIECE_SIZE, null);
                }
                else if (map[i][j] == 1){
                    Point p = calcCor(new Point(i, j));
                    g.drawImage(BLACK_PIECE, p.x - PIECE_SIZE / 2, p.y - PIECE_SIZE / 2, PIECE_SIZE, PIECE_SIZE, null);
                }
            }
        }
        if (chosenPiece != null){
            g.setColor(Color.CYAN);
            Point p = calcCor(new Point(chosenPiece.x, chosenPiece.y));
            g.fillOval(p.x - CHOSEN_MARK_SIZE / 2, p.y - CHOSEN_MARK_SIZE / 2, 20, 20);
        }
    }

    //is in board
    public boolean inBoard(Point p){
        return p.x >= 0 && p.x < ROWS && p.y >=0 && p.y < ROWS;
    }
    //is the click point avaliable
    public boolean isNear(Point p1, Point p2){

        return Math.abs(p1.x - p2.x) < LIMIT && Math.abs(p1.y - p2.y) < LIMIT;
    }

    //if player can move to the choosen point
    public boolean canMove(Piece s, Point t){
        //not empty
        if (map[t.x][t.y] != -1) return false;
        return Math.abs(s.x - t.x) + Math.abs(s.y-t.y) == 1;
    }

    //calculate the coordinates
    public Point calcCor(Point p) {
        return new Point(TOP_LEFT_X + p.x * SPAN_X,
                TOP_LEFT_Y + p.y * SPAN_Y);
    }

    //calculate the click p's  position of the map
    public Point calcPos(Point p){

        Point posP = new Point((p.x - TOP_LEFT_X + SPAN_X / 2) / SPAN_X,
                (p.y - TOP_LEFT_Y + SPAN_Y / 2) / SPAN_Y);

        if (!inBoard(posP)){
            return new Point(-1, -1);
        }
        if (isNear(p, calcCor(posP))){
            return posP;
        }
        return new Point(-1, -1);
    }

    //kill piece
    public void kill(int who, int x, int y){
        map[x][y] = -1;
        pieceNum[who]--;
        System.out.println(pieceNum[who]);
    }

    // eat piece

    public boolean eat(int x, int y){
        int self = map[x][y];
        int opp = self ^ 1;

        if (map[x][0] == opp && map[x][1] == self && map[x][2] == self && map[x][3] == -1){
            kill(opp, x, 0);
        }
        else if (map[x][1] == opp && map[x][0] == -1 && map[x][2] == self && map[x][3] == self){
            kill(opp, x, 1);
        }
        else if (map[x][2] == opp && map[x][3] == -1 && map[x][0] == self && map[x][1] == self){
            kill(opp, x, 2);
        }
        else if (map[x][3] == opp && map[x][1] == self && map[x][2] == self && map[x][0] == -1){
            kill(opp, x, 3);
        }

        if (map[0][y] == opp && map[1][y] == self && map[2][y] == self && map[3][y] == -1){
            kill(opp, 0, y);
        }
        else if (map[1][y] == opp && map[0][y] == -1 && map[2][y] == self && map[3][y] == self){
            kill(opp, 1, y);
        }
        else if (map[2][y] == opp && map[3][y] == -1 && map[0][y] == self && map[1][y] == self){
            kill(opp, 2, y);
        }
        else if (map[3][y] == opp && map[1][y] == self && map[2][y] == self && map[0][y] == -1){
            kill(opp, 3, y);
        }

        return pieceNum[opp] < 2;
    }

    //Move piece
    public boolean move(Piece s, Point tmp){

        Point t = calcPos(tmp);
        map[s.x][s.y] = -1;
        map[t.x][t.y] = s.color;

        chosenPiece = null;
        return eat(t.x, t.y);
    }

    public boolean move(Piece s){

        Point t;
        switch (s.state) {
            case "3":
                t = new Point(s.x, s.y - 1);
                break;
            case "2":
                t = new Point(s.x, s.y + 1);
                break;
            case "1":
                t = new Point(s.x - 1, s.y);
                break;
            case "0":
                t = new Point(s.x + 1, s.y);
                break;
            default:
                System.out.println("Wrong Action");
                return false;
        }
        if (!inBoard(t)){
            System.out.println("Move Out Of Board");
            return false;
        }

        map[s.x][s.y] = -1;
        map[t.x][t.y] = s.color;

        return eat(t.x, t.y);
    }
}
