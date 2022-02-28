package au.gov.qld.fire.jms.domain.mailOut;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@XmlRootElement(name = "form1")
public class OM89Form {

	private Question question;
	private AlarmDetails alarmDetails;
	private PremiseOwnerDetails premiseOwnerDetails;
	private BodyCorporate bodyCorporate;
	private PropertyManager propertyManager;
	private TenantDetails tenantDetails;
	private EmergencyContactInfo emergencyContactInfo;
	private SecurityContactInfo securityContactInfo;
	private FutureEmergencyContacts futureEmergencyContacts;
	private AdditionalInfo additionalInfo;
	private CustomerFeedback customerFeedback;

	/**
	 * @return the question
	 */
	@XmlElement(name = "Questions")
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @return the alarmDetails
	 */
	@XmlElement(name = "adAlarmDetails")
	public AlarmDetails getAlarmDetails() {
		return alarmDetails;
	}

	public void setAlarmDetails(AlarmDetails alarmDetails) {
		this.alarmDetails = alarmDetails;
	}

	/**
	 * @return the premiseOwnerDetails
	 */
	@XmlElement(name = "poPremiseOwnerDetails")
	public PremiseOwnerDetails getPremiseOwnerDetails() {
		return premiseOwnerDetails;
	}

	public void setPremiseOwnerDetails(PremiseOwnerDetails premiseOwnerDetails) {
		this.premiseOwnerDetails = premiseOwnerDetails;
	}

	/**
	 * @return the bodyCorporate
	 */
	@XmlElement(name = "bcBodyCorporate")
	public BodyCorporate getBodyCorporate() {
		return bodyCorporate;
	}

	public void setBodyCorporate(BodyCorporate bodyCorporate) {
		this.bodyCorporate = bodyCorporate;
	}

	/**
	 * @return the propertyManager
	 */
	@XmlElement(name = "pmPropertyManager")
	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}

	/**
	 * @return the tenantDetails
	 */
	@XmlElement(name = "ptTenantDetails")
	public TenantDetails getTenantDetails() {
		return tenantDetails;
	}

	public void setTenantDetails(TenantDetails tenantDetails) {
		this.tenantDetails = tenantDetails;
	}

	/**
	 * @return the emergencyContactInfo
	 */
	@XmlElement(name = "ecEmergencyContactInfo")
	public EmergencyContactInfo getEmergencyContactInfo() {
		return emergencyContactInfo;
	}

	public void setEmergencyContactInfo(EmergencyContactInfo emergencyContactInfo) {
		this.emergencyContactInfo = emergencyContactInfo;
	}

	/**
	 * @return the securityContactInfo
	 */
	@XmlElement(name = "scSecurityContactInfo")
	public SecurityContactInfo getSecurityContactInfo() {
		return securityContactInfo;
	}

	public void setSecurityContactInfo(SecurityContactInfo securityContactInfo) {
		this.securityContactInfo = securityContactInfo;
	}

	/**
	 * @return the futureEmergencyContacts
	 */
	@XmlElement(name = "fecFutureEmergencyContacts")
	public FutureEmergencyContacts getFutureEmergencyContacts() {
		return futureEmergencyContacts;
	}

	public void setFutureEmergencyContacts(FutureEmergencyContacts futureEmergencyContacts) {
		this.futureEmergencyContacts = futureEmergencyContacts;
	}

	/**
	 * @return the additionalInfo
	 */
	@XmlElement(name = "aiAdditionalInfo")
	public AdditionalInfo getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(AdditionalInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * @return the customerFeedback
	 */
	@XmlElement(name = "cfCustomerFeedback")
	public CustomerFeedback getCustomerFeedback() {
		return customerFeedback;
	}

	public void setCustomerFeedback(CustomerFeedback customerFeedback) {
		this.customerFeedback = customerFeedback;
	}

	@XmlRootElement(name = "Questions")
	public static class Question {
		private String ownershipchanged;
		private String bodycorporate;
		private String propertymanager;
		private String commercialtenant;
		@XmlAttribute(name = "")
		public String getOwnershipchanged() {
			return ownershipchanged;
		}
		public void setOwnershipchanged(String ownershipchanged) {
			this.ownershipchanged = ownershipchanged;
		}
		public String getBodycorporate() {
			return bodycorporate;
		}
		public void setBodycorporate(String bodycorporate) {
			this.bodycorporate = bodycorporate;
		}
		public String getPropertymanager() {
			return propertymanager;
		}
		public void setPropertymanager(String propertymanager) {
			this.propertymanager = propertymanager;
		}
		public String getCommercialtenant() {
			return commercialtenant;
		}
		public void setCommercialtenant(String commercialtenant) {
			this.commercialtenant = commercialtenant;
		}
	}

	public static abstract class Base {
		private String fca;
		public String getFca() {
			return fca;
		}
		public void setFca(String fca) {
			this.fca = fca;
		}
	}

	public static abstract class Address extends Base {
		private String streetnumber;
		private String streetname;
		private String suburb;
		private String state;
		private String postcode;
		public String getStreetnumber() {
			return streetnumber;
		}
		public void setStreetnumber(String streetnumber) {
			this.streetnumber = streetnumber;
		}
		public String getStreetname() {
			return streetname;
		}
		public void setStreetname(String streetname) {
			this.streetname = streetname;
		}
		public String getSuburb() {
			return suburb;
		}
		public void setSuburb(String suburb) {
			this.suburb = suburb;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getPostcode() {
			return postcode;
		}
		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}
	}

	public static abstract class Contact extends Address {
		private String contactname;
		private String email;
		private String office;
		private String mobile;
		private String fax;
		public String getContactname() {
			return contactname;
		}
		public void setContactname(String contactname) {
			this.contactname = contactname;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getOffice() {
			return office;
		}
		public void setOffice(String office) {
			this.office = office;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getFax() {
			return fax;
		}
		public void setFax(String fax) {
			this.fax = fax;
		}
	}

	@XmlRootElement(name = "adAlarmDetails")
	public static class AlarmDetails extends Address {
		private String customernumber;
		private String lotplannumber;
		private String premisename;
		private String changepremisenameto;
		public String getCustomernumber() {
			return customernumber;
		}
		public void setCustomernumber(String customernumber) {
			this.customernumber = customernumber;
		}
		public String getLotplannumber() {
			return lotplannumber;
		}
		public void setLotplannumber(String lotplannumber) {
			this.lotplannumber = lotplannumber;
		}
		public String getPremisename() {
			return premisename;
		}
		public void setPremisename(String premisename) {
			this.premisename = premisename;
		}
		public String getChangepremisenameto() {
			return changepremisenameto;
		}
		public void setChangepremisenameto(String changepremisenameto) {
			this.changepremisenameto = changepremisenameto;
		}
	}

	@XmlRootElement(name = "poPremiseOwnerDetails")
	public static class PremiseOwnerDetails extends Contact {
		private String ownername;
		private String abnacn;
		public String getOwnername() {
			return ownername;
		}
		public void setOwnername(String ownername) {
			this.ownername = ownername;
		}
		public String getAbnacn() {
			return abnacn;
		}
		public void setAbnacn(String abnacn) {
			this.abnacn = abnacn;
		}
	}

	@XmlRootElement(name = "bcBodyCorporate")
	public static class BodyCorporate extends Contact {
		private String companyname;
		private String abnacn;
		private String ctsnumber;
		private String bodycorpmanagement;
		public String getCompanyname() {
			return companyname;
		}
		public void setCompanyname(String companyname) {
			this.companyname = companyname;
		}
		public String getAbnacn() {
			return abnacn;
		}
		public void setAbnacn(String abnacn) {
			this.abnacn = abnacn;
		}
		public String getCtsnumber() {
			return ctsnumber;
		}
		public void setCtsnumber(String ctsnumber) {
			this.ctsnumber = ctsnumber;
		}
		public String getBodycorpmanagement() {
			return bodycorpmanagement;
		}
		public void setBodycorpmanagement(String bodycorpmanagement) {
			this.bodycorpmanagement = bodycorpmanagement;
		}
	}

	@XmlRootElement(name = "pmPropertyManager")
	public static class PropertyManager extends Contact {
		private String companyname;
		private String sameassection3details;
		public String getCompanyname() {
			return companyname;
		}
		public void setCompanyname(String companyname) {
			this.companyname = companyname;
		}
		/** same as bodycorporate */
		public String getSameassection3details() {
			return sameassection3details;
		}
		public void setSameassection3details(String sameassection3details) {
			this.sameassection3details = sameassection3details;
		}
		public boolean isSameAsBodyCorporate() {
			return "Y".equals(sameassection3details);
		}
	}

	@XmlRootElement(name = "ptTenantDetails")
	public static class TenantDetails extends Contact {
		private String companyname;
		private String owneroccupied;
		public String getCompanyname() {
			return companyname;
		}
		public void setCompanyname(String companyname) {
			this.companyname = companyname;
		}
		public String getOwneroccupied() {
			return owneroccupied;
		}
		public void setOwneroccupied(String owneroccupied) {
			this.owneroccupied = owneroccupied;
		}
		public boolean isOwnerOccupied() {
			return "Y".equals(owneroccupied);
		}
		public void setOwnerOccupied(boolean ownerOccupied) {
			this.owneroccupied = ownerOccupied ? "Y" : "N";
		}
		@XmlElement(name = "State")
		public String getState() {
			return super.getState();
		}
		public void setState(String state) {
			super.setState(state);
		}
	}

	@XmlRootElement(name = "ecEmergencyContactInfo")
	public static class EmergencyContactInfo extends Base {
		private String contact1givenname;
		private String contact1lastname;
		private String contact1daytimenumber;
		private String contact1afterhoursnumber;
		private String contact2givenname;
		private String contact2lastname;
		private String contact2daytimenumber;
		private String contact2afterhoursnumber;
		private String contact3givenname;
		private String contact3lastname;
		private String contact3daytimenumber;
		private String contact3afterhoursnumber;
		public String getContact1givenname() {
			return contact1givenname;
		}
		public void setContact1givenname(String contact1givenname) {
			this.contact1givenname = contact1givenname;
		}
		public String getContact1lastname() {
			return contact1lastname;
		}
		public void setContact1lastname(String contact1lastname) {
			this.contact1lastname = contact1lastname;
		}
		public String getContact1daytimenumber() {
			return contact1daytimenumber;
		}
		public void setContact1daytimenumber(String contact1daytimenumber) {
			this.contact1daytimenumber = contact1daytimenumber;
		}
		public String getContact1afterhoursnumber() {
			return contact1afterhoursnumber;
		}
		public void setContact1afterhoursnumber(String contact1afterhoursnumber) {
			this.contact1afterhoursnumber = contact1afterhoursnumber;
		}
		public String getContact2givenname() {
			return contact2givenname;
		}
		public void setContact2givenname(String contact2givenname) {
			this.contact2givenname = contact2givenname;
		}
		public String getContact2lastname() {
			return contact2lastname;
		}
		public void setContact2lastname(String contact2lastname) {
			this.contact2lastname = contact2lastname;
		}
		public String getContact2daytimenumber() {
			return contact2daytimenumber;
		}
		public void setContact2daytimenumber(String contact2daytimenumber) {
			this.contact2daytimenumber = contact2daytimenumber;
		}
		public String getContact2afterhoursnumber() {
			return contact2afterhoursnumber;
		}
		public void setContact2afterhoursnumber(String contact2afterhoursnumber) {
			this.contact2afterhoursnumber = contact2afterhoursnumber;
		}
		public String getContact3givenname() {
			return contact3givenname;
		}
		public void setContact3givenname(String contact3givenname) {
			this.contact3givenname = contact3givenname;
		}
		public String getContact3lastname() {
			return contact3lastname;
		}
		public void setContact3lastname(String contact3lastname) {
			this.contact3lastname = contact3lastname;
		}
		public String getContact3daytimenumber() {
			return contact3daytimenumber;
		}
		public void setContact3daytimenumber(String contact3daytimenumber) {
			this.contact3daytimenumber = contact3daytimenumber;
		}
		public String getContact3afterhoursnumber() {
			return contact3afterhoursnumber;
		}
		public void setContact3afterhoursnumber(String contact3afterhoursnumber) {
			this.contact3afterhoursnumber = contact3afterhoursnumber;
		}
	}

	@XmlRootElement(name = "scSecurityContactInfo")
	public static class SecurityContactInfo extends Base {
		private String firealarmmaintenancecompanyname;
		private String firealarmmaintenancecompanyphonenumber;
		private String monitoredsecuritycompanyname;
		private String monitoredsecurityphonenumber;
		private String onsitesecutritycompanyname;
		private String onsitesecurityphonenumber;
		private String onsite24hrs;
		private String onsiteafterhours;
		private String onsiterandompatrols;
		private String othertimes;
		private String guarddogs;
		public String getFirealarmmaintenancecompanyname() {
			return firealarmmaintenancecompanyname;
		}
		public void setFirealarmmaintenancecompanyname(
				String firealarmmaintenancecompanyname) {
			this.firealarmmaintenancecompanyname = firealarmmaintenancecompanyname;
		}
		public String getFirealarmmaintenancecompanyphonenumber() {
			return firealarmmaintenancecompanyphonenumber;
		}
		public void setFirealarmmaintenancecompanyphonenumber(
				String firealarmmaintenancecompanyphonenumber) {
			this.firealarmmaintenancecompanyphonenumber = firealarmmaintenancecompanyphonenumber;
		}
		public String getMonitoredsecuritycompanyname() {
			return monitoredsecuritycompanyname;
		}
		public void setMonitoredsecuritycompanyname(String monitoredsecuritycompanyname) {
			this.monitoredsecuritycompanyname = monitoredsecuritycompanyname;
		}
		public String getMonitoredsecurityphonenumber() {
			return monitoredsecurityphonenumber;
		}
		public void setMonitoredsecurityphonenumber(String monitoredsecurityphonenumber) {
			this.monitoredsecurityphonenumber = monitoredsecurityphonenumber;
		}
		public String getOnsitesecutritycompanyname() {
			return onsitesecutritycompanyname;
		}
		public void setOnsitesecutritycompanyname(String onsitesecutritycompanyname) {
			this.onsitesecutritycompanyname = onsitesecutritycompanyname;
		}
		public String getOnsitesecurityphonenumber() {
			return onsitesecurityphonenumber;
		}
		public void setOnsitesecurityphonenumber(String onsitesecurityphonenumber) {
			this.onsitesecurityphonenumber = onsitesecurityphonenumber;
		}
		public String getOnsite24hrs() {
			return onsite24hrs;
		}
		public void setOnsite24hrs(String onsite24hrs) {
			this.onsite24hrs = onsite24hrs;
		}
		public String getOnsiteafterhours() {
			return onsiteafterhours;
		}
		public void setOnsiteafterhours(String onsiteafterhours) {
			this.onsiteafterhours = onsiteafterhours;
		}
		public String getOnsiterandompatrols() {
			return onsiterandompatrols;
		}
		public void setOnsiterandompatrols(String onsiterandompatrols) {
			this.onsiterandompatrols = onsiterandompatrols;
		}
		public String getOthertimes() {
			return othertimes;
		}
		public void setOthertimes(String othertimes) {
			this.othertimes = othertimes;
		}
		public String getGuarddogs() {
			return guarddogs;
		}
		public void setGuarddogs(String guarddogs) {
			this.guarddogs = guarddogs;
		}
	}

	@XmlRootElement(name = "fecFutureEmergencyContacts")
	public static class FutureEmergencyContacts extends Contact {
		private String emailpost;
		private String relationship;
		@XmlElement(name = "State")
		public String getState() {
			return super.getState();
		}
		public void setState(String state) {
			super.setState(state);
		}
		@XmlElement(name = "contactperson")
		public String getContactname() {
			return super.getContactname();
		}
		public void setContactname(String contactname) {
			super.setContactname(contactname);
		}
		@XmlElement(name = "contactnumber")
		public String getOffice() {
			return super.getOffice();
		}
		public void setOffice(String office) {
			super.setOffice(office);
		}
		public String getEmailpost() {
			return emailpost;
		}
		public void setEmailpost(String emailpost) {
			this.emailpost = emailpost;
		}
		public String getRelationship() {
			return relationship;
		}
		public void setRelationship(String relationship) {
			this.relationship = relationship;
		}
	}

	@XmlRootElement(name = "aiAdditionalInfo")
	public static class AdditionalInfo extends Base {
		private String confirmformcompleted;
		private String contactperson;
		private String contactnumber;
		private String contactposition;
		private String dateformcompleted;
		public String getConfirmformcompleted() {
			return confirmformcompleted;
		}
		public void setConfirmformcompleted(String confirmformcompleted) {
			this.confirmformcompleted = confirmformcompleted;
		}
		public String getContactperson() {
			return contactperson;
		}
		public void setContactperson(String contactperson) {
			this.contactperson = contactperson;
		}
		public String getContactnumber() {
			return contactnumber;
		}
		public void setContactnumber(String contactnumber) {
			this.contactnumber = contactnumber;
		}
		public String getContactposition() {
			return contactposition;
		}
		public void setContactposition(String contactposition) {
			this.contactposition = contactposition;
		}
		public String getDateformcompleted() {
			return dateformcompleted;
		}
		public void setDateformcompleted(String dateformcompleted) {
			this.dateformcompleted = dateformcompleted;
		}
	}

	@XmlRootElement(name = "cfCustomerFeedback")
	public static class CustomerFeedback extends Base {
		private String feedback;
		public String getFeedback() {
			return feedback;
		}
		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
	}

}