package com.qingwenwei.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		JedisPoolConfig poolConfig = new JedisPoolConfig();
//		poolConfig.setMaxTotal(5);
//		poolConfig.setTestOnBorrow(true);
//		poolConfig.setTestOnReturn(true);
//
//		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
//		connectionFactory.setUsePool(true);
//		connectionFactory.setHostName("localhost");
//		connectionFactory.setPort(6379);
//
//		return connectionFactory;
//	}
//
//	@Bean
//	public RedisTemplate<String, Post> redisTemplate() {
//		RedisTemplate<String, Post> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		redisTemplate.setEnableTransactionSupport(true);
//		return redisTemplate;
//	}
//
//	@Bean
//	public StringRedisTemplate stringRedisTemplate() {
//		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
//		stringRedisTemplate.setEnableTransactionSupport(true);
//		return stringRedisTemplate;
//	}

	// @Bean
	// JedisConnectionFactory jedisConnectionFactory() {
	// return new JedisConnectionFactory();
	// }
	//
	// @Bean
	// public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory
	// factory) {
	// RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	// template.setConnectionFactory(jedisConnectionFactory());
	// template.setKeySerializer(new StringRedisSerializer());
	// template.setValueSerializer(new RedisObjectSerializer());
	// return template;
	// }
	//
	// class RedisObjectSerializer implements RedisSerializer<Object> {
	// private Converter<Object, byte[]> serializer = new SerializingConverter();
	// private Converter<byte[], Object> deserializer = new
	// DeserializingConverter();
	//
	// final byte[] EMPTY_ARRAY = new byte[0];
	//
	// public Object deserialize(byte[] bytes) {
	// if (isEmpty(bytes)) {
	// return null;
	// }
	// try {
	// return deserializer.convert(bytes);
	// } catch (Exception ex) {
	// throw new SerializationException("Cannot deserialize", ex);
	// }
	// }
	//
	// public byte[] serialize(Object object) {
	// if (object == null) {
	// return EMPTY_ARRAY;
	// }
	// try {
	// return serializer.convert(object);
	// } catch (Exception ex) {
	// return EMPTY_ARRAY;
	// }
	// }
	//
	// private boolean isEmpty(byte[] data) {
	// return (data == null || data.length == 0);
	// }
	// }

}
