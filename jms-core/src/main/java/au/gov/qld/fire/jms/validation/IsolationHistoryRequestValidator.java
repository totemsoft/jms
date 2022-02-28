package au.gov.qld.fire.jms.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.jms.domain.isolation.IsolationHistoryRequest;
import au.gov.qld.fire.validation.BaseValidator;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationHistoryRequestValidator extends BaseValidator<IsolationHistoryRequest>
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(IsolationHistoryRequest entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        // validate fcaId
        String fcaId = entity.getFcaId();
        if (StringUtils.isBlank(fcaId))
        {
            errors.add(new ValidationMessage("fcaId", "[fcaId] can not be empty"));
        }
        else if (!fcaId.matches(FCA_PATTERN))
        {
            errors.add(new ValidationMessage("fcaId", "[fcaId] does not match pattern 'NNNNN-NN'"));
        }
        // validate input
        String input = entity.getInput();
        if (StringUtils.isBlank(input))
        {
            errors.add(new ValidationMessage("input", "[input] can not be empty"));
        }
        // validate isolationDate
        String isolationDate = entity.getIsolationDate();
        if (StringUtils.isBlank(isolationDate))
        {
            errors.add(new ValidationMessage("isolationDate", "[isolationDate] can not be empty"));
        }
        // validate deIsolationDate and timeIsolated
        String deIsolationDate = entity.getDeIsolationDate();
        String timeIsolated = entity.getTimeIsolated();
        if (StringUtils.isBlank(deIsolationDate) && StringUtils.isBlank(timeIsolated))
        {
            errors.add(new ValidationMessage("deIsolationDate and timeIsolated", "[deIsolationDate and timeIsolated] can not be empty"));
        }
        // validate postcode
        String postcode = entity.getPostcode();
        if (StringUtils.isBlank(postcode))
        {
            //errors.add(new ValidationMessage("postcode", "[postcode] can not be empty"));
        }
        else if (!postcode.matches(POSTCODE_PATTERN))
        {
            errors.add(new ValidationMessage("postcode", "[postcode] does not match pattern 'NNNN'"));
        }
        return errors;
    }

}