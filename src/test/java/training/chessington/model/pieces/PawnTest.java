package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {
    @Test
    public void whitePawnCanMoveUpOneSquare() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, game);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-1, 0)));
    }

    @Test
    public void blackPawnCanMoveDownOneSquare() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(1, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, game);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 0)));
    }

    @Test
    public void whitePawnCanMoveUpTwoSquaresIfNotMoved() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, game);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-2, 0)));
    }

    @Test
    public void blackPawnCanMoveDownTwoSquaresIfNotMoved() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(1, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, game);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(2, 0)));
    }

    @Test
    public void whitePawnCannotMoveUpTwoSquaresIfAlreadyMoved() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates initial = new Coordinates(6, 4);
        board.placePiece(initial, pawn);

        Coordinates moved = initial.plus(-1, 0);
        board.move(initial, moved);

        // Act
        List<Move> moves = pawn.getAllowedMoves(moved, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(moved, moved.plus(-2, 0)));
    }

    @Test
    public void blackPawnCannotMoveDownTwoSquaresIfAlreadyMoved() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates initial = new Coordinates(1, 4);
        board.placePiece(initial, pawn);

        Coordinates moved = initial.plus(1, 0);
        board.move(initial, moved);

        // Act
        List<Move> moves = pawn.getAllowedMoves(moved, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(moved, moved.plus(2, 0)));
    }

    @Test
    public void pawnsCannotMoveIfPieceInFront() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(3, 4);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(4, 4);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords, game);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords, game);

        // Assert
        assertThat(blackMoves).isEmpty();
        assertThat(whiteMoves).isEmpty();
    }

    @Test
    public void pawnsCannotMoveTwoSquaresIfPieceTwoInFront() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(2, 4);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(4, 4);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords, game);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords, game);

        // Assert
        assertThat(blackMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(2, 0)));
        assertThat(whiteMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(-2, 0)));
    }

    @Test
    public void whitePawnsCannotMoveAtTopOfBoard() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(0, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, game);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void blackPawnsCannotMoveAtBottomOfBoard() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords, game);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void whitePawnsCanCaptureDiagonally() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPiece = new Rook(PlayerColour.BLACK);
        Coordinates pawnCoords = new Coordinates(4, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates enemyCoords = pawnCoords.plus(-1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        // Assert
        assertThat(moves).contains(new Move(pawnCoords, enemyCoords));
    }

    @Test
    public void blackPawnsCanCaptureDiagonally() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates pawnCoords = new Coordinates(3, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates enemyCoords = pawnCoords.plus(1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        // Assert
        assertThat(moves).contains(new Move(pawnCoords, enemyCoords));
    }

    @Test
    public void pawnsCannotMoveDiagonallyOffBoard() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoords = new Coordinates(3, 0);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoords = new Coordinates(4, 0);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords, game);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords, game);

        // Assert
        assertThat(blackMoves).isEmpty();
        assertThat(whiteMoves).isEmpty();
    }

    @Test
    public void whitePawnsCannotMoveDiagonallyNotToCapture() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);

        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece friendlyPiece = new Rook(PlayerColour.WHITE);
        Coordinates pawnCoords = new Coordinates(4, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates rookCoords = pawnCoords.plus(-1, 1);
        board.placePiece(rookCoords, friendlyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(pawnCoords, rookCoords));
        Coordinates otherDiagonal = pawnCoords.plus(-1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, otherDiagonal));
    }

    @Test
    public void blackPawnsCannotMoveDiagonallyNotToCapture() {
        // Arrange
        Board board = Board.empty();
        Game game = new Game(board);

        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece friendlyPiece = new Rook(PlayerColour.BLACK);
        Coordinates pawnCoords = new Coordinates(3, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates rookCoords = pawnCoords.plus(1, 1);
        board.placePiece(rookCoords, friendlyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(pawnCoords, rookCoords));
        Coordinates otherDiagonal = pawnCoords.plus(1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, otherDiagonal));
    }

    @Test
    public void pawnCannotMoveDiagonallyOffBoard() {
        Board board = Board.empty();
        Game game = new Game(board);
        Piece pawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPawn = new Pawn(PlayerColour.BLACK);
        Coordinates pawnCoords = new Coordinates(3, 0);
        Coordinates enemyPawnCoords = new Coordinates(4, 1);
        board.placePiece(pawnCoords, pawn);
        board.placePiece(enemyPawnCoords, enemyPawn);
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        Coordinates diag = pawnCoords.plus(1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, diag));
    }

    @Test
    public void canEnPassantCapture() throws InvalidMoveException {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece enemyPawn = new Pawn(PlayerColour.WHITE);
        Coordinates pawnCoords = new Coordinates(4, 2);
        Coordinates enemyPawnCoords = new Coordinates(6, 1);
        board.placePiece(pawnCoords, pawn);
        board.placePiece(enemyPawnCoords, enemyPawn);

        game.makeMove(new Move(enemyPawnCoords, enemyPawnCoords.plus(-2, 0)));
        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        assertThat(moves).contains(
                new Move(pawnCoords, pawnCoords.plus(1, -1))
        );
        game.makeMove(new Move(pawnCoords, pawnCoords.plus(1, -1)));
        assertThat(board.get(enemyPawnCoords.plus(-2, 0))).isNull();
    }

    @Test
    public void cantEnPassantCaptureAfterMove() throws InvalidMoveException {
        Board board = Board.empty();
        Game game = new Game(board);

        Piece pawn = new Pawn(PlayerColour.BLACK);
        Piece friendlyPiece = new Pawn(PlayerColour.BLACK);
        Piece enemyPawn = new Pawn(PlayerColour.WHITE);
        Piece enemyPiece = new Pawn(PlayerColour.WHITE);

        Coordinates pawnCoords = new Coordinates(4, 2);
        Coordinates pieceCoords = new Coordinates(1, 5);
        Coordinates enemyPawnCoords = new Coordinates(6, 1);
        Coordinates enemyPieceCoords = new Coordinates(6, 6);

        board.placePiece(pawnCoords, pawn);
        board.placePiece(pieceCoords, friendlyPiece);
        board.placePiece(enemyPawnCoords, enemyPawn);
        board.placePiece(enemyPieceCoords, enemyPiece);

        List<Move> moves = pawn.getAllowedMoves(pawnCoords, game);

        game.makeMove(new Move(enemyPawnCoords, enemyPawnCoords.plus(-2, 0)));
        game.makeMove(new Move(pieceCoords, pieceCoords.plus(1, 0)));
        game.makeMove(new Move(enemyPieceCoords, enemyPieceCoords.plus(-1, 0)));

        assertThat(moves).doesNotContain(
                new Move(pawnCoords, pawnCoords.plus(1, -1))
        );
    }
}
