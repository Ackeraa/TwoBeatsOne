package TwoToOne;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    JPanel jp;
    Board board;

    int cnt = 0;

    private Player player;
    private Player player1;
    private int playerColor;

    private Acker acker;
    private int ackerColor;

    public Game(int playerColor){

        this.playerColor = playerColor;
        ackerColor = playerColor ^ 1;

        player = new Player(playerColor);
        player1 = new Player(ackerColor);
        acker = new Acker(ackerColor);

        jp = new JPanel();
        board = new Board();

        board.setPreferredSize(new Dimension(600, 600));

        jp.add(board);

        this.add(jp);

        acker.move(board.getMap(), board.getPieceNum());
        Piece ackerPiece = acker.getPiece();
        boolean isAckerWin = board.move(ackerPiece);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.setTitle("TwoToOne");
        this.setLocation(250, 150);
        this.setSize(700, 700);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void restart(){
        board.reset();
        player = new Player(playerColor);
        acker = new Acker(ackerColor);
        this.update(this.getGraphics());
    }

    public void pVp(Point p) {

        if (cnt % 2 == 0)
        {
            player.move(board, p);
            Piece playerPiece = player.getPiece();
            String playerState = playerPiece.state;

            if (playerState.equals("Chosen") || playerState.equals("ChosenAgain")){
                board.update(board.getGraphics());
            }
            if (playerState.equals("Moved"))
            {
                boolean isPlayerWin = board.move(playerPiece, p);
                this.update(this.getGraphics());
                player.setUnChosen();
                cnt++;
                if (isPlayerWin){
                    int res = JOptionPane.showConfirmDialog(board, "赢了，是否重新开始","Success", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (res == 0){
                        restart();
                    }
                    else{
                        System.exit(0);
                    }
                }
            }
        }
        else {
            player1.move(board, p);
            Piece playerPiece = player1.getPiece();
            String playerState = playerPiece.state;

            if (playerState.equals("Chosen") || playerState.equals("ChosenAgain")){
                board.update(board.getGraphics());
            }
            if (playerState.equals("Moved"))
            {
                boolean isPlayerWin = board.move(playerPiece, p);
                this.update(this.getGraphics());
                player1.setUnChosen();
                cnt++;

                if (isPlayerWin){
                    int res = JOptionPane.showConfirmDialog(board, "赢了，是否重新开始","Success", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (res == 0){
                        restart();
                    }
                    else{
                        System.exit(0);
                    }
                }
            }
        }
    }
    public void pVa(Point p){

        player.move(board, p);
        Piece playerPiece = player.getPiece();
        String playerState = playerPiece.state;

        if (playerState.equals("Chosen") || playerState.equals("ChosenAgain")){
            board.update(board.getGraphics());
        }

        if (playerState.equals("Moved")){
            boolean isPlayerWin = board.move(playerPiece, p);
            this.update(this.getGraphics());
            player.setUnChosen();

            if (isPlayerWin){
                int res = JOptionPane.showConfirmDialog(board, "Success，Start a new game?","Success", JOptionPane.YES_NO_CANCEL_OPTION);
                if (res == 0){
                    restart();
                    return;
                }
                else{
                    System.exit(0);
                }
            }
            acker.move(board.getMap(), board.getPieceNum());
            Piece ackerPiece = acker.getPiece();
            System.out.println(ackerPiece);
            boolean isAckerWin = board.move(ackerPiece);
            this.update(this.getGraphics());
            if (isAckerWin){
                int res = JOptionPane.showConfirmDialog(board, "Lost, Start again?","Fail", JOptionPane.YES_NO_CANCEL_OPTION);
                if (res == 0){
                    restart();
                }
                else{
                    System.exit(0);
                }
            }
        }
    }

    public void justA(Point p){

        acker.move(board.getMap(), board.getPieceNum());
        Piece ackerPiece = acker.getPiece();
      //  System.out.println(ackerPiece);
        boolean isAckerWin = board.move(ackerPiece);
        this.update(this.getGraphics());
        if (isAckerWin){
            int res = JOptionPane.showConfirmDialog(board, "Lost, Restart?","Fail", JOptionPane.YES_NO_CANCEL_OPTION);
            if (res == 0){
                restart();
            }
            else{
                System.exit(0);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /*已被选中
        走法不合理
        再次被选中
      未被选中
        未被选中
        被选中
      */
    @Override
    public void mousePressed(MouseEvent e) {

        Point p = new Point(e.getX(), e.getY());
       // pVp(p);
        pVa(p);
        //justA(p);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        //System.out.println(e.getX() + " " + e.getY());
    }

    public static void main(String[] args){
       new Game(1);
    }
}
