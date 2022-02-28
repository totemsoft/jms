package au.gov.qld.fire.jms.validation;

import java.util.ArrayList;
import java.util.List;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.validation.BaseValidator;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaHistoryRequestValidator extends BaseValidator<Request>
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(Request entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
//        // validate fcaId
//        String fcaId = entity.getFcaId();
//        if (StringUtils.isBlank(fcaId))
//        {
//            errors.add(new ValidationMessage("fcaId", "[fcaId] can not be empty"));
//        }
//        else if (!fcaId.matches(FCA_PATTERN))
//        {
//            errors.add(new ValidationMessage("fcaId", "[fcaId] does not match pattern 'NNNNN-NN'"));
//        }
//        // validate isolationDate
//        String isolationDate = entity.getIsolationDate();
//        if (StringUtils.isBlank(isolationDate))
//        {
//            errors.add(new ValidationMessage("isolationDate", "[isolationDate] can not be empty"));
//        }
        return errors;
    }

}