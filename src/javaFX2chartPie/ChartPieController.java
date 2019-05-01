package javaFX2chartPie;

import java.net.URL;
import java.util.Iterator;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChartPieController {

	@FXML
	private Pane pan;

	private Label cap = new Label("");

	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

	@FXML
	void initialize() {
		assert pan != null : "fx:id=\"pan\" was not injected: check your FXML file 'ChartPie.fxml'.";

		PieChart piec = new PieChart();
		pan.getChildren().add(piec);

//		cap.setTextFill(Color.DARKORANGE);
		cap.setTextFill(Color.BLACK);
		cap.setStyle("-fx-font: 24 arial;");
		pan.getChildren().add(cap);
		// move "cap" to the front.
		cap.toFront();

		piec.prefWidthProperty().bind(pan.widthProperty());
		piec.prefHeightProperty().bind(pan.heightProperty());

		piec.setTitle("Pie Chart Sample");
		piec.setStartAngle(90);
		piec.setLegendSide(Side.RIGHT);
		//		piec.setStyle("-fx-pie-label-visible: false;");

		URL url = this.getClass().getResource("res/data.csv");
		OpCsv csv = new OpCsv(url);
		TreeMap<Integer, String[]> map = csv.getNumberReverseCsv(1);
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			int no = it.next();
			String[] words = map.get(no);
			String ename = words[0];
			Double amount = Double.parseDouble(words[1]);

			pieChartData.add(new PieChart.Data(ename, amount));
		}
		piec.getData().addAll(pieChartData);

		for (final PieChart.Data data : piec.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {

					if (e.isPrimaryButtonDown()) {
						cap.setLayoutX(e.getSceneX() - pan.getLayoutX());
						cap.setLayoutY(e.getSceneY() - pan.getLayoutY());
						cap.setText(String.valueOf(data.getPieValue()) + "ml (" + data.getName() + ")");
					}
					else {
						cap.setText("");
						cap.setLayoutX(0);
						cap.setLayoutY(0);
					}
				}
			});
		}
	}
}
