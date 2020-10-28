package homework.day04.work_2;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpClientRequest {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8801";
        Request request =
                new Request.Builder()
                        .url(url)
                        .get()
                        .build();

        final Call call = client.newCall(request);
        Response response = call.execute();
        assert response.body() != null;
        System.out.println(response.body().string());
    }

}
