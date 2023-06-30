package chess;

import java.awt.Point;
import java.io.File;
import java.util.Scanner;

import chess.ui.BoardView;
import chess.ui.PieceView;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

//Représente la planche de jeu avec les pièces.


public class ChessBoard {

	
	private BoardView objBvwDechBrd;
	
	public ChessBoard(int x, int y) {
		objBvwDechBrd = new BoardView(x,y,this);	
	}
	// Place une piece vide dans la case
	public void clearSquare(int x, int y) {
		getGrid()[x][y] = new ChessPiece(x, y, this);
	}

	// Place une piece sur le planche de jeu.
	public void putPiece(ChessPiece piece) {

		Point2D pos = objBvwDechBrd.gridToPane( piece.objPiVwDeChpi.getGridPosX(), piece.objPiVwDeChpi.getGridPosY());
		piece.objPiVwDeChpi.getPiecePane().relocate(pos.getX(), pos.getY());
		getPane().getChildren().add(piece.objPiVwDeChpi.getPiecePane());
		getGrid()[piece.objPiVwDeChpi.getGridPosX()][piece.objPiVwDeChpi.getGridPosY()] = piece;
	}

	
	
	//Convertit des coordonnées en pixels sur la fenêtre d'interface en coordonnées dans la grille de l'échiquier
	//Utilisé pour détecter qu'on a touché une case spécifique de la grille.
	//===========================================>gridToPane deplacer dans BoardView
	public Point2D gridToPaneChessBoa(  int x,int  y) {
		
		return objBvwDechBrd.gridToPane( x,  y);
	}
	//==================================================> methode initial paneToGrid deplacer dans BoardVie
	public Point paneToGridChessBo(double xPos, double yPos) {
			return  objBvwDechBrd.paneToGrid( xPos, yPos);
		}
	
	
	

	public Pane getPane() {
		return objBvwDechBrd.getBoardPane();
	}
	
	public ChessPiece[][] getGrid() {
		return objBvwDechBrd.getGridChessView();
	}
	
	
	
	


	//Les cases vides contiennent une pièce spéciale
	public boolean isEmpty(Point pos) {
		return (objBvwDechBrd.getGridChessView()[pos.x][pos.y].objPiVwDeChpi.getType() == ChessUtils.TYPE_NONE);
	}

	//Verifie si une coordonnee dans la grille est valide
	public boolean isValid(Point pos) {
		return (pos.x >= 0 && pos.x <= 7 && pos.y >= 0 && pos.y <= 7);
	}

	//Verifie si les pièces à deux positions dans la grille sont de la même couleur.
	public boolean isSameColor(Point pos1, Point pos2) {
		return getGrid()[pos1.x][pos1.y].objPiVwDeChpi.getColor() == getGrid()[pos2.x][pos2.y].objPiVwDeChpi.getColor();
	}
	
	//Effectue un mouvement à partir de la notation algébrique des cases ("e2-b5" par exemple)
	public void algebraicMove(String move){
		if(move.length()!=5){
			throw new IllegalArgumentException("Badly formed move");
		}
		String start = move.substring(0,2);
		String end = move.substring(3,5);
		move(ChessUtils.convertAlgebraicPosition(start),ChessUtils.convertAlgebraicPosition(end));
	}
	
	//Effectue un mouvement sur l'echiqier. Quelques regles de base sont implantees ici.
	public boolean move(Point gridPos, Point newGridPos) {

		//Verifie si les coordonnees sont valides
		if (posNonVal(newGridPos))
			return false;

		//Si la case destination est vide, on peut faire le mouvement
		else if (isEmpty(newGridPos)) {
			getGrid()[newGridPos.x][newGridPos.y] = getGrid()[gridPos.x][gridPos.y];
			getGrid()[gridPos.x][gridPos.y] = new ChessPiece(gridPos.x, gridPos.y, this);
			return true;
		}

		//Si elle est occuppe par une piece de couleur differente, alors c'est une capture
		else if (!isSameColor(gridPos, newGridPos)) {			
			getPane().getChildren().remove(getGrid()[newGridPos.x][newGridPos.y].objPiVwDeChpi.getPiecePane());
			getGrid()[newGridPos.x][newGridPos.y] = getGrid()[gridPos.x][gridPos.y];
			getGrid()[gridPos.x][gridPos.y] = new ChessPiece(gridPos.x, gridPos.y, this);

			return true;
		}

		return false;
	}
	
	
	

	//Fonctions de lecture et de sauvegarde d'echiquier dans des fichiers. a implanter.
	
	public static ChessBoard readFromFile(String fileName) throws Exception {

		Scanner scanner = new Scanner(new File(fileName));
		while (scanner.hasNextLine()) {
			
			
			//lireLigne(scanner.next())
		}
        
		return readFromFile(new File(fileName), 0, 0);
	}
	
	
	
	
	public static ChessBoard readFromFile(File file, int x, int y) throws Exception {
		
		
		throw new Exception("Pas implante");
	}
	
	
	
	public void saveToFile(File file) throws Exception {
		throw new Exception("Pas implante");
	}



	
	
	
	
	
	public boolean posNonVal(Point newGridPos ) {
		//Verifie si les coordonnees sont valides
		return (!isValid(newGridPos));
			
	}
	public void move(PieceView pieceView, Point newGridPos,Point oldGridPos ) {
		objBvwDechBrd.ChessBoard_move(this, pieceView, newGridPos, oldGridPos);
	}
	public void apliquercaseVidOuColDif(PieceView pieceView, Point oldGridPos, Point newGridPos ) {
		Point2D newPos = gridToPaneChessBoa( newGridPos.x, newGridPos.y);
		pieceView.getPiecePane().relocate(newPos.getX(), newPos.getY());
		pieceView.setGridPoschessView(newGridPos);
	}
	public void  apliquerNVidNColDif(PieceView pieceView) {
		Point2D oldPos = gridToPaneChessBoa( pieceView.getGridPosX(), pieceView.getGridPosY());
		pieceView.getPiecePane().relocate(oldPos.getX(), oldPos.getY());
	}


	
	
	
}
