package au.gov.qld.fire.jms.web.module.setup;

import org.apache.struts.action.ActionForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DatabaseForm extends ActionForm
{

	/** serialVersionUID */

	private String name;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}