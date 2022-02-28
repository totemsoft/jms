package au.gov.qld.fire.jms.rule.impl;

import java.util.ArrayList;
import java.util.List;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.rule.BusinessRule;
import au.gov.qld.fire.jms.rule.RuleException;
import au.gov.qld.fire.domain.security.SecurityGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BusinessRuleImpl implements BusinessRule
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.rule.BusinessRule#canCloseJob(au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.job.Job, au.gov.qld.fire.jms.domain.action.JobAction)
     */
    public void canCloseJob(User user, Job job, JobAction jobAction) throws RuleException
    {
        SecurityGroup securityGroup = user.getSecurityGroup();
        SecurityGroup closeJobSecurityGroup = job.getJobType().getCloseJobSecurityGroup();

        if (jobAction.getCreatedBy().equals(user))
        {
            throw new RuleException("A person who requests a Close Job cannot authorise the Close Job.");
        }
        if (!securityGroup.equals(closeJobSecurityGroup))
        {
            String name = closeJobSecurityGroup == null ? "unknown" : closeJobSecurityGroup.getName();
            throw new RuleException("Only a person with SecurityGroup [" + name + "] can authorise the Close Job.");
        }
        //check Job Action rule
        List<JobAction> jobActionsToDo = new ArrayList<JobAction>();
        List<JobAction> jobActionsCompleted = new ArrayList<JobAction>();
        ConvertUtils.splitJobActions(job, jobActionsToDo, jobActionsCompleted);
        if (jobActionsToDo.size() != 1)
        {
            throw new RuleException("Active Job should have can have only one 'Action To Do' - to be Closed.");
        }
    }

}