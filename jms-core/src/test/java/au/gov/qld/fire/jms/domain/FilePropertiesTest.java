package au.gov.qld.fire.jms.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FilePropertiesTest extends TestCase
{
    private static final Logger LOG = Logger.getLogger(FilePropertiesTest.class);

    private List<Class> describedClasses = new ArrayList<Class>();

    public final void testProperties()
    {
        try
        {
            Contact contact = new Contact();
            describedClasses.add(Contact.class);
            describe(contact, "contact");

            Address address = new Address();
            describedClasses.add(Address.class);
            describe(address, "address");

            SapHeader sapHeader = new SapHeader();
            describedClasses.add(SapHeader.class);
            describe(sapHeader, "sapHeader");

            User user = new User();
            describedClasses.add(User.class);
            describe(user, "user");

            JobRequest jobRequest = new JobRequest();
            describedClasses.add(JobRequest.class);
            describe(jobRequest, "jobRequest");

            File file = new File();
            describedClasses.add(File.class);
            describe(file, "file");
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     *
     * @param bean
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    private void describe(Object bean, String parent) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException,
        InstantiationException
    {
        if (bean == null)
        {
            return;
        }
        Class beanClass = bean.getClass();

        Map<String, Object> props = BeanUtils.describe(bean);
        Set<Map.Entry<String, Object>> entries = props.entrySet();
        LOG.info(">>>>>>>>>> Describing " + beanClass.getSimpleName() + " properties: <<<<<<<<<<");
        for (Map.Entry<String, Object> entry : entries)
        {
            String name = entry.getKey();
            if ("class".equals(name))
            {
                continue;
            }

            Object value = entry.getValue();
            Field field;
            try
            {
                field = getField(beanClass, name);
                if (field == null)
                {
                    continue;
                }
            }
            catch (Exception e)
            {
                LOG.warn(e.getClass().getSimpleName() + ": " + e.getMessage());
                continue;
            }

            Class clazz = field.getType();
            Package pckg = clazz.getPackage();
            String fullName = parent + "." + name;
            if (pckg != null && pckg.getName().startsWith("au.gov.qld.fire.jms.domain"))
            {
                if (!describedClasses.contains(clazz))
                {
                    describedClasses.add(clazz);
                    describe(clazz.newInstance(), fullName);
                }
                else
                {
                    LOG.info(fullName + " [of type " + clazz.getSimpleName() + "]...");
                    System.out.println(fullName + " [of type " + clazz.getSimpleName() + "]...");
                }
            }
            else if (clazz.isArray())
            {
                describe(clazz.getSuperclass().newInstance(), fullName);
            }
            else if (Collection.class.isAssignableFrom(clazz))
            {
                System.out.println(fullName + " [of type " + clazz.getSimpleName() + "].. ");
                if (value != null)
                {
                    if (Collection.class.isAssignableFrom(value.getClass()))
                    {
                        Collection c = (Collection) value;
                        describe(c.isEmpty() ? null : c.toArray()[0], fullName);
                    }
                    else
                    {
                        LOG.warn(fullName + "," + clazz.getName() + "," + value + ","
                            + value.getClass().getName());
                    }
                }
            }
            else if (Map.class.isAssignableFrom(clazz))
            {
                //TODO:
            }
            else
            {
                LOG.info(fullName);
                if (fullName.endsWith(".id") || fullName.endsWith(".fileId")
                    || fullName.endsWith(".logicallyDeleted"))
                {
                    continue;
                }
                System.out.println(fullName);
            }
        }
    }

    /**
     *
     * @param bean
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    private Field getField(Class clazz, String name) throws NoSuchFieldException
    {
        if (clazz == null)
        {
            return null;
        }

        try
        {
            return clazz.getDeclaredField(name);
        }
        catch (NoSuchFieldException e)
        {
            //check super class
            if (!Class.class.equals(clazz))
            {
                clazz = clazz.getSuperclass();
                return getField(clazz, name);
            }
            throw e;
        }
    }

}