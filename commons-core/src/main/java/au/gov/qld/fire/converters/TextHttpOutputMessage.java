package au.gov.qld.fire.converters;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TextHttpOutputMessage implements HttpOutputMessage
{

    private final OutputStream outputStream;

    private HttpHeaders headers;

    public TextHttpOutputMessage(OutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

    /* (non-Javadoc)
     * @see org.springframework.http.HttpOutputMessage#getBody()
     */
    public OutputStream getBody() throws IOException
    {
        return outputStream;
    }

    /* (non-Javadoc)
     * @see org.springframework.http.HttpMessage#getHeaders()
     */
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