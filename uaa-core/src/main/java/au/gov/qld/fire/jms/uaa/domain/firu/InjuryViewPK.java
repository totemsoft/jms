package au.gov.qld.fire.jms.uaa.domain.firu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Embeddable
public class InjuryViewPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2139713143977808229L;

    private String fireCallNo;

    private Integer injuryNo;

	public InjuryViewPK() {
		super();
	}

	/**
	 * @param fireCallNoInjuryNo, eg "000015.1"
	 */
	public InjuryViewPK(String fireCallNoInjuryNo) {
		assert fireCallNoInjuryNo != null;
		int idx = fireCallNoInjuryNo.indexOf('.');
		this.fireCallNo = fireCallNoInjuryNo.substring(0, idx);
		this.injuryNo = new Integer(fireCallNoInjuryNo.substring(idx + 1));
	}

	@Column(name = "FIRE_CALL_NO", nullable = false)
	public String getFireCallNo() {
		return fireCallNo;
	}

	public void setFireCallNo(String fireCallNo) {
		this.fireCallNo = fireCallNo;
	}

	@Column(name = "INJURY_NO", nullable = false)
	public Integer getInjuryNo() {
		return injuryNo;
	}

	public void setInjuryNo(Integer injuryNo) {
		this.injuryNo = injuryNo;
	}

}