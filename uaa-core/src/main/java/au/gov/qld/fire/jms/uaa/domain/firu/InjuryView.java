package au.gov.qld.fire.jms.uaa.domain.firu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.BaseModel;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@IdClass(InjuryViewPK.class)
@Table(name = "V_FIRU_INJURIES")
@org.hibernate.annotations.Entity(mutable = false)
public class InjuryView extends BaseModel<InjuryViewPK> {

    /** serialVersionUID */
	private static final long serialVersionUID = 60324266658535503L;

	private String fireCallNo;

    private Integer injuryNo;

	private Long injuryId;

    private String region;

    private Integer incidentYear;

	private Date incidentDate;

    private String surname;

    private String firstName;

	private Boolean gender;

    private Date dateOfBirth;

    private String addrLine1;

    private String addrLine2;

    private String suburb;

    private String postcode;

    private String injuryType;

    private String injuryTo;

    private String multipleInjuryType;

    private String multipleInjuryTo;

    private String alcoholLevel;

    private String drugs;

    private String treatedAt;

    @Id
	@Column(name = "FIRE_CALL_NO")
	public String getFireCallNo() {
		return fireCallNo;
	}

	public void setFireCallNo(String fireCallNo) {
		this.fireCallNo = fireCallNo;
	}

    @Id
	@Column(name = "INJURY_NO")
	public Integer getInjuryNo() {
		return injuryNo;
	}

	public void setInjuryNo(Integer injuryNo) {
		this.injuryNo = injuryNo;
	}

	@Transient
	public Long getInjuryId() {
		return injuryId;
	}

	public void setInjuryId(Long injuryId) {
		this.injuryId = injuryId;
	}

	@Column(name = "REGION")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "INCIDENT_YEAR")
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

	@Column(name = "SURNAME") 
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "GENDER")
	@Type(type = "true_false")
	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

    @Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_BIRTH")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "ADDR_LINE_1")
	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	@Column(name = "ADDR_LINE_2")
	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	@Column(name = "SUBURB")
	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	@Column(name = "POSTCODE")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "INJURY_TYPE")
	public String getInjuryType() {
		return injuryType;
	}

	public void setInjuryType(String injuryType) {
		this.injuryType = injuryType;
	}

	@Column(name = "INJURY_TO") 
	public String getInjuryTo() {
		return injuryTo;
	}

	public void setInjuryTo(String injuryTo) {
		this.injuryTo = injuryTo;
	}

	@Column(name = "MULTIPLE_INJURY_TYPE")
	public String getMultipleInjuryType() {
		return multipleInjuryType;
	}

	public void setMultipleInjuryType(String multipleInjuryType) {
		this.multipleInjuryType = multipleInjuryType;
	}

	@Column(name = "MULTIPLE_INJURY_TO")
	public String getMultipleInjuryTo() {
		return multipleInjuryTo;
	}

	public void setMultipleInjuryTo(String multipleInjuryTo) {
		this.multipleInjuryTo = multipleInjuryTo;
	}

	@Column(name = "ALCOHOL_LEVEL")
	public String getAlcoholLevel() {
		return alcoholLevel;
	}

	public void setAlcoholLevel(String alcoholLevel) {
		this.alcoholLevel = alcoholLevel;
	}

	@Column(name = "DRUGS")
	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	@Column(name = "TREATED_AT")
	public String getTreatedAt() {
		return treatedAt;
	}

	public void setTreatedAt(String treatedAt) {
		this.treatedAt = treatedAt;
	}

}