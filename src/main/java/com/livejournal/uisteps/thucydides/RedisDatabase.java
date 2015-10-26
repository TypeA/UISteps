/*
 * Copyright 2015 m.panferova.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.thucydides.RedisData.WorkWithRedis;
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

        return new Jedis("localhost", Injectors.getInjector().getInstance(EnvironmentVariables.class).copy().getPropertyAsInteger("redis.port",1222));
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
