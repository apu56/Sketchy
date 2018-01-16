package Sketchy;

import javafx.scene.shape.Polyline;

public class CurvedLine implements Saveable {
	
	private Polyline _polyline;
	
	public CurvedLine(double x, double y) {
		_polyline = new Polyline(x, y);
	}
	public Polyline getPolyline() {
		return _polyline;
	}

}
