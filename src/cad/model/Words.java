package cad.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Words extends BaseShape {

	private static final long serialVersionUID = 4837158220385259786L;

	private String str;	// 文字内容
	
	public Words(int x, int y, String str, Color color, float thick, float width, float height) {
		super(x, y, color, thick, width, height);
		this.str = str;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		int fontsize = (int)height;
		g.setFont(new Font(null , Font.PLAIN, fontsize));
		g.drawString(str, point.x, point.y);
	}

	@Override
	public Boolean contains(int x, int y) {
		Rectangle2D rectange = new Rectangle2D.Double(point.x, point.y - height, width, height);
		return rectange.contains(x, y);
	}
	
	/**
	 * toString方法复写了默认的toString方法，
	 * 显示调用的类，内存地址，文字的基本信息（包含了
	 * 绘制起点坐标、椭圆宽度、高度、颜色）。
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = super.toString() + "\t";
		s += "文字绘制起点坐标" + point +  ",宽度：" + width;
		s += ",高度：" + height + ",颜色: " + color;
		s += ",coordinate：" + coordinate + ",direction: " + direction;
		s += ",k=" + k + ",b=" + b;
		return s;
	}

}
