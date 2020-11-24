package mainComponent;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;
import mode.Mode;
import panel.ButtonPanel;
import shape.Shape;
import src.ChooseRect;

public class Canvas extends JComponent {
	private static Canvas canvas; // Singleton
	private Mode mode = null;
	private ArrayList<Shape> objects = new ArrayList();
	private ArrayList<Shape> lines = new ArrayList();
	private ArrayList<Integer> selectedObjsArray = new ArrayList<>(); // �Ω������
	private Point selectSourceLocation = new Point(); // �����Ϊ��_�I
	private Point selectDestinationLocation = new Point(); // �����Ϊ����I
	private ChooseRect chooseRect = new ChooseRect(selectSourceLocation, 
			                                       selectDestinationLocation); // ������
	
	private Canvas() {
		setMode();
	}
	
	public static Canvas getCanvas() { // Singleton
		if(canvas == null)
			canvas = new Canvas();
		return canvas;
	}
	
	public void setMode() {
		removeMouseListener((MouseListener) mode);
		removeMouseMotionListener((MouseMotionListener) mode);
		mode = getMode();
		addMouseListener((MouseListener) mode);
		addMouseMotionListener((MouseMotionListener) mode);
	}
	
	public Mode getMode() {
		return ButtonPanel.getMode();
	}
	
	public ArrayList<Shape> getObjectList(){
		return objects;
	}
	
	public ArrayList<Shape> getLineList(){
		return lines;
	}
	
	public ArrayList<Integer> getSelectedObjsArray(){
		return selectedObjsArray;
	}
	
	public void setSelectedObjsArray(ArrayList<Integer> selectedObjsArray){
		this.selectedObjsArray = selectedObjsArray;
	}
	
	public Point getSelectSourceLocation() {
		return selectSourceLocation;
	}
	
	public Point getSelectDestinationLocation(){
		return selectDestinationLocation;
	}
	
	public void setSelectSourceLocation(Point selectSourceLocation) {
		this.selectSourceLocation = selectSourceLocation;
	}
	
	public void setSelectDestinationLocation(Point selectDestinationLocation){
		this.selectDestinationLocation = selectDestinationLocation;
	}
	
	public ChooseRect getChooseRect() {
		return chooseRect;
	}
	
	public void setChooseRect(ChooseRect chooseRect) {
		this.chooseRect = chooseRect;
	}
	
	public void paintComponent(Graphics g) {
		for(Shape obj: objects)
			obj.draw(g);
		for(Shape line: lines)
			line.draw(g);
		chooseRect.draw(g);
	}
}
