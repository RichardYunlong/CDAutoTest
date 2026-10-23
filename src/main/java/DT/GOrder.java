package DT;

import Base.GClazz;

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
	/**
	 *  获得订单详情
	 *
	 *  @return 订单详情
	 */
	public String getOrderDetail() { return orderDetail; }
	/**
	 *  设置订单详情
	 *
	 *  @param orderDetail 订单详情
	 */
	public void setOrderDetail(String orderDetail) { this.orderDetail = orderDetail;}

	/**
	 *  订单编码
	 */
	private String orderId;
	/**
	 *  获得订单编码
	 *
	 *  @return 订单编码
	 */
	public String getOrderId() { return orderId; }
	/**
	 *  设置订单编码
	 *
	 *  @param orderId 订单编码
	 */
	public void setOrderId(String orderId) { this.orderId = orderId;}

	/**
	 *  办理订单号
	 */
	private String handleOrderNo;
	/**
	 *  获得办理订单号
	 *
	 *  @return 办理订单号
	 */
	public String getHandleOrderNo() { return handleOrderNo; }
	/**
	 *  设置办理订单号
	 *
	 *  @param handleOrderNo 办理订单号
	 */
	public void setHandleOrderNo(String handleOrderNo) { this.handleOrderNo = handleOrderNo;}

	/**
	 *  流水号
	 */
	private String flowNo;
	/**
	 *  获得流水号
	 *
	 *  @return 流水号
	 */
	public String getFlowNo() { return flowNo; }
	/**
	 *  设置流水号
	 *
	 *  @param flowNo 流水号
	 */
	public void setFlowNo(String flowNo) { this.flowNo = flowNo; }

	/**
	 *  单个订单内相同商品数量
	 */
	private String  applyCourt;
	/**
	 *  获得单个订单内相同商品数量
	 *
	 *  @return 单个订单内相同商品数量
	 */
	public String getApplyCourt() { return applyCourt; }
	/**
	 *  设置单个订单内相同商品数量
	 *
	 *  @param applyCourt 单个订单内相同商品数量
	 */
	public void setApplyCourt(String applyCourt) { this.applyCourt = applyCourt; }

	/**
	 *  支付返回地址
	 */
	private String  payUrl;
	/**
	 *  获得支付返回地址
	 *
	 *  @return 支付返回地址
	 */
	public String getPayUrl() { return payUrl; }
	/**
	 *  设置支付返回地址
	 *
	 *  @param payUrl 支付返回地址
	 */
	public void setPayUrl(String payUrl) { this.payUrl = payUrl; }

	/**
	 *  支付交易码
	 */
	private String  paymentCode;
	/**
	 *  获得支付交易码
	 *
	 *  @return 支付交易码
	 */
	public String getPaymentCode() { return paymentCode; }
	/**
	 *  设置支付交易码
	 *
	 *  @param paymentCode 支付交易码
	 */
	public void setPaymentCode(String paymentCode) { this.paymentCode = paymentCode; }

	/**
	 *  支付交易信息
	 */
	private String  paymentMsg;
	/**
	 *  获得支付交易信息
	 *
	 *  @return 支付交易信息
	 */
	public String getPaymentMsg() { return paymentMsg; }
	/**
	 *  设置支付交易信息
	 *
	 *  @param paymentMsg 支付交易信息
	 */
	public void setPaymentMsg(String paymentMsg) { this.paymentMsg = paymentMsg; }

	/**
	 *  支付签名值
	 */
	private String  paymentSign;
	/**
	 *  获得支付签名值
	 *
	 *  @return 支付签名值
	 */
	public String getPaymentSign() { return paymentSign; }
	/**
	 *  设置支付签名值
	 *
	 *  @param paymentSign 支付签名值
	 */
	public void setPaymentSign(String paymentSign) { this.paymentSign = paymentSign; }

	/**
	 *  是否需要纸质发票
	 */
	private String  isPaperInvoice;
	/**
	 *  获得是否需要纸质发票
	 *
	 *  @return 是否需要纸质发票
	 */
	public String getIsPaperInvoice() { return isPaperInvoice; }
	/**
	 *  设置是否需要纸质发票
	 *
	 *  @param isPaperInvoice 是否需要纸质发票
	 */
	public void setIsPaperInvoice(String isPaperInvoice) { this.isPaperInvoice = isPaperInvoice; }

	/**
	 *  发票号
	 */
	private String  invoiceNo;
	/**
	 *  获得发票号
	 *
	 *  @return 发票号
	 */
	public String getInvoiceNo() { return invoiceNo; }
	/**
	 *  设置发票号
	 *
	 *  @param invoiceNo 发票号
	 */
	public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
}
