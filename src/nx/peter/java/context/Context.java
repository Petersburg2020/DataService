package nx.peter.java.context;

import nx.peter.java.context.model.Model;
import nx.peter.java.service.Service;

import java.io.Serializable;

public interface Context<C extends Context<C, W, R, S, F, FW, FR, P>,
        W extends Writer,
        R extends Reader,
        S extends Source,
        F extends DataFile,
        FW extends DataFile,
        FR extends DataFile,
        P extends Printer> extends Serializable {

    P getPrettyPrinter(F source);
    W getWriter(CharSequence path);
    R getReader(CharSequence path);
    W getWriter();
    R getReader();
    FW getWritable();
    FR getReadable();
    P getPrettyPrinter();
    S getSource();
    C getContext();
    C set(F source);
    <M extends Model> Service<C, W, R, S, P, FW, FR, F, M> getService(M model);

}
