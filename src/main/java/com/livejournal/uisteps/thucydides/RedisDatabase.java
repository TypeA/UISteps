package com.livejournal.uisteps.thucydides;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.panferova
 */
public class RedisDatabase {

    public GetData redisConnect() {
        return new GetData();
    }

    public Jedis connectToRedis() {
        return new Jedis("localhost", Injectors.getInjector().getInstance(EnvironmentVariables.class).copy().getPropertyAsInteger("redis.port", 1222));
    }

    public class GetData {

        public String hashGetData(String hashTagble, String key) {
            String result = connectToRedis().hget(hashTagble, key);
            return result;
        }

        public Boolean existKey(String key) {
            return connectToRedis().exists(key);
        }
    }

}
