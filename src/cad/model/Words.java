package cad.model;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Words extends BaseShape {

	private static final long serialVersionUID = 4837158220385259786L;

	private String str;	// 文字内容
	
	@Override
	public void setE_point(int x, int y) {
		// TODO Auto-generated method stub
		super.setE_point(x, y);
		int strlen = str.length();
		if(width < strlen * height) {
			height = width / strlen;
		}
		else
			width = height * strlen;
		setCoefficient(width, height);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		int fontsize = (int)height;
		setCoefficient(width, height);
		g.setFont(new Font(null , Font.PLAIN, fontsize));
		g.drawString(str, b_point.x, b_point.y);
	}

	@Override
	public Boolean contains(int x, int y) {
		Rectangle2D rectange = new Rectangle2D.Double(b_point.x, b_point.y, width, height);
		return rectange.contains(x, y);
	}

	@Override
	public void editShapeSize(int len) {
		if(height == 1 && len < 0) {
			System.out.println("已经最短了，再短就成点了！");
			return ;
		}
		// 如果方程类型为x=?
		if(coordinate == 1) {
			// 高度为 （方向为正方向 ？ 高度+改变的大小 ： 高度-改变的大小）
			height = direction == 1 ? height + len : height - len;
		} else if( coordinate == -1 ) {	// 如果方程类型为y=?
			// 宽度为 （方向为正方向 ？ 宽度+改变的大小 ： 宽度-改变的大小）
			width = direction == 1 ? width + len : width - len;
		} else {	// 方程类型为y=kx+b
			width = width + len;
			height = height + k * len;
		}
	}

	@Override
	public void moveShape(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeShape() {
		// TODO Auto-generated method stub

	}

}
