package au.gov.qld.fire.domain.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaDocument implements IDocument {

    private String name;

    private String pathname;

    private String description;

    private byte[] content;

    private String contentType;

    private Date createdDate;

    private List<FcaDocument> children;

	public FcaDocument()
	{
		super();
	}

	public FcaDocument(String name, String pathname, String contentType, Date createdDate)
	{
		this.name = name;
		this.pathname = pathname;
		this.contentType = contentType;
		this.createdDate = createdDate;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the pathname
	 */
	public String getPathname()
	{
		return pathname;
	}

	public void setPathname(String pathname)
	{
		this.pathname = pathname;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public boolean isDir()
	{
		return contentType == null;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return the children
	 */
	public List<FcaDocument> getChildren()
	{
		if (children == null) {
			children = new ArrayList<FcaDocument>();
		}
		return children;
	}

	public void setChildren(List<FcaDocument> children)
	{
		this.children = children;
	}

}