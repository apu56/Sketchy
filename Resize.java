package Sketchy;

public class Resize implements Command{
	
	private Sketchy _sketchy;
	private SketchyShape _sketchyShape;
	
	public Resize(Sketchy sketchy, SketchyShape sketchyShape){
		
	}
	public void undo(Sketchy sketchy){
		
	}
	public void redo(Sketchy sketchy){
		
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}

}
