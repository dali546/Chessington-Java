package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {


    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        Move MOVE_1 = new Move(from, from.plus(colour == PlayerColour.WHITE ? -1 : 1, 0));
        Move MOVE_2 = new Move(from, from.plus(colour == PlayerColour.WHITE ? -2 : 2, 0));
        Move MOVE_CAPTURE_RIGHT = new Move(from, colour == PlayerColour.WHITE ? from.plus(-1, 1) : from.plus(1, -1));
        Move MOVE_CAPTURE_LEFT = new Move(from, colour == PlayerColour.WHITE ? from.plus(-1, -1) : from.plus(1, 1));
        List<Move> moves = new ArrayList<>();
        if (pieceNotObstructedOffsetXY(from, board, colourise(1), 0)) moves.add(MOVE_1);
        if (pawnAtStartPosition(from, board)) moves.add(MOVE_2);
        if (canCaptureEnemyRight(from, board)) moves.add(MOVE_CAPTURE_RIGHT);
        if (canCaptureEnemyLeft(from, board)) moves.add(MOVE_CAPTURE_LEFT);
        if (pawnReachedEndOfBoard(from)) moves.clear();
        return moves;
    }

    private boolean canCaptureEnemyLeft(Coordinates from, Board board) {
        Coordinates captureLeft = (colour == PlayerColour.WHITE) ? from.plus(-1, -1) : from.plus(1, 1);
        return board.isNotOutOfBound(captureLeft) && board.get(captureLeft) != null && board.get(captureLeft).getColour() != colour;
    }

    private boolean canCaptureEnemyRight(Coordinates from, Board board) {
        Coordinates captureRight = (colour == PlayerColour.WHITE) ? from.plus(-1, 1) : from.plus(1, -1);
        return board.isNotOutOfBound(captureRight) && board.get(captureRight) != null && board.get(captureRight).getColour() != colour;
    }

    private boolean pawnAtStartPosition(Coordinates from, Board board) {
        return pieceNotObstructedOffsetXY(from, board, colourise(1), 0) && pieceNotObstructedOffsetXY(from, board, colourise(2), 0) &&
                (colour == PlayerColour.WHITE ? from.getRow() == 6 : from.getRow() == 1);
    }

    private boolean pawnReachedEndOfBoard(Coordinates from) {
        return (colour == PlayerColour.WHITE) ? from.getRow() == 0 : from.getRow() == 7;
    }
}
