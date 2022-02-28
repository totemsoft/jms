package au.gov.qld.fire.converters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TextHttpInputMessage implements HttpInputMessage
{
    /**  */
    private final byte[] body;

    private HttpHeaders headers;

    public TextHttpInputMessage(byte[] body)
    {
        this.body = body;
    }

    public InputStream getBody() throws IOException
    {
        return new ByteArrayInputStream(body);
    }

    public HttpHeaders getHeaders()
    {
        if (this.headers == null)
        {
            this.headers = new HttpHeaders();
            //this.headers.add("Accept", "application/json");
        }
        return this.headers;
    }

}