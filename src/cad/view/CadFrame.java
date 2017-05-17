package cad.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import cad.Constant;
import cad.controller.*;
import cad.model.BaseShape;
import cad.model.Draw;

public class CadFrame extends JFrame {

	private static final long serialVersionUID = 5039762865668927778L;
	
	private JPanel toolPanel = new JPanel();
	private JPanel filePanel;
	private JButton open = new JButton("打开", new ImageIcon(Constant.getImagePath("open.png")));
	private JButton save = new JButton("保存", new ImageIcon(Constant.getImagePath("open.png")));
	private JPanel shapePanel;
	private JPanel colorPanel;
	private DrawPanel drawPanel = new DrawPanel();
	
	private ShapeButton lineBtn = new ShapeButton("line","直线");
	private ShapeButton circle = new ShapeButton("Circle","圆");
	private ShapeButton rectangle = new ShapeButton("Rectangle","矩形");
	private ShapeButton words = new ShapeButton("words","文字");
	private JButton ShowColor = new JButton();

	private Map<String, Draw> drawList = new HashMap<String, Draw>();
	
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

	// TODO 初始化
	private void init() {
		// 设置面板主体
		Color bcolor = new Color(245, 246, 247);
		toolPanel.setBackground(bcolor);
		toolPanel.setPreferredSize(new Dimension(750, 100));
		toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		filePanel = filePanel(bcolor);
		shapePanel = shapePanel(bcolor);
		colorPanel = colorPanel(bcolor);

		toolPanel.add(filePanel);
		toolPanel.add(shapePanel);
		toolPanel.add(colorPanel);
		add(toolPanel, BorderLayout.NORTH);
		
		// 设置画板
		JPanel bg = new JPanel();
		bg.setBackground(Color.GRAY);
		bg.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		bg.add(drawPanel);
		getContentPane().add(bg);
		
		// 设置按键鼠标监听
		setFocusable(true);
		addKeyListener(drawPanel.keyListener);
	}
	
	/**
	 * 设置文件面板
	 * @param bcolor 面板背景色
	 * @return 文件面板
	 */
	private JPanel filePanel(Color bcolor) {
		// TODO 设置文件面板 filePanel
		JPanel filePanel = new JPanel();
		filePanel.setBorder(new TitledBorder("文件"));
		filePanel.setPreferredSize(new Dimension(230, 80));
		filePanel.setBackground(bcolor);
		
		open.setPreferredSize(new Dimension(100, 50));
		save.setPreferredSize(new Dimension(100, 50));
		filePanel.add(open);
		filePanel.add(save);
		
		return filePanel;
	}

	/**
	 * 设置图形选择面板
	 * @param bcolor 面板背景色
	 * @return 图形选择面板
	 */
	private JPanel shapePanel(Color bcolor) {
		// TODO 设置图形选择面板 shapePanel
		JPanel shapePanel = new JPanel();
		shapePanel.setBorder(new TitledBorder("形状"));
		shapePanel.setPreferredSize(new Dimension(250, 80));
		shapePanel.setBackground(bcolor);
		
		drawList.put("line", new DrawLine());
		drawList.put("ellipse", new DrawEllipse());
		drawList.put("rectangle", new DrawRectangle());
		drawList.put("words", new DrawWords());
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(lineBtn);
		bg.add(circle);
		bg.add(rectangle);
		bg.add(words);
		shapePanel.add(lineBtn);
		shapePanel.add(circle);
		shapePanel.add(rectangle);
		shapePanel.add(words);
		
		fileActionListener();
		btnActionListener();
		
		return shapePanel;
	}
	
	/**
	 * 文件保存打开按钮监听
	 */
	private void fileActionListener() {
		// TODO 文件保存打开按钮监听
		JFileChooser openCad = new JFileChooser();
		openCad.setFileFilter(new FileNameExtensionFilter("CAD 文件 (*.cad,*.CAD)", "CAD", "cad"));
		FileSystemView fsv = FileSystemView.getFileSystemView();
		openCad.setCurrentDirectory(fsv.getHomeDirectory());
		
		open.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int rVal = openCad.showOpenDialog(CadFrame.this);
				if(rVal == JFileChooser.APPROVE_OPTION) {
//					System.out.println(openCad.getSelectedFile().getAbsolutePath());
					String addr = openCad.getSelectedFile().getAbsolutePath();
					try {
						FileInputStream freader = new FileInputStream(addr);
						ObjectInputStream objectInputStream = new ObjectInputStream(freader);
						List<BaseShape> shapes = new ArrayList<BaseShape>();
						shapes = (List<BaseShape>)objectInputStream.readObject();
						drawPanel.setShapeList(shapes);
						drawPanel.repaint();
						objectInputStream.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int rVal = openCad.showSaveDialog(CadFrame.this);
				if(rVal == JFileChooser.APPROVE_OPTION) {
//					System.out.println(openCad.getSelectedFile().getAbsolutePath());
					//获得文件名
					File file = openCad.getSelectedFile();
					// 获得被选中的过滤器
					FileNameExtensionFilter filter = (FileNameExtensionFilter)openCad.getFileFilter();
					// 获得过滤器的扩展名
					String ends = filter.getExtensions()[0];
					File newFile = null;
					if( file.getAbsolutePath().toUpperCase().endsWith(ends.toUpperCase())) {
						newFile = file;
					}
					else
						newFile = new File(file.getAbsolutePath() + "." + ends);
					// 如果文件存在，则提示是否覆盖
					if(newFile.exists()) {
						int overwriteSelect = JOptionPane.showConfirmDialog(CadFrame.this, "<html><font size=3>文件"
						+ newFile.getName() + "已经存在，是否覆盖？</font></html>", "是否覆盖？", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if(overwriteSelect != JOptionPane.YES_OPTION)
							return;
					}
					
					try {
						FileOutputStream outputStream = new FileOutputStream(newFile);
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(drawPanel.getShapeList());
						outputStream.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 绘图按钮点击监听
	 */
	private void btnActionListener() {
		// TODO 绘图按钮点击监听
		lineBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeListener();
				drawList.get("line").addListener(drawPanel);
				drawList.get("line").setDrawPanel(drawPanel);
				drawPanel.setFocusable(true);
			}
		});
		circle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeListener();
				drawList.get("ellipse").addListener(drawPanel);
				drawList.get("ellipse").setDrawPanel(drawPanel);
				drawPanel.setFocusable(true);
			}
		});
		rectangle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeListener();
				drawList.get("rectangle").addListener(drawPanel);
				drawList.get("rectangle").setDrawPanel(drawPanel);
				drawPanel.setFocusable(true);
			}
		});
		words.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeListener();
				drawList.get("words").addListener(drawPanel);
				drawList.get("words").setDrawPanel(drawPanel);
				drawPanel.setFocusable(true);
				String val = JOptionPane.showInputDialog(
			            "Please input a value?");
				((DrawWords)drawList.get("words")).setStr(val);
			}
		});
	}
	
	/**
	 * 移除所有鼠标监听
	 */
	private void removeListener() {
		// TODO 移除所有鼠标监听 removeListener
		for( Draw draw : drawList.values()) {
			draw.removeListener(drawPanel);
		}
	}
	
	/**
	 * 设置颜色选择面板
	 * @param bcolor
	 * @return
	 */
	private JPanel colorPanel(Color bcolor) {
		// TODO 设置颜色选择面板 colorPanel
		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(new TitledBorder("颜色"));
		colorPanel.setPreferredSize(new Dimension(200, 80));
		colorPanel.setBackground(bcolor);
		colorPanel.setLayout(new FlowLayout());
		
		ShowColor.setBackground(Color.BLACK);
		ShowColor.setPreferredSize(new Dimension(50, 50));
		ShowColor.setToolTipText("绘图颜色，正在使用中");
		
		JPanel cPanel = new JPanel();
		cPanel.setBackground(bcolor);
		cPanel.setPreferredSize(new Dimension(130, 50));
		ColorButton black = new ColorButton(Color.BLACK, "黑色");
		ColorButton gray = new ColorButton(Color.GRAY, "灰色");
		ColorButton pink = new ColorButton(Color.PINK, "粉红色");
		ColorButton red = new ColorButton(Color.RED, "红色");
		ColorButton orange = new ColorButton(Color.ORANGE, "橙色");
		ColorButton yellow = new ColorButton(Color.YELLOW, "黄色");
		ColorButton green = new ColorButton(Color.GREEN, "绿色");
		ColorButton cyan = new ColorButton(Color.CYAN, "青绿色");
		ColorButton blue = new ColorButton(Color.BLUE, "蓝色");
		ColorButton white = new ColorButton(Color.WHITE, "白色");
		
		cPanel.add(black);
		cPanel.add(gray);
		cPanel.add(pink);
		cPanel.add(red);
		cPanel.add(orange);
		cPanel.add(yellow);
		cPanel.add(green);
		cPanel.add(cyan);
		cPanel.add(blue);
		cPanel.add(white);
		
		colorPanel.add(ShowColor);
		colorPanel.add(cPanel);
		
		return colorPanel;
	}
	
	
	/**
	 * 设置颜色按钮
	 * @author leaves
	 *
	 */
	private class ColorButton extends JButton {
		// TODO 设置颜色按钮 ColorButton
		private static final long serialVersionUID = 1L;

		public ColorButton(Color color, String tipText) {
			setToolTipText(tipText);
			setBackground(color);
			setPreferredSize(new Dimension(20, 20));
			addKeyListener(drawPanel.keyListener);
			addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton but = (JButton) e.getSource();	// 获取当前颜色按钮
					Color color = but.getBackground();		// 获取当前颜色按钮的颜色
					ShowColor.setBackground(color);			// 颜色显示按钮的颜色改为当前颜色按钮的颜色
					drawPanel.setDrawColor(color);			// 更新绘图面板的绘图颜色
					// 将更新后的画图面板放入绘图类中，更新绘图信息
					for( Draw draw : drawList.values()) {
						draw.setDrawPanel(drawPanel);
					}
					// 修改选中的图形的颜色
					BaseShape now_shape = drawPanel.getShapeList().get(drawPanel.getNow_shape_index());
					now_shape.setColor(color);
					drawPanel.repaint();
				}
			});
		}
	}
	
	/**
	 * 设置图形按钮
	 * @author leaves
	 *
	 */
	private class ShapeButton extends JToggleButton {
		// TODO 设置图形按钮 ShapeButton
		private static final long serialVersionUID = 1L;

		public ShapeButton(String img, String tipText) {
			setToolTipText(tipText);
			setPreferredSize(new Dimension(50, 50));
			setFont(new Font("MS UI Gothic", Font.PLAIN, 24));
			ImageIcon icon = new ImageIcon(Constant.getImagePath(img + ".png")); 
			setIcon(icon);
			addKeyListener(drawPanel.keyListener);
		}
	}

}
