package chess;

import java.util.HashSet;
import java.util.Set;

public class TestMoves {
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



    public TestMoves(ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor color) {
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

    public void TestAllMovesBishop(){
        TestMove(-1,1);//diagonal back right
        TestMove(1,1);//diagonal forward right
        TestMove(-1,-1);//diagonal back left
        TestMove(1, -1);//Diagonal Forward Left
    }
    public void TestAllMovesRook(){
        TestMove(1,0);//forward
        TestMove(-1,0);//backwards
        TestMove(0,1);//left
        TestMove(0, -1);//right
    }
    public void TestAllMovesQueen(){
        TestMove(-1,1);//diagonal back right
        TestMove(1,1);//diagonal forward right
        TestMove(-1,-1);//diagonal back left
        TestMove(1, -1);//Diagonal Forward Left
        TestMove(1,0);//forward
        TestMove(-1,0);//backwards
        TestMove(0,1);//left
        TestMove(0, -1);//right
    }
    public void TestAllMovesKing(){
        TestMoveK(-1,1);//diagonal back right
        TestMoveK(1,1);//diagonal forward right
        TestMoveK(-1,-1);//diagonal back left
        TestMoveK(1, -1);//Diagonal Forward Left
        TestMoveK(1,0);//forward
        TestMoveK(-1,0);//backwards
        TestMoveK(0,1);//left
        TestMoveK(0, -1);//right
    }
    public void TestAllMovesKnight(){
        TestMoveK(2,-1);//f2 l1
        TestMoveK(2,1);//f2 r1
        TestMoveK(1,-2);//f1 l2
        TestMoveK(1, 2);//f1 r2
        TestMoveK(-2,-1);//b2 l1
        TestMoveK(-2,1);//b2 r1
        TestMoveK(-1,-2);//b1 l2
        TestMoveK(-1, 2);//b1 r2
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
    public void TestMoveK(int rowInc, int colInc){
        capture = false;

        currentRow = originalPosition.getRow()+rowInc;
        currentCol = originalPosition.getColumn()+colInc;
        boolean inBounds = (currentRow <= 8)&&(currentCol <= 8) && (currentRow >= 1)&& (currentCol >=1);
        if (inBounds && !capture){
            CanMove();

        }
    }
}
