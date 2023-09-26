package com.codefans.springcloud.util;

import okhttp3.*;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: codefans
 * @Date: 2022-08-18 0:50
 */

public class HttpUtils {

    private static final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
            .setSoTimeout(Timeout.ofSeconds(5))
            .build();

    private static final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
            .setIOReactorConfig(ioReactorConfig)
            .build();

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(2000, TimeUnit.MILLISECONDS)
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .writeTimeout(2000, TimeUnit.MILLISECONDS)
            .callTimeout(6000, TimeUnit.MILLISECONDS).build();

    static {
        client.start();
    }

    private static final String requestUrl = "http://localhost:8088/rpcTest";

    /**
     * HTTP exchange with Reactive Streams
     * totalTaskCount=30919, costTime=1016
     * totalTaskCount=36345, costTime=1000
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void asyncRequestWithReactiveStream() throws ExecutionException, InterruptedException, URISyntaxException {

        final HttpHost target = HttpHost.create(new URI("http://localhost:8088"));
        final String[] requestUris = new String[] {"/rpcTest"};

        final SimpleHttpRequest request = SimpleRequestBuilder.post()
                .setHttpHost(target)
                .setPath(requestUris[0])
                .build();

        System.out.println("asyncRequestWithReactiveStream-->Executing request " + request);

        int loopCount = 5;
        int totalCount = 0;
        int taskCount = 0;
        StringBuilder sb = new StringBuilder();
        long begin = System.currentTimeMillis();
        for(int i = 0; i < loopCount; i ++) {
            List<Future<SimpleHttpResponse>> futureList = new ArrayList<>(taskCount);
            long beginTime = System.currentTimeMillis();
            long costTime = 0L;
//            for(int i = 0; i < taskCount; i ++) {
            while ((costTime = (System.currentTimeMillis() - beginTime)) < 1000) {
                final Future<SimpleHttpResponse> future = client.execute(
                        SimpleRequestProducer.create(request),
                        SimpleResponseConsumer.create(),
                        new FutureCallback<SimpleHttpResponse>() {

                            @Override
                            public void completed(final SimpleHttpResponse response) {
//                            System.out.println(request + "-->" + new StatusLine(response) + "-->" + response.getBodyText());
//                            System.out.println(response.getBody());
                            }

                            @Override
                            public void failed(final Exception ex) {
                                System.out.println("请求[" + request + "]异常-->" + ex);
                            }

                            @Override
                            public void cancelled() {
                                System.out.println("请求[" + request + "]cancelled取消!!!");
                            }

                        });
                futureList.add(future);
            }

            taskCount = futureList.size();
            sb.append(taskCount).append(",");
            totalCount += taskCount;
            System.out.println("totalTaskCount=[" + taskCount + "次], costTime=[" + costTime + "ms]");
            for (int j = 0; j < taskCount; j++) {
                futureList.get(j).get();
            }
            futureList.clear();

            Thread.sleep(30 * 1000);

        }
        closeAsyncHttpClient();
        System.out.println("调用次数=[" + sb.toString() + "], avgCount=[" + (totalCount/loopCount) + "次], costTime=[" + (System.currentTimeMillis() - begin) + "ms]");
        System.out.println("asyncRequestWithReactiveStream-->Shutting down");


    }

    private static void closeAsyncHttpClient() {
        client.close(CloseMode.GRACEFUL);
    }

    static org.apache.http.impl.nio.client.CloseableHttpAsyncClient httpclient = org.apache.http.impl.nio.client.HttpAsyncClients.createDefault();
    static {
        httpclient.start();
    }
    public static void asyncRequest() throws IOException, ExecutionException, InterruptedException {

        try {

            HttpGet request = new HttpGet("http://localhost:8088/rpcTest");
            Future<HttpResponse> future = httpclient.execute(request, null);
            HttpResponse response = future.get();
            System.out.println("Response: " + response.getStatusLine());
            System.out.println("Shutting down");
        }
        finally {
//            httpclient.close();
        }
        System.out.println("Done");


    }

    static org.apache.http.impl.nio.client.CloseableHttpAsyncClient asyncHttpclient = org.apache.http.impl.nio.client.HttpAsyncClients.createDefault();
    static {
        asyncHttpclient.start();
    }
    public static Future<HttpResponse> asyncReqWithResponse() throws IOException, ExecutionException, InterruptedException {

        try {
//            httpclient.start();
            HttpGet request = new HttpGet("http://localhost:8088/rpcTest");
            Future<HttpResponse> future = asyncHttpclient.execute(request, null);
            return future;
//            HttpResponse response = future.get();
//            System.out.println("Response: " + response.getStatusLine());
//            System.out.println("Shutting down");
        } finally {
//            asyncHttpclient.close();
        }
//        System.out.println("Done");
    }

    public static void concurrentAsyncRequest() throws InterruptedException, IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000).build();
        org.apache.http.impl.nio.client.CloseableHttpAsyncClient httpclient = org.apache.http.impl.nio.client.HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        try {
            httpclient.start();
            final HttpGet[] requests = new HttpGet[] {
                    new HttpGet(requestUrl)
            };
            final CountDownLatch latch = new CountDownLatch(requests.length);
            for (final HttpGet request: requests) {
                httpclient.execute(request, new org.apache.http.concurrent.FutureCallback<HttpResponse>() {

                    @Override
                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    }

                    @Override
                    public void failed(final Exception ex) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + " cancelled");
                    }

                });
            }
            latch.await();
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
    }

    public static void syncRequest() throws IOException {
        CloseableHttpClient syncHttpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(requestUrl);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = syncHttpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            syncHttpclient.close();
        }
    }

    public static void okHttpSync() {
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void okHttpAsync() {
        Request request = new Request.Builder()
//                .url("http://publicobject.com/helloworld.txt")
                .url(requestUrl)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

//                    Headers responseHeaders = response.headers();
//                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                    }

                    System.out.println(responseBody.string());
                }
            }
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        asyncRequestWithReactiveStream();
//        asyncRequest();
//        concurrentAsyncRequest();

//        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
