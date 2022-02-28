package au.gov.qld.fire.domain;

import au.gov.qld.fire.util.BeanUtils;

/**
 * Create convenient alias for Pair<String, String>
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StringPair extends Pair<String, String> implements Comparable<StringPair> {

    /** serialVersionUID */
    private static final long serialVersionUID = -1031277650212828243L;

    public StringPair() {
        super();
    }
    
    public StringPair(String value) {
        this(value, value);
    }

    public StringPair(String key, String value) {
        super(key, value);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.domain.Pair#setKey(java.lang.Object)
     */
    @Override
    public void setKey(String key) {
        super.setKey(key);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.domain.Pair#setValue(java.lang.Object)
     */
    @Override
    public void setValue(String value) {
        super.setValue(value);
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(StringPair other) {
        int result = BeanUtils.compare(getKey(), other.getKey());
        if (result == 0) {
            result = BeanUtils.compare(getValue(), other.getValue());
        }
        return result;
    }

}