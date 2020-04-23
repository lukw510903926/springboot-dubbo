/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.dubbo.common.util.log;

import java.util.Objects;
import java.util.function.Supplier;
import org.slf4j.Logger;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020/1/30 3:21 下午
 */
public final class LogUtils {

    private static final LogUtils LOG_UTIL = new LogUtils();

    private LogUtils() {

    }

    /**
     * getInstance.
     *
     * @return LogUtils
     */
    public static LogUtils getInstance() {
        return LOG_UTIL;
    }


    /**
     * debug log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void debug(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, supplier.get());
        }
    }

    /**
     * debug log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void debug(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(Objects.toString(supplier.get()));
        }
    }

    /**
     * info log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void info(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isInfoEnabled()) {
            logger.info(format, supplier.get());
        }
    }

    /**
     * info log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void info(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isInfoEnabled()) {
            logger.info(Objects.toString(supplier.get()));
        }
    }

    /**
     * error log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void error(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isErrorEnabled()) {
            logger.error(format, supplier.get());
        }
    }

    /**
     * error log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void error(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isErrorEnabled()) {
            logger.error(Objects.toString(supplier.get()));
        }
    }

    /**
     * warn log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void warn(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, supplier.get());
        }
    }

    /**
     * warn log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void warn(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isWarnEnabled()) {
            logger.warn(Objects.toString(supplier.get()));
        }
    }
}
