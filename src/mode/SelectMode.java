package mode;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.*;

import shape.BasicObj;
import shape.Port;
import shape.Shape;
import src.ChooseRect;
import mainComponent.Canvas;
import panel.CanvasPanel;

public class SelectMode extends Mode{
	private Point point = null;
	private boolean isSelectOnObject = false; // �Ω�u�B�����Ϊ��P�_
	private boolean isDragging = false; // �Ω�u�B�����Ϊ��P�_

	@Override
	public void mousePressed(MouseEvent e) {	
		point = e.getPoint();
		isSelectOnObject = false;
		Canvas.getCanvas().setSelectSourceLocation(new Point());
		Canvas.getCanvas().setSelectDestinationLocation(new Point());
				
		// �N�Ҧ�����IsSelected���A�]��false
		for(Shape obj: Canvas.getCanvas().getObjectList())
			obj.setIsSelected(false);
		// �N����h�Ӫ��󪺰}�C�q�s��l��
		if(Canvas.getCanvas().getSelectedObjsArray().size() > 0)
			Canvas.getCanvas().setSelectedObjsArray(new ArrayList<Integer>());
		// �P�_���󦳵L���|:
		ArrayList<Integer> overlapObjs = new ArrayList<>();
		for(Shape obj: Canvas.getCanvas().getObjectList()) {
			if(isPressed(point, obj) == true && obj.getIsGrouped() == false)
				overlapObjs.add(Canvas.getCanvas().getObjectList().indexOf(obj));
		}
		
		for(Shape obj: Canvas.getCanvas().getObjectList()) {
			if(overlapObjs.size() > 0 && 
			   Canvas.getCanvas().getObjectList().indexOf(obj) == overlapObjs.get(overlapObjs.size()-1)) {
					obj.setIsSelected(true);
					isSelectOnObject = true;
					break;
			}	
		}
		
		// �Y�I���m���b����W�A����o�Ӯy��
		if(isSelectOnObject == false)
			Canvas.getCanvas().setSelectSourceLocation(new Point(point.x, point.y));
		Canvas.getCanvas().repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		point = e.getPoint();
  	  	// �YMousePressed���y�Цb�����
  	  	if(isSelectOnObject == true) {
  	  		// �YMousePressed���y�Цb�����
  	  		for(Shape obj: Canvas.getCanvas().getObjectList()) {
  	  			if(obj.getIsSelected() == true && obj.getIsGrouped() == false) {
  	  				obj.setCenter(new Point(point.x - obj.getWidth()/2, point.y - obj.getHeight()/2));
  	  				Canvas.getCanvas().repaint();
  				    break;
  	  			}
  	  		}
  	  	}
  	  	// �YMousePressed���y�Ф��b���󪫥��
  	  	if(isSelectOnObject == false) {
  	  		isDragging = true;
  	  		Canvas.getCanvas().setSelectDestinationLocation(new Point(point.x, point.y));
  	  		// Drag�X�@�ӿ�����
  	  		Canvas.getCanvas().setChooseRect(
  	  				new ChooseRect(Canvas.getCanvas().getSelectSourceLocation(), 
  	  						       Canvas.getCanvas().getSelectDestinationLocation()));
  	  		Canvas.getCanvas().repaint();
  	  	}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		point = e.getPoint();
		// ������:
		if(isDragging == true) {
			// �P�_���L����쪫��B�䥼�QGroup
			for(Shape obj: Canvas.getCanvas().getObjectList()) {
				if(obj.getCenter().x + obj.getWidth() <= Canvas.getCanvas().getChooseRect().getTopRight().getX() &&
				   obj.getCenter().x >= Canvas.getCanvas().getChooseRect().getTopLeft().getX() &&
				   obj.getCenter().y + obj.getHeight() <= Canvas.getCanvas().getChooseRect().getFloorLeft().getY() &&
				   obj.getCenter().y >= Canvas.getCanvas().getChooseRect().getTopLeft().getY() &&
				   obj.getIsGrouped() == false) {
					 obj.setIsSelected(true);
					 // �[�i������
					 Canvas.getCanvas().getSelectedObjsArray().add(Canvas.getCanvas().getObjectList().indexOf(obj));	
				}
			}
		}
		Canvas.getCanvas().setChooseRect(new ChooseRect(new Point(), new Point()));
		Canvas.getCanvas().repaint();
 		isDragging = false;
	}
}
