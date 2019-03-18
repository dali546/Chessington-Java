package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RookTest {

    @Test
    public void rookCanMoveOnAnEmptyBoard() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(2, 2);
        board.placePiece(coordinates, rook);

        List<Move> moves = rook.getAllowedMoves(coordinates, game);

        assertThat(moves).contains(new Move(coordinates, coordinates.plus(4, 0)));
        assertThat(moves).containsExactlyInAnyOrder(
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

    @Test
    public void rookBlockedByFriendly() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(7, 3);
        Piece friendlyRook = new Rook(PlayerColour.BLACK);
        Coordinates friendlyCoordinates = new Coordinates(3, 3);
        board.placePiece(coordinates, rook);
        board.placePiece(friendlyCoordinates, friendlyRook);

        List<Move> moves = rook.getAllowedMoves(coordinates, game);

        assertThat(moves).doesNotContain(
                new Move(coordinates, friendlyCoordinates),
                new Move(coordinates, friendlyCoordinates.plus(-1, 0)),
                new Move(coordinates, friendlyCoordinates.plus(-2, 0)),
                new Move(coordinates, friendlyCoordinates.plus(-3, 0))
        );
    }

    @Test
    public void rookCanCaptureEnemyPiece() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coordinates = new Coordinates(7, 3);
        Piece enemyRook = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoord = new Coordinates(3, 3);
        board.placePiece(coordinates, rook);
        board.placePiece(enemyCoord, enemyRook);

        List<Move> moves = rook.getAllowedMoves(coordinates, game);

        assertThat(moves).contains(new Move(coordinates, enemyCoord));
    }

    @Test
    public void rookBlockedByEnemyPiece() {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece rook = new Rook(PlayerColour.BLACK);
        Piece enemyRook = new Rook(PlayerColour.WHITE);
        Piece enemyRook2 = new Rook(PlayerColour.WHITE);

        Coordinates coordinates = new Coordinates(7, 3);
        Coordinates enemyCoord = new Coordinates(3, 3);
        Coordinates enemyCoord2 = new Coordinates(1, 3);

        board.placePiece(coordinates, rook);
        board.placePiece(enemyCoord, enemyRook);
        board.placePiece(enemyCoord2, enemyRook2);

        List<Move> moves = rook.getAllowedMoves(coordinates, game);

        assertThat(moves)
                .doesNotContain(new Move(coordinates, enemyCoord2))
                .contains(
                        new Move(coordinates, enemyCoord),
                        new Move(coordinates, enemyCoord.plus(1, 0)),
                        new Move(coordinates, enemyCoord.plus(2, 0)),
                        new Move(coordinates, enemyCoord.plus(3, 0))
                );
    }
}
