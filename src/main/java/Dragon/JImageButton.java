package main.java.Dragon;

import javax.swing.*;
import java.awt.*;
 
public class JImageButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 970075067856858978L;

	public JImageButton(ImageIcon icon){
		setSize(icon.getImage().getWidth(null),
				icon.getImage().getHeight(null));
		setIcon(icon);
		setMargin(new Insets(0,0,0,0));//将边框外的上下左右的空间设置为0
		setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
		setBorderPainted(false);//不打印边框
		setBorder(null);//除去边框
		setText(null);//除去按钮的默认名称
		setFocusPainted(false);//除去焦点的框
		setContentAreaFilled(false);//除去默认的背景填充
	}
}
