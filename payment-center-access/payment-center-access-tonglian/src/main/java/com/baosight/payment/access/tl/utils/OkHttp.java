package com.baosight.payment.access.tl.utils;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttp {
    private static final OkHttpClient client;

    // 初始化 OkHttpClient
    static {
        client = new OkHttpClient.Builder()
                // 连接超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                // 写入超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 发送 GET 请求
     *
     * @param url 请求 URL
     * @return 响应结果字符串
     */
    public static String get(String url) {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }
            return body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送 GET 请求
     *
     * @param url 请求 URL
     */
    public static InputStream getFile(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute();) {
            return response.body() != null ? response.body().byteStream() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送 POST 请求（JSON）
     *
     * @param url  请求 URL
     * @param json 请求体（JSON 格式）
     * @return 响应结果字符串
     */
    public static String postJson(String url, String json) {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送 POST 请求（Form 表单）
     *
     * @param url    请求 URL
     * @param params 请求参数
     * @return 响应结果字符串
     * @throws IOException
     */
    public static ResponseBody postForm(String url, Map<String, Object> params) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        RequestBody body = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Failed to download file: " + response);
        }

        return response.body();
    }


    /**
     * 发送 POST 请求（JSON）
     *
     * @param url  请求 URL
     * @param json 请求体（JSON 格式）
     * @return 响应结果字符串
     */
    public static InputStream post(String url, String json) {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().byteStream() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param url       请求 URL
     * @param fileKey   表单中文件对应的 key
     * @param file      文件对象
     * @param mediaType 文件类型（如 "image/png"）
     * @return 响应结果字符串
     * @throws IOException
     */
    public static String uploadFile(String url, String fileKey, java.io.File file, String mediaType) throws IOException {
        RequestBody fileBody = RequestBody.create(file, MediaType.parse(mediaType));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileKey, file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : null;
        }
    }

    /**
     * 发送 DELETE 请求
     *
     * @param url 请求 URL
     * @return 响应结果字符串
     * @throws IOException
     */
    public static String delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : null;
        }
    }
}
