package nx.peter.java.pis.core;

import nx.peter.java.context.Printer;

/**
 * PrettyPrinter is a printer object for PIS documents
 * @param <N> the type of Node
 */
public interface PrettyPrinter<N extends Node, A extends Node.Attr> extends Printer {
    /**
     * Gets the node being printed
     * @return the printed node
     */
    N getNode();

    /**
     * The string-print-format of a node in a PIS document
     * @return print-format of node
     */
    String print();

    /**
     * Gets the source of this pretty printer
     * @return source of pretty printer
     */
    Source<? extends Source, ? extends Node, ? extends Node.Attr> getSource();

    /**
     * Gets the printer for the given node
     * @param node given node
     * @param <C> type of node
     * @return PrettyPrinter for provided node
     */
    <C extends Node, A extends Node.Attr> PrettyPrinter<C, A> printer(C node);

    /**
     * Checks if provided printer equals this
     * @param printer provided pretty-printer
     * @return true if they are equal
     */
    boolean equals(PrettyPrinter<? extends Node, ? extends Node.Attr> printer);
}
