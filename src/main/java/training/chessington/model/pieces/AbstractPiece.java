package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

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

    private boolean enemyPieceAtOffsetXY(Board board, final Coordinates coords) {
        return board.isNotOutOfBound(coords) && board.get(coords).getColour() != colour;
    }

    int colourise(int offset) {
        return colour == PlayerColour.WHITE ? -offset : offset;
    }


    protected List<Move> getMovesInDirection(Coordinates from, Board board, int rowDir, int colDir) {
        List<Move> moves = new ArrayList<>();

        for (int i = 1; i < Game.SIZE; i++) {
            int rowOffset = rowDir * i, colOffset = colDir * i;
            if (pieceNotObstructedOffsetXY(board, from.plus(rowOffset, colOffset))) moves.add(new Move(from, from.plus(rowOffset, colOffset)));
            else if (enemyPieceAtOffsetXY(board, from.plus(rowOffset, colOffset))) {
                moves.add(new Move(from, from.plus(rowOffset, colOffset)));
                break;
            } else break;
        }
        return moves;
    }

    protected boolean isValidMove(Board board, Coordinates coords) {
        if (pieceNotObstructedOffsetXY(board, coords)) return true;
        return enemyPieceAtOffsetXY(board, coords);
    }

}
