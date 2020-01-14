package TwoToOne;

public class Acker implements BoardConstants{

    private Piece piece;
    private int self;
    private int opp;
    private int DEPTH = 12;
    private int[][] map;
    private int[] pieceNum;

    public Acker(int color){
        piece = new Piece(-1,-1, color, "alive");
        self = color;
        opp = self ^ 1;
    }

    public Piece getPiece(){
        return piece;
    }

    public void kill(int who, int x, int y){
        map[x][y] = -1;
        pieceNum[who]--;
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

    public int[][] copy(int[][] a){
        int[][] b = new int[ROWS][ROWS];
        for (int i = 0; i < ROWS; i++){
            b[i] = a[i].clone();
        }
        return b;
    }
    public boolean canMove(int x, int y){
        return x >= 0 && x < ROWS && y >= 0 && y < ROWS && map[x][y] == -1;
    }
    public void print(int[][] map){
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < ROWS; j++){
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
    }

    public void debug(String x){
        System.out.println(x);
    }
    public int alpha_beta(int depth, int alpha, int beta, int player){

        int val;
        int[][] lmap = copy(map);
        int[] lpieceNum = pieceNum.clone();
        if (pieceNum[opp] < 2) {
      //      debug("depth: " + depth);
            return 1000 * depth;
        }
        if (pieceNum[self] < 2){
            return -1000 * depth;
        }
        if (depth == 0){
            //System.out.println(pieceNum[self] + " " + pieceNum[opp]);
           /* debug("****begin*****");
            debug(pieceNum[self] + " " + pieceNum[opp] + " " + self);
            print(map);
            debug("*****end*********");
*/
            return pieceNum[self] - 2 * pieceNum[opp];
        }

        if (player == self){
            for (int x = 0; x < ROWS; x++){
                for (int y = 0; y < ROWS; y++) {
                    if (map[x][y] != player) continue;
                    for (int k = 0; k < 4; k++) {
                        int xx = x + DIR[k][0];
                        int yy = y + DIR[k][1];
                        if (!canMove(xx, yy)) continue;

                        //do
                        map[x][y] = -1;
                        map[xx][yy] = player;
                        if (eat(xx, yy)){
                           // debug("depth: " + depth);
                            //return INF;
                        }

                        val = alpha_beta(depth - 1, alpha, beta, opp);

                        //undo
                        map = copy(lmap);
                        pieceNum = lpieceNum.clone();

                        if (val > alpha) {
                            if (depth == DEPTH){
                                piece.x = x;
                                piece.y = y;
                                piece.state = k + "";
                                debug("********begin******");
                                debug("x: " + x + " y: " + y + " val: " + val + " dir: " + k);
                                debug("********end******");
                            }
                            alpha = val;
                            //remember
                        }
                        if (alpha >= beta) return alpha;
                    }
                }
            }
            return alpha;
        }
        else{
            for (int x = 0; x < ROWS; x++){
                for (int y = 0; y < ROWS; y++){
                    if (map[x][y] != player) continue;
                    for (int k = 0; k < 4; k++) {
                        int xx = x + DIR[k][0];
                        int yy = y + DIR[k][1];
                        if (!canMove(xx, yy)) continue;

                        //do
                        map[x][y] = -1;
                        map[xx][yy] = player;
                        if (eat(xx, yy)){
                            //return -INF;
                        }

                        val = alpha_beta(depth - 1, alpha, beta, self);

                        //undo
                        map = copy(lmap);
                        pieceNum = lpieceNum.clone();

                        if (val < beta) beta = val;
                        if (alpha >= beta) return beta;
                    }
                }
            }
            return beta;
        }
    }

    public void move(int[][] map, int[] pieceNum){

        this.map = copy(map);
        this.pieceNum = pieceNum.clone();
        alpha_beta(DEPTH, -INF, INF, self);
    }
}
