package au.gov.qld.fire.domain;


/**
 * Create convenient alias for Pair<Long, String>
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BasePair extends Pair<Long, String> {

	/** serialVersionUID */
	private static final long serialVersionUID = 3723614226721288363L;

	public static final BasePair NEW = new BasePair(0L, "* Add New *");

    public BasePair(Long key, String value) {
        super(key, value);
    }

    public BasePair(Long key) {
        super(key, key == null ? null : "" + key);
    }

    public BasePair() {
        super();
    }

}