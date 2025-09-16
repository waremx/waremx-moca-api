package com.waremx.common.core.patterns;

import com.waremx.common.mox.uni.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Handler<T, E> {
    private Handler<T, E> next;
    private Context<T, E> context;

    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    public abstract Handler<T, E> execute(Context<T, E> context);

    @SafeVarargs
    public final static <T, E> Handler<T, E> link(Handler<T, E> first, Handler<T, E> ...handlers) {
        Handler<T, E> current = first;
        for(Handler<T, E> handler: handlers) {
            current.next = handler;
            current = handler;
        }
        return first;
    }

    public final Context build() {
        return this.context;
    }

    protected final Handler<T, E> checkNext(final Context<T, E> context) {
        if (context == null) {
            LOGGER.error("[ERROR]: {}", this.getClass().getSimpleName());
            return this;
        }
        if (this.next == null) {
            this.context = context;
            return this;
        }
        this.context = context;
        return this.next.execute(context);
    }
}
