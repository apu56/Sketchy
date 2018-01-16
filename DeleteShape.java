package Sketchy;

public class DeleteShape implements Command {
	
	
	private Sketchy _sketchy;
	private SketchyShape _sketchyShape;
	
	public DeleteShape(Sketchy sketchy, SketchyShape sketchyShape){
		
		_sketchy = sketchy;
		_sketchyShape = sketchyShape;
	}
	
	public void undo(Sketchy sketchy){
		if (_sketchy.getShape() != null){
			
			_sketchy.getSketchyShapeArrayList().add(_sketchy.getShape());
			_sketchy.getSketchyPane().getChildren().add(_sketchy.getShape().getShape());
			
	
		} else {
			
			_sketchy.getSketchyShapeArrayList().add(_sketchyShape);
			_sketchy.getSketchyPane().getChildren().add(_sketchyShape.getShape());
		}

		
	}
	public void redo(Sketchy sketchy){
		
		if (_sketchy.getShape() != null){
			
			_sketchy.getSketchyShapeArrayList().remove(_sketchy.getShape());
			_sketchy.getSketchyPane().getChildren().remove(_sketchy.getShape().getShape());
			
	
		} else {
			
			_sketchy.getSketchyShapeArrayList().remove(_sketchyShape);
			_sketchy.getSketchyPane().getChildren().remove(_sketchyShape.getShape());
		}
		
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}

}
