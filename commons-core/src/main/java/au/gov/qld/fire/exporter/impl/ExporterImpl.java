package au.gov.qld.fire.exporter.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;

import au.gov.qld.fire.exporter.IExporter;

/**
 * @author Valeri Shibaev shibaevv@apollosoft.net
 */
public class ExporterImpl implements IExporter
{

    /* (non-Javadoc)
     * @see com.xcelerate.elixir.tasks.ExportTask#loadProperties(
     * java.io.InputStream, String[])
     */
    public Map<String, List<String>> loadProperties(InputStream is, String[] groups)
        throws IOException
    {
        Map<String, List<String>> result = new Hashtable<String, List<String>>();
        for (String group : groups)
        {
            result.put(group, new ArrayList<String>());
        }
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        String s;
        while ((s = reader.readLine()) != null)
        {
            if (s == null || s.trim().length() == 0 || s.startsWith("#"))
            {
                continue;
            }
            for (String group : groups)
            {
                if (s.startsWith(group) && s.indexOf('=') > 0)
                {
                    result.get(group).add(s.substring(s.indexOf('=') + 1));
                    break;
                }
            }
        }
        reader.close();
        return result;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.exporter.IExporter#createLine(
     * java.lang.Object, java.util.List, org.apache.commons.collections.Transformer)
     */
    public String[] createLine(Object bean, List<String> beanNames, Transformer transformer)
    {
        return createLine(bean, beanNames, transformer, null);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.exporter.IExporter#createLine(java.lang.Object, java.util.List, org.apache.commons.collections.Transformer, org.apache.commons.collections.Transformer)
     */
    public String[] createLine(Object bean, List<String> beanNames, Transformer transformer,
        Transformer functionTransformer)
    {
        List<String> result = new ArrayList<String>(beanNames.size());
        for (String name : beanNames)
        {
            if (name == null || name.trim().length() == 0)
            {
                result.add(null);
                continue;
            }
            try
            {
                Object p;
                if (name.contains("()") && functionTransformer != null)
                {
                    p = functionTransformer.transform(name);
                }
                else
                {
                    p = PropertyUtils.getProperty(bean, name);
                }
                p = transformer == null ? p : transformer.transform(p);
                result.add(p == null ? null : p.toString());
            }
            catch (NestedNullException ignore)
            {
                result.add(null); // nested bean referenced is null
            }
            catch (Exception e)
            {
                result.add(e.getMessage());
            }
        }
        return (String[]) result.toArray(new String[0]);
    }

}