package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.GamePlayUI.drawChessBoard;

public class Main {
    public static void main(String [] args){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        out.println("Welcome to 240 chess. Type Help to get started");
        Scanner scanner = new Scanner(System.in);
        var result ="";
        while (!result.equals("quit")) {
            result = scanner.nextLine();

            System.out.println();
        }


        drawChessBoard(out);// this is package-private is that okay?

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
}