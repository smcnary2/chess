package chess;

import java.util.HashSet;
import java.util.Set;

public class KnightMoves {
    boolean capture;
    int currentRow;
    int currentCol;
    ChessPosition endPosition;

    ChessPiece.PieceType promotionPiece = null;
    Set<ChessMove> moves = new HashSet<>();
    ChessPosition originalPosition;
    ChessPiece occupant;
    ChessBoard tmpBoard;
    ChessGame.TeamColor currentColor;



    public KnightMoves(ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor color) {
        currentRow = myPosition.getRow();
        currentCol = myPosition.getColumn();
        endPosition = new ChessPosition(currentRow,currentCol);
        tmpBoard = board;
        currentColor = color;
        originalPosition = new ChessPosition(currentRow, currentCol);
    }

    public boolean CanMove(){
        endPosition = new ChessPosition(currentRow,currentCol);
        occupant = tmpBoard.getPiece(endPosition);
        if(occupant != null ){
            capture = !(currentColor == occupant.pieceColor);
            if(capture){
                moves.add(new ChessMove(originalPosition, endPosition, promotionPiece));
                return true;
            }else{
                return false;
            }
        }
        moves.add(new ChessMove(originalPosition, endPosition, promotionPiece));
        return true;
    }

    public void TestAllMoves(){
        TestMove(2,-1);//f2 l1
        TestMove(2,1);//f2 r1
        TestMove(1,-2);//f1 l2
        TestMove(1, 2);//f1 r2
        TestMove(-2,-1);//b2 l1
        TestMove(-2,1);//b2 r1
        TestMove(-1,-2);//b1 l2
        TestMove(-1, 2);//b1 r2
    }

    public void TestMove(int rowInc, int colInc){
        capture = false;

        currentRow = originalPosition.getRow()+rowInc;
        currentCol = originalPosition.getColumn()+colInc;
        boolean inBounds = (currentRow <= 8)&&(currentCol <= 8) && (currentRow >= 1)&& (currentCol >=1);
        if (inBounds && !capture){
            CanMove();

        }
    }
}
