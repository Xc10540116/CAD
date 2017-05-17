package cad.controller;

import java.awt.Font;
import java.awt.event.MouseEvent;

import cad.model.Draw;

public class DrawEllipse extends Draw {


	private void draw(MouseEvent e) {
		System.out.println(point);
		int w = e.getX() - point.x;
		int h = e.getY() - point.y;
		width = width + Math.abs(w);
		height = height + Math.abs(h);
		point.x = w > 0 ? point.x : point.x + w;
		point.y = h > 0 ? point.y : point.y + h;
		g.setColor(color);
	}
}
