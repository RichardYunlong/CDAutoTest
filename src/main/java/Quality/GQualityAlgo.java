package Quality;

import Base.GClazz;
import DT.GLog;
import Sys.GStatic;

/**
 * 质量算法
 *
 * <p>依据 GB/T 16260 软件质量模型，从功能性、可靠性、可使用性、效率、可维护性、
 * 可移植性六大特性及其子特性两个层次对被评对象打分。</p>
 *
 * <p>本类提供两套算法，由子类或调用方选择：</p>
 * <ul>
 *   <li>{@link #calculateDefault()}：默认算法，父特性得分等于其全部子特性得分之和，不引入权重；</li>
 *   <li>{@link #calculate()}：条件算法，在求和结果上再乘以 {@link GQFactor} 给出的行业品质系数。</li>
 * </ul>
 *
 * @see GQFactor
 */
public class GQualityAlgo {
	
    /**
     *  构造函数
     */
	public GQualityAlgo(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 * 功能性
	 *
	 * <p>在指定条件下，软件满足用户显式需求和隐式需求的能力。</p>
	 *
	 * <p>两种算法对它的处理方式不同：</p>
	 * <ol>
	 *   <li>默认算法：下列子特性满分均为 20 分，父特性得分为各子特性得分之和；</li>
	 *   <li>条件算法：在求和结果上再按 {@link GQFactor} 的行业品质系数折算。</li>
	 * </ol>
	 */
	private Integer FUNCTIONALITY = 0;
	/**
	 *  获得功能性
	 *
	 *  @return 功能性
	 */
	public Integer getFUNCTIONALITY() {
		return FUNCTIONALITY;
	}
	/**
	 *  设置功能性
	 *
	 *  @param fUNCTIONALITY 功能性
	 */
	public void setFUNCTIONALITY(Integer fUNCTIONALITY) {
		FUNCTIONALITY = fUNCTIONALITY;
	}
	
	/**
	 * 适应性
	 */
	private Integer FUNCTIONALITY_FLEXIBILITY = 0;
	/**
	 *  获得适应性
	 *
	 *  @return 适应性
	 */
	public Integer getFUNCTIONALITY_FLEXIBILITY() {
		return FUNCTIONALITY_FLEXIBILITY;
	}
	/**
	 *  设置适应性
	 *
	 *  @param fUNCTIONALITY_FLEXIBILITY 适应性
	 */
	public void setFUNCTIONALITY_FLEXIBILITY(Integer fUNCTIONALITY_FLEXIBILITY) { FUNCTIONALITY_FLEXIBILITY = fUNCTIONALITY_FLEXIBILITY; }
	
	/**
	 * 准确性
	 */
	private Integer FUNCTIONALITY_ACCURACY = 0;
	/**
	 *  获得准确性
	 *
	 *  @return 准确性
	 */
	public Integer getFUNCTIONALITY_ACCURACY() {
		return FUNCTIONALITY_ACCURACY;
	}
	/**
	 *  设置准确性
	 *
	 *  @param fUNCTIONALITY_ACCURACY 准确性
	 */
	public void setFUNCTIONALITY_ACCURACY(Integer fUNCTIONALITY_ACCURACY) { FUNCTIONALITY_ACCURACY = fUNCTIONALITY_ACCURACY; }
	
	/**
	 * 交互性
	 */
	private Integer FUNCTIONALITY_INTERACTIVITY = 0;
	/**
	 *  获得交互性
	 *
	 *  @return 交互性
	 */
	public Integer getFUNCTIONALITY_INTERACTIVITY() {
		return FUNCTIONALITY_INTERACTIVITY;
	}
	/**
	 *  设置交互性
	 *
	 *  @param fUNCTIONALITY_INTERACTIVITY 交互性
	 */
	public void setFUNCTIONALITY_INTERACTIVITY(Integer fUNCTIONALITY_INTERACTIVITY) { FUNCTIONALITY_INTERACTIVITY = fUNCTIONALITY_INTERACTIVITY; }
	
	/**
	 * 安全性
	 */
	private Integer FUNCTIONALITY_SECURITY = 0;
	/**
	 *  获得安全性
	 *
	 *  @return 安全性
	 */
	public Integer getFUNCTIONALITY_SECURITY() {
		return FUNCTIONALITY_SECURITY;
	}
	/**
	 *  设置安全性
	 *
	 *  @param fUNCTIONALITY_SECURITY 安全性
	 */
	public void setFUNCTIONALITY_SECURITY(Integer fUNCTIONALITY_SECURITY) { FUNCTIONALITY_SECURITY = fUNCTIONALITY_SECURITY; }

	/**
	 * 功能合规性
	 */
	private Integer FUNCTIONALITY_REGULATORY = 0;
	/**
	 *  获得功能合规性
	 *
	 *  @return 功能合规性
	 */
	public Integer getFUNCTIONALITY_REGULATORY() {
		return FUNCTIONALITY_REGULATORY;
	}
	/**
	 *  设置功能合规性
	 *
	 *  @param fUNCTIONALITY_REGULATORY 功能合规性
	 */
	public void setFUNCTIONALITY_REGULATORY(Integer fUNCTIONALITY_REGULATORY) { FUNCTIONALITY_REGULATORY = fUNCTIONALITY_REGULATORY; }
	
	/**
	 * 可靠性
	 * 在指定条件下使用时,软件产品维持规定的性能级别的能力
	 * 成熟性
	 * 容错性
	 * 易恢复性
	 * 可靠合规性
	 */
	private Integer RELIABILITY = 0;
	/**
	 *  获得可靠性
	 *
	 *  @return 可靠性
	 */
	public Integer getRELIABILITY() {
		return RELIABILITY;
	}
	/**
	 *  设置可靠性
	 *
	 *  @param rELIABILITY 可靠性
	 */
	public void setRELIABILITY(Integer rELIABILITY) {
		RELIABILITY = rELIABILITY;
	}
	
	/**
	 * 成熟性
	 */
	private Integer RELIABILITY_MATURITY = 0;
	/**
	 *  获得成熟性
	 *
	 *  @return 成熟性
	 */
	public Integer getRELIABILITY_MATURITY() {
		return RELIABILITY_MATURITY;
	}
	/**
	 *  设置成熟性
	 *
	 *  @param rELIABILITY_MATURITY 成熟性
	 */
	public void setRELIABILITY_MATURITY(Integer rELIABILITY_MATURITY) { RELIABILITY_MATURITY = rELIABILITY_MATURITY; }

	/**
	 * 容错性
	 */
	private Integer RELIABILITY_FAULTTOLERANCE = 0;
	/**
	 *  获得容错性
	 *
	 *  @return 容错性
	 */
	public Integer getRELIABILITY_FAULTTOLERANCE() {
		return RELIABILITY_FAULTTOLERANCE;
	}
	/**
	 *  设置容错性
	 *
	 *  @param rELIABILITY_FAULTTOLERANCE 容错性
	 */
	public void setRELIABILITY_FAULTTOLERANCE(Integer rELIABILITY_FAULTTOLERANCE) { RELIABILITY_FAULTTOLERANCE = rELIABILITY_FAULTTOLERANCE; }
	
	/**
	 * 易恢复性
	 */
	private Integer RELIABILITY_RECOVERABILITY = 0;
	/**
	 *  获得易恢复性
	 *
	 *  @return 易恢复性
	 */
	public Integer getRELIABILITY_RECOVERABILITY() {
		return RELIABILITY_RECOVERABILITY;
	}
	/**
	 *  设置易恢复性
	 *
	 *  @param rELIABILITY_RECOVERABILITY 易恢复性
	 */
	public void setRELIABILITY_RECOVERABILITY(Integer rELIABILITY_RECOVERABILITY) { RELIABILITY_RECOVERABILITY = rELIABILITY_RECOVERABILITY; }
	
	/**
	 * 可靠合规性
	 */
	private Integer RELIABILITY_REGULATORY = 0;
	/**
	 *  获得可靠合规性
	 *
	 *  @return 可靠合规性
	 */
	public Integer getRELIABILITY_REGULATORY() {
		return RELIABILITY_REGULATORY;
	}
	/**
	 *  设置可靠合规性
	 *
	 *  @param rELIABILITY_REGULATORY 可靠合规性
	 */
	public void setRELIABILITY_REGULATORY(Integer rELIABILITY_REGULATORY) { RELIABILITY_REGULATORY = rELIABILITY_REGULATORY; }
	
	/**
	 * 可使用性
	 * 在指定条件下,软件产品被使用、理解、学习的能力
	 * 易理解性
	 * 易学习性
	 * 易操作性
	 * 用户粘性
	 * 易用合规性
	 */
	private Integer USABILITY = 0;
	/**
	 *  获得可使用性
	 *
	 *  @return 可使用性
	 */
	public Integer getUSABILITY() {
		return USABILITY;
	}
	/**
	 *  设置可使用性
	 *
	 *  @param uSABILITY 可使用性
	 */
	public void setUSABILITY(Integer uSABILITY) {
		USABILITY = uSABILITY;
	}
	
	/**
	 * 易理解性
	 */
	private Integer USABILITY_COMPREHENSIBILITY = 0;
	/**
	 *  获得易理解性
	 *
	 *  @return 易理解性
	 */
	public Integer getUSABILITY_COMPREHENSIBILITY() {
		return USABILITY_COMPREHENSIBILITY;
	}
	/**
	 *  设置易理解性
	 *
	 *  @param uSABILITY_COMPREHENSIBILITY 易理解性
	 */
	public void setUSABILITY_COMPREHENSIBILITY(Integer uSABILITY_COMPREHENSIBILITY) { USABILITY_COMPREHENSIBILITY = uSABILITY_COMPREHENSIBILITY; }
	
	/**
	 * 易学习性
	 */
	private Integer USABILITY_EASYTOLEARN = 0;
	/**
	 *  获得易学习性
	 *
	 *  @return 易学习性
	 */
	public Integer getUSABILITY_EASYTOLEARN() {
		return USABILITY_EASYTOLEARN;
	}
	/**
	 *  设置易学习性
	 *
	 *  @param uSABILITY_EASYTOLEARN 易学习性
	 */
	public void setUSABILITY_EASYTOLEARN(Integer uSABILITY_EASYTOLEARN) { USABILITY_EASYTOLEARN = uSABILITY_EASYTOLEARN; }
	
	/**
	 * 易操作性
	 */
	private Integer USABILITY_OPERABILITY = 0;
	/**
	 *  获得易操作性
	 *
	 *  @return 易操作性
	 */
	public Integer getUSABILITY_OPERABILITY() {
		return USABILITY_OPERABILITY;
	}
	/**
	 *  设置易操作性
	 *
	 *  @param uSABILITY_OPERABILITY 易操作性
	 */
	public void setUSABILITY_OPERABILITY(Integer uSABILITY_OPERABILITY) { USABILITY_OPERABILITY = uSABILITY_OPERABILITY; }
	
	/**
	 * 用户粘性
	 */
	private Integer USABILITY_STICKINESS = 0;
	/**
	 *  获得用户粘性
	 *
	 *  @return 用户粘性
	 */
	public Integer getUSABILITY_STICKINESS() {
		return USABILITY_STICKINESS;
	}
	/**
	 *  设置用户粘性
	 *
	 *  @param uSABILITY_STICKINESS 用户粘性
	 */
	public void setUSABILITY_STICKINESS(Integer uSABILITY_STICKINESS) { USABILITY_STICKINESS = uSABILITY_STICKINESS; }
	
	/**
	 * 易用合规性
	 */
	private Integer USABILITY_REGULATORY = 0;
	/**
	 *  获得易用合规性
	 *
	 *  @return 易用合规性
	 */
	public Integer getUSABILITY_REGULATORY() {
		return USABILITY_REGULATORY;
	}
	/**
	 *  设置易用合规性
	 *
	 *  @param uSABILITY_REGULATORY 易用合规性
	 */
	public void setUSABILITY_REGULATORY(Integer uSABILITY_REGULATORY) { USABILITY_REGULATORY = uSABILITY_REGULATORY; }
	
	/**
	 * 效率
	 * 在指定条件下,相对于所有资源的数量,软件产品可提供适当性能的能力
	 * 时间特性
	 * 资源利用率
	 * 效率合规性
	 */
	private Integer EFFICIENCY = 0;
	/**
	 *  获得效率
	 *
	 *  @return 效率
	 */
	public Integer getEFFICIENCY() {
		return EFFICIENCY;
	}
	/**
	 *  设置效率
	 *
	 *  @param eFFICIENCY 效率
	 */
	public void setEFFICIENCY(Integer eFFICIENCY) {
		EFFICIENCY = eFFICIENCY;
	}
	
	/**
	 * 时间特性
	 */
	private Integer EFFICIENCY_TIMING = 0;
	/**
	 *  获得时间特性
	 *
	 *  @return 时间特性
	 */
	public Integer getEFFICIENCY_TIMING() {
		return EFFICIENCY_TIMING;
	}
	/**
	 *  设置时间特性
	 *
	 *  @param eFFICIENCY_TIMING 时间特性
	 */
	public void setEFFICIENCY_TIMING(Integer eFFICIENCY_TIMING) {
		EFFICIENCY_TIMING = eFFICIENCY_TIMING;
	}
	
	/**
	 * 资源利用率
	 */
	private Integer EFFICIENCY_UTILIZATION = 0;
	/**
	 *  获得资源利用率
	 *
	 *  @return 资源利用率
	 */
	public Integer getEFFICIENCY_UTILIZATION() {
		return EFFICIENCY_UTILIZATION;
	}
	/**
	 *  设置资源利用率
	 *
	 *  @param eFFICIENCY_UTILIZATION 资源利用率
	 */
	public void setEFFICIENCY_UTILIZATION(Integer eFFICIENCY_UTILIZATION) { EFFICIENCY_UTILIZATION = eFFICIENCY_UTILIZATION; }

	/**
	 * 效率合规性
	 */
	private Integer EFFICIENCY_REGULATORY = 0;
	/**
	 *  获得效率合规性
	 *
	 *  @return 效率合规性
	 */
	public Integer getEFFICIENCY_REGULATORY() {
		return EFFICIENCY_REGULATORY;
	}
	/**
	 *  设置效率合规性
	 *
	 *  @param eFFICIENCY_REGULATORY 效率合规性
	 */
	public void setEFFICIENCY_REGULATORY(Integer eFFICIENCY_REGULATORY) { EFFICIENCY_REGULATORY = eFFICIENCY_REGULATORY; }
	
	/**
	 * 可维护性
	 * 指软件产品被修改的能力.修改包括修正、优化和功能规格变更的说明
	 * 易分析性
	 * 稳定性
	 * 易变更性
	 * 易测试性
	 * 可维护合规性
	 */
	private Integer MAINTAINABILITY = 0;
	/**
	 *  获得可维护性
	 *
	 *  @return 可维护性
	 */
	public Integer getMAINTAINABILITY() {
		return MAINTAINABILITY;
	}
	/**
	 *  设置可维护性
	 *
	 *  @param mAINTAINABILITY 可维护性
	 */
	public void setMAINTAINABILITY(Integer mAINTAINABILITY) {
		MAINTAINABILITY = mAINTAINABILITY;
	}
	
	/**
	 * 易分析性
	 */
	private Integer MAINTAINABILITY_ANALYZABILITY = 0;
	/**
	 *  获得易分析性
	 *
	 *  @return 易分析性
	 */
	public Integer getMAINTAINABILITY_ANALYZABILITY() {
		return MAINTAINABILITY_ANALYZABILITY;
	}
	/**
	 *  设置易分析性
	 *
	 *  @param mAINTAINABILITY_ANALYZABILITY 易分析性
	 */
	public void setMAINTAINABILITY_ANALYZABILITY(Integer mAINTAINABILITY_ANALYZABILITY) { MAINTAINABILITY_ANALYZABILITY = mAINTAINABILITY_ANALYZABILITY; }
	
	/**
	 * 稳定性
	 */
	private Integer MAINTAINABILITY_STABILITY = 0;
	/**
	 *  获得稳定性
	 *
	 *  @return 稳定性
	 */
	public Integer getMAINTAINABILITY_STABILITY() {
		return MAINTAINABILITY_STABILITY;
	}
	/**
	 *  设置稳定性
	 *
	 *  @param mAINTAINABILITY_STABILITY 稳定性
	 */
	public void setMAINTAINABILITY_STABILITY(Integer mAINTAINABILITY_STABILITY) { MAINTAINABILITY_STABILITY = mAINTAINABILITY_STABILITY; }
	
	/**
	 * 易变更性
	 */
	private Integer MAINTAINABILITY_CHANGEABILITY = 0;
	/**
	 *  获得易变更性
	 *
	 *  @return 易变更性
	 */
	public Integer getMAINTAINABILITY_CHANGEABILITY() {
		return MAINTAINABILITY_CHANGEABILITY;
	}
	/**
	 *  设置易变更性
	 *
	 *  @param mAINTAINABILITY_CHANGEABILITY 易变更性
	 */
	public void setMAINTAINABILITY_CHANGEABILITY(Integer mAINTAINABILITY_CHANGEABILITY) { MAINTAINABILITY_CHANGEABILITY = mAINTAINABILITY_CHANGEABILITY; }
	
	/**
	 * 易测试性
	 */
	private Integer MAINTAINABILITY_TESTABILITY = 0;
	/**
	 *  获得易测试性
	 *
	 *  @return 易测试性
	 */
	public Integer getMAINTAINABILITY_TESTABILITY() {
		return MAINTAINABILITY_TESTABILITY;
	}
	/**
	 *  设置易测试性
	 *
	 *  @param mAINTAINABILITY_TESTABILITY 易测试性
	 */
	public void setMAINTAINABILITY_TESTABILITY(Integer mAINTAINABILITY_TESTABILITY) { MAINTAINABILITY_TESTABILITY = mAINTAINABILITY_TESTABILITY; }
	
	/**
	 * 可维护合规性
	 */
	private Integer MAINTAINABILITY_REGULATORY = 0;
	/**
	 *  获得可维护合规性
	 *
	 *  @return 可维护合规性
	 */
	public Integer getMAINTAINABILITY_REGULATORY() {
		return MAINTAINABILITY_REGULATORY;
	}
	/**
	 *  设置可维护合规性
	 *
	 *  @param mAINTAINABILITY_REGULATORY 可维护合规性
	 */
	public void setMAINTAINABILITY_REGULATORY(Integer mAINTAINABILITY_REGULATORY) { MAINTAINABILITY_REGULATORY = mAINTAINABILITY_REGULATORY; }
	
	/**
	 * 可移植性
	 * 指软件产品从一个环境迁移到另一个环境的能力
	 * 适应性
	 * 易安装性
	 * 共存性
	 * 易替换性
	 * 可移植合规性
	 */
	private Integer PORTABILITY = 0;
	/**
	 *  获得可移植性
	 *
	 *  @return 可移植性
	 */
	public Integer getPORTABILITY() {
		return PORTABILITY;
	}
	/**
	 *  设置可移植性
	 *
	 *  @param pORTABILITY 可移植性
	 */
	public void setPORTABILITY(Integer pORTABILITY) {
		PORTABILITY = pORTABILITY;
	}

	/**
	 * 适应性
	 */
	private Integer PORTABILITY_ADAPTABILITY = 0;
	/**
	 *  获得适应性
	 *
	 *  @return 适应性
	 */
	public Integer getPORTABILITY_ADAPTABILITY() {
		return PORTABILITY_ADAPTABILITY;
	}
	/**
	 *  设置适应性
	 *
	 *  @param pORTABILITY_ADAPTABILITY 适应性
	 */
	public void setPORTABILITY_ADAPTABILITY(Integer pORTABILITY_ADAPTABILITY) { PORTABILITY_ADAPTABILITY = pORTABILITY_ADAPTABILITY; }

	/**
	 * 易安装性
	 */
	private Integer PORTABILITY_INSTALLATION = 0;
	/**
	 *  获得易安装性
	 *
	 *  @return 易安装性
	 */
	public Integer getPORTABILITY_INSTALLATION() {
		return PORTABILITY_INSTALLATION;
	}
	/**
	 *  设置易安装性
	 *
	 *  @param pORTABILITY_INSTALLATION 易安装性
	 */
	public void setPORTABILITY_INSTALLATION(Integer pORTABILITY_INSTALLATION) { PORTABILITY_INSTALLATION = pORTABILITY_INSTALLATION; }
	
	/**
	 * 共存性
	 */
	private Integer PORTABILITY_COEXISTENCE = 0;
	/**
	 *  获得共存性
	 *
	 *  @return 共存性
	 */
	public Integer getPORTABILITY_COEXISTENCE() {
		return PORTABILITY_COEXISTENCE;
	}
	/**
	 *  设置共存性
	 *
	 *  @param pORTABILITY_COEXISTENCE 共存性
	 */
	public void setPORTABILITY_COEXISTENCE(Integer pORTABILITY_COEXISTENCE) { PORTABILITY_COEXISTENCE = pORTABILITY_COEXISTENCE; }
	
	/**
	 * 易替换性
	 */
	private Integer PORTABILITY_REPLACEABILITY = 0;
	/**
	 *  获得易替换性
	 *
	 *  @return 易替换性
	 */
	public Integer getPORTABILITY_REPLACEABILITY() {
		return PORTABILITY_REPLACEABILITY;
	}
	/**
	 *  设置易替换性
	 *
	 *  @param pORTABILITY_REPLACEABILITY 易替换性
	 */
	public void setPORTABILITY_REPLACEABILITY(Integer pORTABILITY_REPLACEABILITY) { PORTABILITY_REPLACEABILITY = pORTABILITY_REPLACEABILITY; }
	
	/**
	 * 可移植合规性
	 */
	private Integer PORTABILITY_REGULATORY = 0;
	/**
	 *  获得可移植合规性
	 *
	 *  @return 可移植合规性
	 */
	public Integer getPORTABILITY_REGULATORY() {
		return PORTABILITY_REGULATORY;
	}
	/**
	 *  设置可移植合规性
	 *
	 *  @param pORTABILITY_REGULATORY 可移植合规性
	 */
	public void setPORTABILITY_REGULATORY(Integer pORTABILITY_REGULATORY) { PORTABILITY_REGULATORY = pORTABILITY_REGULATORY; }
	
	/**
	 * 默认品质算法
	 *
	 * <p>六个特性的得分均等于各自子特性得分的直接求和，不引入任何权重或系数，
	 * 适用于不需要区分行业差异的场合。</p>
	 */
	protected void calculateDefault() {
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
	 * 品质算法（按行业系数折算）
	 *
	 * <p>先按 {@link #calculateDefault()} 的方式对子特性求和，再乘以
	 * {@link GQFactor#getValue()} 给出的行业品质系数并取整，使不同行业的评分具备可比性。</p>
	 *
	 * <p>注意：可使用性（{@code USABILITY}）与可移植性（{@code PORTABILITY}）当前只做求和，
	 * 不参与系数折算。</p>
	 *
	 * @see GQFactor#getValue()
	 */
	protected void calculate() {
		GLog.logRecord(8, GStatic.gqFactor.getStrComb());
		
		FUNCTIONALITY = FUNCTIONALITY_FLEXIBILITY
				+ FUNCTIONALITY_ACCURACY
				+ FUNCTIONALITY_INTERACTIVITY
				+ FUNCTIONALITY_SECURITY
				+ FUNCTIONALITY_REGULATORY;
		double functionality = FUNCTIONALITY.doubleValue() * GStatic.gqFactor.getValue();
		FUNCTIONALITY = (int) functionality;

		RELIABILITY = RELIABILITY_MATURITY
				+ RELIABILITY_FAULTTOLERANCE
				+ RELIABILITY_RECOVERABILITY
				+ RELIABILITY_REGULATORY;
		double reliability = RELIABILITY.doubleValue() * GStatic.gqFactor.getValue();
		RELIABILITY = (int) reliability;
		
		USABILITY = USABILITY_COMPREHENSIBILITY
				+ USABILITY_EASYTOLEARN
				+ USABILITY_OPERABILITY
				+ USABILITY_STICKINESS
				+ USABILITY_REGULATORY;
		
		EFFICIENCY = EFFICIENCY_TIMING
				+ EFFICIENCY_UTILIZATION
				+ EFFICIENCY_REGULATORY;
		double efficiency = EFFICIENCY.doubleValue() * GStatic.gqFactor.getValue();
		EFFICIENCY = (int) efficiency;
		
		MAINTAINABILITY = MAINTAINABILITY_ANALYZABILITY
				+ MAINTAINABILITY_STABILITY
				+ MAINTAINABILITY_CHANGEABILITY
				+ MAINTAINABILITY_TESTABILITY
				+ MAINTAINABILITY_REGULATORY;
		double maintainability =MAINTAINABILITY.doubleValue() * GStatic.gqFactor.getValue();
		MAINTAINABILITY = (int) maintainability;
		
		PORTABILITY = PORTABILITY_ADAPTABILITY
				+ PORTABILITY_INSTALLATION
				+ PORTABILITY_COEXISTENCE
				+ PORTABILITY_REPLACEABILITY
				+ PORTABILITY_REGULATORY;
	}
}
