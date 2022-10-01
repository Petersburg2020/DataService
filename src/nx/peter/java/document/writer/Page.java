package nx.peter.java.document.writer;

import nx.peter.java.document.core.page.body.Element;
import nx.peter.java.document.writer.page.Body;
import nx.peter.java.document.writer.page.body.Heading;

import java.util.List;

public interface Page<P extends Page, B extends Body, H extends Heading> extends
        Document.Source<P>,
        nx.peter.java.document.core.Page<P, B, H> {
    P set(nx.peter.java.document.core.Page page);
    P set(nx.peter.java.document.core.page.body.Heading heading, nx.peter.java.document.core.page.Body body);
    P setBody(nx.peter.java.document.core.page.Body body);
    P setNumber(int number);
    P setBody(CharSequence body);
    P setBody(Element... elements);
    P setBody(List<Element> elements);
    P setHeading(CharSequence heading);
    P setHeading(nx.peter.java.document.core.page.body.Heading heading);
    boolean removeHeading();
    boolean removeBody();
}
