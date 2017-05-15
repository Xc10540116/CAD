package cad.util;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SwingConsole {
	public static void run(final JFrame f, final int width, final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 当执行关闭操作时要退出程序
				f.setSize(width, height);							// 设置窗体大小
				f.setVisible(true);									// 显示窗体
				f.setLocationRelativeTo(null);						// 设置窗口位于屏幕中央。
//				f.setResizable(false);								// 窗体大小不可改变
			}
		});
	}
}
