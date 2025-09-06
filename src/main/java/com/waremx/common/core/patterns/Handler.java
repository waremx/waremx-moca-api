package com.waremx.common.core.patterns;


import com.waremx.common.mox.uni.Context;
import com.waremx.common.mox.listeners.Observe;

public abstract class Handler<T, E> {
    private Handler<T, E> next;
    private Context<T, E> context;

    public abstract Handler<T, E> execute(Context<T, E> context);

    @SafeVarargs
    public final static <T, E> Handler<T, E> link(Handler<T, E> first, Handler<T, E> ...handlers) {
        Handler<T, E> head = first;
        for(Handler<T, E> handler: handlers) {
            head.next = handler;
            head = handler;
        }
        return first;
    }

    public final <U> Observe<U> build(String observer) {
        return this.context.getObserve(observer);
    }

    protected final Handler<T, E> checkNext(final Context<T, E> context) {
        if (this.next == null) {
            this.context = context;
            return this;
        }
        return this.next.execute(context);
    }
}
