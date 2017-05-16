package cad.model;

import java.awt.*;

/**
 * <code>BaseShape</code>类是所有图形文字的抽象基类，
 * 允许CAD程序绘制各种图形。
 * <p>
 * 一个<code>BaseShape</code>对象封装了CAD程序支持的
 * 基本渲染操作所需的状态信息。 该状态信息包括以下属性： 
 * <ul>
 * <li>绘制起点坐标
 * <li>当前图形的颜色。
 * <li>当前图形的粗细。
 * <li>起点与终点水平方向的距离
 * <li>起点与终点垂直方向的距离
 * </ul>
 * 用于改变大小操作的有关参数：
 * <ul>
 * <li>方程类型
 * <li>方程方向
 * <li>系数
 * <li>常量
 * </ul>
 * <p>
 * @author leaves
 *
 */
public abstract class BaseShape implements java.io.Serializable {

	/**
	 * CAD 1.1 serialVersionUID
	 */
	private static final long serialVersionUID = 3023202635680952583L;

	protected Point point = new Point();	// 绘制起点坐标
	protected Color color = Color.BLACK;	// 颜色
	protected float thick = 1.0f;			// 粗细
	protected float width = 0.0f;			// 起点与终点水平方向的距离
	protected float height = 0.0f;			// 起点与终点垂直方向的距离
	// 图片绘制起点和终点的两个点形成的一元二次方程y = k*x + b
	/**
	 * 二元一次方程类型
	 * <ul>
	 * <li> 0代表二元一次方程为y=kx+b
	 * <li> 1代表二元一次方程为x=?
	 * <li> -1代表二元一次方程为y=?
	 * </ul>
	 */
	protected int coordinate = 0;
	/**
	 * 二元一次方程方向
	 * <ul>
	 * <li> 0为正常情况下，即二元一次方程为y=kx+b
	 * <li> 1代表正方向
	 * <li> -1代表反方向
	 * </ul>
	 */
	protected int direction = 0;
	protected float k = 0.0f;	// 系数
	protected float b = 0.0f;	// 常量
	
	/**
	 * 构造并初始化一个基本图形
	 */
	public BaseShape() {
	}
	
	/**
	 * 用指定的绘制起点、颜色、粗细和长宽构造并初始化一个基本图形
	 * @param x 起点坐标x
	 * @param y 起点坐标y
	 * @param color 颜色
	 * @param thick 粗细
	 * @param width 宽
	 * @param height 长
	 */
	public BaseShape(int x, int y, Color color, float thick, float width, float height) {
		super();
		point.setLocation(x, y);
		this.color = color;
		this.thick = thick;
		this.width = width;
		this.height = height;
		setCoefficient();
	}
	
	/**
	 * 用指定的绘制起点(x,y)、绘制终点(x1,y1)、颜色和粗细构建并初始化一个基本图形
	 * @param x 绘制起点坐标x
	 * @param y 绘制起点坐标y
	 * @param x1 绘制终点坐标x1
	 * @param y1 绘制终点坐标y1
	 * @param color 颜色
	 * @param thick 粗细
	 */
	public BaseShape(int x, int y, int x1, int y1, Color color, float thick) {
		super();
		point.setLocation(x, y);
		this.color = color;
		this.thick = thick;
		int w = x1 - x;
		int h = y1 - y;
		width = (float)w;
		height = (float)h;
		if(width < 0) {
			point.translate(w, 0);
			width = Math.abs(width);
		}
		if(height < 0) {
			point.translate(0, h);
			height = Math.abs(height);
		}
		setCoefficient();
	}

	/**
	 * 利用<code>Graphics</code>包含的各种函数来绘制形状
	 * @param graphics
	 * @see java.awt.Graphics2D
	 */
	public abstract void draw(Graphics2D graphics);
	
	/**
	 * 判断点(x,y)是否在图形范围内
	 * @param x 点x坐标
	 * @param y 点y坐标
	 * @return  true  点在图形范围内<br>
	 * 			false 点在图形范围外
	 */
	public abstract Boolean contains(int x, int y);
	
	/**
	 * 修改图形的大小
	 * @param len 改变的值 - len为正，则图形增大；为负，则图形变小
	 */
	public void editShapeSize(int len) {
		if((width <= 1 || height <= 1) && len < 0) {
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
	
	/**
	 * 移动图形
	 * @param width 位移宽度
	 * @param height 位移高度
	 */
	public void moveShape(int width, int height) {
		point.translate(width, height);
	}
	
	/**
	 * 将图形的一元二次方程类型、方向、系数和常量设置为指定的系数和常量。<br>
	 * 使此图形的后续图形操作都使用此指定的类型、方向、系数和常量。
	 */
	private void setCoefficient() {
		if(width == 0) {
			coordinate = 1;
			direction = height > 0 ? 1 : -1;
		} else if( height == 0 ) {
			coordinate = -1;
			direction = width > 0 ? 1 : -1;
		} else {
			k = height / width;
			b = point.y - k * point.x;
		}
		System.out.println("width:" + width
				+ ", height:" + height
				+ ", coordinate:" + coordinate 
				+ ", direction:" + direction);
	}

	/**
	 * 获取当前图形绘制的起始点
	 * @return 当前图形绘制的起始点
	 * @see BaseShape#setPoint(int, int)
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * 设置当前图形绘制的起始点
	 * @param x 坐标点x
	 * @param y 坐标点y
	 * @see BaseShape#getPoint()
	 */
	public void setPoint(int x, int y) {
		point.x = x;
		point.y = y;
	}
	
	/**
	 * 设置图形的粗细。
	 * <br>
	 * 图形的粗细最小值为1.0f
	 * @param len 改变量
	 */
	public void setThick(int len) {
		thick = thick + len < 1 ? 1.0f : thick + len;
	}
}
