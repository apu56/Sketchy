package Sketchy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;

public class SketchyEllipse implements SketchyShape, Saveable {


	private Ellipse _ellipse;
	private Sketchy _sketchy;
	
	public SketchyEllipse(double x, double y) {
		
		_ellipse = new Ellipse(x, y, 40, 60);
		_ellipse.setFill(Color.BLACK);
		
	}
	
	@Override
	public Ellipse getShape(){
		return _ellipse;
	}
	
	@Override
	public Point2D getCenter(){
		double x = _ellipse.getCenterX();
		double y = _ellipse.getCenterY();
		Point2D center = new Point2D(x,y);
		return center;
	}
	
	@Override
	public void setRotate(double angle){
		_ellipse.setRotate(angle);
	}
	
	@Override
	public void setStroke(Color color){
		_ellipse.setStroke(color);
	}
	
	@Override
	public void setWidth(double dx){
		_ellipse.setRadiusX(_ellipse.getRadiusX() + dx);
	}
	
	@Override
	public void setHeight(double dy){
		_ellipse.setRadiusY(_ellipse.getRadiusY() + dy);
	}
	
	@Override
	public void setCenter(double x, double y){
		_ellipse.setCenterX(x);
		_ellipse.setCenterY(y);
	}
	
	@Override
	public boolean contains(double x, double y){
		return _ellipse.contains(x, y);
	}
	
	public void setCenterX(double x){
		_ellipse.setCenterX(x);
	}
	
	public void setCenterY(double y){
		_ellipse.setCenterY(y);
	}
	
	public void toFront(){
		_ellipse.toFront();
	}
	public void toBack(){
		_ellipse.toBack();
	}
	
	@Override
	public void setX(double x){
		_ellipse.setCenterX(x);
	}
	
	public void setY(double y){
		_ellipse.setCenterY(y);
	}
	
	public void setFill(Paint color){
		_ellipse.setFill(color);
	}
	
	public double getWidth(){
		return _ellipse.getRadiusX()*2;
	}
	
	public double getHeight(){
		return _ellipse.getRadiusY()*2;
	}
	
}
