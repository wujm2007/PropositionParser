package application;

import biz.Parser;
import entity.FormationTree;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class FormationTreeGUI extends Application {

	private static final int PALIGN = 70;
	private static final int UALIGN = 5;
	private static final int BALIGN = 15;
	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("FormationTree");

		GridPane root = new GridPane();

		TextField propositionField = new TextField();
		Button btn = new Button("Parse");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				root.getChildren().removeIf(c -> (c instanceof Canvas));
				String strPropostion = propositionField.getText();
				FormationTree t = Parser.parsePropositon(strPropostion);
				int len = t.toString().length();
				int level = t.getLevel();
				Canvas canvas = new Canvas(len * 6, level * PALIGN);
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setTextAlign(TextAlignment.CENTER);
				drawTree(gc, t, 0, 0, canvas.getWidth());
				root.add(canvas, 0, 1, 2, 1);
			}
		});

		root.add(propositionField, 0, 0);
		root.add(btn, 1, 0);

		primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		primaryStage.show();
	}

	private void drawTree(GraphicsContext gc, FormationTree t, double x, double y, double width) {
		gc.fillText(t.toString(), x + width / 2, y + PALIGN);
		int childCount = t.childCount();
		if (childCount == 0)
			return;
		else if (childCount == 1) {
			gc.strokeLine(x + width / 2, y + PALIGN + UALIGN, x + width / 2, y + PALIGN * 2 - BALIGN);
			drawTree(gc, t.getChild(), x, y + PALIGN, width);
			return;
		} else if (childCount == 2) {
			int lenLeft = t.getLeft().toString().length();
			int lenRight = t.getRight().toString().length();
			double widthLeft = width * lenLeft / (lenLeft + lenRight);
			double widthRight = width * lenRight / (lenLeft + lenRight);
			gc.strokeLine(x + width / 2, y + PALIGN + UALIGN, x + widthLeft / 2, y + PALIGN * 2 - BALIGN);
			gc.strokeLine(x + width / 2, y + PALIGN + UALIGN, x + widthLeft + widthRight / 2, y + PALIGN * 2 - BALIGN);
			drawTree(gc, t.getLeft(), x, y + PALIGN, widthLeft);
			drawTree(gc, t.getRight(), x + widthLeft, y + PALIGN, widthRight);
			return;
		}
	}
}
