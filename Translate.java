package Sketchy;

public class Translate implements Command {
	
	private Sketchy _sketchy;
	private SketchyShape _sketchyShape;
	
	public Translate(Sketchy sketchy, SketchyShape sketchyShape){
		
		_sketchy = sketchy;
		_sketchyShape = sketchyShape;
		
	}
	public void undo(Sketchy sketchy){
		
		_sketchy.getShape().setCenterX(_sketchy.getLocBefore().getX() - _sketchy.getDX());
		_sketchy.getShape().setCenterY(_sketchy.getLocBefore().getY() - _sketchy.getDY());
		System.out.println(_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1)
		.getSketchyShape());
	}
	public void redo(Sketchy sketchy){
		
		_sketchy.getLastShapeInRedoStack().setCenterX(_sketchy.getLocAfter().getX());
		_sketchy.getLastShapeInRedoStack().setCenterY(_sketchy.getLocAfter().getY());
		
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}

}
