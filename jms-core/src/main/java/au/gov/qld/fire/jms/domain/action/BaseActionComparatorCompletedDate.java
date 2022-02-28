package au.gov.qld.fire.jms.domain.action;

import java.util.Comparator;

/**
 * BaseAction Comparator by CompletedDate.
 * Sort completed actions in descending order
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BaseActionComparatorCompletedDate implements Comparator<BaseAction>
{

	/** INSTANCE */
	public static final BaseActionComparatorCompletedDate INSTANCE = new BaseActionComparatorCompletedDate();

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(BaseAction a1, BaseAction a2)
    {
        return a2.getCompletedDate().compareTo(a1.getCompletedDate());
    }
}