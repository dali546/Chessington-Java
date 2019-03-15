package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends AbstractPiece {
    public Knight(PlayerColour colour) {
        super(PieceType.KNIGHT, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();

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

    private boolean isValidMove(Board board, Coordinates coords) {
        if (pieceNotObstructedOffsetXY(board, coords)) return true;
        return enemyPieceAtOffsetXY(board, coords);
    }
}
