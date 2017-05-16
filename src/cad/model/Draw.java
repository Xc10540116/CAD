package cad.model;

import java.awt.*;
import java.awt.event.*;

import cad.view.DrawPanel;

public class Draw extends MouseAdapter {
	
	protected BaseShape shape;
	protected Graphics2D g;
	protected DrawPanel panel;
	
	/**
	 * 在画板中加入鼠标监听
	 * @param panel 画板
	 */
	public void addListener(DrawPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
	/**
	 * 从画板中移除鼠标监听
	 * @param panel 画板
	 */
	public void removeListener(DrawPanel panel) {
		panel.removeMouseListener(this);
		panel.removeMouseMotionListener(this);
	}
	
	/**
	 * 从画板中获取一些信息
	 * @param panel 画板
	 */
	public void setDrawPanel(DrawPanel panel) {
		this.panel = panel;
		panel.setVisible(true);
		this.g = (Graphics2D)panel.getGraphics();
		shape.color = panel.getDrawColor();
		shape.thick = panel.getThick();
	}
	
	/**
	 * 鼠标按下时，将按下的点赋值给起点
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if(panel.getNow_shape_index() == -1) {
			shape.point.x = e.getX();
			shape.point.y = e.getY();
		}
	}

}
