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
 * <li>绘制终点坐标
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

	private static final long serialVersionUID = 3023202635680952583L;

	protected Point b_point = new Point();	// 绘制起点坐标
	protected Point e_point = new Point();	// 绘制终点坐标
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
	 * 用指定的颜色和粗细构造并初始化一个基本图形
	 * @param color 颜色
	 * @param thick 粗细
	 */
	public BaseShape(Color color, Float thick) {
		this.color = color;
		this.thick = thick;
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
	public abstract void editShapeSize(int len);
	
	/**
	 * 移动图形
	 * @param width 位移宽度
	 * @param height 位移高度
	 */
	public abstract void moveShape(int width, int height);
	
	public abstract void removeShape();
	
	/**
	 * 将图形的一元二次方程系数和常量设置为指定的系数和常量。
	 * 使此图形的后续图形操作都使用此指定的系数和常量。
	 */
	public void setCoefficient() {
		width = e_point.x - b_point.x;
		height = e_point.y - b_point.y;
		if(width == 0) {
			coordinate = 1;
			direction = height > 0 ? 1 : -1;
		} else if( height == 0 ) {
			coordinate = -1;
			direction = width > 0 ? 1 : -1;
		} else {
			k = height / width;
			b = b_point.y - k * b_point.x;
		}
		System.out.println("width:" + width
				+ ", height:" + height
				+ ", coordinate:" + coordinate 
				+ ", direction:" + direction);
	}
}
