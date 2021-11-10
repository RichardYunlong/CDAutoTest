package DT;

import Base.GClazz;

/**
 *  订单
 */
public class GOrder {
	
    /**
     *  构造函数
     */
	private GOrder(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  订单详情
	 */
	private static String orderDetail;
	
	public static String getOrderDetail() {
		return orderDetail;
	}

	public static void setOrderDetail(String orderDetail) {
		GOrder.orderDetail = orderDetail;
	}

	public static String getOrderId() {
		return orderId;
	}

	public static void setOrderId(String orderId) {
		GOrder.orderId = orderId;
	}

	public static String getHandleOrderNo() {
		return handleOrderNo;
	}

	public static void setHandleOrderNo(String handleOrderNo) {
		GOrder.handleOrderNo = handleOrderNo;
	}

	public static String getFlowNo() {
		return flowNo;
	}

	public static void setFlowNo(String flowNo) {
		GOrder.flowNo = flowNo;
	}

	public static String getApplyCourt() {
		return applyCourt;
	}

	public static void setApplyCourt(String applyCourt) {
		GOrder.applyCourt = applyCourt;
	}

	public static String getPayUrl() {
		return payUrl;
	}

	public static void setPayUrl(String payUrl) {
		GOrder.payUrl = payUrl;
	}

	public static String getPaymentCode() {
		return paymentCode;
	}

	public static void setPaymentCode(String paymentCode) {
		GOrder.paymentCode = paymentCode;
	}

	public static String getPaymentMsg() {
		return paymentMsg;
	}

	public static void setPaymentMsg(String paymentMsg) {
		GOrder.paymentMsg = paymentMsg;
	}

	public static String getPaymentSign() {
		return paymentSign;
	}

	public static void setPaymentSign(String paymentSign) {
		GOrder.paymentSign = paymentSign;
	}

	public static String getIsPaperInvoice() {
		return isPaperInvoice;
	}

	public static void setIsPaperInvoice(String isPaperInvoice) {
		GOrder.isPaperInvoice = isPaperInvoice;
	}

	public static String getInvoiceNo() {
		return invoiceNo;
	}

	public static void setInvoiceNo(String invoiceNo) {
		GOrder.invoiceNo = invoiceNo;
	}

	/**
	 *  订单编码
	 */
	private static String orderId;
	
	/**
	 *  办理订单号
	 */
	private static String handleOrderNo;
	
	/**
	 *  流水号
	 */
	private static String flowNo;
	
	/**
	 *  单个订单内相同商品数量
	 */
	private static String  applyCourt;
	
	/**
	 *  支付返回地址
	 */
	private static String  payUrl;
	
	/**
	 *  支付交易码
	 */
	private static String  paymentCode;
	
	/**
	 *  支付交易信息
	 */
	private static String  paymentMsg;
	
	/**
	 *  支付签名值
	 */
	private static String  paymentSign;
	
	/**
	 *  是否需要纸质发票
	 */
	private static String  isPaperInvoice;
	
	/**
	 *  发票号
	 */
	private static String  invoiceNo;
}
