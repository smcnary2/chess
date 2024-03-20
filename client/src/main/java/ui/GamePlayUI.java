package ui;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ui.EscapeSequences.*;
public class GamePlayUI {

    private static final int BOARD_SIZE_IN_SQUARES = 4;
    private static final int SQUARE_SIZE_IN_CHARS = 2;
    private static final int LINE_WIDTH_IN_CHARS = 2;


    static void drawChessBoard(PrintStream out){
        String[] boarder = {"h","g","f","e","d","c","b","a"};
        List<String> pieces = new ArrayList<>(Arrays.asList(WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK));
        drawBoarders(out, boarder);
        out.println();
        drawRow(out,1,pieces);
        drawBoarders(out, boarder);
        boarder = new String[]{"a", "b", "c", "d", "e", "f", "g", "h" };
        pieces = new ArrayList<>(Arrays.asList(BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK,  BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN,WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK));

        out.println();
        out.println();
        out.println();
        out.println();
        drawBoarders(out,boarder);
        out.println();
        drawRow(out,-8,pieces);
        drawBoarders(out,boarder);


    }
    private static void drawBoarders(PrintStream out,String[] boarder){
        setDarkGrey(out);

        out.print(" ");
        for(int boardCol  = 0; boardCol<BOARD_SIZE_IN_SQUARES*2; ++boardCol){
            drawBoarder(out, boarder[boardCol]);
            out.print("       ");
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

    private static void drawRow(PrintStream out,int row, List<String> piecesNotPawns){

        int numloops = 0;
        for (int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES*2; squareRow = (numloops%2==0) ? squareRow+1:squareRow) {
            setWhite(out);
            if(numloops%2 ==0){
                out.print(Math.abs(row));//numbers on left side of board

            }else{
                out.print(' ');
            }

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                setGrey(out);

                if (squareRow%2 ==0) {
                    setDarkGrey(out);
                    if(numloops ==1|| numloops ==3|| numloops ==12 ||numloops ==15){
                        out.print(EMPTY);
                        out.print(piecesNotPawns.getFirst());
                        piecesNotPawns.removeFirst();
                    }else{

                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }

                }
                else {
                    setGrey(out);
                    if(numloops ==1|| numloops ==3 ||numloops ==12 ||numloops ==15){
                        out.print(EMPTY);
                        out.print(piecesNotPawns.getFirst());
                        piecesNotPawns.removeFirst();
                    }else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }
                }
                    // Draw right line
                if(squareRow%2 !=0){
                    setDarkGrey(out);
                    if( numloops ==1|| numloops ==3||  numloops ==12 ||numloops ==15){
                        out.print(EMPTY);
                        out.print(piecesNotPawns.getFirst());
                        piecesNotPawns.removeFirst();
                    }else{

                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }
                }else{
                    setGrey(out);
                    if(numloops ==1|| numloops ==3|| numloops ==12 ||numloops ==15){
                        out.print(EMPTY);
                        out.print(piecesNotPawns.getFirst());
                        piecesNotPawns.removeFirst();
                    }else {

                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }
                }

                setBlack(out);
            }
            setWhite(out);
            if(numloops%2 ==0){
                out.print(Math.abs(row));//numbers on right side of board
                row= row+1;
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
