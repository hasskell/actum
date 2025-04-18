package org.actum.core;

public interface Flow {
    void execute();
    boolean matched();
}
