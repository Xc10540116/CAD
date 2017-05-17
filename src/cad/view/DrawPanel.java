package cad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import cad.model.BaseShape;

public class DrawPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 图形颜色
	private Color drawColor = Color.BLACK;
	// 线条粗细
	private Float thick = 1.0f;
	// 已绘图形列表
	private List<BaseShape> shapeList = new LinkedList<BaseShape>();
	
	private int now_shape_index = -1;
	
	/**
	 * 构造函数，设置面板的背景颜色和大小
	 */
	public DrawPanel() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(700, 460));
		addMouseListener(mouseListener);
		addKeyListener(keyListener);
//		setFocusable(true);
	}
	
	/**
	 * 面板重绘
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		for( BaseShape bShape : shapeList) {
			bShape.draw((Graphics2D)g);
		}
	}
	
	/**
	 * 新增图形
	 * @param shape 图形
	 */
	public void addshape(BaseShape shape) {
		shapeList.add(shape);
	}
	
	/**
	 * 获取图形列表
	 * @return 图形列表
	 * @see DrawPanel#setShapeList(List)
	 */
	public List<BaseShape> getShapeList() {
		return shapeList;
	}

	/**
	 * 将图形列表更新为指定的图形列表
	 * @param shapeList 指定的图形列表
	 * @see DrawPanel#getShapeList()
	 */
	public void setShapeList(List<BaseShape> shapeList) {
		this.shapeList = shapeList;
	}

	/**
	 * 获取当前的绘制颜色
	 * @return	当前的绘制颜色
	 * @see model.DrawPanel#setDrawColor(Color)
	 */
	public Color getDrawColor() {
		return drawColor;
	}

	/**
	 * 将面板中图形绘制的当前颜色替换为指定的颜色。
	 * 使后续图形新的绘制操作都使用此指定颜色。
	 * @param drawColor 新的绘制颜色
	 * @see model.DrawPanel#getDrawColor
	 */
	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	/**
	 * 获取当前的绘制粗细
	 * @return 当前的绘制粗细
	 * @see model.DrawPanel#setThick(Float)
	 */
	public Float getThick() {
		return thick;
	}

	/**
	 * 将面板中图形绘制的当前粗细替换为指定的粗细。
	 * 使后续图形新的绘制操作都使用此指定粗细。
	 * @param thick 新的绘制粗细
	 * @see model.DrawPanel#getThick
	 */
	public void setThick(Float thick) {
		this.thick = thick;
	}

	// TODO 当前选中的图形的索引
	public int getNow_shape_index() {
		return now_shape_index;
	}

	public void setNow_shape_index(int now_shape_index) {
		this.now_shape_index = now_shape_index;
	}
	
	/**
	 * TODO 鼠标监听
	 */
	MouseListener mouseListener = new MouseListener() {
		
		private int x1, y1, x2, y2, width, height;
		
		@Override
		public void mousePressed(MouseEvent e) {
			getShape(e);
			if(now_shape_index != -1) {
				x1 = e.getX();
				y1 = e.getY();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(now_shape_index != -1) {
				x2 = e.getX();
				y2 = e.getY();
				width = x2 - x1;
				height = y2 - y1;
				shapeList.get(now_shape_index).moveShape(width, height);
				repaint();
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
//			getShape(e);
			for( BaseShape shape : shapeList)
				System.out.println(shape.toString());
		}
		
		private void getShape(MouseEvent e) {
			if(!shapeList.isEmpty()) {
				Boolean isSelected = false;
				for(int i = shapeList.size() - 1; i >= 0; i--) {
					if(shapeList.get(i).contains(e.getX(), e.getY())) {
						now_shape_index = i;
						isSelected = true;
						break;
					}
				}
				if(!isSelected)
					now_shape_index = -1;
			}
			System.out.println("当前索引：" + now_shape_index);
		}
	};

	/**
	 * TODO 按键监听
	 */
	KeyListener keyListener = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int code = e.getKeyCode();
			
			if(now_shape_index != -1) {
				BaseShape shape = shapeList.get(now_shape_index);
				switch (code) {
				case 46:	// .
					shape.setThick(1);
					break;
				case 44:	// ,
					shape.setThick(-1);
					break;
				case 107:	// +
					shape.editShapeSize(1);
					break;
				case 109:	// -
					shape.editShapeSize(-1);
					break;
				case 82:	// r
					if(now_shape_index != -1) {
						shapeList.remove(now_shape_index);
						now_shape_index = -1;
					}
					break;
				}
				repaint();
			}
		}
	};

}
