package chess;

import java.util.HashSet;
import java.util.Set;

public class RookMoves {
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
    public RookMoves(ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor color){
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
        TestMove(1,0);//forward
        TestMove(-1,0);//backwards
        TestMove(0,1);//left
        TestMove(0, -1);//right
    }

    public void TestMove(int rowInc, int colInc){
        boolean notblocked = true;
        capture = false;

        currentRow = originalPosition.getRow()+rowInc;
        currentCol = originalPosition.getColumn()+colInc;
        boolean inBounds = (currentRow <= 8)&&(currentCol <= 8) && (currentRow >= 1)&& (currentCol >=1);
        while (inBounds && !capture && notblocked){
            notblocked = CanMove();
            currentRow = currentRow + rowInc;
            currentCol = currentCol + colInc;
            inBounds = (currentRow <= 8)&&(currentCol <= 8) && (currentRow >=1)&& (currentCol >=1);
        }
    }
}
