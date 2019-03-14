package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        return new ArrayList<Move>() {{
            if (colour == PlayerColour.BLACK) add(new Move(from, from.plus(1, 0)));
            if (colour == PlayerColour.WHITE) add(new Move(from, from.plus(-1, 0)));
            if (colour == PlayerColour.WHITE && from.getRow()==6) add(new Move(from,from.plus(-2,0)));
            if (colour == PlayerColour.BLACK && from.getRow()==1) add(new Move(from,from.plus(2,0)));
        }};
    }
}
