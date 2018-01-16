package Sketchy;

public class DrawRectangle implements Command{
	
	private Sketchy _sketchy;
	private SketchyShape _sketchyShape;

	
	public DrawRectangle(Sketchy sketchy, SketchyShape sketchyShape){
		
		_sketchy = sketchy;
		_sketchyShape = sketchyShape;
		
	}
	
	public void undo(Sketchy sketchy){
		
//		System.out.println("a " + _sketchy.getSketchyShapeArrayList().size());
		
		_sketchy.deleteShape(_sketchyShape);
		_sketchy.getSketchyShapeArrayList().remove(_sketchyShape);
		System.out.println("removedShape " + _sketchyShape.getShape());
		
//		System.out.println("a " + _sketchy.getSketchyShapeArrayList().size());
//		_sketchy.deleteShape(_sketchy.getSketchyShapeArrayList().get(_sketchy.getSketchyShapeArrayList().size()-1));
//		_sketchy.getShape().deleteShape(_sketchy);
		
		
	}
	
	public void redo(Sketchy sketchy){
		

		_sketchy.getSketchyShapeArrayList().add(_sketchyShape);
		_sketchy.addShape(_sketchyShape);
		System.out.println("addedShape " + _sketchyShape.getShape());
		
//		_sketchy.getSketchyPane().getChildren().add(_sketchy.getShape());
		
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}

}
