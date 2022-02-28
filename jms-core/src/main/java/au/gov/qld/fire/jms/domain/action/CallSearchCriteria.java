package au.gov.qld.fire.jms.domain.action;

import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class CallSearchCriteria extends TodoSearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = 4643539793571292602L;

	/** ACTION_TYPE */
    public static final ActionTypeEnum ACTION_TYPE = ActionTypeEnum.CALL;

	public CallSearchCriteria()
	{
		setActionTypeId(ACTION_TYPE.getId());
	}

}