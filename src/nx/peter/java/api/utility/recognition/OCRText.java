package nx.peter.java.api.utility.recognition;

import nx.peter.java.api.Api;

import java.util.List;

public interface OCRText extends Api {
    String URL = "https://ocr-extract-text.p.rapidapi.com/ocr?url=";

    String getText();
    boolean getStatus();
    String getImageUrl();
    long getExecutionTimeMS();
    Languages getDetectedLanguages();

    record Language(String code, int confidence) {}
    class Languages extends Items<Languages, Language> {
        public Languages(List<Language> items) {
            super(items);
        }
    }
}
