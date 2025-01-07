package Base;

/**
 *  数字进度
 */
public class GMaticProgress {

    /**
     *  初始值
     */
    private int PROGRESS_CUR;

    /**
     *  获得已加载
     *
     *  @return 当前进度分子
     */
    public int getPROGRESS_CUR() {
        return this.PROGRESS_CUR;
    }

    /**
     *  设置已加载
     *
     * @param pROGRESS_CUR 指定分子值
     */
    public void setPROGRESS_CUR(int pROGRESS_CUR) {
        this.PROGRESS_CUR = pROGRESS_CUR;
    }

    /**
     *  已加载+1
     */
    public void progress() {
        this.PROGRESS_CUR++;
    }

    /**
     *  已加载+n
     *
     *  @param n 分子增量
     */
    public void progress(int n) {
        this.PROGRESS_CUR = this.PROGRESS_CUR + n;
    }

    /**
     *  共需要加载
     */
    public final int PROGRESS_TOTAL = 0;

    /**
     *  构造函数
     *
     *  @param cur 当前分子
     *  @param total 进度分母
     */
    @SuppressWarnings("UnusedAssignment")
    public GMaticProgress(int cur, int total){
        this.PROGRESS_CUR = cur;
        this.PROGRESS_CUR = total;
    }
}
