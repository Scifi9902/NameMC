package xyz.damt.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HandlerManager {

    private final Map<Class<? extends IHandler>, IHandler> handlerMap;

    public HandlerManager() {
        this.handlerMap = new HashMap<>();
    }

    public <T> T getHandler(Class<T> clazz) {
        if (this.handlerMap.containsKey(clazz)) {
            return (T) this.handlerMap.get(clazz);
        }
        return null;
    }

    public Collection<IHandler> getHandlers() {
        return this.handlerMap.values();
    }

    public void registerHandler(IHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Failed to load " + handler.getClass().getName() + " due to the instance being null.");
        }

        handler.load();
        this.handlerMap.put(handler.getClass(), handler);
    }
}
