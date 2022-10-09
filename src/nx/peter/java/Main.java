package nx.peter.java;

import nx.peter.java.api.Api;
import nx.peter.java.api.RapidApi;
import nx.peter.java.api.sports.Football;
import nx.peter.java.api.sports.Sports;
import nx.peter.java.api.utility.VisualRecognition;
import nx.peter.java.api.utility.recognition.OCRText;
import nx.peter.java.document.Model;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Api api = new RapidApi(RapidApi.RequestMethod.POST, OCRText.URL, "", "");
        // println(api.getResponse().getBody());

        Sports.getFootball(
                new Sports.OnRequestListener<>() {
                    @Override
                    public void onRequesting(Football api, long currentTimeInSecs) {
                        // println(currentTimeInSecs + "ms");
                    }

                    @Override
                    public void onCompleted(Football api, Api.Status status, long durationInMillis) {
                        println(api.getMatches());
                    }
                }
        );

        VisualRecognition.getOCRText("https%3A%2F%2Fqph.cf2.quoracdn.net%2Fmain-qimg-60dad75c0dddf8f4aa1a95040d7c3ca5-pjlq",
                new VisualRecognition.OnRequestListener<>() {
                    @Override
                    public void onRequesting(OCRText api, long currentTimeInSecs) {
                        println(currentTimeInSecs);
                    }

                    @Override
                    public void onCompleted(OCRText api, Api.Status status, long durationInMillis) {
                        println(api.getExecutionTimeMS() + "ms");
                        println(status);
                        println(durationInMillis + "ms");
                    }
                });
        // println(ocr.getResponse().getStatus());

        System.out.println("\"" + System.lineSeparator() + "\"");
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
