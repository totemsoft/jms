package au.gov.qld.fire.exporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Component;

/**
 * @author Valeri Shibaev shibaevv@apollosoft.net
 */
@Component
public interface IExporter
{
    /**
     * 
     * @param is The source
     * @param groups - The groups (eg header, rows)
     * @return
     * @throws IOException
     */
    Map<String, List<String>> loadProperties(InputStream is, String[] groups) throws IOException;

    /**
     * 
     * @param bean
     * @param beanNames
     * @param transformer
     * @return a string array with each element as a separate entry.
     */
    String[] createLine(Object bean, List<String> beanNames, Transformer transformer);

    /**
     * 
     * @param bean
     * @param beanNames
     * @param transformer
     * @param functionTransformer
     * @return
     */
    String[] createLine(Object bean, List<String> beanNames, Transformer transformer, Transformer functionTransformer);

}