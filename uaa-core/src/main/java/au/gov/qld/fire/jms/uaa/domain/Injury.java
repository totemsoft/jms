package au.gov.qld.fire.jms.uaa.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.uaa.domain.refdata.InjuryType;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "UAA_INJURY")
public class Injury extends Auditable<Long> {

	/** serialVersionUID */
	private static final long serialVersionUID = -2153173951855829025L;

    private String fireCallNo;

    private Integer injuryNo;

    private Region region;

    private Integer incidentYear;

	private Date incidentDate;

    private Contact contact;

    private Address address;

    private InjuryType injuryType;

    private String injuryTo;

    private InjuryType multipleInjuryType;

    private String multipleInjuryTo;

    private String alcoholLevel;

    private String drugs;

    private String treatedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INJURY_ID")
	@Override
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Column(name = "FIRE_CALL_NO", nullable = false, length = 6)
	public String getFireCallNo() {
		return fireCallNo;
	}

	public void setFireCallNo(String fireCallNo) {
		this.fireCallNo = fireCallNo;
	}

	@Column(name = "INJURY_NO", nullable = false, length = 2)
	public Integer getInjuryNo() {
		return injuryNo;
	}

	public void setInjuryNo(Integer injuryNo) {
		this.injuryNo = injuryNo;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID", nullable = false)
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Column(name = "INCIDENT_YEAR", length = 4)
	public Integer getIncidentYear() {
		return incidentYear;
	}

	public void setIncidentYear(Integer incidentYear) {
		this.incidentYear = incidentYear;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INCIDENT_DATE")
	public Date getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(Date incidentDate) {
		this.incidentDate = incidentDate;
	}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID")
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INJURY_TYPE_ID")
	public InjuryType getInjuryType() {
		return injuryType;
	}

	public void setInjuryType(InjuryType injuryType) {
		this.injuryType = injuryType;
	}

	@Column(name = "INJURY_TO", length = 15) 
	public String getInjuryTo() {
		return injuryTo;
	}

	public void setInjuryTo(String injuryTo) {
		this.injuryTo = injuryTo;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MULTIPLE_INJURY_TYPE_ID")
	public InjuryType getMultipleInjuryType() {
		return multipleInjuryType;
	}

	public void setMultipleInjuryType(InjuryType multipleInjuryType) {
		this.multipleInjuryType = multipleInjuryType;
	}

	@Column(name = "MULTIPLE_INJURY_TO", length = 50)
	public String getMultipleInjuryTo() {
		return multipleInjuryTo;
	}

	public void setMultipleInjuryTo(String multipleInjuryTo) {
		this.multipleInjuryTo = multipleInjuryTo;
	}

	@Column(name = "ALCOHOL_LEVEL", length = 10)
	public String getAlcoholLevel() {
		return alcoholLevel;
	}

	public void setAlcoholLevel(String alcoholLevel) {
		this.alcoholLevel = alcoholLevel;
	}

	@Column(name = "DRUGS", length = 25)
	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	@Column(name = "TREATED_AT", length = 40)
	public String getTreatedAt() {
		return treatedAt;
	}

	public void setTreatedAt(String treatedAt) {
		this.treatedAt = treatedAt;
	}

}