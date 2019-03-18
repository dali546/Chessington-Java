package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int SIZE = 8;
    private final Board board;
    private List<Move> moveHistory;

    private boolean ppRequired;
    private Coordinates ppSquare;

    private PlayerColour nextPlayer = PlayerColour.WHITE;

    private boolean isEnded = false;

    public Game(Board board) {
        this.board = board;
        moveHistory = new ArrayList<>();
        ppRequired = false;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPpRequired() {
        return ppRequired;
    }

    public Piece pieceAt(int row, int col) {
        return board.get(new Coordinates(row, col));
    }

    public List<Move> getAllowedMoves(Coordinates from) {
        if (isEnded) {
            return new ArrayList<>();
        }

        Piece piece = board.get(from);
        if (piece == null || piece.getColour() != nextPlayer) {
            return new ArrayList<>();
        }

        return piece.getAllowedMoves(from, this);
    }

    public void makeMove(Move move) throws InvalidMoveException {
        if (isEnded) {
            throw new InvalidMoveException("Game has ended!");
        }

        if (ppRequired) {
            throw new InvalidMoveException("Wait for opponent to PP!");
        }

        Coordinates from = move.getFrom();
        Coordinates to = move.getTo();

        Piece piece = board.get(from);
        if (piece == null) {
            throw new InvalidMoveException(String.format("No piece at %s", from));
        }

        if (piece.getColour() != nextPlayer) {
            throw new InvalidMoveException(String.format("Wrong colour piece - it is %s's turn", nextPlayer));
        }

        if (!piece.getAllowedMoves(from, this).contains(move)) {
            throw new InvalidMoveException(String.format("Cannot move piece %s from %s to %s", piece, from, to));
        }


        boolean isToEmpty = board.get(to) == null;
        board.move(from, to);
        moveHistory.add(new Move(from, to));

        /// En Passant Move ///
        Move lastMove = getMoveHistory().isEmpty() ? null : getMoveHistory().get(getMoveHistory().size() - 1);
        if (lastMoveValidEnPassant(lastMove, piece, isToEmpty))
            board.eat(to.plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0));
        /// END En Passant Move ///

        /// Pawn Promotion ///
        if (piece.getColour() == PlayerColour.WHITE ? to.getRow() == 0 : to.getRow() == 7 && piece.getType() == Piece.PieceType.PAWN) {
            ppRequired = true;
            ppSquare = to;
        }
        /// END Pawn Promotion ///


        nextPlayer = nextPlayer == PlayerColour.WHITE ? PlayerColour.BLACK : PlayerColour.WHITE;
    }

    public void promotePawn(Piece.PieceType pieceType) {
        PlayerColour colour = nextPlayer == PlayerColour.WHITE ? PlayerColour.BLACK : PlayerColour.WHITE;
        Piece newPiece;
        switch (pieceType) {
            case ROOK: newPiece = new Rook(colour); break;
            case BISHOP: newPiece = new Bishop(colour); break;
            case QUEEN: newPiece = new Queen(colour); break;
            case KNIGHT: newPiece = new Knight(colour); break;
            default: newPiece = new Pawn(colour); break;
        }
        board.placePiece(ppSquare, newPiece);
        ppRequired = false;
    }

    private boolean lastMoveValidEnPassant(Move lastMove, Piece piece, boolean isEmpty) {
        return lastMove != null && //NOT START OF GAME
                board.get(lastMove.getTo().plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0)) != null && //PIECE BELOW IS NOT NULL
                piece.getType() == Piece.PieceType.PAWN && //CURRENT PIECE IS PAWN
                isEmpty && //TO IS EMPTY
                board.get(lastMove.getTo().plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0)).getType()
                        == Piece.PieceType.PAWN && //PIECE BELOW IS PAWN
                board.get(lastMove.getTo().plus((piece.getColour() == PlayerColour.WHITE ? 1 : -1), 0)).getColour()
                        != piece.getColour(); //PIECE BELOW IS ENEMY
    }

    public boolean isEnded() {
        return isEnded;
    }

    public String getResult() {
        return null;
    }
}
