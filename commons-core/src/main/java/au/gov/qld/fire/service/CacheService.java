package au.gov.qld.fire.service;

import java.util.List;

import org.springframework.cache.Cache;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface CacheService
{

	List<Cache> findAll();

	void clearCache(String name);

}