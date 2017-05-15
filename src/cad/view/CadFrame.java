package cad.view;

import javax.swing.JFrame;

public class CadFrame extends JFrame {

	private static final long serialVersionUID = 5039762865668927778L;
	
	/**
	 * 构造函数
	 */
	public CadFrame() {
		// 设置JFrame标题
		setTitle(getClass().getSimpleName());
		// 初始化程序显示页面
		init();
	}
	
	/**
	 * 构造函数
	 * @param title 标题
	 */
	public CadFrame(String title) {
		// 设置JFrame标题
		setTitle(title);
		// 初始化程序显示页面
		init();
	}
	
	private void init() {
		
	}

}
