package chess.ui;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import chess.ChessBoard;
import chess.ChessUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PieceView {

	
		// Panneau d'interface contenant l'image de la pièce
		private Pane piecePane;
		// Position de la pièce sur l'échiquier
		private int gridPosX;
		private int gridPosY;

		private int type;
		private int color;

		
		// Référence à la planche de jeu. Utilisée pour déplacer la pièce.
		private ChessBoard board;
		
		// Pour créer des pièces à mettre sur les cases vides
		public PieceView(int x, int y, ChessBoard b) {
			
			gridPosX =x;
			gridPosY= y;
			board = b;
			type = ChessUtils.TYPE_NONE;
			color = ChessUtils.COLORLESS;

		}
		// Création d'une pièce normale. La position algébrique en notation d'échecs
		// lui donne sa position sur la grille.
		public PieceView(String name, String pos, ChessBoard b) {
			color = ChessUtils.getColor(name);
			type = ChessUtils.getType(name);
			board = b;
			
			Image pieceImage;
			try {
				pieceImage = new Image(new FileInputStream("images/" + PieceView.prefixes[color] + PieceView.names[type] + ".png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			ImageView pieceView = new ImageView(pieceImage);

			pieceView.setX(0);
			pieceView.setY(0);
			pieceView.setFitHeight(pieceSize);
			pieceView.setFitWidth(pieceSize);
			pieceView.setPreserveRatio(true);
			
			piecePane = new Pane(pieceView);			
			enableDragging();
			setAlgebraicPos(pos);

		}
		//Change la position avec la notation algébrique
		public void setAlgebraicPos(String pos) {

			Point pos2d = ChessUtils.convertAlgebraicPosition(pos);

			gridPosX = pos2d.x;
			gridPosY = pos2d.y;
		}
		
		
		
	// Utilisé pour générer les noms de fichiers contenant les images des pièces.
	public static final String names[] = { "pawn", "knight", "bishop", "rook", "queen", "king" };
	public static final String prefixes[] = { "w", "b" };
	// Taille d'une pièce dans l'interface
	public static double pieceSize = 75.0;
	
	
	public int getGridPosX() {
		return gridPosX;
	}
	public int getGridPosY() {
		return gridPosY;
	}
	
	public ChessBoard getBoard() {
		return board;
	}
	public Pane getPiecePane() {
		return piecePane;
	}

	
	
	
	public Point getGridPoschessView() {
		return new Point(gridPosX, gridPosY);
	}
	
	public void setGridPoschessView(Point pos) {
		gridPosX = pos.x;
		gridPosY = pos.y;
	}
	public int getType() {
		return type;
	}
	public int getColor() {
		return color;
	}
	// Gestionnaire d'événements pour le déplacement des pièces
	public void enableDragging() {

		final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
	
		// Lorsque la pièce est saisie, on préserve la position de départ
		piecePane.setOnMousePressed(event -> {
			
			Point2D posEvendpar = new Point2D( event.getSceneX(), event.getSceneY() );
			//assigner a attribut point2D de mousseAnchor le point ayant la possition de evenement 
			mouseAnchor.set(  posEvendpar    );
		});
	
	
		// Lorsqu'on relâche la pièce, le mouvement correspondant est appliqué
		// au jeu d'échecs si possible.
		// L'image de la pièce est également centrée sur la case la plus proche.
		piecePane.setOnMouseReleased(event -> {
			
			//point  destination de la position  du piece sur echiquier (verssion paneau) pixel
			Point newGridPos = board.paneToGridChessBo(event.getSceneX(),event.getSceneY());
			//le point intial de la position du piece sur la echiquier (verssion paneau) pixel
			Point oldGridPos = getGridPoschessView();
			move(newGridPos,oldGridPos);
	
		});
		
		// À chaque événement de déplacement, on déplace la pièce et on met à
		// jour la position de départ
		piecePane.setOnMouseDragged(event -> {
			double deltaX = event.getSceneX() - mouseAnchor.get().getX();
			double deltaY = event.getSceneY() - mouseAnchor.get().getY();
			piecePane.relocate(piecePane.getLayoutX() + deltaX, piecePane.getLayoutY() + deltaY);
			piecePane.toFront();
			mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
	
		});
	}

	//coquille
		public void move(Point newGridPos,Point oldGridPos) {
			
			 board.move(this ,newGridPos, oldGridPos );
		}

}
