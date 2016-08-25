package com.devskiller.android.blog.app.model;

public class Response {

    private ResponseData responseData;

    public Response(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseData=" + responseData +
                '}';
    }
}
