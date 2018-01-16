package Sketchy;

public class DrawWithPen implements Command {
	
	
	
	private Sketchy _sketchy;
	private CurvedLine _line;
	private SketchyShape _sketchyShape;
	
	public DrawWithPen(Sketchy sketchy, CurvedLine line){
		
		_sketchy = sketchy;
		_line = line;
		_sketchyShape = null;
		
	}
	public void undo(Sketchy sketchy){
		
		
		_sketchy.getSaveableArrayList().remove(_line);
		_sketchy.getSketchyPane().getChildren().remove(_line.getPolyline());
		
//		_sketchy.getSketchyPane().getChildren().remove(_sketchy.getLineList().get(_sketchy.getLineList().size() - 1).getPolyline());
//		_sketchy.getSaveableArrayList().remove(_sketchy.getLineList().get(_sketchy.getLineList().size() - 1));

	}
	public void redo(Sketchy sketchy){
		
		_sketchy.getSaveableArrayList().add(_line);
		_sketchy.getSketchyPane().getChildren().add(_line.getPolyline());
		
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}

}
