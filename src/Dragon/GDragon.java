package Dragon;

import javax.swing.*;

import AutoTest.GText;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GDragon extends JFrame implements MouseMotionListener, MouseListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2185678343896377699L;
	
	Point sp;
    int compStartHeight;
    int compStartWidth;
    int minHeight = 100;
    int minWidth = 100;
    
    public static int USED_WIDTH = 0;
    public static int USED_HEIGHT = 0;
    public static int WT = 666;
    public static int DX = 0;
    public static int DY = 0;
    public static String IMG = "./image/role_tm.png";
    public static String F_TYPE = "微软雅黑";
    public static int F_SIZE = 14; 
    public static Color F_COLOR = Color.BLUE;

   	/*
   	 * 
   	 * */
//	public static void main(String[] args) 
//	{
//		new GDragon("");
//	}
	
    
   	/*
   	 * 初始化提示界面
   	 * @param message 信息内容
   	 * @param mTime 持续时间
   	 * @param dX 提示位置横坐标
   	 * @param dY 提示位置纵坐标
   	 * @param img 提示图片 分辨率必须为240*360
   	 * @param fType 字体
   	 * @param fSize 字号
   	 * @param fColor 字色
   	 * */
    public GDragon(String message, int mTime, int dX, int dY, String img, String fType, int fSize, Color fColor)
    {
        super("testing frame");
        
    	if(mTime > 0) {
    		WT = mTime;
    	}
    	if(dX > 0) {
    		DX = dX;
    	}
    	if(dY > 0) {
    		DY = dY;
    	}
        if(img != null && !img.equals("")) {
        	IMG = img;
        }else {
            Random random = new Random();
            int indexIMG = random.nextInt(3);
            switch(indexIMG) {
            	case 0:{
            		IMG = "./image/role_tm.png";
            		break;
            	}
            	case 1:{
            		IMG = "./image/role_tm1.png";
            		break;
            	}
            	case 2:{
            		IMG = "./image/role_tm2.png";
            		break;
            	}
            	default:{
            		IMG = "./image/role_tm.png";
            		break;
            	}
            }
        }
        if(fType != null && !fType.equals("")) {
        	F_TYPE = fType;
        }
        if(fSize > 0) {
        	F_SIZE = fSize;
        }
        if(fColor != null) {
        	F_COLOR = fColor;
        }


        //加载图片并计算尺寸
        int dW = getImgWidth(IMG);
        int dH = getImgHeight(IMG);
        addFrameWidth(IMG);
        addFrameWidth(IMG);
        addFrameHeight(IMG);
        addFrameHeight("./image/close_tm.png");
        setSize(dW*3, 25 + dH);
        if(DX > 0 && DY > 0) {
        	setLocation(DX, DY);
        }else {
        	setLocation(this.getToolkit().getScreenSize().width - USED_WIDTH - 100, this.getToolkit().getScreenSize().height - USED_HEIGHT - 100);
        }
        
        //设置背景色
        setBackground(Color.black);
        
        //去掉标题栏
        setUndecorated(true);
         
        //添加鼠标事件监听
        addMouseMotionListener(this);
        addMouseListener(this);

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

  	    //配置关闭按钮
        JImageButton jBtn_Close = new JImageButton(new ImageIcon("./image/close_tm.png"));
 	    jBtn_Close.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				System.out.println("点击了CLOSE");
 				dispose();
 			}
        });
 	    
 	    //配置确定按钮
        JImageButton jBtn_Yes = new JImageButton (new ImageIcon("./image/ok_tm.png"));
        jBtn_Yes.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				System.out.println("点击了YES");
 			}
        });
        
        //配置取消按钮
        JImageButton jBtn_No = new JImageButton (new ImageIcon("./image/cancel_tm.png"));
        jBtn_No.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				System.out.println("点击了NO");
 			}
        });

        //用于填充
        JLabel jL_temp1 = new JLabel(GText.getRandomStringByLength(dW/4 + 7, " "));
        JLabel jL_temp2 = new JLabel(GText.getRandomStringByLength(dW/4 + 7, " "));
        
        //将所有组件添加到面板容器
        //设置面板容器排版方式:0代表左对齐，1，居中对齐， 2，代表右对齐，后面两个是左右、上下的距离
        JPanel msg = new JPanel();
        msg.setPreferredSize(new Dimension(dW, dH));
        msg.setLayout(new BorderLayout(0, 0));
        msg.add(jL_Title, BorderLayout.NORTH);
        msg.add(jL_MSG, BorderLayout.CENTER);
        msg.setBackground(new Color(0,0,0,0));
        
        JPanel btn = new JPanel();
        btn.setSize(dW*3, 25);
        btn.setLayout(new FlowLayout(0,0,0));
        btn.add(jBtn_Yes);
        btn.add(jL_temp1);
        btn.add(jBtn_No);
        btn.add(jL_temp2);
        btn.add(jBtn_Close);
        btn.setBackground(new Color(0,0,0,0));

        setLayout(new BorderLayout(0, 0));
        add(msg, BorderLayout.WEST);
        add(jL_Role, BorderLayout.CENTER);
      	add(btn, BorderLayout.SOUTH);
        
        //设置窗口总在上面
        setAlwaysOnTop(true);
        
        //设置关闭事件效果为技术进程
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //设置窗体为透明窗体
        setBackground(new Color(192,192,192,100));
        
        //显示窗体
        System.out.println("提示窗-正常开启");
        setVisible(true);
        
        try {
        	Thread.sleep(WT);
        	System.out.println("提示窗-正常关闭");
    		dispose();
    		USED_WIDTH = 0;
            USED_HEIGHT = 0;
        }catch(Exception e) {
        	System.out.println("提示窗-异常关闭");
        	dispose();
        	e.printStackTrace();
        }        
    }
    
    public void mouseMoved(MouseEvent e) 
    {
         Point p = e.getPoint(); 

         if (p.y > e.getComponent().getSize().height - 5)
         {
              setCursor( Cursor.getPredefinedCursor( Cursor.N_RESIZE_CURSOR )); 
         }
         else  if((p.x > e.getComponent().getSize().width - 5))
         {
              setCursor( Cursor.getPredefinedCursor( Cursor.W_RESIZE_CURSOR)); 
         }       
    }

    public void mouseDragged(MouseEvent e)
    {          
         Point p = e.getPoint();
         int compWidth = getSize().width;
         int compHight = getSize().height;
         if (getCursor().getType() == Cursor.N_RESIZE_CURSOR)
         {
              int nextHeight = compStartHeight+p.y-sp.y;
              if (nextHeight > minHeight)
              {
                   setSize(compWidth,nextHeight);
                   setVisible(true);     
              }
              

         }else if(getCursor().getType() == Cursor.W_RESIZE_CURSOR){
       	  int nextWidth = compStartWidth+p.x-sp.x;
             if (nextWidth > minWidth)
             {
                  setSize(nextWidth,compHight);
                  setVisible(true);     
             }
         }
         else
         {
              int x = getX()+p.x-sp.x;     
              int y = getY()+p.y-sp.y;     
              setLocation(x,y); 
         }

    }    


    public void mousePressed(MouseEvent e) 
    {
         sp = e.getPoint(); 
         compStartHeight = getSize().height;
         compStartWidth=getSize().width;
    }

    public void mouseEntered(MouseEvent e) 
    {
   	 	
    }
    
    public void mouseExited(MouseEvent e) 
    {
         if (sp == null)
         {
              setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR)); 
         }

    }

   	public void mouseClicked(MouseEvent e) 
    {
   	
    }
   
   	public void mouseReleased(MouseEvent e) 
    {
         sp = null;     
    }
   	
    private int getImgWidth(String strImgPath) {
    	MediaTracker mt=new MediaTracker(this);
    	Image img = Toolkit.getDefaultToolkit().createImage(strImgPath);
	    mt.addImage(img, 0);
	    try {
	      mt.waitForAll();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    return img.getWidth(null); 
    }
    
    private int getImgHeight(String strImgPath) {
    	MediaTracker mt=new MediaTracker(this);
    	Image img = Toolkit.getDefaultToolkit().createImage(strImgPath);
	    mt.addImage(img, 0);
	    try {
	      mt.waitForAll();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    return img.getHeight(null);
    }
    
    private void addFrameWidth(String strImgPath) {
    	MediaTracker mt=new MediaTracker(this);
    	Image img = Toolkit.getDefaultToolkit().createImage(strImgPath);
	    mt.addImage(img, 0);
	    try {
	      mt.waitForAll();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
        int addW = img.getWidth(null);
	    USED_WIDTH += addW;
    }
    
    private void addFrameHeight(String strImgPath) {
    	MediaTracker mt=new MediaTracker(this);
    	Image img = Toolkit.getDefaultToolkit().createImage(strImgPath);
	    mt.addImage(img, 0);
	    try {
	      mt.waitForAll();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
        int addH = img.getHeight(null);
	    USED_HEIGHT += addH;
    }
}
