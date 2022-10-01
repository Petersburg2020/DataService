package nx.peter.java.document.core;

import nx.peter.java.context.model.Model;
import nx.peter.java.document.DocumentContext;
import nx.peter.java.document.reader.DocumentReader;
import nx.peter.java.document.type.*;
import nx.peter.java.document.writer.DocumentWriter;
import nx.peter.java.json.Json;
import nx.peter.java.json.JsonObject;
import nx.peter.java.json.core.JsonElement;
import nx.peter.java.json.writer.JsonWriter;
import nx.peter.java.pis.Pis;
import nx.peter.java.pis.core.Node;
import nx.peter.java.pis.writer.PisWriter;
import nx.peter.java.service.Service;
import nx.peter.java.storage.File;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DocumentCore<D extends DocumentCore, Doc extends Document, N extends Node, J extends JsonElement> {
    protected Doc document;

    public DocumentCore(Document document) {
        this.document = document != null ? (Doc) document : (Doc) new Book();
    }

    public DocumentCore(Document.Type type) {
        type = type != null ? type : Document.Type.Unknown;
        document = switch (type) {
            case Book, Unknown -> (Doc) new Book();
            case Journal -> (Doc) new Journal();
            case Magazine -> (Doc) new Magazine();
            case NewsPaper -> (Doc) new NewsPaper();
            case Thesis -> (Doc) new Thesis();
        };
    }

    public DocumentCore(CharSequence path, Document.Type type) {
        this(type);
        setFromFile(path);
    }

    public D setPath(CharSequence path) {
        ((nx.peter.java.document.Document) document).setFilePath(path);
        return (D) this;
    }

    public D setFromFile(CharSequence path) {
        ((nx.peter.java.document.Document) document).setFromFile(path);
        return (D) this;
    }

    public String getPath() {
        return ((nx.peter.java.document.Document) document).getFilePath();
    }

    public File getFile() {
        return new File(getPath());
    }

    public Doc getDocument() {
        return document;
    }

    public Source<Doc, N, J> getSource() {
        return new Source<>() {
            @Override
            public N toPis() {
                return (N) ((nx.peter.java.document.Document) document).toPis();
            }

            @Override
            public J toJson() {
                return (J) ((nx.peter.java.document.Document) document).toJson();
            }

            @Override
            public PrettyPrinter<Doc, N, J> getPrettyPrinter() {
                return new PrettyPrinter<>() {
                    @Override
                    public N toPis() {
                        return (N) ((nx.peter.java.document.Document) document).toPis();
                    }

                    @Override
                    public J toJson() {
                        return (J) ((nx.peter.java.document.Document) document).toJson();
                    }

                    @Override
                    public Doc getDocument() {
                        return document;
                    }

                    @Override
                    public String print() {
                        int rand = Random.nextInt(1, 2);
                        System.out.println("Rand: " + rand);
                        return rand == 1 ?
                                ((nx.peter.java.pis.Node) toPis()).getPrettyPrinter().print() :
                                ((JsonObject) toJson()).getPrettyPrinter().print();
                    }

                    @Override
                    public String toString() {
                        return print();
                    }
                };
            }

            @Override
            public DocumentContext getContext() {
                return new Context(document);
            }

            @Override
            public byte[] encode() {
                return toString().getBytes();
            }

            @Override
            public Doc getDocument() {
                return document;
            }

            @Override
            public String toString() {
                return document.toString();
            }
        };
    }

    public J getJson() {
        return (J) ((nx.peter.java.document.Document) document).toJson();
    }

    public N getPis() {
        return (N) ((nx.peter.java.document.Document) document).toPis();
    }

    @Override
    public String toString() {
        return document.toString();
    }




    protected static class Context implements DocumentContext {
        protected nx.peter.java.document.Document document;

        public Context(Document document) {
            set(document);
        }

        @Override
        public PrettyPrinter getPrettyPrinter(Document source) {
            return ((nx.peter.java.document.Document) source).getPrettyPrinter();
        }

        @Override
        public DocumentWriter getWriter(CharSequence path) {
            return getWriter().setPath(path);
        }

        @Override
        public DocumentReader getReader(CharSequence path) {
            return getReader().setPath(path);
        }

        @Override
        public DocumentWriter getWriter() {
            return new DocumentWriter(document);
        }

        @Override
        public DocumentReader getReader() {
            return new DocumentReader(document);
        }

        @Override
        public nx.peter.java.document.writer.Document getWritable() {
            return document;
        }

        @Override
        public nx.peter.java.document.reader.Document getReadable() {
            return document;
        }

        @Override
        public PrettyPrinter getPrettyPrinter() {
            return document.getPrettyPrinter();
        }

        @Override
        public Source getSource() {
            return Random.nextInt(1, 2) == 1 ? getReader().getSource() : getWriter().getSource();
        }

        @Override
        public DocumentContext getContext() {
            return this;
        }

        @Override
        public DocumentContext set(Document document) {
            this.document = document != null ? (nx.peter.java.document.Document) document : new Book();
            return this;
        }

        @Override
        public <M extends Model> Service<DocumentContext, DocumentWriter, DocumentReader, Source, PrettyPrinter, nx.peter.java.document.writer.Document, nx.peter.java.document.reader.Document, Document, M> getService(M model) {
            Thread runner = new Thread(() -> {
                String path = File.FILES_FOLDER;
                int type = Random.nextInt(1, 2);
                AtomicBoolean written = new AtomicBoolean(false);
                if (type == 1) {
                    path += "temp.json";
                    JsonWriter writer = new JsonWriter(path);
                    writer.setRoot(Json.fromString(((nx.peter.java.document.Model)model).content));
                    written.set(writer.store());
                    System.out.println("Written0: " + written);
                } else {
                    path += "temp.pis";
                    PisWriter writer = new PisWriter(path);
                    writer.setRoot(Pis.toPis(((nx.peter.java.document.Model)model).content, false).getNode());
                    written.set(writer.store());
                    System.out.println("Written1: " + written);
                }
                if (written.get()) {
                    document.setFromFile(path);
                    written.set(new File(path).delete());
                    System.out.println("Deleted: " + written);
                    // Util.sleep(2000);
                }
            });
            runner.setName("Runner");

            final AtomicBoolean start = new AtomicBoolean(), stop = new AtomicBoolean();
            final Service.OnServiceRunListener<DocumentContext, M>[] mListener = new Service.OnServiceRunListener[]{null};
            Thread service = new Thread(() -> {
                while (runner.getState().equals(Thread.State.RUNNABLE) && mListener[0] != null) {
                    if ((runner.isInterrupted() || stop.get() || runner.getState().equals(Thread.State.TERMINATED))) {
                        mListener[0].onStop(Context.this, model);
                        System.out.println("I'm here!");
                        stop.set(true);
                        start.set(false);
                        runner.stop();
                        break;
                    } else mListener[0].onRun(Context.this, model);
                }
            });
            service.setName("Service");
            return new Service<>() {
                @Override
                public void start() {
                    if (!start.get()) {
                        start.set(true);
                        stop.set(false);
                        runner.start();
                        service.start();
                    }
                }

                @Override
                public void stop() {
                    if (start.get() && !stop.get()) {
                        start.set(false);
                        stop.set(true);
                        runner.interrupt();
                        Util.sleep(2000);
                    }
                }

                @Override
                public void dispose() {
                    service.interrupt();
                    service.stop();
                }

                @Override
                public boolean hasStopped() {
                    return stop.get() && !start.get();
                }

                @Override
                public boolean hasStarted() {
                    return start.get() && !stop.get();
                }

                @Override
                public boolean isRunning() {
                    return hasStarted() && !hasStopped();
                }

                @Override
                public boolean equals(Service service) {
                    return service != null && service.getListener().equals(getListener());
                }

                @Override
                public void getModel(Callback<M> callback) {
                    if (callback != null)
                        callback.onReceive(model);
                }

                @Override
                public void getContext(Callback<DocumentContext> callback) {
                    if (callback != null)
                        callback.onReceive(Context.this);
                }

                @Override
                public void getDataFile(Callback<Document> callback) {
                    if (callback != null)
                        callback.onReceive(document);
                }

                @Override
                public void getPrinter(Callback<PrettyPrinter> callback) {
                    if (callback != null)
                        callback.onReceive(getPrettyPrinter());
                }

                @Override
                public void getWriter(Callback<DocumentWriter> callback) {
                    if (callback != null)
                        callback.onReceive(new DocumentWriter(document));
                }

                @Override
                public void getReader(Callback<DocumentReader> callback) {
                    if (callback != null)
                        callback.onReceive(new DocumentReader(document));
                }

                @Override
                public OnServiceRunListener<DocumentContext, M> getListener() {
                    return mListener[0];
                }

                @Override
                public void setListener(OnServiceRunListener<DocumentContext, M> listener) {
                    mListener[0] = listener;
                }
            };
        }

        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public Document.Type getType() {
            return document.getType();
        }

        @Override
        public String toString() {
            return document.toString();
        }
    }
}
