package Sketchy;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Stack;

public class Sketchy {

	private Pane _sketchyPane;

	private CurvedLine _line;
	private MyEnum _option;
	private ArrayList<SketchyShape> _shapes;
	private ArrayList<Saveable> _saveables;
	private ObservableList<Node> _observableList;
	private SketchyShape _shape;
	private SketchyRectangle _sketchyRectangle;
	private SketchyEllipse _sketchyEllipse;
	private double _angleR;
	private double _angleD;
	private MouseEvent _e;
	// private SketchyShape _otherShape;

	private Stack<Command> _undoStack;
	private Stack<Command> _redoStack;
	private Command _command;
	private DrawRectangle _drawRectangle;
	private DrawEllipse _drawEllipse;
	private FillShape _fillShape;
	private DrawWithPen _drawWithPen;
	private DeleteShape _deleteShape;
	private Raise _raise;
	private Lower _lower;
	private Translate _translate;

	private Point2D _locBefore;
	private Point2D _locAfter;
	private double dx;
	private double dy;



	private Color _prevCol;
	private Color _currCol;

	public Sketchy() {
		_currCol = Color.BLACK;
		_prevCol = Color.BLACK;
		_sketchyPane = new Pane();
		_sketchyPane.setPrefSize(800.0, 700.0);
		_sketchyPane.setStyle("-fx-background-color: white;");
		_sketchyPane.addEventHandler(MouseEvent.ANY, new MouseHandler());
		_sketchyPane.setFocusTraversable(true);
		_option = MyEnum.SELECTSHAPE;
		_shapes = new ArrayList<SketchyShape>();
		// must add stuff to this list!
		_saveables = new ArrayList<Saveable>();

		_shape = null;
		// _otherShape = null;
		_angleR = 0.0;
		_undoStack = new Stack<Command>();
		_redoStack = new Stack<Command>();
		_command = null;

	}

	public void setColor(Color color) {
		_currCol = color;
	}

	public Pane getSketchyPane() {
		return _sketchyPane;

	}

	public void getOption(MyEnum enom) {
		_option = enom;
	}

	private class MouseHandler implements EventHandler<MouseEvent> {

		private Point2D mousePoint;
		
		private Point2D newPos;

		@Override
		public void handle(MouseEvent e) {

			_e = e;

			switch (_option) {

			case SELECTSHAPE:

				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {

					deselectShape();

					mousePoint = new Point2D(e.getX(), e.getY());
					newPos = new Point2D(e.getX(), e.getY());
					_locBefore = new Point2D(e.getX(), e.getY());

					for (int i = _shapes.size() - 1; i >= 0; i--) {

						Point2D newPoint = Sketchy.this.rotatePoint(mousePoint,
								_shapes.get(i).getCenter(), _angleD);

						// Rectangle rect = new Rectangle(newPoint.getX(),
						// newPoint.getY(), 5, 5);
						// Sketchy.this.getSketchyPane().getChildren().add(rect);
						//
						// System.out.println(_shapes.get(i).contains(
						// newPoint.getX(), newPoint.getY()));

						if (_shapes.get(i).contains(newPoint.getX(),
								newPoint.getY())) {

							// System.out.println("hello");

							Sketchy.this._shape = _shapes.get(i);
							
							dx = mousePoint.getX() - _shape.getCenter().getX();
							dy = mousePoint.getY() - _shape.getCenter().getY();
							
//							if (_shape instanceof SketchyRectangle){
//							
//								DX = mousePoint.getX() - _sketchyRectangle.getCenter().getX();
//								DY = mousePoint.getY() - _sketchyRectangle.getCenter().getY();
//							} else {
//								DX = mousePoint.getX() - _sketchyEllipse.getCenter().getX();
//								DY = mousePoint.getY() - _sketchyEllipse.getCenter().getY();
//							}
							
							

							Sketchy.this._shape.setStroke(Color.RED);

							break;

						} else {

							deselectShape();
						}
					}
				}

				if (_shape != null
						&& e.getEventType() == MouseEvent.MOUSE_DRAGGED) {

					if (e.isControlDown()) {

						Point2D currentPos = new Point2D(e.getX(), e.getY());

						Sketchy.this.rotate(_shape, mousePoint, currentPos);

					} else if (e.isShiftDown()) {

						Point2D currentPos = new Point2D(e.getX(), e.getY());

						Sketchy.this.resizeShape(_shape, newPos, currentPos);

						newPos = new Point2D(e.getX(), e.getY());

					} else {

//						if (_shape instanceof SketchyRectangle){
							
							_shape.setCenterX(e.getX() - dx);
							_shape.setCenterY(e.getY() - dy);

							_locAfter = new Point2D(_shape.getCenter().getX(),
									_shape.getCenter().getY());
							
							Sketchy.this.addTranslateToUndoStack(_sketchyRectangle);
							
							

						}

//						_shape.setCenterX(e.getX() - dx);
//						_shape.setCenterY(e.getY() - dy);
//
//						_locAfter = new Point2D(_shape.getCenter().getX(),
//								_shape.getCenter().getY());
//						
//						Sketchy.this.addTranslateToUndoStack(Sketchy.this.getShape());

					
				}
				break;
					

			case DRAWWITHPEN:

				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {

					_line = new CurvedLine(e.getX(), e.getY());
					_sketchyPane.getChildren().add(_line.getPolyline());

					// saveable here
					_saveables.add(_line);

					// comand here
					_drawWithPen = new DrawWithPen(Sketchy.this, _line);
					_command = _drawWithPen;
					_undoStack.push(_command);
					_redoStack.clear();

				}
				if (_line != null
						&& e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					_line.getPolyline().getPoints().addAll(e.getX(), e.getY());
				}
				break;

			case DRAWRECTANGLE:

				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {

					deselectShape();

					_sketchyRectangle = new SketchyRectangle(e.getX(), e.getY());
					// _otherShape = _sketchyRectangle;
					_sketchyRectangle.getShape().setFill(_currCol);
					_sketchyPane.getChildren()
							.add(_sketchyRectangle.getShape());
					_shapes.add(_sketchyRectangle);
					System.out.println(_shapes.size());

					// saveables here
					_saveables.add(_sketchyRectangle);

					
//					_locBefore = new Point2D(_sketchyRectangle.getCenter().getX(),
//							_sketchyRectangle.getCenter().getY());
					
					
					// command stuff here
					_drawRectangle = new DrawRectangle(Sketchy.this,
							_sketchyRectangle);
					_command = _drawRectangle;
					_undoStack.push(_command);
					_redoStack.clear();
					
					

				}
				break;

			case DRAWELLIPSE:

				if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {

					deselectShape();

					_sketchyEllipse = new SketchyEllipse(e.getX(), e.getY());
					// _otherShape = _sketchyEllipse;
					_sketchyPane.getChildren().add(_sketchyEllipse.getShape());
					_shapes.add(_sketchyEllipse);

					// saveables here
					_saveables.add(_sketchyEllipse);

					
					
//					_locBefore = new Point2D(_sketchyEllipse.getCenter().getX(),
//							_sketchyRectangle.getCenter().getY());
					
					
					// comand here
					_drawEllipse = new DrawEllipse(Sketchy.this,
							_sketchyEllipse);
					_command = _drawEllipse;
					_undoStack.push(_command);
					_redoStack.clear();
				}
				break;
			}
		}
	}
	public double getDX(){
		return dx;
	}
	public double getDY(){
		return dy;
	}

	public MouseEvent getEvent() {
		return _e;
	}

	public SketchyShape getShape() {
		return _shape;
	}

	public ArrayList<SketchyShape> getSketchyShapeArrayList() {
		return _shapes;
	}

	public ArrayList<Saveable> getSaveableArrayList() {
		return _saveables;
	}

	public void deselectShape() {
		for (int i = _shapes.size() - 1; i >= 0; i--) {

			if (_shapes.get(i) != null) {
				_shapes.get(i).setStroke(null);
			}
		}
		_shape = null;
	}

	public void rotate(SketchyShape shape, Point2D prev, Point2D curr) {
		_angleR = (Math.atan2((prev.getY() - shape.getCenter().getY()),
				(prev.getX() - shape.getCenter().getX())) - Math.atan2((curr
				.getY() - shape.getCenter().getY()), (curr.getX() - shape
				.getCenter().getX())));
		_angleD = -Math.toDegrees(_angleR);
		shape.setRotate(_angleD);
	}

	public Point2D rotatePoint(Point2D pointToRotate, Point2D rotateAround,
			double angle) {

		double sine = Math.sin(Math.toRadians(angle));
		double cosine = Math.cos(Math.toRadians(angle));

		Point2D point = new Point2D(
				(pointToRotate.getX() - rotateAround.getX()),
				(pointToRotate.getY() - rotateAround.getY()));

		Point2D point2 = new Point2D(
				((point.getX() * cosine) + (point.getY() * sine)),
				((-point.getX() * sine) + (point.getY() * cosine)));

		Point2D point3 = new Point2D((point2.getX() + rotateAround.getX()),
				(point2.getY() + rotateAround.getY()));

		return point3;

	}

	public void resizeShape(SketchyShape shape, Point2D prev, Point2D curr) {

		double rotation = _angleD;
		// error here
		Point2D oldCenter = new Point2D(shape.getCenter().getX(), shape
				.getCenter().getY());

		Point2D rotatedPrev = this.rotatePoint(prev, oldCenter, rotation);
		Point2D rotatedCurr = this.rotatePoint(curr, oldCenter, rotation);

		double dx = Math.abs(rotatedCurr.getX() - rotatedPrev.getX());
		double dy = Math.abs(rotatedCurr.getY() - rotatedPrev.getY());

		double distanceXCurr = Math.abs(rotatedCurr.getX()
				- _shape.getCenter().getX());
		double distanceXPrev = Math.abs(rotatedPrev.getX()
				- _shape.getCenter().getX());

		double distanceYCurr = Math.abs(rotatedCurr.getY()
				- _shape.getCenter().getY());
		double distanceYPrev = Math.abs(rotatedPrev.getY()
				- _shape.getCenter().getY());

		if (distanceXCurr < distanceXPrev) {
			dx = -1 * dx;
		}

		if (distanceYCurr < distanceYPrev) {
			dy = -1 * dy;
		}

		shape.setWidth(dx);
		shape.setHeight(dy);

		Point2D newCenter = shape.getCenter();

		// tried psuedocode, didn't work
		shape.setCenterX(oldCenter.getX());
		shape.setCenterY(oldCenter.getY());

	}

	public Point2D getLocBefore() {
		return _locBefore;
	}

	public Point2D getLocAfter() {
		return _locAfter;
	}

	public Stack<Command> getUndoStack() {
		return _undoStack;
	}

	public Stack<Command> getRedoStack() {
		return _redoStack;
	}

	public Command getCommand() {
		return _command;
	}

	public void deleteShape(SketchyShape sketchyShape) {
		this.getSketchyPane().getChildren().remove(sketchyShape.getShape());
	}

	public void addShape(SketchyShape sketchyShape) {
		this.getSketchyPane().getChildren().add(sketchyShape.getShape());
	}

	public SketchyRectangle getRect() {
		return _sketchyRectangle;
	}

	public void addFillShapeToUndoStack(Control control,
			SketchyShape sketchyShape) {
		_fillShape = new FillShape(this, control, sketchyShape);
		_command = _fillShape;
		_undoStack.push(_command);
		_redoStack.clear();
	}

	// public SketchyShape getOtherShape(){
	// return _otherShape;
	// }

	public CurvedLine getCurvedLine() {
		return _line;
	}

	public void addDltShapeToUndoStack(SketchyShape sketchyShape) {
		_deleteShape = new DeleteShape(this, sketchyShape);
		_command = _deleteShape;
		_undoStack.push(_command);
		_redoStack.clear();
	}

	// NEED FIXING HERE
	public void addRaiseToUndoStack(SketchyShape sketchyShape) {
		_raise = new Raise(this, sketchyShape);
		_command = _raise;
		_undoStack.push(_command);
		_redoStack.clear();

	}

	public void addLowerToUndoStack(SketchyShape sketchyShape) {
		_lower = new Lower(this, sketchyShape);
		_command = _lower;
		_undoStack.push(_command);
		_redoStack.clear();

	}
	
	public void addTranslateToUndoStack(SketchyShape sketchyShape) {
		_translate = new Translate(this, sketchyShape);
		_command = _translate;
		_undoStack.push(_command);
		_redoStack.clear();
	}

	public void fillSelectedShape(SketchyShape sketchyShape, Color color,
			Control control) {

		Paint cBefore = sketchyShape.getShape().getFill();
		sketchyShape.setFill(color);
		this.addFillShapeToUndoStack(control, sketchyShape);

	}
	public SketchyShape getLastShapeInUndoStack(){
		return this.getUndoStack().get(this.getUndoStack().size() - 1)
				.getSketchyShape();
	}
	public SketchyShape getLastShapeInRedoStack(){
		return this.getRedoStack().get(this.getRedoStack().size() - 1)
		.getSketchyShape();
	}

}
