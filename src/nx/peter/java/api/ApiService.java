package nx.peter.java.api;

public interface ApiService {

    interface OnRequestListener<A extends Api> {
        void onBegin(A api);
        void onRequesting(A api, long currentTimeInMills);
        void onCompleted(A api, Api.Status status, long durationInMillis);
    }

}
