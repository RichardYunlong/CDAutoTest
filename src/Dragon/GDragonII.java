package Dragon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DT.GLog;
import Sys.GSys;

/**
 * 欢迎界面
 */
public class GDragonII extends GBase {
		
		/**
		 * 句柄编号
		 */
		private static final long serialVersionUID = 1366934872088790043L;
		
	    int minHeight = 100;
	    int minWidth = 100;
		
	    public GDragonII(String message, int dX, int dY)
	    {
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
	            	GLog.logRecord(8, "当前进度值: " + progressBar.getValue() + "; " + "进度百分比: " + progressBar.getPercentComplete());
	            }
	        });

	        //添加功能按钮
	        add3Button();
	        
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
	        //GLog.logRecord(8, "提示窗-正常开启");
	        setVisible(true);
	        
	        try {
	        	new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							try {
								Thread.sleep(100);
							} catch (Exception e) {
								GSys.logErrorSys("GDragonII[" + Arrays.toString(e.getStackTrace()) +"]");
							}
							progressBar.setString(GSys.getPROGRESS_CUR() + "%");
							progressBar.setValue(GSys.getPROGRESS_CUR());
							if (GSys.getPROGRESS_CUR() >= 100) {
								progressBar.setForeground(Color.GREEN);
								GLog.logRecord(8, "加载完成");
								break;
							}
						}
						//GLog.logRecord(8, "提示窗-正常关闭");
	    	    		dispose();
					}
				}).start();	
	        }catch(Exception e) {
	        	GLog.logRecord(8, "提示窗-异常关闭");
	        	dispose();
	        	GLog.logSysFunctionException("GDragonII", e);
	        }
	    }
}
