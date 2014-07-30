package TicTacToe;

/**
 * Created by John on 7/27/2014.
 */
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by John on 7/27/2014.
 */
public class TicTacToe1_2 extends Application {
	// Indicate which player has a turn, initially it is the X player
	private char whoseTurn = 'X';
	private Color fontFill = Color.LIME;
	private String player1name = "Player1";
	private int player1Score = 0;
	private String player2name = "Player2";
	private int player2Score = 0;

	// Create and initialize cell
	private Cell[][] cell = new Cell[3][3];

	// Create and initialize a status label
	private Label lblStatus = new Label("Start a New Game");

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Pane to hold cell
		GridPane pane = new GridPane();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				pane.add(cell[i][j] = new Cell(), j, i);

		// Creates a welcome screen
		Pane welcomePane = new Pane();
		welcomePane.setPrefSize(315, 407);
		Font font = new Font("Courier New", 30);
		Text txWelcome = new Text("Start A New Game!");
		txWelcome.setFont(font);
		txWelcome.setFill(fontFill);
		txWelcome.setX(welcomePane.getPrefWidth() * 0.02);
		txWelcome.setY(welcomePane.getPrefHeight() / 2);
		welcomePane.getChildren().add(txWelcome);

		// Menu for starting and resetting a game
		MenuBar menuBar = new MenuBar();
		Menu fileMenuButton = new Menu("File");
		MenuItem miNew = new MenuItem("New");
		MenuItem miScores = new MenuItem("Scores");
		fileMenuButton.getItems().addAll(miNew, miScores);
		menuBar.getMenus().add(fileMenuButton);

		// BorderPane to hold the cells and label and menu
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: black");
		lblStatus.setStyle("-fx-text-fill: lime");
		borderPane.setTop(menuBar);
		borderPane.setCenter(welcomePane);

		// Have the New menu item start a new game
		miNew.setOnAction(e -> {
			setPlayerNames().show();
			whoseTurn = 'X';
			borderPane.getChildren().removeAll(pane, lblStatus);
			pane.getChildren().clear();
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					pane.add(cell[i][j] = new Cell(), j, i);
			borderPane.setCenter(pane);
			lblStatus = new Label("X's turn to play");
			lblStatus.setStyle("-fx-text-fill: lime");
			borderPane.setBottom(lblStatus);
		});

		// Have the Scores menu item show the scores
		miScores.setOnAction(e -> {
			showScores().show();
		});

		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane);
		primaryStage.setTitle("TicTacToe"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	/** Start */
	public static void main(String[] args) {
		launch(args);
	}

	/** Opens a window to enter the player names */
	public Stage setPlayerNames() {
		// Create a window for entering the player1's names
		Stage player1 = new Stage(StageStyle.DECORATED);
		player1.centerOnScreen();
		HBox play1NameHBox = new HBox(20);
		play1NameHBox.setStyle("-fx-background-color: black");
		Label lbPlayer1 = new Label("Enter Player1's name: ");
		lbPlayer1.setTextFill(fontFill);
		TextField tfPlayer1 = new TextField(player1name);
		tfPlayer1.setStyle("-fx-background-color: black;" +
				"-fx-text-fill: lime; -fx-border-color: lime");
		tfPlayer1.setPrefWidth(100);
		play1NameHBox.getChildren().addAll(lbPlayer1, tfPlayer1);
		play1NameHBox.setAlignment(Pos.CENTER);
		Scene name1Scene = new Scene(play1NameHBox, 280, 40);
		player1.setTitle("Player1's Name");
		player1.setScene(name1Scene);

		// Create a window for entering the player2's names
		Stage player2 = new Stage(StageStyle.DECORATED);
		player2.centerOnScreen();
		HBox play2NameHBox = new HBox(20);
		play2NameHBox.setStyle("-fx-background-color: black");
		Label lbPlayer2 = new Label("Enter Player2's name: ");
		lbPlayer2.setTextFill(fontFill);
		TextField tfPlayer2 = new TextField(player2name);
		tfPlayer2.setStyle("-fx-background-color: black;" +
				"-fx-text-fill: lime; -fx-border-color: lime");
		tfPlayer2.setPrefWidth(100);
		play2NameHBox.getChildren().addAll(lbPlayer2, tfPlayer2);
		play2NameHBox.setAlignment(Pos.CENTER);
		Scene name2Scene = new Scene(play2NameHBox, 280, 40);
		player2.setTitle("Player2's Name");
		player2.setScene(name2Scene);

		// Set the player names upon pressing enter
		tfPlayer1.setOnAction(e -> {
			player1name = tfPlayer1.getText();
			player1.close();
			player2.show();
		});

		tfPlayer2.setOnAction(e -> {
			player2name = tfPlayer2.getText();
			player2.close();
		});

		return player1;
	}

	/** Opens the scores window */
	public Stage showScores() {
		// Create a new window for displaying the history
		Stage playerScores = new Stage();
		playerScores.centerOnScreen();
		VBox players = new VBox(25);
		players.setSpacing(20);
		players.setStyle("-fx-background-color: black");
		players.setAlignment(Pos.CENTER);
		HBox player1 = new HBox();
		player1.setAlignment(Pos.CENTER);
		HBox player2 = new HBox();
		player2.setAlignment(Pos.CENTER);
		Font font = new Font("Courier New", 20);

		// Set Player1's name and score
		Text txPlayer1Name = new Text(this.player1name);
		txPlayer1Name.setFont(font);
		txPlayer1Name.setFill(fontFill);
		Text txPlayer1Score = new Text();
		txPlayer1Score.setFont(font);
		txPlayer1Score.setFill(fontFill);
		txPlayer1Score.setText(String.format(": %d", this.player1Score));
		player1.getChildren().addAll(txPlayer1Name, txPlayer1Score);

		// Set Player2's name and score
		Text txPlayer2Name = new Text(this.player2name);
		txPlayer2Name.setFont(font);
		txPlayer2Name.setFill(fontFill);
		Text txPlayer2Score = new Text();
		txPlayer2Score.setFont(font);
		txPlayer2Score.setFill(fontFill);
		txPlayer2Score.setText(String.format(": %d", this.player2Score));
		player2.getChildren().addAll(txPlayer2Name, txPlayer2Score);

		// Add to VBox
		players.getChildren().addAll(player1, player2);

		// Create a scene and add it too the stage
		Scene scene = new Scene(players, 300, 75);
		playerScores.setTitle("Player Scores"); // Set the stage title
		playerScores.setScene(scene); // Place the scene in the stage

		return playerScores;
	}

	/** Determine if the cells are all occupied */
	public boolean isFull() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (cell[i][j].getToken() == ' ')
					return false;

		return true;
	}

	/** Determine if the player with the specified token wins */
	public boolean isWon(char token) {
		for (int i = 0; i < 3; i++)
			if (cell[i][0].getToken() == token &&
					cell[i][1].getToken() == token &&
					cell[i][2].getToken() == token) {
				return true;
			}

		for (int j = 0; j < 3; j++)
			if (cell[0][j].getToken() == token &&
					cell[1][j].getToken() == token &&
					cell[2][j].getToken() == token)  {
				return true;
			}
		// Check diagonals
		if (cell[0][0].getToken() == token &&
				cell[1][1].getToken() == token &&
				cell[2][2].getToken() == token) {
			return true;
		} else if (cell[0][2].getToken() == token &&
				cell[1][1].getToken() == token &&
				cell[2][0].getToken() == token) {
			return true;
		}

		return false;
	}

	// An inner class for a cell
	public class Cell extends Pane {
		// Load image variables
		String val;
		Image bufferedImage;

		// Token used for this cell
		private char token = ' ';

		public Cell() {
			setStyle("-fx-background-color: black;" +
					"-fx-border-color: lime");
			this.setPrefSize(105, 125);
			this.setOnMouseClicked(e -> handleMouseClick());
		}

		/** Return token */
		public char getToken() {
			return token;
		}

		/** Set a new token */
		public void setToken(char c) {
			token = c;

			if (token == 'X') {
				val = "x.jpg";
				bufferedImage = SwingFXUtils.toFXImage(
						LoadImage("image/" + val), null);
				// final String image = "file:///C:/image/x.jpg";
				ImageView imageX = new ImageView(bufferedImage);
				imageX.setX(this.getPrefWidth() * 0.035);
				imageX.setY(this.getPrefHeight() * 0.035);
				this.getChildren().add(imageX);
			} else if (token == 'O') {
				val = "o.jpg";
				bufferedImage = SwingFXUtils.toFXImage(
						LoadImage("image/" + val), null);
				// final String image = "file:///C:/image/o.jpg";
				ImageView imageO = new ImageView(bufferedImage);
				imageO.setX(this.getPrefWidth() * 0.035);
				imageO.setY(this.getPrefHeight() * 0.035);
				this.getChildren().add(imageO);
			}
		}

		/** Load an image and return a buffered image */
		public BufferedImage LoadImage(String f) {
			BufferedImage bi = null;
			try {
				InputStream is = TicTacToe1_2.class.getResourceAsStream("/" + f);
				bi = ImageIO.read(is);
			}
			catch(IOException e) {
				// Create a message and place it in a pane
				Label message = new Label("ERROR opening file:" + e);
				message.setAlignment(Pos.CENTER);
				Pane pane = new Pane(message);
				// Create stage and scene and place scene in stage
				Stage errorMessage = new Stage();
				Scene scene = new Scene(pane);
				errorMessage.setTitle("Error!"); // Set the stage title
				errorMessage.setScene(scene); // Place the scene in the stage
				errorMessage.show();
			}

			return bi;
		}

		/* Handle a mouse click event */
		private void handleMouseClick() {
			// If cell is empty and game is not over
			if (token == ' ' && whoseTurn != ' ') {
				setToken(whoseTurn); // Set token in the cell

				// Check game status
				if (isWon(whoseTurn)) {
					lblStatus.setText(whoseTurn + " won! The game is over");
					if (whoseTurn == 'X') {
						player1Score++;
					} else {
						player2Score++;
					}
					whoseTurn = ' '; // Game is over
				} else if (isFull()) {
					lblStatus.setText("Draw! the game is over");
					whoseTurn = ' '; // Game is over
				} else {
					// Change the turn
					whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
					// Display whose turn
					lblStatus.setText(whoseTurn + "'s turn");
				}
			}
		}
	}
}
