package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends AbstractPiece {
    public Knight(PlayerColour colour) {
        super(PieceType.KNIGHT, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        List<Move> moves = new ArrayList<>();
        Board board = game.getBoard();

        if (isValidMove(board, from.plus(2, 1))) moves.add(new Move(from, from.plus(2, 1)));
        if (isValidMove(board, from.plus(2, -1))) moves.add(new Move(from, from.plus(2, -1)));
        if (isValidMove(board, from.plus(1, 2))) moves.add(new Move(from, from.plus(1, 2)));
        if (isValidMove(board, from.plus(1, -2))) moves.add(new Move(from, from.plus(1, -2)));
        if (isValidMove(board, from.plus(-1, -2))) moves.add(new Move(from, from.plus(-1, -2)));
        if (isValidMove(board, from.plus(-1, 2))) moves.add(new Move(from, from.plus(-1, 2)));
        if (isValidMove(board, from.plus(-2, 1))) moves.add(new Move(from, from.plus(-2, 1)));
        if (isValidMove(board, from.plus(-2, -1))) moves.add(new Move(from, from.plus(-2, -1)));

        return moves;
    }

}
