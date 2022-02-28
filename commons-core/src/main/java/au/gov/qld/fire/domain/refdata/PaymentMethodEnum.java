package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum PaymentMethodEnum
{

	NONE("Not Required"),
	CREDIT_CARD("Credit Card"),
	PURCHASE_ORDER("Purchase Order"),
	CHEQUE("Cheque"),
	BPAY("BPay"),
	;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(PaymentMethodEnum.class);

	private final String name;

	private PaymentMethodEnum(String name)
	{
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return ordinal();
	}

    /**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the name
	 */
	public String getCode()
	{
		return name();
	}

    /**
     *
     * @param name
     * @return
     */
    public static PaymentMethodEnum findByName(String name)
    {
        for (PaymentMethodEnum item : values())
        {
            if (item.name.equals(name))
            {
                return item;
            }
        }
        LOG.warn("Unhandled PaymentMethod [" + name + "]");
        return null;
    }

}