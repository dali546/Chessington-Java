package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {

    @Test
    public void knightCannotMoveOffBoard() {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coord = new Coordinates(7, 1);
        board.placePiece(coord, knight);

        List<Move> moves = knight.getAllowedMoves(coord, game);

        assertThat(moves).containsExactlyInAnyOrder(
                new Move(coord, coord.plus(-2, 1)),
                new Move(coord, coord.plus(-2, -1)),
                new Move(coord, coord.plus(-1, 2))
        );
    }

    @Test
    public void knightCanHave8Moves() {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coord = new Coordinates(3, 3);
        board.placePiece(coord, knight);

        List<Move> moves = knight.getAllowedMoves(coord, game);

        assertThat(moves).containsExactlyInAnyOrder(
                new Move(coord, coord.plus(2, 1)),
                new Move(coord, coord.plus(2, -1)),
                new Move(coord, coord.plus(1, 2)),
                new Move(coord, coord.plus(1, -2)),
                new Move(coord, coord.plus(-1, -2)),
                new Move(coord, coord.plus(-1, 2)),
                new Move(coord, coord.plus(-2, 1)),
                new Move(coord, coord.plus(-2, -1))
        );
    }

    @Test
    public void knightCannotCaptureFriend() {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece knight = new Knight(PlayerColour.WHITE);
        Piece friendlyPawn = new Pawn(PlayerColour.WHITE);
        Coordinates coord = new Coordinates(3, 3);
        Coordinates pawnCoord = new Coordinates(5, 4);
        board.placePiece(coord, knight);
        board.placePiece(pawnCoord, friendlyPawn);

        List<Move> moves = knight.getAllowedMoves(coord, game);

        assertThat(moves).doesNotContain(
                new Move(coord, pawnCoord)
        );
    }

    @Test
    public void knightCanEatEnemy() {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece knight = new Knight(PlayerColour.WHITE);
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates coord = new Coordinates(3, 3);
        Coordinates pawnCoord = new Coordinates(5, 4);
        board.placePiece(coord, knight);
        board.placePiece(pawnCoord, enemyPiece);

        List<Move> moves = knight.getAllowedMoves(coord, game);

        assertThat(moves).contains(
                new Move(coord, pawnCoord)
        );
    }

}
