package nx.peter.java;

import nx.peter.java.bible.AKJVBible;
import nx.peter.java.bible.Bible;
import nx.peter.java.context.Context;
import nx.peter.java.context.Reader;
import nx.peter.java.context.Writer;
import nx.peter.java.document.Model;
import nx.peter.java.document.core.Document;
import nx.peter.java.document.reader.DocumentReader;
import nx.peter.java.document.writer.DocumentWriter;
import nx.peter.java.json.core.Root;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.json.reader.JsonReader;
import nx.peter.java.json.writer.JsonWriter;
import nx.peter.java.pis.reader.PisReader;
import nx.peter.java.service.Service;
import nx.peter.java.storage.File;
import nx.peter.java.storage.FileManager;
import nx.peter.java.util.advanced.Advanced;
import nx.peter.java.util.data.DataManager;
import nx.peter.java.util.data.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // println(Util.isArr(new Object[]{}));

        Test m = new Test("Peter", 20, Arrays.asList("Base", "Tree", "Deal"), "Here is the content.");
        // println(Advanced.getObjectDetail(m));
        JsonObject<?> json = Advanced.toJson(m);
        // println(json);

        json = new JsonReader(json.toString(), false).getSource().getObject();
        // println(json.getPrettyPrinter());

        Reader<?, ?> reader = new DocumentReader(File.FILES_FOLDER + "piservices_java.json", Document.Type.Book);

        nx.peter.java.document.reader.Document document = (nx.peter.java.document.reader.Document) reader.getSource().getContext().getReadable();
        println(document.getPrettyPrinter());

        println();

        Word word = new Word(
                "{\"values\": [23, \"Peter\", 40.34, true, {\"scores\": [24, 51, 30, 89] ] }"
        );
        int start = word.indexOf('[');
        int end = DataManager.getCoverIndex(word, '[', ']', 0);
        println(DataManager.getCoverIndex(word, '[', ']', 0));
        println();

        Bible bible = new AKJVBible();
        println(bible.getName());

        bible.findVerses("God", new Bible.OnSearchListener<>() {
            @Override
            public void onSearchInProgress(Bible.Result<Bible.Verse> result, int durationInSecs) {}

            @Override
            public void onSearchCompleted(Bible.Result<Bible.Verse> result, long durationInMillis) {
                println(result.get(1));
            }
        });
    }

    public static class Test extends Model {
        public String name;
        public int age;
        public List<String> list;

        public Test(String name, int age) {
            this(name, age, new ArrayList<>());
        }

        public Test(String name, int age, List<String> list) {
            this("Test", 1, "", name, age, list);
        }

        public Test(String name, int age, List<String> list, String content) {
            this("Test", 1L, content, name, age, list);
        }

        public Test(String content) {
            this("Test", 1, content, "", 0, new ArrayList<>());
        }

        public Test(String modelName, long modelId, String content, String name, int age, List<String> list) {
            super(modelName, modelId, content);
            this.name = name;
            this.age = age;
            this.list = list;
        }
    }

    public static void println() {
        println("");
    }

    public static void print(Object object) {
        System.out.print(object);
    }

    public static void println(Object object) {
        System.out.println(object);
    }
}
