package cad.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Line extends BaseShape {

	private static final long serialVersionUID = -2028648010081272032L;
	
	private Point e_point = new Point();	// 绘制终点
	
	public Line(int x, int y, int x1, int y1, Color color, float thick) {
		super(x, y, color, thick, x1 - x, y1 - y);
		e_point.setLocation(x1, y1);
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(thick));
		graphics.drawLine(point.x, point.y, e_point.x, e_point.y);
	}

	// TODO 点(x,y)是否在直线中
	/**
	 * 判断点(x,y)是否在此直线的范围内
	 * @return  true  点在直线范围内<br>
	 * 			false 点在直线范围外
	 */
	@Override
	public Boolean contains(int x, int y) {
		// 生成直线对象
		Line2D line = new Line2D.Float(point, e_point);
		// 判断点到直线的距离
		Double len2 = line.ptSegDist(x, y);
		// 设置允许点击的距离
		Double inline = 3.0d + thick;
		// 如果点到线的距离 小于 允许点击的距离
		if(len2 < inline)
			// 点在线上
			return true;
		else
			// 点不在线上
			return false;
	}

	/**
	 * 修改直线的长短
	 * @param len 直线长短改变的值 - len为正，则直线变长；为负，则直线变短
	 */
	@Override
	public void editShapeSize(int len) {
		super.editShapeSize(len);
		if(coordinate == 1) {
			e_point.y = direction == 1 ? e_point.y + len : e_point.y - len;
		} else if( coordinate == -1 ) {
			e_point.x = direction == 1 ? e_point.x + len : e_point.x - len;
		} else {
			e_point.x = e_point.x + len;
			e_point.y = (int)Math.rint(k * e_point.x + b);
		}
	}

	@Override
	public void moveShape(int width, int height) {
		super.moveShape(width, height);
		e_point.translate(width, height);
	}

	/**
	 * 获取当前图形绘制的终点
	 * @return 当前图形绘制的终点
	 * @see BaseShape#setE_point(int, int)
	 */
	public Point getE_point() {
		return e_point;
	}

	/**
	 * 设置当前图形绘制的终点
	 * @param x 坐标点x
	 * @param y 坐标点y
	 * @see BaseShape#getE_point()
	 */
	public void setE_point(int x, int y) {
		e_point.x = x;
		e_point.y = y;
	}
	
	/**
	 * toString方法复写了默认的toString方法，
	 * 显示调用的类，内存地址，直线的基本信息（包含了
	 * 起点坐标、终点坐标、颜色和粗细）。
	 */
	@Override
	public String toString() {
		String s = super.toString() + "\t";
		s += "起点坐标" + point + ", 终点坐标" + e_point + ", ";
		s += "颜色: " + color + ", 粗细: " + thick;
		s += ",coordinate：" + coordinate + ",direction: " + direction;
		s += ",k=" + k + ",b=" + b;
		return s;
	}

}
