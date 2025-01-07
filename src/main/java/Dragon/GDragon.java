package main.java.Dragon;

import main.java.DT.GLog;
import main.java.Base.GText;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 智能助理提示窗
 */
public class GDragon extends GBase {
	
    /**
	 * 句柄编号
	 */
	private static final long serialVersionUID = -2185678343896377699L;

    /**
	 * 默认等待时间 单位ms
	 */
	private int WT = 666;
    
    /**
	 * 默认智能助理头像
	 */
	private String IMG = "./image/role_tm.png";
	
    /**
	 * 单位块
	 */
	private static final String IMG25 = "./image/empty25.png";
	
    /**
	 * 关闭按钮图片
	 */
	private static final String IMG_CLOSE = "./image/close_tm.png";
    
    /**
	 * 默认提示信息字体
	 */
	private String F_TYPE = "微软雅黑";
    
    /**
	 * 默认提示信息字号
	 */
	private int F_SIZE = 14; 
    
    /**
	 * 默认提示信息字色
	 */
	private Color F_COLOR = Color.BLUE;
	
    /**
	 * 箭头类型
	 * 0-左上
	 * 1-右上
	 * 2-左下
	 * 3-右下
	 */
	private int P_TYPE = 0;
	
   	/*
   	 * 设置时间 
	 *
   	 * @param mTime 持续时间
   	 * */
	private void setTime(int mTime) {
    	if(mTime > 0) {
    		WT = mTime;
    	}
	}
	
   	/*
   	 * 设置位置和尺寸 
	 *
   	 * @param mTime 持续时间
   	 * @param dX 提示位置横坐标
   	 * @param dY 提示位置纵坐标
   	 * */
	private void setLocationByLeftTop(int dX, int dY) {        
        //设置占用区域宽度和高度 宽度默认为4个头像宽度；高度默认为一个头像高度加功能按钮高度
        compStartWidth = getImgWidth(IMG) * 4 + 25 * 2;
        compStartHeight = getImgHeight(IMG_CLOSE) + getImgHeight(IMG);
        
        //初始化窗口大小
        setSize(compStartWidth, compStartHeight);
        
        //设置窗口左上角顶点位置
        if(dX > 0 && dY > 0) {
        	//如果起点横坐标加水平宽度大于屏幕横向宽度，则窗口左移显示
        	if(dX + compStartWidth > this.getToolkit().getScreenSize().width) {
        		dX = this.getToolkit().getScreenSize().width - compStartWidth - (this.getToolkit().getScreenSize().width - dX);
        		
            	//如果起点纵坐标加竖直高度大于屏幕纵向高度，则窗口上移显示
            	if(dY + compStartHeight > this.getToolkit().getScreenSize().height) {
            		dY = this.getToolkit().getScreenSize().height - compStartHeight - (this.getToolkit().getScreenSize().height - dY);
            		P_TYPE = 3;
            	}else {
            		P_TYPE = 1;
            	}
        	}else {
            	//如果起点纵坐标加竖直高度大于屏幕纵向高度，则窗口上移显示
            	if(dY + compStartHeight > this.getToolkit().getScreenSize().height) {
            		dY = this.getToolkit().getScreenSize().height - compStartHeight - (this.getToolkit().getScreenSize().height - dY);
            		P_TYPE = 2;	
            	}
        	}
        	
        	setLocation(dX, dY);
        }else {
        	//默认紧贴右下角摆放
        	setLocation(this.getToolkit().getScreenSize().width - compStartWidth, this.getToolkit().getScreenSize().height - compStartHeight);
        }
	}
	
   	/*
   	 * 设置字体 
	 *
   	 * @param fType 字体
   	 * @param fSize 字号
   	 * @param fColor 字色
   	 * */
	private void setFont(String fType, int fSize, Color fColor) {
        //准备字体类型
        if(fType != null && !fType.isEmpty()) {
        	F_TYPE = fType;
        }
        
        //准备字体大小
        if(fSize > 0) {
        	F_SIZE = fSize;
        }
        
        //准备字体颜色
        if(fColor != null) {
        	F_COLOR = fColor;
        }
	}
	
   	/*
   	 * 设置字体 
	 *
   	 * @param img 提示图片 分辨率必须为240*360
   	 * */
	private void setIcon(String img) {
    	//准备头像
        if(img != null && !img.isEmpty()) {
        	IMG = img;
        }else {
            Random random;
    		try {
    			random = SecureRandom.getInstanceStrong();
                int indexIMG = random.nextInt(3);
                switch(indexIMG) {
                    case 1:{
                        IMG = "./image/role_tm1.png";
                        break;
                    }
                    case 2:{
                        IMG = "./image/role_tm2.png";
                        break;
                    }
                    case 0:
                    default:{
                        IMG = "./image/role_tm.png";
                        break;
                    }
                }
            } catch (NoSuchAlgorithmException e) {
    			GLog.logSysFunctionException("setIcon", e);
    		}
        }
	}
    
   	/*
   	 * 初始化提示界面
   	 * 
   	 * @param message 信息内容
   	 * @param mTime 持续时间
   	 * @param dX 提示位置横坐标
   	 * @param dY 提示位置纵坐标
   	 * @param img 提示图片 分辨率必须为240*360
   	 * @param fType 字体
   	 * @param fSize 字号
   	 * @param fColor 字色
   	 * */
    @SuppressWarnings("MagicConstant")
    public GDragon(String message, int mTime, int dX, int dY, String img, String fType, int fSize, Color fColor)
    {
        setTime(mTime);
        setLocationByLeftTop(dX, dY);
        setFont(fType, fSize, fColor);
        setIcon(img);
        
        //配置箭头区
        JLabel jL_Lp = new JLabel();
        JLabel jL_Rp = new JLabel();
        Icon icon_Lp;
        Icon icon_Rp;
      	switch(P_TYPE){
	  		case 1:{
	  			icon_Lp = new ImageIcon(IMG25);
	  			icon_Rp = new ImageIcon("./image/rt.png");
	  			jL_Rp.setVerticalAlignment(JLabel.TOP);
	  			break;
	  		}
	  		case 2:{
	  			icon_Lp = new ImageIcon("./image/lb.png");
	  			icon_Rp = new ImageIcon(IMG25);
	  			jL_Lp.setVerticalAlignment(JLabel.BOTTOM);
	  			break;
	  		}
	  		case 3:{
	  			icon_Lp = new ImageIcon(IMG25);
	  			icon_Rp = new ImageIcon("./image/rb.png");
	  			jL_Rp.setVerticalAlignment(JLabel.BOTTOM);
	  			break;
	  		}
	  		default:{
	  			icon_Lp = new ImageIcon("./image/lt.png");
	  			icon_Rp = new ImageIcon(IMG25);
	  			jL_Lp.setVerticalAlignment(JLabel.TOP);
	  			break;
	  		}
	  	}
      	jL_Lp.setIcon(icon_Lp);
      	jL_Rp.setIcon(icon_Rp);
        
        //配置角色图片
        JLabel jL_Role = new JLabel();
        Icon icon_Role = new ImageIcon(IMG);
        jL_Role.setIcon(icon_Role);
        
        //配置消息标题
        JLabel jL_Title = new JLabel("");
        jL_Title.setFont(new Font(F_TYPE, Font.HANGING_BASELINE, F_SIZE));
        jL_Title.setForeground(Color.GREEN);
        jL_Title.setBackground(new Color(0,0,0,0));
  	    
  	    //配置消息内容
        JTextArea jL_MSG = new JTextArea(message);
        jL_MSG.setLineWrap(true);
        jL_MSG.setFont(new Font(F_TYPE, Font.HANGING_BASELINE, F_SIZE));
        jL_MSG.setForeground(F_COLOR);
        jL_MSG.setBackground(new Color(0,0,0,0));

  	    //添加功能按钮
        add3Button();
        
        //设置文字区排版方式:0-代表左对齐，1-居中对齐， 2-代表右对齐，后面两个是左右、上下的距离
        JPanel msg = new JPanel();
        msg.setPreferredSize(new Dimension(getImgWidth(IMG) * 3, getImgHeight(IMG)));
        msg.setLayout(new BorderLayout(0, 0));
        msg.add(jL_Title, BorderLayout.NORTH);
        msg.add(jL_MSG, BorderLayout.CENTER);
        msg.setBackground(new Color(0,0,0,0));
        
        //设置功能按钮区域
        JPanel btn = new JPanel();
        //用于填充
        JLabel jL_temp1 = new JLabel(GText.getRandomStringByLength(((compStartWidth - 25 * 2) / 4 * 3 - 25 * 2) / 3, " "));
        JLabel jL_temp2 = new JLabel(GText.getRandomStringByLength(((compStartWidth - 25 * 2) / 4 - 25) / 3, " "));
        btn.setSize(compStartWidth - 25 * 2, getImgHeight(IMG_CLOSE));
        btn.setLayout(new FlowLayout(0,0,0));
        btn.add(jBtn_Yes);
        btn.add(jL_temp1);
        btn.add(jBtn_No);
        btn.add(jL_temp2);
        btn.add(jBtn_Close);
        btn.setBackground(new Color(0,0,0,0));
        
        //设置文字及头像区
        JPanel msgAndRole = new JPanel();
        msgAndRole.setLayout(new BorderLayout(0, 0));
        msgAndRole.add(msg, BorderLayout.WEST);
        msgAndRole.add(jL_Role, BorderLayout.EAST);
        msgAndRole.add(btn, BorderLayout.SOUTH);
        msgAndRole.setBackground(new Color(0,0,0,0));

        //装配整个区域
        setLayout(new BorderLayout(0, 0));
        add(jL_Lp, BorderLayout.WEST);
        add(msgAndRole, BorderLayout.CENTER);
      	add(jL_Rp, BorderLayout.EAST);
      	
        //设置背景色
        setBackground(Color.black);
        
        //去掉标题栏
        setUndecorated(true);
         
        //添加鼠标事件监听
        addMouseMotionListener(this);
        addMouseListener(this);
        
        //设置窗口总在上面
        setAlwaysOnTop(true);
        
        //设置关闭事件效果为技术进程
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //设置窗体为透明窗体
        setBackground(new Color(192,192,192,100));
        
        //显示窗体
        //GLog.logRecord(8, "提示窗-正常开启");
        setVisible(true);
        
        try {
        	Thread.sleep(WT);
        	//GLog.logRecord(8, "提示窗-正常关闭");
    		dispose();
    		compStartWidth = 0;
    		compStartHeight = 0;
        }catch(Exception e) {
        	GLog.logRecord(8, "提示窗-异常关闭");
        	dispose();
        	GLog.logSysFunctionException("GDragon", e);
        }        
    }
   	
    /**
	 * 获得图片水平宽度
	 */
    private int getImgWidth(String strImgPath) {
    	MediaTracker mt=new MediaTracker(this);
    	Image img = Toolkit.getDefaultToolkit().createImage(strImgPath);
	    mt.addImage(img, 0);
	    try {
	      mt.waitForAll();
	    } catch (Exception e) {
	    	GLog.logSysFunctionException("getImgWidth", e);
	    }
	    return img.getWidth(null); 
    }
    
    /**
	 * 获得图片竖直高度
	 */
    private int getImgHeight(String strImgPath) {
    	MediaTracker mt=new MediaTracker(this);
    	Image img = Toolkit.getDefaultToolkit().createImage(strImgPath);
	    mt.addImage(img, 0);
	    try {
	      mt.waitForAll();
	    } catch (Exception e) {
	      GLog.logSysFunctionException("getImgHeight", e);
	    }
	    return img.getHeight(null);
    }
}
