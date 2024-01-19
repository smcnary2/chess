package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    PieceType promotionPiece;
    PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.row;
        int currentCol = myPosition.column;
        PieceType currentPieceType;//goes through switch
        if (this.promotionPiece == null) {//I might get rid of this
            currentPieceType = pieceType;
        } else {
            currentPieceType = this.promotionPiece;
        }
        switch(currentPieceType){
            case BISHOP:
                var BishopObj = new BishopMoves(myPosition, board, pieceColor);
                BishopObj.TestAllMoves();
                return BishopObj.moves;
            case PAWN:
                var PawnObj = new PawnMoves(myPosition, board, pieceColor);
                PawnObj.TestAllMoves();
                return PawnObj.moves;
        }

        throw new RuntimeException("Not implemented");
    }

}
