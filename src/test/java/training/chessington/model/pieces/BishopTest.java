package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BishopTest {

    @Test
    public void bishopCanMoveOnAnEmptyBoard() {
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(2, 2);
        board.placePiece(coordinates, bishop);

        List<Move> moves = bishop.getAllowedMoves(coordinates, board);

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
                new Move(coordinates, coordinates.plus(5, 5))
        );
    }

    @Test
    public void bishopBlockedByFriendly() {
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(7, 3);
        Piece friendlyBishop = new Bishop(PlayerColour.BLACK);
        Coordinates friendlyCoordinates = new Coordinates(5, 1);
        board.placePiece(coordinates, bishop);
        board.placePiece(friendlyCoordinates, friendlyBishop);

        List<Move> moves = bishop.getAllowedMoves(coordinates, board);

        assertThat(moves).doesNotContain(
                new Move(coordinates, friendlyCoordinates),
                new Move(coordinates, friendlyCoordinates.plus(-1, -1))
        );
    }

    @Test
    public void bishopCanCaptureEnemyPiece() {
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(7, 3);
        Piece enemyBishop = new Bishop(PlayerColour.WHITE);
        Coordinates enemyCoord = new Coordinates(4, 0);
        board.placePiece(coordinates, bishop);
        board.placePiece(enemyCoord, enemyBishop);

        List<Move> moves = bishop.getAllowedMoves(coordinates, board);

        assertThat(moves).contains(new Move(coordinates, enemyCoord));
    }

    @Test
    public void bishopBlockedByEnemyPiece() {
        Board board = Board.empty();

        Piece bishop = new Bishop(PlayerColour.BLACK);
        Piece enemyBishop = new Bishop(PlayerColour.WHITE);
        Piece enemyBishop2 = new Bishop(PlayerColour.WHITE);

        Coordinates coordinates = new Coordinates(1, 1);
        Coordinates enemyCoord = new Coordinates(4, 4);
        Coordinates enemyCoord2 = new Coordinates(5, 5);

        board.placePiece(coordinates, bishop);
        board.placePiece(enemyCoord, enemyBishop);
        board.placePiece(enemyCoord2, enemyBishop2);

        List<Move> moves = bishop.getAllowedMoves(coordinates, board);

        assertThat(moves)
                .doesNotContain(new Move(coordinates, enemyCoord2))
                .contains(
                        new Move(coordinates, enemyCoord),
                        new Move(coordinates, enemyCoord.plus(-1, -1)),
                        new Move(coordinates, enemyCoord.plus(-2, -2))
                );
    }
}
