/*
 * ==========================================================================================
 * AnimationViewer.java : Moves shapes around on the screen according to different paths.
 * It is the main drawing area where shapes are added and manipulated.
 * YOUR UPI: aedg256
 * ==========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListDataListener;
import java.lang.reflect.Field;

class AnimationViewer extends JComponent implements Runnable, TreeModel {
	private Thread animationThread = null; // the thread for animation
	private static int DELAY = 20; // the current animation speed
	private ShapeType currentShapeType = Shape.DEFAULT_SHAPETYPE; // the current shape type,
	private PathType currentPathType = Shape.DEFAULT_PATHTYPE; // the current path type
	private Color currentColor = Shape.DEFAULT_COLOR; // the current fill colour of a shape
	private Color currentBorderColor = Shape.DEFAULT_BORDER_COLOR;
	private int currentPanelWidth = Shape.DEFAULT_PANEL_WIDTH, currentPanelHeight = Shape.DEFAULT_PANEL_HEIGHT,currentWidth = Shape.DEFAULT_WIDTH, currentHeight = Shape.DEFAULT_HEIGHT;
	private String currentLabel = Shape.DEFAULT_LABEL;

	protected NestedShape root; // NestedShape to contain shapes

	private ArrayList<TreeModelListener> treeModelListeners = new ArrayList<>();

	protected DefaultListModel<Shape> listModel;

	public AnimationViewer() {
		start();
		// Create the root NestedShape with the default panel width and height
		root = new NestedShape(Shape.DEFAULT_PANEL_WIDTH, Shape.DEFAULT_PANEL_HEIGHT);
		listModel = new DefaultListModel<>();
	}

	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Iterate through the inner shapes of the root
		for (Shape currentShape : root.getAllInnerShapes()) {
			currentShape.move();
			currentShape.draw(g);
			currentShape.drawHandles(g);
			currentShape.drawString(g);
		}
	}

	public void resetMarginSize() {
		currentPanelWidth = getWidth();
		currentPanelHeight = getHeight();
		// Reset the panel size for the root and its inner shapes
		root.resetPanelSize(currentPanelWidth, currentPanelHeight);
	}
	
	public NestedShape getRoot() {
		return root; // Return the root NestedShape
	}

	public boolean isLeaf(Object node) {
		return !(node instanceof NestedShape); // Return true if the node is not a NestedShape
	}

	public boolean isRoot(Shape selectedNode) {
		return selectedNode == root; // Return true if the selectedNode is the root
	}

	public Object getChild(Object parent, int index) {
		if (parent instanceof NestedShape) {
			NestedShape nestedParent = (NestedShape) parent;
			if (index >= 0 && index < nestedParent.getSize()) {
				return nestedParent.getInnerShapeAt(index);
			}
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent instanceof NestedShape) {
			NestedShape nestedParent = (NestedShape) parent;
			return nestedParent.getSize();
		}
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof NestedShape && child instanceof Shape) {
			NestedShape nestedParent = (NestedShape) parent;
			return nestedParent.indexOf((Shape) child);
		}
		return -1;
	}

	public void addTreeModelListener(final TreeModelListener tml) {
		treeModelListeners.add(tml);
	}

	public void removeTreeModelListener(final TreeModelListener tml) {
		treeModelListeners.remove(tml);
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		// This is an empty method. No need to provide implementation.
	}
	
	private void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {

		System.out.printf("Called fireTreeNodesInserted: path=%s, childIndices=%s, children=%s\n",
				Arrays.toString(path), Arrays.toString(childIndices), Arrays.toString(children));


		TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);


		for (TreeModelListener listener : treeModelListeners) {
			listener.treeNodesInserted(event);
		}
	}

	public void addShapeNode(NestedShape selectedNode) {

		int innerWidth = selectedNode.getWidth() / 5;
		int innerHeight = selectedNode.getHeight() / 5;

		ShapeType shapeType = getCurrentShapeType();
		PathType pathType = getCurrentPathType();

		Shape newInnerShape;
		switch (shapeType) {
			case RECTANGLE:
				newInnerShape = new RectangleShape(0, 0, innerWidth, innerHeight,
						selectedNode.getWidth(), selectedNode.getHeight(),
						selectedNode.getColor(), selectedNode.getBorderColor(), pathType);
				break;
			case OVAL:
				newInnerShape = new OvalShape(0, 0, innerWidth, innerHeight,
						selectedNode.getWidth(), selectedNode.getHeight(),
						selectedNode.getColor(), selectedNode.getBorderColor(), pathType);
				break;
			case NESTED:
				newInnerShape = new NestedShape(0, 0, innerWidth, innerHeight,
						selectedNode.getWidth(), selectedNode.getHeight(),
						selectedNode.getColor(), selectedNode.getBorderColor(), pathType);
				break;
			default:
				return;
		}

		newInnerShape.resetPanelSize(selectedNode.getWidth(), selectedNode.getHeight());
		newInnerShape.setColor(selectedNode.getColor());
		newInnerShape.setBorderColor(selectedNode.getBorderColor());

		selectedNode.addInnerShape(newInnerShape);

		Object[] path = selectedNode.getPath();
		int childIndex = selectedNode.getSize() - 1;
		Object[] children = { newInnerShape };

		fireTreeNodesInserted(this, path, new int[]{childIndex}, children);
	}
	public void reload(Shape selectedNode) {
		if (selectedNode instanceof NestedShape) {

			listModel.clear();


			NestedShape nestedShape = (NestedShape) selectedNode;
			for (Shape innerShape : nestedShape.getAllInnerShapes()) {
				listModel.addElement(innerShape);
			}
		}
	}

	private void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {

		System.out.printf("Called fireTreeNodesRemoved: path=%s, childIndices=%s, children=%s\n",
				Arrays.toString(path), Arrays.toString(childIndices), Arrays.toString(children));

		TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);

		for (TreeModelListener listener : treeModelListeners) {
			listener.treeNodesRemoved(event);
		}
	}

	public void removeNodeFromParent(Shape selectedNode) {

		NestedShape parentNode = selectedNode.getParent();
		if (parentNode == null) {
			return;
		}

		int childIndex = parentNode.indexOf(selectedNode);

		parentNode.removeInnerShape(selectedNode);

		Object[] path = parentNode.getPath();
		Object[] children = { selectedNode };

		fireTreeNodesRemoved(this, path, new int[]{childIndex}, children);
	}

	// you don't need to make any changes after this line ______________
	public String getCurrentLabel() {return currentLabel;}
	public int getCurrentHeight() { return currentHeight; }
	public int getCurrentWidth() { return currentWidth; }
	public Color getCurrentColor() { return currentColor; }
	public Color getCurrentBorderColor() { return currentBorderColor; }
	public void setCurrentShapeType(ShapeType value) {currentShapeType = value;}
	public void setCurrentPathType(PathType value) {currentPathType = value;}
	public ShapeType getCurrentShapeType() {return currentShapeType;}
	public PathType getCurrentPathType() {return currentPathType;}
	public void update(Graphics g) {
		paint(g);
	}
	public void start() {
		animationThread = new Thread(this);
		animationThread.start();
	}
	public void stop() {
		if (animationThread != null) {
			animationThread = null;
		}
	}
	public void run() {
		Thread myThread = Thread.currentThread();
		while (animationThread == myThread) {
			repaint();
			pause(DELAY);
		}
	}
	private void pause(int milliseconds) {
		try {
			Thread.sleep((long) milliseconds);
		} catch (InterruptedException ie) {}
	}
}
