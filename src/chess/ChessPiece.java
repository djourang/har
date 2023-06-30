package chess;

import java.awt.Point;
import java.util.ArrayList;

import chess.ui.PieceView;



public class ChessPiece {
	
	
	PieceView objPiVwDeChpi;
	
//objPieceViewDansChessP


	// Pour créer des pièces à mettre sur les cases vides
		public ChessPiece(int x, int y, ChessBoard b) {
			
			objPiVwDeChpi = new PieceView( x,  y,  b);
		}

		// Création d'une pièce normale. La position algébrique en notation d'échecs
		// lui donne sa position sur la grille.
		public ChessPiece(String name, String pos, ChessBoard b) {

			objPiVwDeChpi = new PieceView( name,  pos,  b);

		}

	// Crée la liste de pièces avec leur position de départ pour un jeu d'échecs standard
	public static ArrayList<ChessPiece> createInitialPieces(ChessBoard board) {

		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();

		pieces.add(new ChessPiece("wr", "a1", board));
		pieces.add(new ChessPiece("wr", "h1", board));
		pieces.add(new ChessPiece("wn", "b1", board));
		pieces.add(new ChessPiece("wn", "g1", board));
		pieces.add(new ChessPiece("wb", "c1", board));
		pieces.add(new ChessPiece("wb", "f1", board));
		pieces.add(new ChessPiece("wq", "d1", board));
		pieces.add(new ChessPiece("wk", "e1", board));

		for (int i = 0; i < 8; i++) {
			pieces.add(new ChessPiece("wp", ((char) ('a' + i)) + "2", board));
		}

		pieces.add(new ChessPiece("br", "a8", board));
		pieces.add(new ChessPiece("br", "h8", board));
		pieces.add(new ChessPiece("bn", "b8", board));
		pieces.add(new ChessPiece("bn", "g8", board));
		pieces.add(new ChessPiece("bb", "c8", board));
		pieces.add(new ChessPiece("bb", "f8", board));
		pieces.add(new ChessPiece("bq", "d8", board));
		pieces.add(new ChessPiece("bk", "e8", board));

		for (int i = 0; i < 8; i++) {
			pieces.add(new ChessPiece("bp", ((char) ('a' + i)) + "7", board));
		}

		return pieces;
	}
	
	//Pour savoir si c'est une pièce vide (pour les cases vides de l'échiquier).
	public boolean isNone() {

		return objPiVwDeChpi.getType() == ChessUtils.TYPE_NONE;
	}

	public Point getGridPos() {
		return objPiVwDeChpi.getGridPoschessView();
	}

	public void setGridPos(Point pos) {
		objPiVwDeChpi.setGridPoschessView(pos);
	}

}
