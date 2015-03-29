package com.jfreer.game.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import gen.ProtocolUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyHandler extends TextWebSocketHandler {
    private ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();

    public ConcurrentHashMap<String, WebSocketSession> getSessionMap() {
        return sessionMap;
    }

    private ObjectMapper json = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println(session.getId() + ":" + message.getPayload());
        //System.out.println(message.getPayload());
        try {
            ReqStruct reqStruct = json.readValue(message.getPayload(), ReqStruct.class);
            Integer protocolNo = reqStruct.getNo();
            if (ProtocolUtils.REQ_MAPPINGS.containsKey(protocolNo)) {
                Class reqClazz = ProtocolUtils.REQ_MAPPINGS.get(protocolNo);
                Class protocolClazz = ProtocolUtils.getHandlerName(reqClazz);
                Object obj = json.readValue(reqStruct.getData(), reqClazz);
                if (obj instanceof IReq) {
                    IProtocol protocol = (IProtocol) protocolClazz.newInstance();
                    protocol.process(session, (IReq) obj);
                } else {
                    System.out.println(String.format("[%s] must extend [%s]!", protocolClazz.toString(), IProtocol.class.toString()));
                }
            } else {
                System.out.println("Protocol number not exist !!!" + protocolNo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("session:" + session.toString() + ":" + status.toString());
        sessionMap.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("session:" + session.toString() + "#####");
        sessionMap.put(session.getId(), session);
    }
}