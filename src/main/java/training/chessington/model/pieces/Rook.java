package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour) {
        super(PieceType.ROOK, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from,Game game) {
        Board board = game.getBoard();
        List<Move> moves = new ArrayList<>();

        moves.addAll(downMoves(from, board));
        moves.addAll(upMoves(from, board));
        moves.addAll(rightMoves(from, board));
        moves.addAll(leftMoves(from, board));

        return moves;
    }

    private List<Move> downMoves(Coordinates from, Board board) {
        return getMovesInDirection(from,board,1,0);
    }

    private List<Move> upMoves(Coordinates from, Board board) {
        return getMovesInDirection(from,board,-1,0);
    }

    private List<Move> rightMoves(Coordinates from, Board board) {
        return getMovesInDirection(from, board, 0, 1);
    }

    private List<Move> leftMoves(Coordinates from, Board board) {
        return getMovesInDirection(from,board,0,-1);
    }
}