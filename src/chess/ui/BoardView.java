package chess.ui;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import chess.ChessBoard;
import chess.ChessPiece;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BoardView {





	
	// Les cases ont un peu plus de 80 pixels
	public static final double squareSize = 80.3;
	// La bordure fait 80 pixels tout le tour
	public static final int borderSize = 80;
	// L'arrière plan est dans une fenetre 800x800.
	public static final int sceneSize = 800;
	// La bordure fait 80 pixels tout le tour
	public static final int pieceDeltaX = 12;
	// La bordure fait 80 pixels tout le tour
	public static final int pieceDeltaY = 2;
	
	
	private ChessPiece[][]  grid;
	private double startX ;
	private double startY;
	//Panneau d'interface représentant la planche de jeu
	private Pane boardPane;
	private ImageView boardView;
	
	
	public BoardView(int x,int y,ChessBoard th) {
		startX = x;
		startY = y;
		grid = new ChessPiece[8][8];
		boardView = creArPlan() ;
		boardPane = setXYFitwFitPres(th);

	}

	//=================================>methode deplacer de chessBoard


	public  Point paneToGrid(double xPos, double yPos) {
		
		if (xPos < (borderSize + startX))
			xPos = borderSize + startX;
		if (xPos > startX + sceneSize - borderSize)
			xPos = startX + sceneSize - borderSize - (squareSize / 2);
		if (yPos < borderSize + startY)
			yPos = borderSize + startY;
		if (yPos > startY + sceneSize - borderSize)
			yPos = startY + sceneSize - borderSize - (squareSize / 2);
		int xGridPos = (int) ((xPos - (startX + borderSize)) / squareSize);
		int yGridPos = (int) ((yPos - (startY + borderSize)) / squareSize);
		return new Point(xGridPos, yGridPos);
	}
	
	public  Point2D gridToPane( int x, int y) {

		if (x < 0 || x > 7 || y < 0 || y > 7)
			throw new IllegalArgumentException("Piece out of grid: (" + x + "," + y + ")");

		return new Point2D(startX + x * squareSize +borderSize + pieceDeltaX,
				startY + y * squareSize + borderSize +pieceDeltaY);

	}
	
	//=============================================>constructeur decouper en methodes
	
	public  Pane setXYFitwFitPres(ChessBoard th) {
	
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				grid[i][j] = new ChessPiece(i, j, th);
			}
		}
		
		boardView.setX(startX);
		boardView.setY(startY);
		boardView.setFitHeight(sceneSize);
		boardView.setFitWidth(sceneSize);
		boardView.setPreserveRatio(true);
		
		return (boardPane = new Pane(boardView));
		
		
	}
	
	public  ImageView creArPlan() {
		
		Image boardImage;
				// Création de l'arrière-plan.
				try {
					boardImage = new Image(new FileInputStream("images/board.jpg"));

				} catch (FileNotFoundException e) {

					return boardView ;
				}
				return boardView = new ImageView(boardImage);
	}
	
	
	//=============================================>
	
	public ChessPiece[][] getGridChessView() {
	return grid;
}
	public double getStartX() {
		return startX;
	}
	public double getStartY() {
		return startY;
	}
	public Pane getBoardPane() {
		return boardPane;
	}

	public void ChessBoard_move(ChessBoard chessBoard, PieceView pieceView, Point newGridPos, Point oldGridPos ) {
		
	
		
		
		//Si la case destination est vide, ou couleur est differente;
		boolean samecol = chessBoard.isSameColor(oldGridPos, newGridPos);
		
		boolean caseVide = chessBoard.isEmpty(newGridPos);
		
		
		if ( caseVide  || !samecol ) {
			chessBoard.apliquercaseVidOuColDif(pieceView,  oldGridPos, newGridPos );
			
		} else {
			
			chessBoard.apliquerNVidNColDif(pieceView);
		}
		
	}
	
	
}
