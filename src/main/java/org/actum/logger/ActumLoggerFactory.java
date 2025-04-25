package org.actum.logger;

import org.slf4j.Logger;

public class ActumLoggerFactory {

    public static ActumLogger systemOut(){
        return ((logLevel, message) -> System.out.println(message));
    }

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
