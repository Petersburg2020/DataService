package nx.peter.java.api;

import nx.peter.java.json.core.Source;
import nx.peter.java.json.reader.JsonArray;
import nx.peter.java.json.reader.JsonElement;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.json.reader.JsonReader;
import nx.peter.java.util.data.Word;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RapidApi implements Api {
    protected JsonReader reader;
    protected HttpRequest request;
    protected HttpResponse<String> response;
    protected boolean isError;
    protected String url, message, host, postText;
    protected RequestMethod method;

    public RapidApi(CharSequence url) {
       this(RequestMethod.GET, url, "", "");
    }

    public RapidApi(RequestMethod method, CharSequence url, CharSequence postText, CharSequence contentType) {
        this.method = method;
        url = url != null ? url : "";
        Word urlText = new Word(url);
        this.url = urlText.get();
        host = urlText.isNotEmpty() && urlText.startsWith("https://") ? urlText.subLetters("https://".length()).contains("/") ? urlText.subLetters("https://".length(), urlText.indexOf("/", "https://".length() + 1)).get() : urlText.subLetters("https://".length()).get() : "";

        this.postText = postText != null ? postText.toString() : "";
        isError = false;
        setup();
    }


    protected void setup() {
        switch (method) {
            case GET -> {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("X-RapidAPI-Key", "6472ee921bmsh39b2e2f91879c11p1ce74djsn371eebaf24ed")
                        .header("X-RapidAPI-Host", host)
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
            }
            case POST -> {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("X-RapidAPI-Key", "6472ee921bmsh39b2e2f91879c11p1ce74djsn371eebaf24ed")
                        .header("X-RapidAPI-Host", host)
                        .method("POST", HttpRequest.BodyPublishers.ofString("url=https%3A%2F%2Fstorage.googleapis.com%2Fapi4ai-static%2Fsamples%2Fimg-bg-removal-2.jpg"))
                        .build();
            }
        }
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            reader = new JsonReader(response.body(), false);
            message = "Content was sent to body!";
        } catch (Exception e) {
            reader = new JsonReader(e.getMessage(), false);
            message = e.getMessage();
            isError = true;
            System.out.println(e.getMessage());
        } finally {
            if (isError) message = "Content was not sent!";
        }
    }


    @Override
    public String getUrl() {
        return url;
    }

    public String getHost() {
        return host;
    }

    @Override
    public Response getResponse() {
        return new Response() {

            @Override
            public boolean isUnsuccessful() {
                return isError;
            }

            @Override
            public boolean isSuccessful() {
                return !isError;
            }

            @Override
            public String getMessage() {
                return getBody().isObject() && getBody().getObject().containsKey("message") ? getBody().getObject().getString("message") : message;//.getObject().getString("message");
            }

            @Override
            public Body getBody() {
                return new Body() {
                    @Override
                    public Source<JsonArray, JsonObject, JsonElement> getSource() {
                        return reader.getSource();
                    }

                    @Override
                    public JsonArray<?> getArray() {
                        return isArray() ? getSource().getArray() : new nx.peter.java.json.JsonArray();
                    }

                    @Override
                    public JsonObject<?> getObject() {
                        return isObject() ? getSource().getObject() : new nx.peter.java.json.JsonObject();
                    }

                    @Override
                    public JsonElement<?> getJson() {
                        return getSource().getElement();
                    }

                    @Override
                    public boolean isArray() {
                        return getSource().isArray();
                    }

                    @Override
                    public boolean isObject() {
                        return getSource().isObject();
                    }

                    @Override
                    public boolean isUndefined() {
                        return getSource().isUndefined();
                    }

                    @Override
                    public String toString() {
                        return isSuccessful() ? response.body() : "";
                    }
                };
            }

            @Override
            public Status getStatus() {
                return isSuccessful() ? Status.SUCCESS : Status.FAILURE;
            }

            @Override
            public String toString() {
                return getBody().getSource().getPrettyPrinter().toString();
            }
        };
    }


    public enum RequestMethod {
        GET,
        POST
    }

}
