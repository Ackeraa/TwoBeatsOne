package TwoToOne;

public class MCT implements BoardConstants{

    private Piece piece;
    private int self;
    private int opp;
    private int DEPTH = 12;
    private int[][] map;
    private int[] pieceNum;

    public MCT(int color){
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


    public void move(int[][] map, int[] pieceNum){

        this.map = copy(map);
        this.pieceNum = pieceNum.clone();
    }
}
