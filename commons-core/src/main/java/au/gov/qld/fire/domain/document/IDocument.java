package au.gov.qld.fire.domain.document;

import java.util.Date;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface IDocument
{

    String getName();

    String getDescription();

    byte[] getContent();

    String getContentType();

    Date getCreatedDate();

}