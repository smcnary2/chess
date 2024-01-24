package chess;

import java.util.HashSet;
import java.util.Set;

public class PawnMoves {
    int currentRow;
    int currentCol;
    ChessPosition endPosition;

    ChessPiece.PieceType promotionPiece = null;
    Set<ChessMove> moves = new HashSet<>();
    ChessPosition originalPosition;
    ChessPiece occupant;
    ChessBoard tmpBoard;
    int rowInc;//increment for row depending on direction

    ChessGame.TeamColor currentColor;
    public PawnMoves(ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor color){
        currentRow = myPosition.getRow();
        currentCol = myPosition.getColumn();
        endPosition = new ChessPosition(currentRow,currentCol);
        tmpBoard = board;
        currentColor = color;
        originalPosition = new ChessPosition(currentRow, currentCol);
    }
    public void TestAllMoves(){
        if(currentColor == ChessGame.TeamColor.WHITE){
            rowInc = 1;
            TestMoves();
            Capture(1);
            Capture(-1);

        }else{
            rowInc = -1;
            TestMoves();
            Capture(1);
            Capture(-1);
        }
    }


    public void TestMoves(){

        currentRow=currentRow + rowInc;
        //single
        if((currentRow >= 1) && (currentCol <= 8)){
           endPosition = new ChessPosition(currentRow,currentCol);
           //is position empty
           occupant = tmpBoard.getPiece(endPosition);
           if(occupant == null){
               CheckPromotion();
               if ((originalPosition.row == 2 &&rowInc ==1) ||(originalPosition.row ==7 && rowInc ==-1)){
                   currentRow = currentRow + rowInc;
                   endPosition = new ChessPosition(currentRow, currentCol);
                   occupant = tmpBoard.getPiece(endPosition);
                   if(occupant == null){
                       moves.add(new ChessMove(originalPosition,endPosition, promotionPiece));
                   }
               }
           }

        }

    }

    public void Capture(int colInc){
        currentRow = originalPosition.getRow() +rowInc;
        currentCol = originalPosition.getColumn() +colInc;
        if((currentRow >= 1) && (currentCol >=1 ) && (currentRow <= 8) && (currentCol <= 8)){
            endPosition = new ChessPosition(currentRow,currentCol);
            occupant = tmpBoard.getPiece(endPosition);
            if((occupant != null)&& (occupant.getTeamColor() != currentColor)){
                CheckPromotion();
            }
        }
    }

    public void CheckPromotion(){
        if(currentRow == 1 || currentRow ==8){
            moves.add(new ChessMove(originalPosition, endPosition, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(originalPosition, endPosition, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(originalPosition, endPosition, ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(originalPosition, endPosition, ChessPiece.PieceType.KNIGHT));
        }else{
            moves.add(new ChessMove(originalPosition,endPosition, promotionPiece));
        }
    }


}
