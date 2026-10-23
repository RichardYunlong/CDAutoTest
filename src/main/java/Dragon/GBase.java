package Dragon;

import DT.GLog;
import Sys.GPath;

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
	 * 鼠标移动时切换光标形状，提示"可调整窗口大小"
	 *
	 * <p>靠近右边界显示水平调整光标，靠近下边界显示垂直调整光标。</p>
	 *
	 * @param e 鼠标事件，用于取得光标坐标与触发组件尺寸
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
	 * 鼠标拖拽时按光标类型调整窗口尺寸
	 *
	 * <p>依据 {@link #mousePressed(MouseEvent)} 记录的起始尺寸与按下点，
	 * 计算新的宽高并重设窗口大小，实现无边框窗口的拖拽缩放。</p>
	 *
	 * @param e 鼠标事件，用于取得当前光标坐标
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
	 * 鼠标按下时记录起始状态
	 *
	 * <p>保存按下点坐标与当前窗口宽高，供 {@link #mouseDragged(MouseEvent)} 计算缩放量。</p>
	 *
	 * @param e 鼠标事件，用于取得按下点坐标
	 */
    public void mousePressed(MouseEvent e) 
    {
         sp = e.getPoint(); 
         compStartHeight = getSize().height;
         compStartWidth=getSize().width;
    }

    /**
	 * 鼠标进入组件
	 *
	 * <p>当前为空实现，保留以完整实现 {@code MouseListener} 接口。</p>
	 *
	 * @param e 鼠标事件
	 */
    public void mouseEntered(MouseEvent e) 
    {
   	 	
    }
    
    /**
	 * 鼠标离开组件时恢复默认光标
	 *
	 * <p>仅在未处于拖拽状态（{@code sp} 为 {@code null}）时恢复，
	 * 避免缩放过程中光标闪烁。</p>
	 *
	 * @param e 鼠标事件
	 */
    public void mouseExited(MouseEvent e) 
    {
         if (sp == null)
         {
              setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR)); 
         }

    }

    /**
	 * 鼠标点击
	 *
	 * <p>当前为空实现，保留以完整实现 {@code MouseListener} 接口。</p>
	 *
	 * @param e 鼠标事件
	 */
   	public void mouseClicked(MouseEvent e) 
    {
   	
    }
   
    /**
	 * 鼠标释放时清除拖拽状态
	 *
	 * @param e 鼠标事件
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
        jBtn_Close = new JImageButton(new ImageIcon(GPath.IMAGE_TEMP_PATH + "close_tm.png"));
 	    jBtn_Close.addActionListener(e -> {
             GLog.logRecord(8, "点击了CLOSE");
             dispose();
         });
 	    
 	    //配置确定按钮
        jBtn_Yes = new JImageButton(new ImageIcon(GPath.IMAGE_TEMP_PATH + "ok_tm.png"));
        jBtn_Yes.addActionListener(e -> {
            GLog.logRecord(8, "点击了YES");
            dispose();
        });
        
        //配置取消按钮
        jBtn_No = new JImageButton(new ImageIcon(GPath.IMAGE_TEMP_PATH + "cancel_tm.png"));
        jBtn_No.addActionListener(e -> {
            GLog.logRecord(8, "点击了NO");
            dispose();
        });
    }
}
