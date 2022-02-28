package au.gov.qld.fire.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import au.gov.qld.fire.service.CacheService;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class CacheServiceImpl implements CacheService
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(CacheServiceImpl.class);

	//@Inject private EntityManager em;

    /** spring cacheManager */
    @Inject private CacheManager cacheManager;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.CacheService#findAll()
	 */
	@Override
	public List<Cache> findAll()
	{
		List<Cache> result = new ArrayList<Cache>();
        for (String cacheName : cacheManager.getCacheNames()) {
        	result.add(cacheManager.getCache(cacheName));
        }
        return result;
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.CacheService#clearCache(java.lang.String)
	 */
	@Override
	public void clearCache(String name)
	{
        // spring
        for (String cacheName : cacheManager.getCacheNames()) {
            if (StringUtils.isBlank(name)) {
                cacheManager.getCache(cacheName).clear();
            } else if (StringUtils.equals(name, cacheName)) {
                cacheManager.getCache(cacheName).clear();
                break;
            }
        }
//        // clear hibernate and spring cache
//        org.hibernate.SessionFactory sf = ((org.hibernate.Session) em.getDelegate()).getSessionFactory();
//        // hibernate collections
//        Map<String, org.hibernate.metadata.CollectionMetadata> collectionMetadata = sf.getAllCollectionMetadata();
//        for (String cacheName : collectionMetadata.keySet()) {
//            if (StringUtils.isBlank(name)) {
//                sf.evictCollection(cacheName);
//            } else if (StringUtils.equals(name, cacheName)) {
//                sf.evictCollection(cacheName);
//                break;
//            }
//        }
//        // hibernate entities
//        Map<String, org.hibernate.metadata.ClassMetadata> classMetadata = sf.getAllClassMetadata();
//        for (String cacheName : classMetadata.keySet()) {
//            if (StringUtils.isBlank(name)) {
//                sf.evictEntity(cacheName);
//            } else if (StringUtils.equals(name, cacheName)) {
//                sf.evictEntity(cacheName);
//                break;
//            }
//        }
//        // hibernate queries
//        if (StringUtils.isBlank(name)) {
//            sf.evictQueries();
//            //sf.evictQueries("non-default-ones");
//        }
	}

}