package cad.controller;

import java.awt.Point;

import cad.model.Draw;

public class DrawWords extends Draw {

	private Point begin_point = new Point();
	private Double width;
	private Double height;
	private String word;
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
