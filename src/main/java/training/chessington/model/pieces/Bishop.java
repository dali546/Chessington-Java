package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractPiece {
    public Bishop(PlayerColour colour) {
        super(PieceType.BISHOP, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(bottomLeft(from, board));
        moves.addAll(bottomRight(from, board));
        moves.addAll(topRight(from, board));
        moves.addAll(topLeft(from, board));
        return moves;
    }

    private List<Move> bottomLeft(Coordinates from, Board board) {
        return getMovesInDirection(from,board,1,-1);
    }

    private List<Move> bottomRight(Coordinates from, Board board) {
        return getMovesInDirection(from,board,1,1);
    }

    private List<Move> topRight(Coordinates from, Board board) {
        return getMovesInDirection(from, board, -1, 1);
    }

    private List<Move> topLeft(Coordinates from, Board board) {
        return getMovesInDirection(from,board,-1,-1);
    }

}