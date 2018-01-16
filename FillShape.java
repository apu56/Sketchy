package Sketchy;

public class FillShape implements Command{
	
	private Sketchy _sketchy;
	private Control _control;
	private SketchyShape _sketchyShape;
	private boolean _check;
	
	public FillShape(Sketchy sketchy, Control control, SketchyShape sketchyShape){
		_sketchy = sketchy;
		_control = control;
		_sketchyShape = sketchyShape;
		_check = false;
		
	}
	public void undo(Sketchy sketchy){
		
		if (_sketchy.getShape() != null){
			
			_sketchy.getShape().setFill(_control.getPrevColor());
			_check = false;
		}
		
		_sketchyShape.setFill(_control.getPrevColor());
		_check = true;
		
	}
	public void redo(Sketchy sketchy){
		
		if (_check == false){
			
			_sketchy.getShape().setFill(_control.getColor());
			
		} else {
		
		_sketchyShape.setFill(_control.getColor());
		
	}
	}
	@Override
	public SketchyShape getSketchyShape(){
		return _sketchyShape;
	}

	
}
