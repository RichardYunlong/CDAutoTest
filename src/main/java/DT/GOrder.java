package main.java.DT;

import main.java.Base.GClazz;

/**
 *  订单
 */
public class GOrder {
	
    /**
     *  构造函数
     */
	public GOrder(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  订单详情
	 */
	private String orderDetail;
	public String getOrderDetail() { return orderDetail; }
	public void setOrderDetail(String orderDetail) { this.orderDetail = orderDetail;}

	/**
	 *  订单编码
	 */
	private String orderId;
	public String getOrderId() { return orderId; }
	public void setOrderId(String orderId) { this.orderId = orderId;}

	/**
	 *  办理订单号
	 */
	private String handleOrderNo;
	public String getHandleOrderNo() { return handleOrderNo; }
	public void setHandleOrderNo(String handleOrderNo) { this.handleOrderNo = handleOrderNo;}

	/**
	 *  流水号
	 */
	private String flowNo;
	public String getFlowNo() { return flowNo; }
	public void setFlowNo(String flowNo) { this.flowNo = flowNo; }

	/**
	 *  单个订单内相同商品数量
	 */
	private String  applyCourt;
	public String getApplyCourt() { return applyCourt; }
	public void setApplyCourt(String applyCourt) { this.applyCourt = applyCourt; }

	/**
	 *  支付返回地址
	 */
	private String  payUrl;
	public String getPayUrl() { return payUrl; }
	public void setPayUrl(String payUrl) { this.payUrl = payUrl; }

	/**
	 *  支付交易码
	 */
	private String  paymentCode;
	public String getPaymentCode() { return paymentCode; }
	public void setPaymentCode(String paymentCode) { this.paymentCode = paymentCode; }

	/**
	 *  支付交易信息
	 */
	private String  paymentMsg;
	public String getPaymentMsg() { return paymentMsg; }
	public void setPaymentMsg(String paymentMsg) { this.paymentMsg = paymentMsg; }

	/**
	 *  支付签名值
	 */
	private String  paymentSign;
	public String getPaymentSign() { return paymentSign; }
	public void setPaymentSign(String paymentSign) { this.paymentSign = paymentSign; }

	/**
	 *  是否需要纸质发票
	 */
	private String  isPaperInvoice;
	public String getIsPaperInvoice() { return isPaperInvoice; }
	public void setIsPaperInvoice(String isPaperInvoice) { this.isPaperInvoice = isPaperInvoice; }

	/**
	 *  发票号
	 */
	private String  invoiceNo;
	public String getInvoiceNo() { return invoiceNo; }
	public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
}
