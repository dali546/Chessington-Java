package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        return PlayerColour.WHITE == colour ? WhiteMoves(from, board) : BlackMoves(from, board);
    }

    private List<Move> BlackMoves(Coordinates from, Board board) {
        if (from.getRow() == 7) return new ArrayList<>();
        if (board.get(new Coordinates(from.getRow() + 1, from.getCol())) != null) return new ArrayList<>();
        return new ArrayList<Move>() {{
            Piece pieceRight = board.get(new Coordinates(from.getRow() + 1, from.getCol() + 1));
            Piece pieceLeft = board.get(new Coordinates(from.getRow() + 1, from.getCol() - 1));

            if (pieceRight != null) if (pieceRight.getColour() != colour) add(new Move(from, from.plus(1, 1)));
            if (pieceLeft != null) if (pieceLeft.getColour() != colour) add(new Move(from, from.plus(1, -1)));


            add(new Move(from, from.plus(1, 0)));
            if (from.getRow() == 1) add(new Move(from, from.plus(2, 0)));
        }};
    }

    private List<Move> WhiteMoves(Coordinates from, Board board) {
        if (from.getRow() == 0) return new ArrayList<>();
        if (board.get(new Coordinates(from.getRow() - 1, from.getCol())) != null) return new ArrayList<>();
        return new ArrayList<Move>() {{
            Piece pieceRight = board.get(new Coordinates(from.getRow() - 1, from.getCol() + 1));
            Piece pieceLeft = board.get(new Coordinates(from.getRow() - 1, from.getCol() - 1));

            add(new Move(from, from.plus(-1, 0)));
            if (pieceRight != null) if (pieceRight.getColour() != colour) add(new Move(from, from.plus(-1, 1)));
            if (pieceLeft != null) if (pieceLeft.getColour() != colour) add(new Move(from, from.plus(-1, -1)));
            if (from.getRow() == 6) add(new Move(from, from.plus(-2, 0)));
        }};
    }
}
