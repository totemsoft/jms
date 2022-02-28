package au.gov.qld.fire.jms.domain.mail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_ACTIVE_MAIL_REGISTER")
public class ActiveMailRegister
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "MAIL_REGISTER_ID")
    private Long mailRegisterId;

    @Column(name = "MAIL_TYPE_ID", nullable = true)
    private Long mailTypeId;

    @Temporal(TemporalType.DATE)
    @Column(name = "MAIL_REGISTER_DATE", nullable = true)
    private Date date;

    @Column(name = "MAIL_REGISTER_NO", nullable = true)
    private String mailRegisterNo;

    @Column(name = "MAIL_IN", nullable = false)
    @Type(type = "yes_no")
    private boolean mailIn;

    @Column(name = "RTS", nullable = false)
    @Type(type = "yes_no")
    private boolean rts;

    @Column(name = "FCA_NO", nullable = true)
    private String fcaId;

    @Column(name = "SAP_CUST_NO", nullable = true)
    private Long sapCustNo;

    @Column(name = "FIRST_NAME", nullable = true)
    private String firstName;

    @Column(name = "SURNAME", nullable = true)
    private String surname;

	/**
	 * @return the mailRegisterId
	 */
	public Long getMailRegisterId()
	{
		return mailRegisterId;
	}

	/**
	 * @return the mailTypeId
	 */
	public Long getMailTypeId()
	{
		return mailTypeId;
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * @return the mailRegisterNo
	 */
	public String getMailRegisterNo()
	{
		return mailRegisterNo;
	}

	/**
	 * @return the mailIn
	 */
	public boolean isMailIn()
	{
		return mailIn;
	}

	/**
	 * @return the rts
	 */
	public boolean isRts()
	{
		return rts;
	}

	/**
	 * @return the fcaId
	 */
	public String getFcaId()
	{
		return fcaId;
	}

	/**
	 * @return the sapCustNo
	 */
	public Long getSapCustNo()
	{
		return sapCustNo;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return the surname
	 */
	public String getSurname()
	{
		return surname;
	}

}