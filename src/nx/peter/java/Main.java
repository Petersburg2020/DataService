package nx.peter.java;

import nx.peter.java.document.Model;
import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.shape.flat.Circle;
import nx.peter.java.math.shape.flat.ComplexShape;
import nx.peter.java.math.shape.flat.Hole;
import nx.peter.java.math.shape.flat.Sector;
import nx.peter.java.math.shape.solid.Cube;
import nx.peter.java.math.value.*;
import nx.peter.java.math.value.Mass.MassUnit;
import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.util.Random;
import nx.peter.java.util.data.encryption.Pin;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*Api api = new RapidApi(RapidApi.RequestMethod.POST, OCRText.URL, "", "");
        // println(api.getResponse().getBody());

        Sports.getFootball(
                new Sports.OnRequestListener<>() {
                    @Override
                    public void onBegin(Football api) {

                    }

                    @Override
                    public void onRequesting(Football api, long currentTimeInSecs) {
                        // println(currentTimeInSecs + "ms");
                    }

                    @Override
                    public void onCompleted(Football api, Api.Status status, long durationInMillis) {
                        println(api.getMatches().getDetails());
                    }
                }
        );

        VisualRecognition.getOCRText("https%3A%2F%2Fqph.cf2.quoracdn.net%2Fmain-qimg-60dad75c0dddf8f4aa1a95040d7c3ca5-pjlq",
                new VisualRecognition.OnRequestListener<>() {
                    @Override
                    public void onBegin(OCRText api) {
                        println(api.getText());
                    }

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
                });*/

        println(Pin.generate(4));

        Value<?, ?> value = new Mass();
        println(value.random(10, 100));

        value = new Angle();
        println(value.random(0, 360));

        value = new Force();
        println(value.random(10, 2000));

        Shape shape = new Circle(23.78);
        println(shape);

        shape = new Cube(34, Linear.LinearUnit.Yard);
        println(shape);

        shape = new Sector(23, 56);
        println(shape);

        shape = new Hole(new Circle(10));
        ComplexShape complex = new ComplexShape();
        complex.set(
                new Circle(23.78),
                new Sector(23, 56),
                shape
        );
        println(complex);
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
