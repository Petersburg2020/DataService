package nx.peter.java.api.sports;

import nx.peter.java.api.Api;

import java.util.List;

public interface Football extends Api {
    String URL = "https://free-football-soccer-videos.p.rapidapi.com";

    Matches getMatches();
    LiveStreams getLiveStreams();
    Competitions getCompetitions();


    record Team(String name, String url) {}

    record Teams(Team home, Team away) {}

    /**
     * <h1>
     *     ******************************** <br>
     * <b>|| ------------ Match ----------- ||</b><br>
     * ******************************** </h1>
     * Match is a record that stores match details
     * @param date date & time of match
     * @param thumbnail match thumbnail (url of image)
     * @param teams match teams (home & away)
     * @param competition match league, cup or friendlies name
     * @param stream live stream detail
     * @param videos videos (highlight or live or both or none)
     */
    record Match(String date, String thumbnail, Teams teams, Competition competition, LiveStream stream, Videos videos) {}

    class Matches extends Items<Matches, Match> {
        public Matches(List<Match> matches) {
            super(matches);
        }
    }

    record LiveStream(Video video, Teams teams) {}

    class LiveStreams extends Items<LiveStreams, LiveStream> {
        public LiveStreams(List<LiveStream> items) {
            super(items);
        }
    }

    record Competition(String name, String url, int id) {}

    class Competitions extends Items<Competitions, Competition> {
        public Competitions(List<Competition> items) {
            super(items);
        }
    }

    record Video(String embed, String url, String title) {}

    class Videos extends Items<Videos, Video> {
        public Videos(List<Video> items) {
            super(items);
        }
    }
}
