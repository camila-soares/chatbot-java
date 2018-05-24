package services;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.CompressionCodec;
import io.lettuce.core.codec.RedisCodec;
import play.Logger;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by marcio on 2/3/17.
 */
@Singleton
public class CacheApi {
    private Map<String, Object> memoryCache;

    @Inject
    public CacheApi(ApplicationLifecycle applicationLifecycle) {
   //     StatefulRedisConnection<String, Object>  CompressionCodec.CompressionType.GZIP));




    }

    /**
     * Saves object on cache with key for a default period of 30 minutes
     *
     * @param key    Object key
     * @param object Object that implements serializable
     */


    /**
     * Saves given object on cache with key for a period of seconds
     *
     * @param key        Object key
     * @param object     Object that implements serializable
     * @param expiration Expiration in seconds
     */


    /**
     * Returns object from cache with key
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")


    public class SerializedObjectCodec implements RedisCodec<String, Object> {
        private Charset charset = Charset.forName("UTF-8");

        @Override
        public String decodeKey(ByteBuffer bytes) {
            return charset.decode(bytes).toString();
        }

        @Override
        public Object decodeValue(ByteBuffer bytes) {
            try {
                byte[] array = new byte[bytes.remaining()];
                bytes.get(array);
                ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(array));
                return is.readObject();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public ByteBuffer encodeKey(String key) {
            return charset.encode(key);
        }

        @Override
        public ByteBuffer encodeValue(Object value) {
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bytes);
                os.writeObject(value);
                return ByteBuffer.wrap(bytes.toByteArray());
            } catch (IOException e) {
                return null;
            }
        }
    }
}
