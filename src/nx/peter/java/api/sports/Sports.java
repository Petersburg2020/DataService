package nx.peter.java.api.sports;

import nx.peter.java.api.Api;
import nx.peter.java.api.ApiService;
import nx.peter.java.api.RapidApi;
import nx.peter.java.json.reader.JsonArray;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.util.data.Letters;
import nx.peter.java.util.data.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Sports implements ApiService {

    protected static Football getFootball() {
        Api api = new RapidApi(Football.URL);
        Api.Response response = api.getResponse();
        Api.Response.Body body = response.getBody();
        List<Football.Match> matches = new ArrayList<>();
        List<Football.Competition> competitions = new ArrayList<>();
        List<Football.LiveStream> liveStreams = new ArrayList<>();

        if (response.isSuccessful() && body.isArray()) {
            JsonArray<?> array = body.getArray();
            for (int n = 0; n < array.size(); n++) {
                JsonObject<?> object = array.getObject(n);

                if (object != null) {
                    /* ************* TEAMS (HOME & AWAY) ************ */
                    JsonObject<?> home = object.getObject("side1"); // Get team 1 from json
                    JsonObject<?> away = object.getObject("side2"); // Get team 2 from json
                    Football.Teams teams = new Football.Teams(
                            new Football.Team(
                                    home.getString("name"), // Team name
                                    extractUrl(home.getString("url")) // Team url
                            ),
                            new Football.Team(
                                    away.getString("name"), // Team name
                                    extractUrl(away.getString("url")) // Team url
                            )
                    );

                    // System.out.println(teams);
                    // System.out.println();

                    /* ************* COMPETITION ************ */
                    JsonObject<?> comp = object.getObject("competition"); // Get competition from json
                    Football.Competition competition = new Football.Competition(
                            comp.getString("name"),
                            extractUrl(comp.getString("url")), // url
                            comp.getInt("id", -1)
                    );

                    // ADD TO LIST OF COMPETITIONS
                    competitions.add(competition);
                    /* ********************************************** */

                    /* ************* VIDEOS (HIGHLIGHT & LIVE) ************** */
                    /* ************ HIGHLIGHTS VIDEO ************ */
                    JsonArray<?> vid = object.getArray("videos"); // Get videos from json
                    List<Football.Video> videoList = new ArrayList<>();
                    for (int v = 0; v < vid.size(); v++) {
                        JsonObject<?> video = vid.getObject(v); // Get video from json
                        String embed = video.getString("embed");

                        // Extract url from iFrame embed
                        String url = extractUrl(
                                new Word(embed).subLetters("https:", embed.indexOf("src"), "' ") != null ?
                                        new Word(embed).subLetters("https:", embed.indexOf("src"), "' ").get() :
                                        ""
                        );
                        // System.out.println("\nUrl0: " + url + "\n");
                        embed = formatEmbed(embed);
                        // System.out.println("Embed2: " + embed);
                        // System.out.println();
                        // System.out.println("Url: " + url + "\n");

                        // ADD TO VIDEOS
                        videoList.add(
                                new Football.Video(
                                        embed, // Embed Web-iFrame-Link
                                        url, // Video url
                                        video.getString("title") // Video title
                                )
                        );
                    }
                    Football.Videos videos = new Football.Videos(videoList);
                    /* ******************************************************* */

                    // System.out.println("Videos" + videos);

                    /* ************* LIVE STREAM ************* */
                    String embed = object.getString("embed");
                    embed = formatEmbed(embed);
                    Football.Video video = new Football.Video(
                            embed, // Embed Web-iFrame-Link
                            extractUrl(object.getString("url")), // Video url
                            object.getString("title") // Video title
                    );
                    Football.LiveStream liveStream = new Football.LiveStream(
                            video, // Live-stream match video detail
                            teams // Match Teams details
                    );

                    // ADD TO LIST OF LIVE STREAMS
                    liveStreams.add(liveStream);
                    /* *************************************** */

                    /* ************** MATCH ************** */
                    Football.Match match = new Football.Match(
                            object.getString("date"), // Date & Time of Match (e.g "2022-10-03T10:30:00+0000")
                            extractUrl(object.getString("thumbnail")), // Thumbnail Url
                            teams, // Home & Away Teams
                            competition, // Competition as in League or Cup or Friendlies
                            liveStream, // Live Stream of Match
                            videos // Highlight & Live Videos
                    );
                    // ADD TO LIST OF MATCHES
                    matches.add(match);
                    /* *********************************** */
                }
            }
        }

        // System.out.println("Competitions" + competitions);

        return new Football() {

            @Override
            public Matches getMatches() {
                return new Matches(matches);
            }

            @Override
            public LiveStreams getLiveStreams() {
                return new LiveStreams(liveStreams);
            }

            @Override
            public Competitions getCompetitions() {
                return new Competitions(competitions);
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

            @Override
            public String toString() {
                return getResponse().toString();
            }
        };
    }

    public static void getFootball(OnRequestListener<Football> listener) {
        Api api = new RapidApi(Football.URL);
        Api.Response response = api.getResponse();
        Api.Response.Body body = response.getBody();
        List<Football.Match> matches = new ArrayList<>();
        List<Football.Competition> competitions = new ArrayList<>();
        List<Football.LiveStream> liveStreams = new ArrayList<>();

        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        final int[] counter = {0};

        final Thread requester = new Thread(() -> {
            if (response.isSuccessful() && body.isArray()) {
                JsonArray<?> array = body.getArray();
                for (int n = 0; n < array.size(); n++) {
                    JsonObject<?> object = array.getObject(n);

                    if (object != null) {
                        /* ************* TEAMS (HOME & AWAY) ************ */
                        JsonObject<?> home = object.getObject("side1"); // Get team 1 from json
                        JsonObject<?> away = object.getObject("side2"); // Get team 2 from json
                        Football.Teams teams = new Football.Teams(
                                new Football.Team(
                                        home.getString("name"), // Team name
                                        extractUrl(home.getString("url")) // Team url
                                ),
                                new Football.Team(
                                        away.getString("name"), // Team name
                                        extractUrl(away.getString("url")) // Team url
                                )
                        );

                        /* ************* COMPETITION ************ */
                        JsonObject<?> comp = object.getObject("competition"); // Get competition from json
                        Football.Competition competition = new Football.Competition(
                                comp.getString("name"),
                                extractUrl(comp.getString("url")), // url
                                comp.getInt("id", -1)
                        );

                        // ADD TO LIST OF COMPETITIONS
                        competitions.add(competition);
                        /* ********************************************** */

                        /* ************* VIDEOS (HIGHLIGHT & LIVE) ************** */
                        /* ************ HIGHLIGHTS VIDEO ************ */
                        JsonArray<?> vid = object.getArray("videos"); // Get videos from json
                        List<Football.Video> videoList = new ArrayList<>();
                        for (int v = 0; v < vid.size(); v++) {
                            JsonObject<?> video = vid.getObject(v); // Get video from json
                            String embed = video.getString("embed");

                            // Extract url from iFrame embed
                            String url = extractUrl(
                                    new Word(embed).subLetters("https:", embed.indexOf("src"), "' ") != null ?
                                            new Word(embed).subLetters("https:", embed.indexOf("src"), "' ").get() :
                                            ""
                            );
                            embed = formatEmbed(embed);

                            // ADD TO VIDEOS
                            videoList.add(
                                    new Football.Video(
                                            embed, // Embed Web-iFrame-Link
                                            url, // Video url
                                            video.getString("title") // Video title
                                    )
                            );
                        }
                        Football.Videos videos = new Football.Videos(videoList);
                        /* ******************************************************* */

                        /* ************* LIVE STREAM ************* */
                        String embed = object.getString("embed");
                        embed = formatEmbed(embed);
                        Football.Video video = new Football.Video(
                                embed, // Embed Web-iFrame-Link
                                extractUrl(object.getString("url")), // Video url
                                object.getString("title") // Video title
                        );
                        Football.LiveStream liveStream = new Football.LiveStream(
                                video, // Live-stream match video detail
                                teams // Match Teams details
                        );

                        // ADD TO LIST OF LIVE STREAMS
                        liveStreams.add(liveStream);
                        /* *************************************** */

                        /* ************** MATCH ************** */
                        Football.Match match = new Football.Match(
                                object.getString("date"), // Date & Time of Match (e.g "2022-10-03T10:30:00+0000")
                                extractUrl(object.getString("thumbnail")), // Thumbnail Url
                                teams, // Home & Away Teams
                                competition, // Competition as in League or Cup or Friendlies
                                liveStream, // Live Stream of Match
                                videos // Highlight & Live Videos
                        );
                        // ADD TO LIST OF MATCHES
                        matches.add(match);
                        /* *********************************** */
                    }
                }
            }
        });
        requester.start();

        new Thread(() -> {
            Football football = new Football() {

                @Override
                public Matches getMatches() {
                    return new Matches(matches);
                }

                @Override
                public LiveStreams getLiveStreams() {
                    return new LiveStreams(liveStreams);
                }

                @Override
                public Competitions getCompetitions() {
                    return new Competitions(competitions);
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

                @Override
                public String toString() {
                    return getResponse().toString();
                }
            };
            listener.onBegin(football);
            while (requester.getState().equals(Thread.State.RUNNABLE)) {
                long current = System.currentTimeMillis();
                football = new Football() {

                    @Override
                    public Matches getMatches() {
                        return new Matches(matches);
                    }

                    @Override
                    public LiveStreams getLiveStreams() {
                        return new LiveStreams(liveStreams);
                    }

                    @Override
                    public Competitions getCompetitions() {
                        return new Competitions(competitions);
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

                    @Override
                    public String toString() {
                        return getResponse().toString();
                    }
                };

                // Process Listener
                if (!requester.getState().equals(Thread.State.TERMINATED))
                    listener.onRequesting(football, current - start.get());
                else break;
            }
            listener.onCompleted(football, football.getResponse().getStatus(), System.currentTimeMillis() - start.get());
        }).start();
    }

    protected static String extractUrl(String url) {
        // System.out.println("Url: " + url + "\n");
        Word w = new Word(url);
        StringBuilder wUrl = new StringBuilder("");
        if (w.contains("/")) {
            wUrl.append("https://");
            int start = 0, end = "https://".length() - 1;
            while (start > -1 && start < w.length() - 1 && end < w.length() - 1) {
                start = w.getNextAlphabetIndex(end);
                if (start == -1) break;
                end = w.indexOf('/', start) - 1;
                if (end <= -1) {
                    if (start < w.length() - 1) {
                        String s = w.substring(start);
                        wUrl.append(s);
                        // System.out.println("S: " + s);
                    }
                    break;
                } else {
                    String s = w.substring(start, end) + "/";
                    wUrl.append(s);
                    // System.out.println("S: " + s);
                }
                // System.out.println("Start: " + start + ", End: " + end);
                // System.out.println("wUrl: " + wUrl);

                if (end >= w.length() - 1 || start >= w.length() - 1) break;
            }
            // System.out.println("Url: " + wUrl + "\n");
        } else return url;
        return wUrl.toString();
    }

    protected static String formatEmbed(String embed) {
        String temp = new Word(embed).subLetters("https:", embed.indexOf("src"), "' ") != null ?
                new Word(embed).subLetters("https:", embed.indexOf("src"), "' ").get() :
                "";
        String url = extractUrl(temp);
        // System.out.println("Temp: " + temp);
        // System.out.println("Url: " + url);
        return formatEmbed(embed, url, temp);
    }

    protected static String formatEmbed(String emb, String url, String temp) {
        Word embed = new Word(emb).replace(temp, url);
        // System.out.println("\nEmbed1: " + embed.get());
        String result = "";
        int start = 0, end = 0;
        while (end > -1 && start < embed.length() - 1) {
            start = embed.indexOf("<", end);
            if (start <= -1) break;
            else if (start > 0) result += embed.substring(end, start);

            end = embed.getNextAlphabetIndex(start);
            if (end == -1) break;

            Letters<?> content = embed.subLetters(start, end);
            if (content.contains('/'))
                result += "</" + embed.subLetters(embed.getAlphabetAt(end).get(), end, ">").get() + ">";


            // System.out.println("Start: " + start + ", End: " + end + ", Length: " + emb.length());
            // System.out.println("Content: " + content.get());
            // System.out.println("Result: " + result + "\n");
            if (end >= embed.length() - 1) break;
        }
        return result;
    }

}
