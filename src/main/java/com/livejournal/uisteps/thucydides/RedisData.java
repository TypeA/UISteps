package com.livejournal.uisteps.thucydides;

/**
 *
 * @author m.panferova
 */
public class RedisData extends RedisDatabase {

    public Discovery discovery() {
        return new Discovery();
    }

    public class Discovery extends RedisData {

        public int getMainAnnounceId() {
            int idSlot = 1;
            for (int i = 1; i < 4; i++) {
                if (redisConnect().existKey("disc.ann." + i)) {
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
