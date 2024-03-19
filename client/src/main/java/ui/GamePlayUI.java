package ui;

import java.io.PrintStream;

import static ui.EscapeSequences.*;
public class GamePlayUI {

    private static final int BOARD_SIZE_IN_SQUARES = 4;
    private static final int SQUARE_SIZE_IN_CHARS = 2;
    private static final int LINE_WIDTH_IN_CHARS = 2;


    static void drawChessBoard(PrintStream out){
        drawBoarders(out);
        out.println();
        drawRow(out);
        drawBoarders(out);

    }
    private static void drawBoarders(PrintStream out){
        setDarkGrey(out);
        String[] boarder = {"h","g","f","e","d","c","b","a"};
        out.print(" ");
        for(int boardCol  = 0; boardCol<BOARD_SIZE_IN_SQUARES*2; ++boardCol){
            drawBoarder(out, boarder[boardCol]);
            out.print("      ");
        }
    }
    private static void drawBoarder(PrintStream out, String s){
        printBoarderText(out, s);


    }
    private static void printBoarderText(PrintStream out, String s){
        setWhite(out);
        out.print(s);
        setDarkGrey(out);
    }

    private static void drawRow(PrintStream out){
        int numloops = 0;
        for (int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES*2; squareRow = (numloops%2==0) ? squareRow+1:squareRow) {
            setWhite(out);
            if(numloops%2 ==0){
                out.print(squareRow+1);
            }else{
                out.print(' ');
            }

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                setGrey(out);

                if (squareRow%2 ==0) {
                    setBlack(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }
                    // Draw right line
                if(squareRow%2 !=0){
                    setBlack(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }else{
                    setGrey(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }

                setDarkGrey(out);
            }
            setWhite(out);
            if(numloops%2 ==0){
                out.print(squareRow+1);
            }

            out.println();
            numloops++;
        }
    }


    private static void setGrey(PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }
    private static void setDarkGrey(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREY);
    }
    private static void setWhite(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void setBlack(PrintStream out){
        out.print(SET_BG_COLOR_BLACK);
    }
    private static void setBlue(PrintStream out){
        out.print(SET_TEXT_COLOR_BLUE);

    }

    private static void setRed(PrintStream out){
        out.print(SET_TEXT_COLOR_RED);
    }

}
