package au.gov.qld.fire.service;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;

/**
 * Getting SessionFactory From EntityManagerFactory in Spring App context
 * http://code.google.com/p/arc-pocs/wiki/GettingSessionFactoryFromEntityManagerFactory
 * @author Rick Hightower
 */
public class CacheServiceFactoryConverter {

	public static SessionFactory getSessionFactory(EntityManagerFactory hemf) {
		return ((HibernateEntityManagerFactory) hemf).getSessionFactory();
	}

}