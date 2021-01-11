package com.lai.app.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ProtostuffSerializer<T> implements RedisSerializer<T> {

    private RuntimeSchema<T> schema;

    public ProtostuffSerializer(Class<T> clazz){
        schema = RuntimeSchema.createFrom(clazz);
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return ProtostuffIOUtil.toByteArray(t, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        T t = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        return t;
    }
}
