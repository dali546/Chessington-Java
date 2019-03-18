package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {


    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        Board board = game.getBoard();
        Move lastMove = game.getMoveHistory().isEmpty() ? null : game.getMoveHistory().get(game.getMoveHistory().size() - 1);
        List<Move> moves = new ArrayList<>();

        if (pieceNotObstructedOffsetXY(board, from.plus(colourise(1), 0)))
            moves.add(new Move(from, from.plus(colour == PlayerColour.WHITE ? -1 : 1, 0)));
        if (pawnAtStartPosition(from, board))
            moves.add(new Move(from, from.plus(colour == PlayerColour.WHITE ? -2 : 2, 0)));
        if (canCaptureEnemy(from, board, -1))
            moves.add(new Move(from, colour == PlayerColour.WHITE ? from.plus(-1, 1) : from.plus(1, -1)));
        if (canCaptureEnemy(from, board, 1))
            moves.add(new Move(from, colour == PlayerColour.WHITE ? from.plus(-1, -1) : from.plus(1, 1)));
        if (canEnPassant(lastMove, from, board)) moves.add(new Move(from, lastMove.getTo().plus(colourise(1), 0)));
        if (!board.isNotOutOfBound(from)) moves.clear();
        return moves;
    }

    private boolean canEnPassant(Move move, Coordinates from, Board board) {
        return move != null && //NOT START OF GAME
                move.getTo().equals(move.getFrom().plus(colourise(-2), 0)) && //MOVED TWO SPACES FORWARD
                (colour == PlayerColour.WHITE ? move.getFrom().getRow() == 1 : move.getFrom().getRow() == 6) && //MOVED FROM START
                (move.getTo().plus(0, 1).equals(from) || move.getTo().plus(0, -1).equals(from)) && //TO RIGHT OR LEFT OF THIS
                (board.get(move.getTo()).getType() == PieceType.PAWN && board.get(move.getTo()).getColour() != colour) && //ENEMY PIECE & PAWN
                board.get(move.getTo().plus(colourise(1), 0)) == null; //DESTINATION IS NULL
    }

    private boolean canCaptureEnemy(Coordinates from, Board board, final int offset) {
        Coordinates capture = from.plus(colourise(1), colourise(offset));
        return board.isNotOutOfBound(capture) && board.get(capture) != null && board.get(capture).getColour() != colour;
    }

    private boolean pawnAtStartPosition(Coordinates from, Board board) {
        return pieceNotObstructedOffsetXY(board, from.plus(colourise(1), 0)) && pieceNotObstructedOffsetXY(board, from.plus(colourise(2), 0)) &&
                (colour == PlayerColour.WHITE ? from.getRow() == 6 : from.getRow() == 1);
    }

}
