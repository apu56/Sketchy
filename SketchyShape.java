package Sketchy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public interface SketchyShape {
	
	public Point2D getCenter();
	public void setRotate(double angle);
	public void setStroke(Color color);
	public void setWidth(double dx);
	public void setHeight(double dy);
	public void setCenter(double x, double y);
	public boolean contains(double x, double y);
	public Shape getShape();
	public void toFront();
	public void toBack();
	public void setX(double x);
	public void setY(double x);
	public void setFill(Paint color);
	public double getWidth();
	public double getHeight();
	public void setCenterX(double x);
	public void setCenterY(double y);
//	public void deleteShape(Sketchy sketchy);
	
	
	

}
