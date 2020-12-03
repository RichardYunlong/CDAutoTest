package Dragon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import AutoTest.GMsg;
import AutoTest.GSys;

public class GDragonII extends JFrame implements MouseMotionListener, MouseListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1366934872088790043L;
	
		Point sp;
	    int compStartHeight;
	    int compStartWidth;
	    int minHeight = 100;
	    int minWidth = 100;
	    
//	    private static final int PROGRESS_CUR = 0;
//	    private static final int PROGRESS_TOTAL = 100;
//	    private static int currentProgress = MIN_PROGRESS;

	   	/*
	   	 * 
	   	 * */
		public static void main(String[] args) 
		{
			new GDragonII(GMsg.SYSTEM_WELCOME, 0, 0, "");
		}
		
	    public GDragonII(String message, int dX, int dY, String img)
	    {
	        super("testing frame");
	        setSize(720, 265);
	        if(dX > 0 && dY > 0) {
	        	setLocation(dX, dY);
	        }else {
	        	setLocationRelativeTo(null);
	        }
	        
	        //设置背景色
	        setBackground(Color.black);
	        
	        //去掉标题栏
	        setUndecorated(true);
	         
	        //添加鼠标事件监听
	        addMouseMotionListener(this);
	        addMouseListener(this);
	        
	        //配置标题
	        JLabel jL_Title = new JLabel("");
	        jL_Title.setFont(new Font("宋体", Font.HANGING_BASELINE, 36));
	        jL_Title.setForeground(Color.BLUE);
	        jL_Title.setBackground(new Color(0,0,0,0));
	  	    
	  	    //欢迎语
	        JTextArea jL_MSG = new JTextArea(message);
	        jL_MSG.setLineWrap(true);
	        jL_MSG.setFont(new Font("宋体", Font.PLAIN, 14));
	        jL_MSG.setForeground(Color.BLUE);
	        jL_MSG.setBackground(new Color(192,192,192,100));
	        
	        // 创建一个进度条
	        JProgressBar progressBar = new JProgressBar();
	        progressBar.setStringPainted(true);
	        progressBar.addChangeListener(new ChangeListener() {
	            @Override
	            public void stateChanged(ChangeEvent e) {
	                System.out.println("当前进度值: " + progressBar.getValue() + "; " +
	                        "进度百分比: " + progressBar.getPercentComplete());
	            }
	        });


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
	        
	        JPanel jL_ProccessBar = new JPanel();
	        jL_ProccessBar.setSize(720, 25);
	        jL_ProccessBar.setLayout(new FlowLayout(1,0,0));
	        jL_ProccessBar.add(progressBar);
//	        jL_ProccessBar.add(jBtn_Yes);
//	        jL_ProccessBar.add(jBtn_No);
	        jL_ProccessBar.add(jBtn_Close);
	        jL_ProccessBar.setBackground(new Color(0,0,0,0));

	        setLayout(new BorderLayout(0, 0));
	        add(jL_Title, BorderLayout.NORTH);
	        add(jL_MSG, BorderLayout.CENTER);
	      	add(jL_ProccessBar, BorderLayout.SOUTH);
	        
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
	        	new Thread(new Runnable() {
					@Override
					public void run() {
						//int count = 0;
						// pB.setForeground(progressDefaultColor);
//    						try {
//    							Thread.sleep(100);
//    						} catch (InterruptedException e) {
//    							e.printStackTrace();
//    						}
						while (true) {
							// if(isStop()){
							// setStop(false);
							// break;
							// }
							//count++;
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							progressBar.setString(GSys.PROGRESS_CUR + "%");
							progressBar.setValue(GSys.PROGRESS_CUR);
							if (GSys.PROGRESS_CUR >= 100) {
								progressBar.setForeground(Color.GREEN);
								System.out.println("加载完成");
								break;
							}
						}
						System.out.println("提示窗-正常关闭");
	    	    		dispose();
					}
				}).start();	
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
}
