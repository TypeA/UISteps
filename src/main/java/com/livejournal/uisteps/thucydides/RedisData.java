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

/**
 *
 * @author m.panferova
 */
public class RedisData extends RedisDatabase {

    public WorkWithRedis workWithRedis() {
        return new WorkWithRedis();
    }

    public class WorkWithRedis extends RedisData {

        public Announce announce() {
            return new Announce();
        }
    }

    public class Announce extends RedisData {

        public int getIdMainAnnounce() {
            int idSlot = 1;
            for (int i = 1; i < 4; i++) {
                if (redisConnect().existKey("disc.ann."+i)) {
                    String mainSlot = redisConnect().hashGetData("disc.ann." + i, "main");
                    if (mainSlot.equals("1")) {
                        idSlot = i;
                        break;
                    }
                }
            }
            return idSlot;
        }
    }

}
