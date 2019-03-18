package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {
    public King(PlayerColour colour) {
        super(PieceType.KING, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        Board board = game.getBoard();
        List<Move> moves = new ArrayList<>();

        if (isValidMove(board, from.plus(1, 1))) moves.add(new Move(from, from.plus(1, 1)));
        if (isValidMove(board, from.plus(1, 0))) moves.add(new Move(from, from.plus(1, 0)));
        if (isValidMove(board, from.plus(1, -1))) moves.add(new Move(from, from.plus(1, -1)));
        if (isValidMove(board, from.plus(0, 1))) moves.add(new Move(from, from.plus(0, 1)));
        if (isValidMove(board, from.plus(0, -1))) moves.add(new Move(from, from.plus(0, -1)));
        if (isValidMove(board, from.plus(-1, 1))) moves.add(new Move(from, from.plus(-1, 1)));
        if (isValidMove(board, from.plus(-1, 0))) moves.add(new Move(from, from.plus(-1, 0)));
        if (isValidMove(board, from.plus(-1, -1))) moves.add(new Move(from, from.plus(-1, -1)));

        return moves;
    }

}
