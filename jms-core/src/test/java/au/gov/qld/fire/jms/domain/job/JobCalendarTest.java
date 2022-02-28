package au.gov.qld.fire.jms.domain.job;

import junit.framework.TestCase;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobCalendarTest extends TestCase
{

    /**
     * Test method for {@link au.gov.qld.fire.jms.domain.job.JobCalendar#JobCalendar(int, int)}.
     */
    public final void testJobCalendar()
    {
        JobCalendar jobCalendar = new JobCalendar(2009, 0);
        assertNotNull(jobCalendar);
    }

}
