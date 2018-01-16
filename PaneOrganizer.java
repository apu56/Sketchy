package Sketchy;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import Sketchy.Sketchy;
import Sketchy.Control;

public class PaneOrganizer {

	private BorderPane _root;
	private SketchyShape _sketchyShape;
	
	public PaneOrganizer() {
		Sketchy sketchy = new Sketchy();
		Control control = new Control(sketchy);
		_root = new BorderPane();
		_root.setPrefSize(1000.0, 700.0);
		_root.setCenter(sketchy.getSketchyPane());
		_root.setLeft(control.getLeftPane());
	}
	public Pane getRoot() {
		return _root;
}


}
