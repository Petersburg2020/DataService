package nx.peter.java.document.reader.page;

import nx.peter.java.document.reader.page.body.Element;
import nx.peter.java.document.reader.Document;
import nx.peter.java.document.reader.page.body.Heading;
import nx.peter.java.document.reader.page.body.List;
import nx.peter.java.document.reader.page.body.Text;

public interface Body<B extends Body> extends
        nx.peter.java.document.core.page.Body<B>,
        Document.Source<B> {

    Element.Elements<Text> getTexts();
    Element.Elements<List> getLists();
    Element.Elements<Heading> getHeadings();
    Element.Elements<Element> getElements();
    nx.peter.java.document.core.page.body.Element getElementByPosition(int position);
    Element.Elements<Element> getElementsByType(Element.Entity entity);
    <E extends nx.peter.java.document.core.page.body.Element> Element.Elements<E> getElementsByClass(Class<E> clazz);
}
