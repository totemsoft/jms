package au.gov.qld.fire.jms.uaa.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.BaseModel;

/**
 * TODO: <T extends BaseModel<ID>>
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class UaaBaseDaoImpl<T extends BaseModel<?>> extends BaseDaoImpl<T>
{

    @PersistenceContext//(unitName = "uaa")
    private EntityManager entityManager;

    protected EntityManager getEntityManager()
    {
        return entityManager;
    }

}