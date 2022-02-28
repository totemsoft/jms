package au.gov.qld.fire.converters;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.FactoryBean;

/**
 * provides pre-configured JACKSON mapper (@see app-servlet.xml).
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

	private final ObjectMapper mapper;

	public ObjectMapperFactoryBean() {
		mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.AUTO_DETECT_FIELDS, false);
		mapper.configure(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS, false);
		mapper.configure(SerializationConfig.Feature.AUTO_DETECT_GETTERS, false);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.AUTO_DETECT_CREATORS, false);
        mapper.configure(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, false);
        mapper.configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
	}

	public ObjectMapper getObject() throws Exception {
		return mapper;
	}

	public Class<? extends ObjectMapper> getObjectType() {
		return ObjectMapper.class;
	}

	public boolean isSingleton() {
		return false;
	}

}