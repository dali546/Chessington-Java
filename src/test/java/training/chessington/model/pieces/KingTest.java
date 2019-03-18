package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class KingTest {

    @Test
    public void kingCanMove1Square() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coordinates = new Coordinates(3, 3);
        board.placePiece(coordinates, king);

        List<Move> moves = king.getAllowedMoves(coordinates, game);

        assertThat(moves).contains(
                new Move(coordinates, coordinates.plus(1, 0)),
                new Move(coordinates, coordinates.plus(1, 1)),
                new Move(coordinates, coordinates.plus(1, -1)),
                new Move(coordinates, coordinates.plus(0, 1)),
                new Move(coordinates, coordinates.plus(0, -1)),
                new Move(coordinates, coordinates.plus(-1, 0)),
                new Move(coordinates, coordinates.plus(-1, 1)),
                new Move(coordinates, coordinates.plus(-1, -1))
        );
    }

    @Test
    public void kingCannotMoveOffBoard() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coord = new Coordinates(7, 1);
        board.placePiece(coord, king);

        List<Move> moves = king.getAllowedMoves(coord, game);

        assertThat(moves).containsExactlyInAnyOrder(
                new Move(coord, coord.plus(-1, -1)),
                new Move(coord, coord.plus(-1, 0)),
                new Move(coord, coord.plus(-1, 1)),
                new Move(coord, coord.plus(0, -1)),
                new Move(coord, coord.plus(0, 1))
        );
    }

    @Test
    public void kingCannotCaptureFriend() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece king = new King(PlayerColour.WHITE);
        Piece friendlyPawn = new Pawn(PlayerColour.WHITE);
        Coordinates coord = new Coordinates(3, 3);
        Coordinates pawnCoord = new Coordinates(3, 4);
        board.placePiece(coord, king);
        board.placePiece(pawnCoord, friendlyPawn);

        List<Move> moves = king.getAllowedMoves(coord, game);

        assertThat(moves).doesNotContain(
                new Move(coord, pawnCoord)
        );
    }

    @Test
    public void kingCanEatEnemy() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece king = new King(PlayerColour.WHITE);
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates coord = new Coordinates(3, 3);
        Coordinates pawnCoord = new Coordinates(4, 4);
        board.placePiece(coord, king);
        board.placePiece(pawnCoord, enemyPiece);

        List<Move> moves = king.getAllowedMoves(coord, game);

        assertThat(moves).contains(
                new Move(coord, pawnCoord)
        );
    }

}
