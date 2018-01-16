package Sketchy;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;



public class Control {

	private VBox _leftPane;
	private MyEnum _myEnum;
	private ToggleGroup _group;
	private RadioButton _button1;
	private RadioButton _button2;
	private RadioButton _button3;
	private RadioButton _button4;
	private Button _raiseBtn;
	private Button _lowerBtn;
	private Button _fillBtn;
	private Button _deleteBtn;
	private Button _undoBtn;
	private Button _redoBtn;
	private Button _saveBtn;
	private Button _loadBtn;
	private Label _drawingOptions;
	private Label _shapeOptions;
	private Label _operations;
	private Label _setTheColor;
	private Sketchy _sketchy;
	private Color _c;
	private Paint _cBefore;
	private SketchyShape _sketchyShape;
	private boolean check;
	
	
	private FillShape _fillShape;
	

	public Control(Sketchy sketchy) {

		_sketchy = sketchy;
		_sketchyShape = null;
		_leftPane = new VBox();
		_leftPane.setPrefSize(200.0, 600.0);
		_leftPane.setStyle("-fx-background-color: grey;");
		_leftPane.setSpacing(8);
		_leftPane.setAlignment(Pos.CENTER);

		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				_c = colorPicker.getValue();
				_sketchy.setColor(_c);
//				System.out.println("New Color's RGB = " + _c.getRed() + " "
//						+ _c.getGreen() + " " + _c.getBlue());
			}
		});

		colorPicker.getStyleClass().add("menu-button");

		this.setUpToggleGroup();
		this.setUpOtherButtons();
		this.setUpLabels();
		_leftPane.getChildren().addAll(_drawingOptions, _button1, _button2,
				_button3, _button4, _setTheColor, colorPicker, _shapeOptions,
				_raiseBtn, _lowerBtn, _fillBtn, _deleteBtn, _operations,
				_undoBtn, _redoBtn, _saveBtn, _loadBtn);


		_button1.setOnAction(new ButtonHandler());
		_button2.setOnAction(new ButtonHandler());
		_button3.setOnAction(new ButtonHandler());
		_button4.setOnAction(new ButtonHandler());
		
		_fillBtn.setOnAction(new FillBtnHandler());
		_deleteBtn.setOnAction(new DltBtnHandler());
		_raiseBtn.setOnAction(new RaiseBtnHandler());
		_lowerBtn.setOnAction(new LowerBtnHandler());
		_undoBtn.setOnAction(new UndoBtnHandler());
		_redoBtn.setOnAction(new RedoBtnHandler());

	}
	public void setShape(SketchyShape sketchyShape){

		
	}

	public VBox getLeftPane() {
		return _leftPane;
	}

	public MyEnum getMyEnum() {
		return _myEnum;
	}
//	public RadioButton getButton1() {
//		return _button1;
//	}
//	public RadioButton getButton2() {
//		return _button2;
//	}
//	public RadioButton getButton3() {
//		return _button3;
//	}
//	public RadioButton getButton4() {
//		return _button4;
//	}
	private void setUpToggleGroup() {
		_group = new ToggleGroup();
		_button1 = new RadioButton("Select Shape");
		_button1.setToggleGroup(_group);
		_button1.setSelected(true);
		_button2 = new RadioButton("Draw with Pen");
		_button2.setToggleGroup(_group);
		_button3 = new RadioButton("Draw Rectangle");
		_button3.setToggleGroup(_group);
		_button4 = new RadioButton("Draw Ellipse");
		_button4.setToggleGroup(_group);
	}
	private void setUpOtherButtons() {
		_raiseBtn = new Button("Raise");
		_lowerBtn = new Button("Lower");
		_fillBtn = new Button("fill");
		_deleteBtn = new Button("delete");
		_undoBtn = new Button("undo");
		_redoBtn = new Button("redo");
		_saveBtn = new Button("save");
		_loadBtn = new Button("load");
	}

	private void setUpLabels() {

		_drawingOptions = new Label("Drawing Options");
		_setTheColor = new Label("Set the Color");
		_shapeOptions = new Label("Shape Options");
		_operations = new Label("Operations");

	}

	private class ButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			if (_button1.isSelected()) {
				_myEnum = MyEnum.SELECTSHAPE;
			} else if (_button2.isSelected()) {
				_myEnum = MyEnum.DRAWWITHPEN;
			} else if (_button3.isSelected()) {
				_myEnum = MyEnum.DRAWRECTANGLE;
			} else if (_button4.isSelected()) {
				_myEnum = MyEnum.DRAWELLIPSE;
			} 
			_sketchy.getOption(_myEnum);
		}

	}
	private class FillBtnHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			
			if (_sketchy.getShape() != null){
				
				_cBefore = _sketchy.getShape().getShape().getFill();
				_sketchy.getShape().setFill(_c);
//				System.out.println("before " + _sketchy.getUndoStack().size());
				_sketchy.addFillShapeToUndoStack(Control.this, _sketchy.getShape());
//				System.out.println("after " + _sketchy.getUndoStack().size());
				
			} else {
				
				Control.this.setFillOfShape(_c);
//				System.out.println("before " + _sketchy.getUndoStack().size());
				_sketchy.addFillShapeToUndoStack(Control.this, 
						_sketchy.getSketchyShapeArrayList().get(_sketchy.getSketchyShapeArrayList().size() - 1));
//				System.out.println("after " + _sketchy.getUndoStack().size());
			}
			
		}
	}
	public void setFillOfShape(Color color){
		_cBefore = _sketchy.getSketchyShapeArrayList().get(_sketchy.getSketchyShapeArrayList().size() - 1).getShape().getFill();
		_sketchy.getSketchyShapeArrayList().get(_sketchy.getSketchyShapeArrayList().size() - 1).setFill(color);	
	}
	
	public Paint getPrevColor(){
		return _cBefore;
	}
	
	public Color getColor(){
		return _c;
	}
	
	private class DltBtnHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			
			
			if (_sketchy.getShape() != null){
				
				_sketchy.getSketchyPane().getChildren().remove(_sketchy.getShape().getShape());
				_sketchy.getSketchyShapeArrayList().remove(_sketchy.getShape());
				_sketchy.addDltShapeToUndoStack(_sketchy.getShape());
				
			} else {
				
				_sketchy.getSketchyPane().getChildren().remove(_sketchy.getSketchyShapeArrayList().size() - 1);
				_sketchy.getSketchyShapeArrayList().remove(_sketchy.getSketchyShapeArrayList().size() - 1);
				_sketchy.addDltShapeToUndoStack(_sketchy.getSketchyShapeArrayList().get(_sketchy.getSketchyShapeArrayList().size() - 1));
				
			}
				
		}
	}
	private class RaiseBtnHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			_sketchy.getShape().toFront();
			
//			System.out.println("a " + _sketchy.getSketchyShapeArrayList().indexOf(_sketchy.getShape()));
			System.out.println("a " + _sketchy.getSaveableArrayList().indexOf(_sketchy.getShape()));
			_sketchy.getSketchyShapeArrayList().remove(_sketchy.getShape());
			_sketchy.getSaveableArrayList().remove(_sketchy.getShape());
			
			_sketchy.getSketchyShapeArrayList().add(_sketchy.getShape());
			_sketchy.getSaveableArrayList().add((Saveable) _sketchy.getShape());
			System.out.println("b " + _sketchy.getSaveableArrayList().indexOf(_sketchy.getShape()));
			
			_sketchy.addRaiseToUndoStack(_sketchy.getShape());
			
//			System.out.println("b " + _sketchy.getSketchyShapeArrayList().indexOf(_sketchy.getShape()));
			
		}
	}
	private class LowerBtnHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			_sketchy.getShape().toBack();
			
			System.out.println("c " + _sketchy.getSketchyShapeArrayList().indexOf(_sketchy.getShape()));
			_sketchy.getSketchyShapeArrayList().remove(_sketchy.getShape());
			_sketchy.getSaveableArrayList().remove(_sketchy.getShape());
			
			_sketchy.getSketchyShapeArrayList().add(0, _sketchy.getShape());
			_sketchy.getSaveableArrayList().add(0, (Saveable) _sketchy.getShape());
			System.out.println("d " + _sketchy.getSketchyShapeArrayList().indexOf(_sketchy.getShape()));
			
			_sketchy.addLowerToUndoStack(_sketchy.getShape());
		}
	}
	
	private class UndoBtnHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			
//			if (_sketchy.getUndoStack().isEmpty()){
//				_undoBtn.setDisable(true);
//			} else {
//				_undoBtn.setDisable(false);
//				System.out.println("a " + _sketchy.getRedoStack().size());
//				System.out.println(_sketchy.getUndoStack().size() - 1);
				_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1).undo(_sketchy);
				_sketchy.getRedoStack().push(_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1));
				_sketchy.getUndoStack().pop();
//				_sketchy.getRedoStack().push(_sketchy.getCommand());
//				System.out.println("b " + _sketchy.getRedoStack().size());
//			}			
		}
	}
	                               
	private class RedoBtnHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			
//			if (_sketchy.getRedoStack().isEmpty()){
//				_redoBtn.setDisable(true);
//			} else {
//				
//				_redoBtn.setDisable(false);
				
				_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1).redo(_sketchy);
				_sketchy.getUndoStack().push(_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1));
				_sketchy.getRedoStack().pop();
//				System.out.println("c " + _sketchy.getRedoStack().size());
//				_sketchy.getUndoStack().push(_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1));
//			}
		}
	}
	

}
