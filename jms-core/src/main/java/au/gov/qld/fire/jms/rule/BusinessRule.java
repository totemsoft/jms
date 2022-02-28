package au.gov.qld.fire.jms.rule;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.job.Job;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface BusinessRule
{

    /**
     * Check if user can be close this job.
     * @param user
     * @param job
     * @param jobAction
     * @throws RuleException
     */
    void canCloseJob(User user, Job job, JobAction jobAction) throws RuleException;

}