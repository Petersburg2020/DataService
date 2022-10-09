package nx.peter.java.api.utility;

import nx.peter.java.api.Api;
import nx.peter.java.api.ApiService;
import nx.peter.java.api.RapidApi;
import nx.peter.java.api.sports.Football;
import nx.peter.java.api.utility.recognition.OCRText;
import nx.peter.java.json.reader.JsonArray;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.util.data.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class VisualRecognition implements ApiService {
    protected static OCRText getOCRText(CharSequence url) {
        url = url != null ? url : "";
        final String finalUrl = url.toString();
        RapidApi api = new RapidApi(OCRText.URL + url);
        Api.Response response = api.getResponse();;
        JsonObject<?> object = response.getBody().getObject();
        List<OCRText.Language> detectedLanguages = new ArrayList<>();

        if (response.isSuccessful() && object != null) {
            JsonArray<?> array = object.getArray("detectedLanguages");
            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    JsonObject<?> lang = array.getObject(i);

                    // Add detected language
                    detectedLanguages.add(new OCRText.Language(
                            lang.getString("languageCode"), // Get language code
                            lang.getInt("confidence", 0) // Get language confidence level
                    ));
                }
            }
        }

        return new OCRText() {
            @Override
            public String getText() {
                return response.isSuccessful() && object != null ? object.getString("text") : "";
            }

            @Override
            public boolean getStatus() {
                return response.isSuccessful() && object != null && object.getBoolean("status", false);
            }

            @Override
            public String getImageUrl() {
                return finalUrl;
            }

            @Override
            public long getExecutionTimeMS() {
                return response.isSuccessful() && object != null ? object.getInt("executionTimeMS", 0) : 0;
            }

            @Override
            public Languages getDetectedLanguages() {
                return new Languages(detectedLanguages);
            }

            @Override
            public String getUrl() {
                return api.getUrl();
            }

            @Override
            public String getHost() {
                return api.getHost();
            }

            @Override
            public Response getResponse() {
                return response;
            }
        };
    }

    public static void getOCRText(CharSequence url, OnRequestListener<OCRText> listener) {
        url = url != null ? url : "";
        final String finalUrl = url.toString();
        RapidApi api = new RapidApi(OCRText.URL + url);
        Api.Response response = api.getResponse();;
        final JsonObject<?>[] object = {null};
        List<OCRText.Language> detectedLanguages = new ArrayList<>();

        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        final int[] counter = {0};

        final Thread requester = new Thread(() -> {
            object[0] = response.getBody().getObject();
            if (response.isSuccessful() && object[0] != null) {
                JsonArray<?> array = object[0].getArray("detectedLanguages");
                if (array != null) {
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject<?> lang = array.getObject(i);

                        // Add detected language
                        detectedLanguages.add(new OCRText.Language(
                                lang.getString("languageCode"), // Get language code
                                lang.getInt("confidence", 0) // Get language confidence level
                        ));
                    }
                }
            }
        });
        requester.start();


        new Thread(() -> {
            OCRText ocr = new OCRText() {
                @Override
                public String getText() {
                    return response.isSuccessful() && object[0] != null ? object[0].getString("text") : "";
                }

                @Override
                public boolean getStatus() {
                    return response.isSuccessful() && object[0] != null && object[0].getBoolean("status", false);
                }

                @Override
                public String getImageUrl() {
                    return finalUrl;
                }

                @Override
                public long getExecutionTimeMS() {
                    return response.isSuccessful() && object[0] != null ? object[0].getInt("executionTimeMS", 0) : 0;
                }

                @Override
                public Languages getDetectedLanguages() {
                    return new Languages(detectedLanguages);
                }

                @Override
                public String getUrl() {
                    return api.getUrl();
                }

                @Override
                public String getHost() {
                    return api.getHost();
                }

                @Override
                public Response getResponse() {
                    return response;
                }
            };
            while (requester.getState().equals(Thread.State.RUNNABLE)) {
                long current = System.currentTimeMillis();
                ocr = new OCRText() {
                    @Override
                    public String getText() {
                        return response.isSuccessful() && object[0] != null ? object[0].getString("text") : "";
                    }

                    @Override
                    public boolean getStatus() {
                        return response.isSuccessful() && object[0] != null && object[0].getBoolean("status", false);
                    }

                    @Override
                    public String getImageUrl() {
                        return finalUrl;
                    }

                    @Override
                    public long getExecutionTimeMS() {
                        return response.isSuccessful() && object[0] != null ? object[0].getInt("executionTimeMS", 0) : 0;
                    }

                    @Override
                    public Languages getDetectedLanguages() {
                        return new Languages(detectedLanguages);
                    }

                    @Override
                    public String getUrl() {
                        return api.getUrl();
                    }

                    @Override
                    public String getHost() {
                        return api.getHost();
                    }

                    @Override
                    public Response getResponse() {
                        return response;
                    }
                };

                System.out.println("Check: " + !requester.getState().equals(Thread.State.TERMINATED));
                // Process Listener
                if (!requester.getState().equals(Thread.State.TERMINATED)) {
                    int duration = (int) ((current - start.get()) / 1000);
                    System.out.println("Here!");
                    if (duration - counter[0] >= 1) {
                        listener.onRequesting(ocr, duration);
                        counter[0] = duration;
                    }
                } else break;
            }
            listener.onCompleted(ocr, ocr.getResponse().getStatus(), System.currentTimeMillis() - start.get());
        }).start();

    }
}
