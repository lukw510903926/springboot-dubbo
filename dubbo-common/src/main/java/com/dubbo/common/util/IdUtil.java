package com.dubbo.common.util;

import org.apache.commons.lang3.StringUtils;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 11:09
 **/
public class IdUtil {

    private static final ThreadLocal<Integer> SEED = ThreadLocal.withInitial(() -> 0);

    private static final String MACHINE_ID;

    static {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        MACHINE_ID = hardwareAbstractionLayer.getNetworkIFs()[0].getMacaddr().replace(":", "").toUpperCase();
    }

    private IdUtil(){}

    public static String uuid() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generate(String prefix) {

        if (!StringUtils.isBlank(prefix) && prefix.length() <= 4) {
            int seed = SEED.get();
            int random = ThreadLocalRandom.current().nextInt(0, 9999);
            Serializable[] tempArray = {System.currentTimeMillis(), random, Thread.currentThread().getId(), MACHINE_ID, seed};
            String tmp = StringUtils.join(tempArray);
            int over = tmp.length() - (32 - prefix.length());
            if (over > 0) {
                tmp = StringUtils.substring(tmp, over);
            } else {
                tmp = StringUtils.leftPad(tmp, 32 - prefix.length(), '0');
            }

            ++seed;
            if (seed > 9999) {
                seed = 0;
            }
            SEED.set(seed);
            return prefix + tmp;
        } else {
            throw new IllegalArgumentException("prefix illegal");
        }
    }
}
