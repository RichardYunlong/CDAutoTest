package main.java.Dragon;

import main.java.DT.GLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GBase extends JFrame implements MouseMotionListener, MouseListener{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7502511063783928888L;

	/**
	 * 指针
	 */
	Point sp;
	
    /**
	 * 实际竖直高度
	 */
	protected int compStartHeight;
    
    /**
	 * 实际水平宽度
	 */
	protected int compStartWidth;
	
    /**
	 * 关闭按钮
	 */
	protected JImageButton jBtn_Close;
	
    /**
	 * 确定按钮
	 */
	protected JImageButton jBtn_Yes;
	
    /**
	 * 取消按钮
	 */
	protected JImageButton jBtn_No;
	
    
    /**
	 * 鼠标拖动效果
	 */
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

    /**
	 * 鼠标拉伸效果
	 */
    public void mouseDragged(MouseEvent e)
    {          
         Point p = e.getPoint();
         int compWidth = getSize().width;
         int compHight = getSize().height;
         if (getCursor().getType() == Cursor.N_RESIZE_CURSOR)
         {
              int nextHeight = compStartHeight+p.y-sp.y;
              if (nextHeight > 100)
              {
                   setSize(compWidth,nextHeight);
                   setVisible(true);     
              }
              

         }else if(getCursor().getType() == Cursor.W_RESIZE_CURSOR){
       	  int nextWidth = compStartWidth+p.x-sp.x;
             if (nextWidth > 100)
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
	
    /**
	 * 鼠标点住效果
	 */
    public void mousePressed(MouseEvent e) 
    {
         sp = e.getPoint(); 
         compStartHeight = getSize().height;
         compStartWidth=getSize().width;
    }

    /**
	 * 鼠标悬浮效果
	 */
    public void mouseEntered(MouseEvent e) 
    {
   	 	
    }
    
    /**
	 * 鼠标离开效果
	 */
    public void mouseExited(MouseEvent e) 
    {
         if (sp == null)
         {
              setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR)); 
         }

    }

    /**
	 * 鼠标点击效果
	 */
   	public void mouseClicked(MouseEvent e) 
    {
   	
    }
   
    /**
	 * 鼠标释放效果
	 */
   	public void mouseReleased(MouseEvent e) 
    {
         sp = null;     
    }
   	
   	public GBase(){
   		super("testing frame");
   	}
   	
    /**
	 * 鼠标点击效果
	 */
   	protected void add3Button() 
    {
  	    //配置关闭按钮
        jBtn_Close = new JImageButton(new ImageIcon("./image/close_tm.png"));
 	    jBtn_Close.addActionListener(e -> {
             GLog.logRecord(8, "点击了CLOSE");
             dispose();
         });
 	    
 	    //配置确定按钮
        jBtn_Yes = new JImageButton(new ImageIcon("./image/ok_tm.png"));
        jBtn_Yes.addActionListener(e -> {
            GLog.logRecord(8, "点击了YES");
            dispose();
        });
        
        //配置取消按钮
        jBtn_No = new JImageButton(new ImageIcon("./image/cancel_tm.png"));
        jBtn_No.addActionListener(e -> {
            GLog.logRecord(8, "点击了NO");
            dispose();
        });
    }
}
