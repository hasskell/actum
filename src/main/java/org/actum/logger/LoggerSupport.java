package org.actum.logger;

import org.slf4j.Logger;

public interface LoggerSupport <T>{

    T withLogger(ActumLogger logger);

    T trace();

    default T withSystemOut() {
        return withLogger(ActumLoggerFactory.systemOut());
    }

    default T withSlf4j(Logger logger) {
        return withLogger(ActumLoggerFactory.slf4j(logger));
    }
}
