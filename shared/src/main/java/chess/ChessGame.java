package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    public ChessBoard board;
    public TeamColor colorInPlay;
    public Collection <ChessMove> moves;
    public ChessPosition kingInPlay;

    public ChessGame() {
        colorInPlay = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return colorInPlay;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        colorInPlay = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = this.board.getPiece(startPosition);

        moves = piece.pieceMoves(this.board, startPosition);
        //call isInCheck
        //simulate move on fake board
        Collection<ChessMove> movesPt2 = new HashSet<>();
        ChessBoard fakeBoard;
        boolean check = false;
        for (ChessMove m:
                moves) {
            fakeBoard = board;
            //check if move causes check
            ChessMove tmp = new ChessMove(new ChessPosition(m.getStartPosition().getRow(), m.getStartPosition().getColumn()), new ChessPosition(m.getEndPosition().getRow(), m.getEndPosition().getColumn()), m.getPromotionPiece());
            ChessPiece currentPiece = this.board.getPiece(new ChessPosition(m.getEndPosition().getRow(), m.getEndPosition().getColumn()));
            fakeBoard.movePiece(tmp);//move piece back
            check = this.isInCheck(piece.getTeamColor());

            tmp = new ChessMove(new ChessPosition(m.getEndPosition().getRow(), m.getEndPosition().getColumn()), new ChessPosition(m.getStartPosition().getRow(), m.getStartPosition().getColumn()), m.getPromotionPiece());
            fakeBoard.movePiece(tmp);
            fakeBoard.addPiece(new ChessPosition(m.getEndPosition().getRow(), m.getEndPosition().getColumn()),currentPiece);
            if(!check){//try to eliminate check change from checkmate to modify the board
                movesPt2.add(m);// if you can't remove move
            }
            // add to 2nd list instead of removing
        }



        return movesPt2;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        moves = this.validMoves(move.getStartPosition());
        //check if theres a promotion and switch out piece
        if(!(this.board.getPiece(move.getStartPosition()).getTeamColor().equals(colorInPlay))){
            throw new InvalidMoveException();
        }

        //contains

        if (moves.contains(move)) {

            this.board.movePiece(move);
            if (this.board.getPiece(move.getStartPosition()) != null) {
                this.board.addPiece(move.getStartPosition(), null);
            }

            ChessPiece tmp = null;
            if (move.getPromotionPiece() != null) {//check if there's a promotion
                if (move.getPromotionPiece().equals(ChessPiece.PieceType.KING)) {
                    tmp = new ChessPiece(colorInPlay, ChessPiece.PieceType.KING);
                } else if (move.getPromotionPiece().equals(ChessPiece.PieceType.KNIGHT)) {
                    tmp = new ChessPiece(colorInPlay, ChessPiece.PieceType.KNIGHT);
                } else if (move.getPromotionPiece().equals(ChessPiece.PieceType.ROOK)) {
                    tmp = new ChessPiece(colorInPlay, ChessPiece.PieceType.ROOK);
                } else if (move.getPromotionPiece().equals(ChessPiece.PieceType.BISHOP)) {
                    tmp = new ChessPiece(colorInPlay, ChessPiece.PieceType.BISHOP);
                } else if (move.getPromotionPiece().equals(ChessPiece.PieceType.QUEEN)) {
                    tmp = new ChessPiece(colorInPlay, ChessPiece.PieceType.QUEEN);
                }
                this.board.addPiece(move.getEndPosition(), tmp);//get position of piece and change it to promotion piece
            }
        }else{
            throw new InvalidMoveException();
        }
        if(colorInPlay.equals(TeamColor.WHITE)){
            colorInPlay = TeamColor.BLACK;
        }else{
            colorInPlay = TeamColor.WHITE;
        }
    }
    public void findKing(TeamColor teamColor){
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition tmpPos = new ChessPosition(i,j);
                ChessPiece tmp = board.getPiece(tmpPos);
                if(tmp != null && tmp.getPieceType().equals(ChessPiece.PieceType.KING)&& tmp.getTeamColor() == teamColor){
                    kingInPlay = tmpPos;
                }
            }
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        findKing(teamColor);//find king
        if(kingInPlay == null){
            System.out.println("no king found");
        }
        for (int i = 1; i < 9; i++) {//use piece moves on all pieces
            for (int j = 1; j < 9; j++) {
                ChessPosition currentPos = new ChessPosition(i,j);
                ChessPiece currentPiece = board.getPiece(currentPos);
                if( currentPiece != null && currentPiece.getTeamColor() != teamColor){
                    moves = currentPiece.pieceMoves(this.board,currentPos);//PieceMoves
                    if(moves != null){
                        if((moves.contains(new ChessMove(currentPos,kingInPlay, null))) || (moves.contains(new ChessMove(currentPos,kingInPlay, ChessPiece.PieceType.QUEEN)))){
                            return true;
                        }

                    }

                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessBoard fakeBoard;
        boolean check = this.isInCheck(teamColor);

        if(check){//if check is true see if king can escape
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition currentPosition = new ChessPosition(i,j);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    if(currentPiece != null && currentPiece.getTeamColor() != teamColor){
                        moves = currentPiece.pieceMoves(this.board, currentPosition);
                        if(moves != null){
                            for (ChessMove m :
                                    moves) {
                                fakeBoard = board;
                                ChessMove tmp = new ChessMove(currentPosition, new ChessPosition(m.getEndPosition().getRow(), m.getEndPosition().getColumn()), m.getPromotionPiece());
                                fakeBoard.movePiece(tmp);//move piece
                                kingInPlay = null;
                                findKing(teamColor);
                                if(kingInPlay ==null){
                                    check = true;
                                }else{
                                    check = this.isInCheck(colorInPlay);
                                }

                            }
                        }
                        if(check){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessBoard fakeBoard;
        boolean check = this.isInCheck(colorInPlay);
        if(!check){//move this
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition currentPos = new ChessPosition(i, j);
                    ChessPiece currentPiece = board.getPiece(currentPos);
                    if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                        moves = currentPiece.pieceMoves(this.board, currentPos);//piece.piece moves
                        if (moves != null) {
                            for (ChessMove m :
                                    moves) {
                                fakeBoard = board;
                                ChessMove tmp = new ChessMove(currentPos, new ChessPosition(m.getEndPosition().getRow(), m.getEndPosition().getColumn()), m.getPromotionPiece());
                                fakeBoard.movePiece(tmp);//move piece
                                check = this.isInCheck(teamColor);

                            }
                            if (check) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}

