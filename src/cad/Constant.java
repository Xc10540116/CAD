package cad;

import java.net.URL;

public class Constant {
	
	public static URL getImagePath(String filename) {
		return Constant.class.getResource("images/" + filename);
	}
}
