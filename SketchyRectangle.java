package Sketchy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SketchyRectangle implements SketchyShape, Saveable {
	
	private Rectangle _rect;
	private Point2D _center;
	private Sketchy _sketchy;
//	private Control _control;
//	private Color _currColor;
	
	public SketchyRectangle(double x, double y) {
		
		_rect = new Rectangle(x, y, 500, 100);
		_rect.setFill(Color.BLACK);
	}
	@Override
	public Rectangle getShape(){
		return _rect;
	}
	@Override
	public Point2D getCenter(){
		double x = (_rect.getX() + _rect.getWidth()/2);
		double y = (_rect.getY() + _rect.getHeight()/2);
		_center = new Point2D(x,y);
		return _center;
	}
	@Override
	public void setRotate(double angle){
		_rect.setRotate(angle);
	}
	@Override
	public void setStroke(Color color){
		_rect.setStroke(color);
	}
	@Override
	public void setWidth(double dx){
		_rect.setWidth(_rect.getWidth() + 2*dx); 
	}
	@Override
	public void setHeight(double dy){
		_rect.setHeight(_rect.getHeight() + 2*dy);
	}
	@Override
	public void setCenter(double x, double y){
		double centerX = this.getCenter().getX();
		centerX = this.getCenter().getX() + x;
		double centerY = this.getCenter().getY();
		centerY = this.getCenter().getY() + y;
		_center = new Point2D(centerX, centerY);
	}
	@Override
	public boolean contains(double x, double y){
		return _rect.contains(x, y);
		
	}
	@Override
	public void setX(double x){
		_rect.setX(x);
	}
	@Override
	public void setY(double y){
		_rect.setY(y);
	}
	public void toFront(){
		_rect.toFront();
	}
	public void toBack(){
		_rect.toBack();
	}
	public double getX(){
		return _rect.getX();
	}
	public double getY(){
		return _rect.getY();
	}
	public void setFill(Paint color){
		_rect.setFill(color);
	}
	public Point2D getPoint(){
		return new Point2D(_rect.getX(), _rect.getY());
	}
	public void setCenterX(double x){
		_rect.setX(x - _rect.getWidth()/2);
	}
	public void setCenterY(double y){
		_rect.setY(y - _rect.getHeight()/2);
	}
	public double getWidth(){
		return _rect.getWidth();
	}
	public double getHeight(){
		return _rect.getHeight();
	}
//	@Override
//	public void deleteShape(Sketchy sketchy){
//		_sketchy = sketchy;
//		_sketchy.getSketchyPane().getChildren().remove(_rect);
//	}
	

}
