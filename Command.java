package Sketchy;

public interface Command {
	
	public void undo(Sketchy sketchy);
	public void redo(Sketchy sketchy);
	public SketchyShape getSketchyShape();

}
