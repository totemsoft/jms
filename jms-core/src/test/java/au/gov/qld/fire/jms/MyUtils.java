package au.gov.qld.fire.jms;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class MyUtils
{
    public static void main(String[] args)
    {
        try
        {
            generateHibernate();
            System.exit(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    public static void generateHibernate()
    {
        Collection<File> files = FileUtils.listFiles(new File(
            "src/main/resources/au/gov/qld/fire/jms/hibernate"), new String[] {"hbm.xml"}, true);
        for (File file : files)
        {
            String parent = file.getParentFile().getName();
            parent = "hibernate".equals(parent) ? "" : parent + "/";
            System.out.println("        <mapping resource=\"au/gov/qld/fire/jms/hibernate/"
                + parent + file.getName() + "\"/>");
        }
    }
}