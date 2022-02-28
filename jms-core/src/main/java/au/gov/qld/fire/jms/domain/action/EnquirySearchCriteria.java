package au.gov.qld.fire.jms.domain.action;

import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EnquirySearchCriteria extends TodoSearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = 1638853144678893653L;

    /** ACTION_TYPE */
    public static final ActionTypeEnum ACTION_TYPE = ActionTypeEnum.DIARY;

	public EnquirySearchCriteria()
	{
		setActionTypeId(ACTION_TYPE.getId());
	}

}