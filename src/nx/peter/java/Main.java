package nx.peter.java;

import nx.peter.java.context.Context;
import nx.peter.java.context.Reader;
import nx.peter.java.context.Writer;
import nx.peter.java.document.Model;
import nx.peter.java.document.reader.Document;
import nx.peter.java.document.reader.DocumentReader;
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
        println(json.getPrettyPrinter());

        Reader<?, ?> reader = new JsonReader(File.FILES_FOLDER + "quiz.json");

        Writer<?, ?> writer = new JsonWriter(File.FILES_FOLDER + "test.json").setRoot((Root) reader.getSource());
        println("Saved: " + writer.store());

        m = Advanced.fromJson(json, Test.class);
        println(m);
        // println(FileManager.readString(File.FILES_FOLDER + "piservices_java.json"));

        /*DocumentReader reader = new DocumentReader(File.FILES_FOLDER + "piservices_java.json", Document.Type.Book);
        Context context = reader.getSource().getContext();
        println();
        // println(context.getPrettyPrinter());
        Service service = context.getService(new Test(reader.getJson().toString()));
        service.setListener(new Service.OnServiceRunListener<>() {
            @Override
            public void onRun(Context context, nx.peter.java.context.model.Model model) {
                // println("Model{\n" + model + "\n}");
            }

            @Override
            public void onStop(Context context, nx.peter.java.context.model.Model model) {
                println(context.getPrettyPrinter().print());
            }
        });

        // service.start();
        // println(context.getPrettyPrinter().print());
        println();

        Document document = reader.getDocument();
        // println(document.pageCount());
        println();*/

        /*PisReader pisReader = new PisReader(File.FILES_FOLDER + "piservices_java.pis");
        println(pisReader.getSource().getNode().getPrettyPrinter());*/
        println();

        // json = new JsonReader(File.FILES_FOLDER + "piservices_java.json").getSource().getObject();

        Word word = new Word(
                "{\"values\": [23, \"Peter\", 40.34, true, {\"scores\": [24, 51, 30, 89] ] }"
        );
        int start = word.indexOf('[');
        int end = DataManager.getCoverIndex(word, '[', ']', 0);
        println(DataManager.getCoverIndex(word, '[', ']', 0));

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
