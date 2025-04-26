package org.actum.logger;

import org.slf4j.Logger;

/**
 * Actum logger factory
 */
public class ActumLoggerFactory {

    /**
     * Standard system out trace
     * @return ActumLogger
     */
    public static ActumLogger systemOut(){
        return ((logLevel, message) -> System.out.println(message));
    }

    /**
     * Slf4j trace
     * @param logger logger
     * @return ActumLogger
     */
    public static ActumLogger slf4j(Logger logger){
        return ((logLevel, message) -> {
            switch (logLevel){
                case TRACE -> logger.trace(message);
                case INFO -> logger.info(message);
                case DEBUG -> logger.debug(message);
                case WARN -> logger.warn(message);
                case ERROR -> logger.error(message);
            }
        });
    }
}
