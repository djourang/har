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

	
		// Panneau d'interface contenant l'image de la pi�ce
		private Pane piecePane;
		// Position de la pi�ce sur l'�chiquier
		private int gridPosX;
		private int gridPosY;

		private int type;
		private int color;

		
		// R�f�rence � la planche de jeu. Utilis�e pour d�placer la pi�ce.
		private ChessBoard board;
		
		// Pour cr�er des pi�ces � mettre sur les cases vides
		public PieceView(int x, int y, ChessBoard b) {
			
			gridPosX =x;
			gridPosY= y;
			board = b;
			type = ChessUtils.TYPE_NONE;
			color = ChessUtils.COLORLESS;

		}
		// Cr�ation d'une pi�ce normale. La position alg�brique en notation d'�checs
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
		//Change la position avec la notation alg�brique
		public void setAlgebraicPos(String pos) {

			Point pos2d = ChessUtils.convertAlgebraicPosition(pos);

			gridPosX = pos2d.x;
			gridPosY = pos2d.y;
		}
		
		
		
	// Utilis� pour g�n�rer les noms de fichiers contenant les images des pi�ces.
	public static final String names[] = { "pawn", "knight", "bishop", "rook", "queen", "king" };
	public static final String prefixes[] = { "w", "b" };
	// Taille d'une pi�ce dans l'interface
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
	// Gestionnaire d'�v�nements pour le d�placement des pi�ces
	public void enableDragging() {

		final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
	
		// Lorsque la pi�ce est saisie, on pr�serve la position de d�part
		piecePane.setOnMousePressed(event -> {
			
			Point2D posEvendpar = new Point2D( event.getSceneX(), event.getSceneY() );
			//assigner a attribut point2D de mousseAnchor le point ayant la possition de evenement 
			mouseAnchor.set(  posEvendpar    );
		});
	
	
		// Lorsqu'on rel�che la pi�ce, le mouvement correspondant est appliqu�
		// au jeu d'�checs si possible.
		// L'image de la pi�ce est �galement centr�e sur la case la plus proche.
		piecePane.setOnMouseReleased(event -> {
			
			//point  destination de la position  du piece sur echiquier (verssion paneau) pixel
			Point newGridPos = board.paneToGridChessBo(event.getSceneX(),event.getSceneY());
			//le point intial de la position du piece sur la echiquier (verssion paneau) pixel
			Point oldGridPos = getGridPoschessView();
			move(newGridPos,oldGridPos);
	
		});
		
		// � chaque �v�nement de d�placement, on d�place la pi�ce et on met �
		// jour la position de d�part
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
