package cad.controller;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;

import cad.model.Draw;
import cad.model.Words;

public class DrawWords extends Draw {

	private String str;
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(panel.getNow_shape_index() == -1) {
			super.mouseReleased(e);
			if(!point.equals(new Point(e.getX(),e.getY()))) {
				draw(e);
				Words words = new Words(point.x, point.y, str, color, thick, width, height);
				panel.addshape(words);
				System.out.println(words.toString());
				int now_index = panel.getShapeList().size() - 1;
				panel.setNow_shape_index(now_index);
				panel.removeMouseListener(this);
			}
		}
	}

	private void draw(MouseEvent e) {
		System.out.println(point);
		int w = e.getX() - point.x;
		int h = e.getY() - point.y;
		width = width + Math.abs(w);
		height = height + Math.abs(h);
		point.x = w > 0 ? point.x : point.x + w;
		point.y = h > 0 ? point.y + h : point.y;
		g.setColor(color);
		int fontsize = (int)height;
		g.setFont(new Font(null , Font.PLAIN, fontsize));
		g.drawString(str, point.x, point.y);
		System.out.println(proportion(str));
	}
	
	private float proportion(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		String term = str.replaceAll(regEx, "aa");
        float result = term.length() / 2.0f;
        return result;
	}
}
