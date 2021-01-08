package AutoTest;

/**
 * 图形处理
 */
public class GGraph {
	
	/**
	 * 根据左上角坐标和
	 * 
	 * @param dX 左上角顶点横坐标
	 * @param dY 左上角顶点纵坐标
	 * @param dWidth 宽度
	 * @param dHeight 高度
	 * @return 创建成功返回true，否则返回false
	 */
	public static boolean drawRectangle(int dX, int dY, int dWidth, int dHeight) {
		boolean bRes = false;
		int gWidth = dWidth;
		int gHeight = dHeight;
		
		if(dWidth > 24) {dWidth = 24;}
		if(dHeight > 10) {dHeight = 10;}
		
		try {
			//输出顶点左表
			System.out.print("(dX,dY)=(" + dX + "," + dY +")");
			for(int l1 = 0;l1 < (dWidth - 13); l1++) {
				System.out.print("  ");
			}
			System.out.print("dWidth=" + gWidth +"\n");
			
			//绘制第1行
			for(int w = 0;w < dWidth; w++) {
				System.out.print("——");
			}
			System.out.print("\n");
			
			//绘制第2行至n-1行
			for(int h = 2;h < dHeight - 1; h++) {
				for(int w = 0;w <= dWidth; w++) {
					if(w == 0 || w == dWidth) {
						System.out.print("|");
						if(w == dWidth) {
							if(h == dHeight / 2) {
								System.out.print("dHeight=" + gHeight);
							}
							System.out.print("\n");
						}
					}else {

						System.out.print("  ");
					}
				}
			}
			
			//绘制第你行
			for(int w = 0;w < dWidth; w++) {
				System.out.print("——");
			}
			System.out.print("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bRes;
	}
	
	public static void main (String[] agrs) {
		drawRectangle(0, 0, 30, 20);
	}
}
