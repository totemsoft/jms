package au.gov.qld.fire.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class Pair<T1, T2> implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1246263347855307353L;

    private T1 key;

    private T2 value;

    protected Pair() {
        super();
    }

    public Pair(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the key
     */
    public T1 getKey() {
        return key;
    }

    public void setKey(T1 key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public T2 getValue() {
        return value;
    }

    public void setValue(T2 value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        if (this == other) {
            return true; // super implementation
        }
        if (other != null) {
            Pair<T1, T2> castOther = (Pair<T1, T2>) other;
            return new EqualsBuilder().append(this.key, castOther.key).append(this.value, castOther.value).isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder().append(this.key).append(this.value).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("key", this.key).append("value", this.value).toString();
    }

}