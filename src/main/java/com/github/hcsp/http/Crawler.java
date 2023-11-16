package com.github.hcsp.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Crawler {
    static class GitHubPullRequest {                //静态内部类
        int number;
        String title;

         String author;


        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        // 修改 getAuthor 方法以返回 author 的 login 字段

        public String getAuthor() {
            return author;
        }

    }

    public static List<GitHubPullRequest> getFirstPageOfPullRequests(String repo) throws IOException {
        OkHttpClient client = new OkHttpClient();                       //创建一个客户端用来发起HTTP请求
        String url = "https://api.github.com/repos/" + repo + "/pulls";

        String json = run(client, url);

        Gson gson = new Gson();
        Type type = new TypeToken<List<GitHubPullRequest>>(){}.getType();
        List<GitHubPullRequest> pullRequests = gson.fromJson(json, type);

        return pullRequests;
    }

    public static String run(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        List<GitHubPullRequest> pullRequests = getFirstPageOfPullRequests("gradle/gradle");
        System.out.println(pullRequests);
    }
}
