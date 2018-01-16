package Sketchy;

public class DrawEllipse implements Command{
	
	private Sketchy _sketchy;
	private SketchyShape _sketchyShape;
	
	public DrawEllipse(Sketchy sketchy, SketchyShape sketchyShape){
		_sketchy = sketchy;
		_sketchyShape = sketchyShape;
		
	}

	public void undo(Sketchy sketchy){
		
		_sketchy.deleteShape(_sketchyShape);
		_sketchy.getSketchyShapeArrayList().remove(_sketchyShape);
//		System.out.println("removedShape " + _sketchyShape.getShape());
		
//		_sketchy.getSketchyShapeArrayList().remove(_sketchy.getShape());
//		_sketchy.deleteShape(_sketchyShape);
	}
	public void redo(Sketchy sketchy){
		
		_sketchy.getSketchyShapeArrayList().add(_sketchyShape);
		_sketchy.addShape(_sketchyShape);
		System.out.println("addedShape " + _sketchyShape.getShape());
		
//		_sketchy.getSketchyShapeArrayList().add(_sketchy.getShape());
//		_sketchy.getSketchyPane().getChildren().add(_sketchy.getOtherShape().getShape());
		
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}
}
