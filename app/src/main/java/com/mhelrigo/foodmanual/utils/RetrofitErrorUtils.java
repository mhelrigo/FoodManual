package com.mhelrigo.foodmanual.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RetrofitErrorUtils {
    public static Response socketTimeoutError(Request request){
        return new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                .protocol(Protocol.HTTP_1_1)
                .message("Server Error, Try again later")
                .request(request)
                .code(Constants.HTTPResponse.SOCKET_TIMEOUT)
                .build();
    }

    public static Response connectionError(Request request){
        return new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                .protocol(Protocol.HTTP_1_1)
                .message("No Internet Connection")
                .request(request)
                .code(Constants.HTTPResponse.CONNECTION_ERROR)
                .build();
    }

    public static Response ioError(Request request){
        return new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                .protocol(Protocol.HTTP_1_1)
                .message("IOError")
                .request(request)
                .code(Constants.HTTPResponse.IO_ERROR)
                .build();
    }

    public static Response badRequest(Request request, Response response){
        try {
            return new Response.Builder()
                    .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                    .protocol(Protocol.HTTP_1_1)
                    .message(response.body().string())
                    .request(request)
                    .code(Constants.HTTPResponse.RESPONSE_400)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Response pageNotFound(Request request){
        return new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                .protocol(Protocol.HTTP_1_1)
                .message("Page Not Found")
                .request(request)
                .code(Constants.HTTPResponse.RESPONSE_404)
                .build();
    }
}