package com.event.eventweb.response;

import com.event.eventweb.dto.PlatformResponse;
import com.event.eventweb.dto.UnauthorizedResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseMsg {


//    public static ServerResponse successResponse(String messageKey) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(true);
//        serverResponse.setCode("0");
//        return MessageComposer.compose(serverResponse, messageKey);
//    }
//
//    public static ServerResponse successResponse(String messageKey, Object ojb) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(true);
//        serverResponse.setCode("0");
//        serverResponse.setObj(ojb);
//        return MessageComposer.compose(serverResponse, messageKey);
//    }
//
//    public static ServerResponse successResponse(String messageKey, Map<String, String> parameters) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(true);
//        serverResponse.setCode("0");
//        return MessageComposer.compose(serverResponse, messageKey, parameters);
//    }
//
    public static PlatformResponse successResponse() {
        PlatformResponse serverResponse = new PlatformResponse();
        serverResponse.setSuccess(true);
        serverResponse.setCode("0");
        return serverResponse;
    }
//
    public static PlatformResponse failureResponse(String message) {
        PlatformResponse platformResponse = new PlatformResponse();
        platformResponse.setSuccess(false);
        platformResponse.setCode("2");
        platformResponse.setMessage(message);
        return platformResponse;
    }
//
//    public static ServerResponse failureStaticMessage(String message) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(false);
//        serverResponse.setCode("2");
//        serverResponse.setMessage(message);
//        return serverResponse;
//    }
//
//    public static ServerResponse failureResponse(String messageKey, Object data) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(false);
//        serverResponse.setCode("2");
//        serverResponse.setObj(data);
//        return MessageComposer.compose(serverResponse, messageKey);
//    }
//
//    public static ServerResponse failureStaticMessage(String message, Object data) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(false);
//        serverResponse.setCode("2");
//        serverResponse.setErrObj(data);
//        serverResponse.setMessage(message);
//        return serverResponse;
//    }
//
//    public static ServerResponse failureResponse(String messageKey, String respCode) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(false);
//        serverResponse.setCode(respCode);
//        return MessageComposer.compose(serverResponse, messageKey);
//    }
//
//    public static ServerResponse failureResponse(String messageKey, Map<String, String> parameters) {
//        ServerResponse serverResponse = new ServerResponse();
//        serverResponse.setSuccess(false);
//        serverResponse.setCode("2");
//        return MessageComposer.compose(serverResponse, messageKey, parameters);
//    }

    public static UnauthorizedResponse unauthorizedResponse(String messageKey) {
        //Message message = MessageComposer.compose(messageKey);

        UnauthorizedResponse unauthorizedResponse = new UnauthorizedResponse();
        unauthorizedResponse.setSuccess(false);
        unauthorizedResponse.setSessionTimeout("Y");
        unauthorizedResponse.setMessage(messageKey);
        unauthorizedResponse.setUnicodeMessage(messageKey);
        return unauthorizedResponse;
    }

//    public static ServerResponse responseMessage(ServerResponse serverResponse, String messageKey) {
//        Message message = MessageComposer.compose(messageKey);
//        serverResponse.setMessage(message.getMessage());
//        serverResponse.setUnicodeMessage(message.getUnicodeMessage());
//        return serverResponse;
//    }
}