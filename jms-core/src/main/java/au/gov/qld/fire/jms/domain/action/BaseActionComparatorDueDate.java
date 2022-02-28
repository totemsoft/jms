package au.gov.qld.fire.jms.domain.action;

import java.util.Comparator;

/**
 * BaseAction Comparator by DueDate.
 * Sort todo actions in ascending order
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BaseActionComparatorDueDate implements Comparator<BaseAction>
{

	/** INSTANCE */
	public static final BaseActionComparatorDueDate INSTANCE = new BaseActionComparatorDueDate();

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(BaseAction a1, BaseAction a2)
    {
        return a1.getDueDate().compareTo(a2.getDueDate());
    }
}