package au.gov.qld.fire.jms.domain.refdata;

import org.apache.commons.lang.StringUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionCodeSearchCriteria
{

    private Boolean active = true;

    private String actionTypes;

    /**
	 * @return the active
	 */
	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	/**
     * @return the actionTypes
     */
    public String getActionTypes()
    {
        if (StringUtils.isBlank(actionTypes))
        {
            actionTypes = null;
        }
        return actionTypes;
    }

    /**
     * @param actionTypes the actionTypes to set
     */
    public void setActionTypes(String actionTypes)
    {
        this.actionTypes = actionTypes;
    }

}