package Base;

/**
 * 字符串队列
 */
public class GQueue {	 

	/**
	 * 队列中存放的数据
	 */
	 private String[] data;

	 /**
	  * 队列的大小
	  */
	 private int maxSize;

	 /**
	  * 指向队列头部的指针
	  */
	 private int front;

	 /**
	  * 指向队列尾部的指针
	  */
	 private int rear;

	 /**
	  * 
	  */
	 public GQueue(int maxSize){
		 this.maxSize = maxSize;
		 data = new String[maxSize];
		 for(int i = 0;i < maxSize;i++) {
			 data[i] = "";
		 }
		 front = -1;
		 rear = -1;
	 }
	 
	 /**
	  * 判断队列是否已满
	  * 
	  * @return
	  */
	 public boolean isFull(){
		 return rear == maxSize -1 ;
	 }

	 /**
	  * 判断队列是否为空
	  * @return
	  */
	 public boolean isEmpty(){
		 return rear == front;
	 }

	 /**
	  * 添加数据到队列
	  * @param n
	  */
	 public void add(String str){
		 if(isFull()){
			 System.out.println("队列已满，不能添加");
			 return;
		 }
		 data[++rear] = str;
	 }

	 /**
	  * 显示头部数据
	  * 
	  * @return
	  */
	 public void head(){
		 if(isEmpty()){
			 throw new RuntimeException("队列为空");
		 }
		 System.out.println("当前头部:" + data[front+1]);
	 }

	 /**
	  * 取出头部数据
	  * 
	  * @return
	  */
	 public String pop(){
		 if(isEmpty()){
			 throw new RuntimeException("队列为空");
		 }
		 String a = data[++front];
		 data[front] = "";
		 System.out.println("取出头部:" + a);
		 return a;
	}

	 /**
	  * 打印全部数据
	  */
	 public void console(){
		if(isEmpty()){
			System.out.println("队列为空");
			return;
		}
		for(int i=0;i<data.length;i++){
			System.out.println(data[i]);
		}
	}
}

	 


