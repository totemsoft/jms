package au.gov.qld.fire.domain;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AuditableListener {

    //@PersistenceContext
    //private EntityManager em;

    /**
     * Set required AbstractAuditable properties (id == null)
     * @param auditable
     */
    @PrePersist
    public void prePersist(Auditable<?> auditable)
    {
        Date now = ThreadLocalUtils.getDate();
        User user = ThreadLocalUtils.getUser();
        if (auditable.getCreatedDate() == null)
        {
            auditable.setCreatedDate(now);
        }
        if (auditable.getCreatedBy() == null)
        {
            auditable.setCreatedBy(user);
        }
    }

    /**
     * Set required AbstractAuditable properties (id != null)
     * @param auditable
     */
    @PreUpdate
    public void preUpdate(Auditable<?> auditable)
    {
        Date now = ThreadLocalUtils.getDate();
        User user = ThreadLocalUtils.getUser();
        auditable.setUpdatedDate(now);
        auditable.setUpdatedBy(user);
    }

//    @PreRemove
//    public void preRemove(Auditable<?> auditable) {
//        Date now = ThreadLocalUtils.getDate();
//        User user = ThreadLocalUtils.getUser();
//        auditable.setUpdatedDate(now);
//        auditable.setUpdatedBy(user);
//        auditable.setLogicallyDeleted(true);
//    }

}