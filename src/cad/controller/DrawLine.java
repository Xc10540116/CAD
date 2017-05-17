package cad.controller;

import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import cad.model.Draw;
import cad.model.Line;

public class DrawLine extends Draw {

	private Point end_point = new Point();
	Shape lineShape = new Line2D.Float();
	
	/**
	 * 鼠标松开时，执行绘图操作，并将直线放入图形列表中，将索引指向当前直线
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(panel.getNow_shape_index() == -1) {
			super.mouseReleased(e);
			end_point.setLocation(e.getX(), e.getY());
			if(!point.equals(end_point)) {
				draw();
				Line line = new Line(point.x, point.y, end_point.x, end_point.y, color, thick);
				panel.addshape(line);
				int now_index = panel.getShapeList().size() - 1;
				panel.setNow_shape_index(now_index);
			}
		}
	}
	
	/**
	 * 绘制直线
	 */
	private void draw() {
		g.setColor(color);
		g.setStroke(new BasicStroke(thick));
		lineShape = new Line2D.Float(point.x, point.y, end_point.x, end_point.y);
		g.draw(lineShape);
	}
}
