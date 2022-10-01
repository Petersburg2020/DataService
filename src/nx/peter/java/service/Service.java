package nx.peter.java.service;

import nx.peter.java.context.*;
import nx.peter.java.context.model.Model;

import java.io.Serializable;

public interface Service<C extends Context, W extends Writer,
        R extends Reader, S extends Source, P extends Printer, DW extends DataFile,
        DR extends DataFile, D extends DataFile, M extends Model> {
    void start();
    void stop();
    void dispose();
    boolean hasStopped();
    boolean hasStarted();
    boolean isRunning();
    boolean equals(Service service);
    void getModel(Callback<M> callback);
    void getContext(Callback<C> callback);
    void getDataFile(Callback<D> callback);
    void getPrinter(Callback<P> callback);
    void getWriter(Callback<W> callback);
    void getReader(Callback<R> callback);
    OnServiceRunListener<C, M> getListener();
    void setListener(OnServiceRunListener<C, M> listener);


    interface OnServiceRunListener<C extends Context, M extends Model> {
        void onRun(C context, M model);
        void onStop(C context, M model);
    }

    interface Callback<I extends Serializable> {
        void onReceive(I item);
    }

    abstract class Builder<B extends Builder,
            SR extends Service,
            C extends Context, W extends Writer,
            R extends Reader, S extends Source, P extends Printer, DW extends DataFile,
            DR extends DataFile, D extends DataFile, M extends Model> {
        private C context;
        private M model;

        public Builder() {}

        public Builder(M model) {
            setModel(model);
        }

        protected Builder(C context, M model) {
            this(model);
            setContext(context);
        }

        public B setContext(C context) {
            this.context = context;
            return (B) this;
        }

        public B setModel(M model) {
            this.model = model;
            return (B) this;
        }

        public abstract SR build();

    }
}
