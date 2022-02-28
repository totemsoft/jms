package au.gov.qld.fire.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import au.gov.qld.fire.util.DateUtils;


/**
 * Shared WebBindingInitializer for custom editors.
 * Require Spring 2.5.1+
 * https://jira.springsource.org/browse/SPR-4182 (Apply custom editors to @RequestParam parameters)
 */
public class BindingInitializer implements WebBindingInitializer
{

    /* (non-Javadoc)
     * @see org.springframework.web.bind.support.WebBindingInitializer#initBinder(org.springframework.web.bind.WebDataBinder, org.springframework.web.context.request.WebRequest)
     */
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        //
        SimpleDateFormat dateFormat = DateUtils.DEFAULT_DATE;
        //dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        //
        binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(false));

        // to actually be able to convert Multipart instance to byte[] we have to register a custom editor
        //binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

}