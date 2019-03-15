package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour) {
        super(PieceType.ROOK, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(downMoves(from, board));
        moves.addAll(upMoves(from, board));
        moves.addAll(rightMoves(from, board));
        moves.addAll(leftMoves(from, board));
        return moves;
    }

    private List<Move> downMoves(Coordinates from, Board board) {
        return getMoves(from,board,1,0);
    }

    private List<Move> upMoves(Coordinates from, Board board) {
        return getMoves(from,board,-1,0);
    }

    private List<Move> rightMoves(Coordinates from, Board board) {
        return getMoves(from, board, 0, 1);
    }

    private List<Move> leftMoves(Coordinates from, Board board) {
        return getMoves(from,board,0,-1);
    }

    private List<Move> getMoves(Coordinates from, Board board, int rowDir, int colDir) {
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
}