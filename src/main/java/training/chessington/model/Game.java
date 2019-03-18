package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int SIZE = 8;
    private final Board board;
    private List<Move> moveHistory;

    private PlayerColour nextPlayer = PlayerColour.WHITE;

    private boolean isEnded = false;

    public Game(Board board) {
        this.board = board;
        moveHistory = new ArrayList<>();
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Board getBoard() {
        return board;
    }

    public Piece pieceAt(int row, int col) {
        return board.get(new Coordinates(row, col));
    }

    public List<Move> getAllowedMoves(Coordinates from) {
        if (isEnded) {
            return new ArrayList<>();
        }

        Piece piece = board.get(from);
        if (piece == null || piece.getColour() != nextPlayer) {
            return new ArrayList<>();
        }

        return piece.getAllowedMoves(from, this);
    }

    public void makeMove(Move move) throws InvalidMoveException {
        if (isEnded) {
            throw new InvalidMoveException("Game has ended!");
        }

        Coordinates from = move.getFrom();
        Coordinates to = move.getTo();

        Piece piece = board.get(from);
        if (piece == null) {
            throw new InvalidMoveException(String.format("No piece at %s", from));
        }

        if (piece.getColour() != nextPlayer) {
            throw new InvalidMoveException(String.format("Wrong colour piece - it is %s's turn", nextPlayer));
        }

        if (!piece.getAllowedMoves(from, this).contains(move)) {
            throw new InvalidMoveException(String.format("Cannot move piece %s from %s to %s", piece, from, to));
        }

        boolean isToEmpty = board.get(to) == null;
        board.move(from, to);
        moveHistory.add(new Move(from, to));

        Move lastMove = getMoveHistory().isEmpty() ? null : getMoveHistory().get(getMoveHistory().size() - 1);
        if (lastMoveValidEnPassant(lastMove, piece, isToEmpty))
            board.eat(to.plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0));

        nextPlayer = nextPlayer == PlayerColour.WHITE ? PlayerColour.BLACK : PlayerColour.WHITE;
    }

    private boolean lastMoveValidEnPassant(Move lastMove, Piece piece, boolean isEmpty) {
        return lastMove != null && //NOT START OF GAME
                board.get(lastMove.getTo().plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0)) != null && //PIECE BELOW IS NOT NULL
                piece.getType() == Piece.PieceType.PAWN && //CURRENT PIECE IS PAWN
                isEmpty && //TO IS EMPTY
                board.get(lastMove.getTo().plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0)).getType()
                        == Piece.PieceType.PAWN && //PIECE BELOW IS PAWN
                board.get(lastMove.getTo().plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0)).getColour()
                        != piece.getColour(); //PIECE BELOW IS ENEMY
    }

    public boolean isEnded() {
        return isEnded;
    }

    public String getResult() {
        return null;
    }
}
