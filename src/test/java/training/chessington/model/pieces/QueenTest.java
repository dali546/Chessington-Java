package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class QueenTest {

    @Test
    public void queenBlockedByEnemyPiece() {
        Board board = Board.empty();

        Piece queen = new Queen(PlayerColour.BLACK);
        Piece enemyBishop = new Bishop(PlayerColour.WHITE);
        Piece enemyPawn = new Pawn(PlayerColour.WHITE);
        Piece enemyRook = new Rook(PlayerColour.WHITE);
        Piece enemyKnight = new Knight(PlayerColour.WHITE);

        Coordinates coordinates = new Coordinates(3, 3);
        Coordinates enemyBishopCoord = new Coordinates(5, 5);
        Coordinates enemyPawnCoord = new Coordinates(6, 6);
        Coordinates enemyRookCoord = new Coordinates(3, 5);
        Coordinates enemyKnightCoord = new Coordinates(3, 7);

        board.placePiece(coordinates, queen);
        board.placePiece(enemyBishopCoord, enemyBishop);
        board.placePiece(enemyPawnCoord, enemyPawn);
        board.placePiece(enemyRookCoord, enemyRook);
        board.placePiece(enemyKnightCoord, enemyKnight);

        List<Move> moves = queen.getAllowedMoves(coordinates, board);

        assertThat(moves)
                .doesNotContain(
                        new Move(coordinates, enemyKnightCoord),
                        new Move(coordinates, enemyPawnCoord))
                .contains(
                        new Move(coordinates, enemyBishopCoord),
                        new Move(coordinates, enemyBishopCoord.plus(-1, -1)),
                        new Move(coordinates, enemyRookCoord),
                        new Move(coordinates, enemyRookCoord.plus(0, -1))
                );
    }

    @Test
    public void queenBlockedByFriendly() {
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.BLACK);
        Piece friendPawn = new Pawn(PlayerColour.BLACK);
        Piece friendlyBishop = new Bishop(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(7, 3);
        Coordinates pawnFriend = new Coordinates(3, 3);
        Coordinates bishopFriend = new Coordinates(5, 1);
        board.placePiece(coordinates, queen);
        board.placePiece(pawnFriend, friendPawn);
        board.placePiece(bishopFriend, friendlyBishop);

        List<Move> moves = queen.getAllowedMoves(coordinates, board);

        assertThat(moves).doesNotContain(
                new Move(coordinates, pawnFriend),
                new Move(coordinates, pawnFriend.plus(-1, 0)),
                new Move(coordinates, pawnFriend.plus(-2, 0)),
                new Move(coordinates, pawnFriend.plus(-3, 0)),
                new Move(coordinates, bishopFriend),
                new Move(coordinates, bishopFriend.plus(-1, -1))
        );
    }

    @Test
    public void queenCanCaptureEnemyPiece() {
        Board board = Board.empty();
        Piece rook = new Queen(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(7, 3);
        Piece enemyRook = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoord = new Coordinates(3, 3);
        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoord2 = new Coordinates(7, 7);
        board.placePiece(coordinates, rook);
        board.placePiece(enemyCoord, enemyRook);
        board.placePiece(enemyCoord2, enemyPiece);
        List<Move> moves = rook.getAllowedMoves(coordinates, board);

        assertThat(moves).contains(
                new Move(coordinates, enemyCoord),
                new Move(coordinates, enemyCoord2)
        );
    }

    @Test
    public void queenCanMoveOnAnEmptyBoard() {
        new BishopTest();
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(2, 2);
        board.placePiece(coordinates, queen);

        List<Move> moves = queen.getAllowedMoves(coordinates, board);

        assertThat(moves).containsExactlyInAnyOrder(
                new Move(coordinates, coordinates.plus(-1, -1)),
                new Move(coordinates, coordinates.plus(-2, -2)),
                new Move(coordinates, coordinates.plus(-1, 1)),
                new Move(coordinates, coordinates.plus(-2, 2)),
                new Move(coordinates, coordinates.plus(1, -1)),
                new Move(coordinates, coordinates.plus(2, -2)),
                new Move(coordinates, coordinates.plus(1, 1)),
                new Move(coordinates, coordinates.plus(2, 2)),
                new Move(coordinates, coordinates.plus(3, 3)),
                new Move(coordinates, coordinates.plus(4, 4)),
                new Move(coordinates, coordinates.plus(5, 5)),
                new Move(coordinates, coordinates.plus(-1, 0)),
                new Move(coordinates, coordinates.plus(-2, 0)),
                new Move(coordinates, coordinates.plus(1, 0)),
                new Move(coordinates, coordinates.plus(2, 0)),
                new Move(coordinates, coordinates.plus(3, 0)),
                new Move(coordinates, coordinates.plus(4, 0)),
                new Move(coordinates, coordinates.plus(5, 0)),
                new Move(coordinates, coordinates.plus(0, -1)),
                new Move(coordinates, coordinates.plus(0, -2)),
                new Move(coordinates, coordinates.plus(0, 1)),
                new Move(coordinates, coordinates.plus(0, 2)),
                new Move(coordinates, coordinates.plus(0, 3)),
                new Move(coordinates, coordinates.plus(0, 4)),
                new Move(coordinates, coordinates.plus(0, 5))
        );
    }
}
