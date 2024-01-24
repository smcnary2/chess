package chess;

import java.util.Collection;
import java.util.Objects;

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

        PieceType currentPieceType;//goes through switch
        if (this.promotionPiece == null) {//I might get rid of this
            currentPieceType = pieceType;
        } else {
            currentPieceType = this.promotionPiece;
        }
        switch(currentPieceType){
            case BISHOP:
                var BishopObj = new BishopMoves(myPosition, board, pieceColor);
                BishopObj.TestAllMovesBishop();
                return BishopObj.moves;
            case PAWN:
                var PawnObj = new PawnMoves(myPosition, board, pieceColor);
                PawnObj.TestAllMoves();
                return PawnObj.moves;
            case ROOK:
                var RookObj = new BishopMoves(myPosition, board, pieceColor);
                RookObj.TestAllMovesRook();
                return RookObj.moves;
            case QUEEN:
                var QueenObj = new BishopMoves(myPosition,board,pieceColor);
                QueenObj.TestAllMovesQueen();
                return QueenObj.moves;
            case KING:
                var KingObj = new BishopMoves(myPosition,board,pieceColor);
                KingObj.TestAllMovesKing();
                return KingObj.moves;
            case KNIGHT:
                var KnightObj = new BishopMoves(myPosition,board,pieceColor);
                KnightObj.TestAllMovesKnight();
                return KnightObj.moves;
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && promotionPiece == that.promotionPiece && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, promotionPiece, pieceType);
    }
}
