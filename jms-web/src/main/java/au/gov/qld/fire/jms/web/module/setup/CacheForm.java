package au.gov.qld.fire.jms.web.module.setup;

import org.apache.struts.action.ActionForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class CacheForm extends ActionForm
{

	/** serialVersionUID */
	private static final long serialVersionUID = 7312228801349444658L;

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