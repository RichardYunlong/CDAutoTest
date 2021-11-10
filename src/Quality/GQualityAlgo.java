package Quality;

import Base.GClazz;
import DT.GLog;

/**
 * 质量算法
 */
public class GQualityAlgo {
	
    /**
     *  构造函数
     */
	private GQualityAlgo(){
		GClazz.thisAToolClass();
	}
	
	/**
	 * 功能性
	 * 
	 * 在指定条件下,软件满足用户显式需求和隐式需求的能力
	 * 
	 * 1.默认算法：以下各项满分均为20分
	 * 2.条件算法：按照不同的
	 */
	private static Integer FUNCTIONALITY = Integer.valueOf(0);
	
	/**
	 * 适应性
	 */
	private static Integer FUNCTIONALITY_FLEXIBILITY = Integer.valueOf(0);
	
	/**
	 * 准确性
	 */
	private static Integer FUNCTIONALITY_ACCURACY = Integer.valueOf(0);
	
	/**
	 * 交互性
	 */
	private static Integer FUNCTIONALITY_INTERACTIVITY = Integer.valueOf(0);
	
	/**
	 * 安全性
	 */
	private static Integer FUNCTIONALITY_SECURITY = Integer.valueOf(0);

	/**
	 * 功能合规性
	 */
	private static Integer FUNCTIONALITY_REGULATORY = Integer.valueOf(0);
	
	/**
	 * 可靠性
	 * 
	 * 在指定条件下使用时,软件产品维持规定的性能级别的能力
	 * 成熟性
	 * 容错性
	 * 易恢复性
	 * 可靠合规性
	 */
	private static Integer RELIABILITY = Integer.valueOf(0);
	
	/**
	 * 成熟性
	 */
	private static Integer RELIABILITY_MATURITY = Integer.valueOf(0);

	/**
	 * 容错性
	 */
	private static Integer RELIABILITY_FAULTTOLERANCE = Integer.valueOf(0);
	
	/**
	 * 易恢复性
	 */
	private static Integer RELIABILITY_RECOVERABILITY = Integer.valueOf(0);
	
	/**
	 * 可靠合规性
	 */
	private static Integer RELIABILITY_REGULATORY = Integer.valueOf(0);
	
	/**
	 * 可使用性
	 * 
	 * 在指定条件下,软件产品被使用、理解、学习的能力
	 * 易理解性
	 * 易学习性
	 * 易操作性
	 * 用户粘性
	 * 易用合规性
	 */
	private static Integer USABILITY = Integer.valueOf(0);
	
	/**
	 * 易理解性
	 */
	private static Integer USABILITY_COMPREHENSIBILITY = Integer.valueOf(0);
	
	/**
	 * 易学习性
	 */
	private static Integer USABILITY_EASYTOLEARN = Integer.valueOf(0);
	
	/**
	 * 易操作性
	 */
	private static Integer USABILITY_OPERABILITY = Integer.valueOf(0);
	
	/**
	 * 用户粘性
	 */
	private static Integer USABILITY_STICKINESS = Integer.valueOf(0);
	
	/**
	 * 易用合规性
	 */
	private static Integer USABILITY_REGULATORY = Integer.valueOf(0);
	
	/**
	 * 效率
	 * 
	 * 在指定条件下,相对于所有资源的数量,软件产品可提供适当性能的能力
	 * 时间特性
	 * 资源利用率
	 * 效率合规性
	 */
	private static Integer EFFICIENCY = Integer.valueOf(0);
	
	/**
	 * 时间特性
	 */
	private static Integer EFFICIENCY_TIMING = Integer.valueOf(0);
	
	/**
	 * 资源利用率
	 */
	private static Integer EFFICIENCY_UTILIZATION = Integer.valueOf(0);
	
	/**
	 * 效率合规性
	 */
	private static Integer EFFICIENCY_REGULATORY = Integer.valueOf(0);
	
	/**
	 * 可维护性
	 * 
	 * 指软件产品被修改的能力.修改包括修正、优化和功能规格变更的说明
	 * 易分析性
	 * 稳定性
	 * 易变更性
	 * 易测试性
	 * 可维护合规性
	 */
	private static Integer MAINTAINABILITY = Integer.valueOf(0);
	
	/**
	 * 易分析性
	 */
	private static Integer MAINTAINABILITY_ANALYZABILITY = Integer.valueOf(0);
	
	/**
	 * 稳定性
	 */
	private static Integer MAINTAINABILITY_STABILITY = Integer.valueOf(0);
	
	/**
	 * 易变更性
	 */
	private static Integer MAINTAINABILITY_CHANGEABILITY = Integer.valueOf(0);
	
	/**
	 * 易测试性
	 */
	private static Integer MAINTAINABILITY_TESTABILITY = Integer.valueOf(0);
	
	/**
	 * 可维护合规性
	 */
	private static Integer MAINTAINABILITY_REGULATORY = Integer.valueOf(0);
	
	/**
	 * 可移植性
	 * 
	 * 指软件产品从一个环境迁移到另一个环境的能力
	 * 适应性
	 * 易安装性
	 * 共存性
	 * 易替换性
	 * 可移植合规性
	 */
	private static Integer PORTABILITY = Integer.valueOf(0);
	
	/**
	 * 适应性
	 */
	private static Integer PORTABILITY_ADAPTABILITY = Integer.valueOf(0);
	
	/**
	 * 易安装性
	 */
	private static Integer PORTABILITY_INSTALLATION = Integer.valueOf(0);
	
	/**
	 * 共存性
	 */
	private static Integer PORTABILITY_COEXISTENCE = Integer.valueOf(0);
	
	/**
	 * 易替换性
	 */
	private static Integer PORTABILITY_REPLACEABILITY = Integer.valueOf(0);
	
	/**
	 * 可移植合规性
	 */
	private static Integer PORTABILITY_REGULATORY = Integer.valueOf(0);
	
	public static Integer getFUNCTIONALITY() {
		return FUNCTIONALITY;
	}

	public static void setFUNCTIONALITY(Integer fUNCTIONALITY) {
		FUNCTIONALITY = fUNCTIONALITY;
	}

	public static Integer getFUNCTIONALITY_FLEXIBILITY() {
		return FUNCTIONALITY_FLEXIBILITY;
	}

	public static void setFUNCTIONALITY_FLEXIBILITY(Integer fUNCTIONALITY_FLEXIBILITY) {
		FUNCTIONALITY_FLEXIBILITY = fUNCTIONALITY_FLEXIBILITY;
	}

	public static Integer getFUNCTIONALITY_ACCURACY() {
		return FUNCTIONALITY_ACCURACY;
	}

	public static void setFUNCTIONALITY_ACCURACY(Integer fUNCTIONALITY_ACCURACY) {
		FUNCTIONALITY_ACCURACY = fUNCTIONALITY_ACCURACY;
	}

	public static Integer getFUNCTIONALITY_INTERACTIVITY() {
		return FUNCTIONALITY_INTERACTIVITY;
	}

	public static void setFUNCTIONALITY_INTERACTIVITY(Integer fUNCTIONALITY_INTERACTIVITY) {
		FUNCTIONALITY_INTERACTIVITY = fUNCTIONALITY_INTERACTIVITY;
	}

	public static Integer getFUNCTIONALITY_SECURITY() {
		return FUNCTIONALITY_SECURITY;
	}

	public static void setFUNCTIONALITY_SECURITY(Integer fUNCTIONALITY_SECURITY) {
		FUNCTIONALITY_SECURITY = fUNCTIONALITY_SECURITY;
	}

	public static Integer getFUNCTIONALITY_REGULATORY() {
		return FUNCTIONALITY_REGULATORY;
	}

	public static void setFUNCTIONALITY_REGULATORY(Integer fUNCTIONALITY_REGULATORY) {
		FUNCTIONALITY_REGULATORY = fUNCTIONALITY_REGULATORY;
	}

	public static Integer getRELIABILITY() {
		return RELIABILITY;
	}

	public static void setRELIABILITY(Integer rELIABILITY) {
		RELIABILITY = rELIABILITY;
	}

	public static Integer getRELIABILITY_MATURITY() {
		return RELIABILITY_MATURITY;
	}

	public static void setRELIABILITY_MATURITY(Integer rELIABILITY_MATURITY) {
		RELIABILITY_MATURITY = rELIABILITY_MATURITY;
	}

	public static Integer getRELIABILITY_FAULTTOLERANCE() {
		return RELIABILITY_FAULTTOLERANCE;
	}

	public static void setRELIABILITY_FAULTTOLERANCE(Integer rELIABILITY_FAULTTOLERANCE) {
		RELIABILITY_FAULTTOLERANCE = rELIABILITY_FAULTTOLERANCE;
	}

	public static Integer getRELIABILITY_RECOVERABILITY() {
		return RELIABILITY_RECOVERABILITY;
	}

	public static void setRELIABILITY_RECOVERABILITY(Integer rELIABILITY_RECOVERABILITY) {
		RELIABILITY_RECOVERABILITY = rELIABILITY_RECOVERABILITY;
	}

	public static Integer getRELIABILITY_REGULATORY() {
		return RELIABILITY_REGULATORY;
	}

	public static void setRELIABILITY_REGULATORY(Integer rELIABILITY_REGULATORY) {
		RELIABILITY_REGULATORY = rELIABILITY_REGULATORY;
	}

	public static Integer getUSABILITY() {
		return USABILITY;
	}

	public static void setUSABILITY(Integer uSABILITY) {
		USABILITY = uSABILITY;
	}

	public static Integer getUSABILITY_COMPREHENSIBILITY() {
		return USABILITY_COMPREHENSIBILITY;
	}

	public static void setUSABILITY_COMPREHENSIBILITY(Integer uSABILITY_COMPREHENSIBILITY) {
		USABILITY_COMPREHENSIBILITY = uSABILITY_COMPREHENSIBILITY;
	}

	public static Integer getUSABILITY_EASYTOLEARN() {
		return USABILITY_EASYTOLEARN;
	}

	public static void setUSABILITY_EASYTOLEARN(Integer uSABILITY_EASYTOLEARN) {
		USABILITY_EASYTOLEARN = uSABILITY_EASYTOLEARN;
	}

	public static Integer getUSABILITY_OPERABILITY() {
		return USABILITY_OPERABILITY;
	}

	public static void setUSABILITY_OPERABILITY(Integer uSABILITY_OPERABILITY) {
		USABILITY_OPERABILITY = uSABILITY_OPERABILITY;
	}

	public static Integer getUSABILITY_STICKINESS() {
		return USABILITY_STICKINESS;
	}

	public static void setUSABILITY_STICKINESS(Integer uSABILITY_STICKINESS) {
		USABILITY_STICKINESS = uSABILITY_STICKINESS;
	}

	public static Integer getUSABILITY_REGULATORY() {
		return USABILITY_REGULATORY;
	}

	public static void setUSABILITY_REGULATORY(Integer uSABILITY_REGULATORY) {
		USABILITY_REGULATORY = uSABILITY_REGULATORY;
	}

	public static Integer getEFFICIENCY() {
		return EFFICIENCY;
	}

	public static void setEFFICIENCY(Integer eFFICIENCY) {
		EFFICIENCY = eFFICIENCY;
	}

	public static Integer getEFFICIENCY_TIMING() {
		return EFFICIENCY_TIMING;
	}

	public static void setEFFICIENCY_TIMING(Integer eFFICIENCY_TIMING) {
		EFFICIENCY_TIMING = eFFICIENCY_TIMING;
	}

	public static Integer getEFFICIENCY_UTILIZATION() {
		return EFFICIENCY_UTILIZATION;
	}

	public static void setEFFICIENCY_UTILIZATION(Integer eFFICIENCY_UTILIZATION) {
		EFFICIENCY_UTILIZATION = eFFICIENCY_UTILIZATION;
	}

	public static Integer getEFFICIENCY_REGULATORY() {
		return EFFICIENCY_REGULATORY;
	}

	public static void setEFFICIENCY_REGULATORY(Integer eFFICIENCY_REGULATORY) {
		EFFICIENCY_REGULATORY = eFFICIENCY_REGULATORY;
	}

	public static Integer getMAINTAINABILITY() {
		return MAINTAINABILITY;
	}

	public static void setMAINTAINABILITY(Integer mAINTAINABILITY) {
		MAINTAINABILITY = mAINTAINABILITY;
	}

	public static Integer getMAINTAINABILITY_ANALYZABILITY() {
		return MAINTAINABILITY_ANALYZABILITY;
	}

	public static void setMAINTAINABILITY_ANALYZABILITY(Integer mAINTAINABILITY_ANALYZABILITY) {
		MAINTAINABILITY_ANALYZABILITY = mAINTAINABILITY_ANALYZABILITY;
	}

	public static Integer getMAINTAINABILITY_STABILITY() {
		return MAINTAINABILITY_STABILITY;
	}

	public static void setMAINTAINABILITY_STABILITY(Integer mAINTAINABILITY_STABILITY) {
		MAINTAINABILITY_STABILITY = mAINTAINABILITY_STABILITY;
	}

	public static Integer getMAINTAINABILITY_CHANGEABILITY() {
		return MAINTAINABILITY_CHANGEABILITY;
	}

	public static void setMAINTAINABILITY_CHANGEABILITY(Integer mAINTAINABILITY_CHANGEABILITY) {
		MAINTAINABILITY_CHANGEABILITY = mAINTAINABILITY_CHANGEABILITY;
	}

	public static Integer getMAINTAINABILITY_TESTABILITY() {
		return MAINTAINABILITY_TESTABILITY;
	}

	public static void setMAINTAINABILITY_TESTABILITY(Integer mAINTAINABILITY_TESTABILITY) {
		MAINTAINABILITY_TESTABILITY = mAINTAINABILITY_TESTABILITY;
	}

	public static Integer getMAINTAINABILITY_REGULATORY() {
		return MAINTAINABILITY_REGULATORY;
	}

	public static void setMAINTAINABILITY_REGULATORY(Integer mAINTAINABILITY_REGULATORY) {
		MAINTAINABILITY_REGULATORY = mAINTAINABILITY_REGULATORY;
	}

	public static Integer getPORTABILITY() {
		return PORTABILITY;
	}

	public static void setPORTABILITY(Integer pORTABILITY) {
		PORTABILITY = pORTABILITY;
	}

	public static Integer getPORTABILITY_ADAPTABILITY() {
		return PORTABILITY_ADAPTABILITY;
	}

	public static void setPORTABILITY_ADAPTABILITY(Integer pORTABILITY_ADAPTABILITY) {
		PORTABILITY_ADAPTABILITY = pORTABILITY_ADAPTABILITY;
	}

	public static Integer getPORTABILITY_INSTALLATION() {
		return PORTABILITY_INSTALLATION;
	}

	public static void setPORTABILITY_INSTALLATION(Integer pORTABILITY_INSTALLATION) {
		PORTABILITY_INSTALLATION = pORTABILITY_INSTALLATION;
	}

	public static Integer getPORTABILITY_COEXISTENCE() {
		return PORTABILITY_COEXISTENCE;
	}

	public static void setPORTABILITY_COEXISTENCE(Integer pORTABILITY_COEXISTENCE) {
		PORTABILITY_COEXISTENCE = pORTABILITY_COEXISTENCE;
	}

	public static Integer getPORTABILITY_REPLACEABILITY() {
		return PORTABILITY_REPLACEABILITY;
	}

	public static void setPORTABILITY_REPLACEABILITY(Integer pORTABILITY_REPLACEABILITY) {
		PORTABILITY_REPLACEABILITY = pORTABILITY_REPLACEABILITY;
	}

	public static Integer getPORTABILITY_REGULATORY() {
		return PORTABILITY_REGULATORY;
	}

	public static void setPORTABILITY_REGULATORY(Integer pORTABILITY_REGULATORY) {
		PORTABILITY_REGULATORY = pORTABILITY_REGULATORY;
	}
	
	/**
	 * 默认品质算法
	 * 
	 * 相关各项分数直接相加
	 */
	protected static void calculateDefault() {
		FUNCTIONALITY = FUNCTIONALITY_FLEXIBILITY
				+ FUNCTIONALITY_ACCURACY
				+ FUNCTIONALITY_INTERACTIVITY
				+ FUNCTIONALITY_SECURITY
				+ FUNCTIONALITY_REGULATORY;

		RELIABILITY = RELIABILITY_MATURITY
				+ RELIABILITY_FAULTTOLERANCE
				+ RELIABILITY_RECOVERABILITY
				+ RELIABILITY_REGULATORY;
		
		USABILITY = USABILITY_COMPREHENSIBILITY
				+ USABILITY_EASYTOLEARN
				+ USABILITY_OPERABILITY
				+ USABILITY_STICKINESS
				+ USABILITY_REGULATORY;
		
		EFFICIENCY = EFFICIENCY_TIMING
				+ EFFICIENCY_UTILIZATION
				+ EFFICIENCY_REGULATORY;
		
		MAINTAINABILITY = MAINTAINABILITY_ANALYZABILITY
				+ MAINTAINABILITY_STABILITY
				+ MAINTAINABILITY_CHANGEABILITY
				+ MAINTAINABILITY_TESTABILITY
				+ MAINTAINABILITY_REGULATORY;
		
		PORTABILITY = PORTABILITY_ADAPTABILITY
				+ PORTABILITY_INSTALLATION
				+ PORTABILITY_COEXISTENCE
				+ PORTABILITY_REPLACEABILITY
				+ PORTABILITY_REGULATORY;
	}
	
	/**
	 * 品质算法 
	 * 
	 * 根据系统内写死的品质系数换算品质得分
	 */
	protected static void calculate() {
		GLog.logRecord(8, GQFactor.getStrComb());
		
		FUNCTIONALITY = FUNCTIONALITY_FLEXIBILITY
				+ FUNCTIONALITY_ACCURACY
				+ FUNCTIONALITY_INTERACTIVITY
				+ FUNCTIONALITY_SECURITY
				+ FUNCTIONALITY_REGULATORY;
		Double functionality = FUNCTIONALITY.doubleValue() * GQFactor.getValue();
		FUNCTIONALITY = Integer.valueOf(functionality.intValue());

		RELIABILITY = RELIABILITY_MATURITY
				+ RELIABILITY_FAULTTOLERANCE
				+ RELIABILITY_RECOVERABILITY
				+ RELIABILITY_REGULATORY;
		Double reliability = RELIABILITY.doubleValue() * GQFactor.getValue();
		RELIABILITY = Integer.valueOf(reliability.intValue());
		
		USABILITY = USABILITY_COMPREHENSIBILITY
				+ USABILITY_EASYTOLEARN
				+ USABILITY_OPERABILITY
				+ USABILITY_STICKINESS
				+ USABILITY_REGULATORY;
		
		EFFICIENCY = EFFICIENCY_TIMING
				+ EFFICIENCY_UTILIZATION
				+ EFFICIENCY_REGULATORY;
		Double efficiency = EFFICIENCY.doubleValue() * GQFactor.getValue();
		EFFICIENCY = Integer.valueOf(efficiency.intValue());
		
		MAINTAINABILITY = MAINTAINABILITY_ANALYZABILITY
				+ MAINTAINABILITY_STABILITY
				+ MAINTAINABILITY_CHANGEABILITY
				+ MAINTAINABILITY_TESTABILITY
				+ MAINTAINABILITY_REGULATORY;
		Double maintainability =MAINTAINABILITY.doubleValue() * GQFactor.getValue();
		MAINTAINABILITY = Integer.valueOf(maintainability.intValue());
		
		PORTABILITY = PORTABILITY_ADAPTABILITY
				+ PORTABILITY_INSTALLATION
				+ PORTABILITY_COEXISTENCE
				+ PORTABILITY_REPLACEABILITY
				+ PORTABILITY_REGULATORY;
	}
}
