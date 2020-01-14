package TwoToOne;

import javax.swing.*;
import java.awt.*;

public interface BoardConstants {

    int ROWS = 4;
    int TOP_LEFT_X = 50;
    int TOP_LEFT_Y = 50;
    int BOARD_WIDTH = 500;
    int BOARD_HEIGHT = 500;
    int SPAN_X = BOARD_WIDTH / (ROWS - 1);
    int SPAN_Y = BOARD_HEIGHT / (ROWS - 1);
    int LIMIT = 100;
    int PIECE_SIZE = 100;
    int CHOSEN_MARK_SIZE = 20;
    int INF = (int)1e9;
    int[][] DIR = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    Image BLACK_PIECE = new ImageIcon("image/black.png").getImage();
    Image WHITE_PIECE = new ImageIcon("image/white.png").getImage();
    Image BackGROUND = new ImageIcon("image/bgi.png").getImage();

}
