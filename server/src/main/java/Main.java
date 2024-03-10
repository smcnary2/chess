import chess.ChessGame;
import chess.ChessPiece;
import dataAccess.DataAccessException;
import server.Server;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        Server chessServer = new Server();
        chessServer.run(8080);
        //persistance test
        //tests you make
        // find auth in AuthDAO
    }

}