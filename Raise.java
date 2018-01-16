package Sketchy;

public class Raise implements Command {

	private Sketchy _sketchy;
	private SketchyShape _sketchyShape;

	// private boolean _check;

	public Raise(Sketchy sketchy, SketchyShape sketchyShape) {

		_sketchy = sketchy;
		_sketchyShape = sketchyShape;

	}

	public void undo(Sketchy sketchy) {

		_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1)
				.getSketchyShape().toBack();

		_sketchy.getSketchyShapeArrayList().remove(
				_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1)
						.getSketchyShape());
		_sketchy.getSaveableArrayList().remove(
				_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1)
						.getSketchyShape().getShape());

		_sketchy.getSketchyShapeArrayList().add(
				0,
				_sketchy.getUndoStack().get(_sketchy.getUndoStack().size() - 1)
						.getSketchyShape());
		_sketchy.getSaveableArrayList().add(
				0,
				(Saveable) _sketchy.getUndoStack()
						.get(_sketchy.getUndoStack().size() - 1)
						.getSketchyShape());

	}

	public void redo(Sketchy sketchy) {

		_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1)
				.getSketchyShape().toFront();

		_sketchy.getSketchyShapeArrayList().remove(
				_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1)
						.getSketchyShape());
		_sketchy.getSaveableArrayList().remove(
				_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1)
						.getSketchyShape().getShape());

		_sketchy.getSketchyShapeArrayList().add(
				_sketchy.getRedoStack().get(_sketchy.getRedoStack().size() - 1)
						.getSketchyShape());
		_sketchy.getSaveableArrayList().add(
				(Saveable) _sketchy.getRedoStack()
						.get(_sketchy.getRedoStack().size() - 1)
						.getSketchyShape());

	}

	@Override
	public SketchyShape getSketchyShape() {
		return _sketchyShape;
	}

}
