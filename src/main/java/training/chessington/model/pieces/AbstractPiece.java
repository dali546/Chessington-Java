package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.PlayerColour;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour) {
        this.type = type;
        this.colour = colour;
    }

    @Override
    public Piece.PieceType getType() {
        return type;
    }

    @Override
    public PlayerColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour.toString() + " " + type.toString();
    }

    //Checks if there is no piece xy spaces in-front of current piece
    boolean pieceNotObstructedOffsetXY(Board board, Coordinates coords) {
        return board.isNotOutOfBound(coords) && board.get(coords) == null;
    }

    boolean enemyPieceAtOffsetXY(Board board, final Coordinates coords) {
        return board.isNotOutOfBound(coords) && board.get(coords).getColour() != colour;
    }

    int colourise(int offset) {
        return colour == PlayerColour.WHITE ? -offset : offset;
    }

}
