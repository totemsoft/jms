package au.gov.qld.fire.validation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import au.gov.qld.fire.validation.ValidationException.ValidationMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseValidator<T> implements Validator
{

	protected static final String FCA_PATTERN = "\\d{5}+-\\d{2}+";

	protected static final String POSTCODE_PATTERN = "\\d{4}+";

    /** validatorClass. */
    private final Class<T> validatorClass;

    @SuppressWarnings("unchecked")
    protected BaseValidator()
    {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType)
        {
            ParameterizedType pType = (ParameterizedType) type;
            this.validatorClass = (Class<T>) pType.getActualTypeArguments()[0];
        }
        else
        {
            this.validatorClass = null;
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
	public boolean supports(Class clazz)
    {
        return validatorClass.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors)
    {
        T entity = (T) target;
        List<ValidationMessage> messages = validate(entity);
        // TODO: add messages to errors (BindingResult)
        for (ValidationMessage message : messages)
        {
            //errors.add(..);
        }
    }

    public abstract List<ValidationMessage> validate(T entity);

}