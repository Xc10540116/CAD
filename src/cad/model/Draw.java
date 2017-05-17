package cad.model;

import java.awt.*;
import java.awt.event.*;

import cad.view.DrawPanel;

public abstract class Draw extends MouseAdapter {

	protected Point point = new Point();	// 绘制起点坐标
	protected Color color = Color.BLACK;	// 颜色
	protected float thick = 1.0f;			// 粗细
	protected float width = 0.0f;			// 起点与终点水平方向的距离
	protected float height = 0.0f;			// 起点与终点垂直方向的距离
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
		this.color = panel.getDrawColor();
		this.thick = panel.getThick();
	}
	
	/**
	 * 鼠标按下时，将按下的点赋值给起点
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if(panel.getNow_shape_index() == -1) {
			point.x = e.getX();
			point.y = e.getY();
		}
	}

}
