package au.gov.qld.fire.domain.envers;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RevisionListener implements org.hibernate.envers.RevisionListener {

    //private static final Logger LOG = Logger.getLogger(RevisionListener.class);

	public void newRevision(Object revision) {
		//RevisionEntity entity = (RevisionEntity) revision;
	    // set application specific data
	}

    @PrePersist public void prePersist(RevisionEntity entity) {

    }

    @PostPersist public void postPersist(RevisionEntity entity) {

    }

}