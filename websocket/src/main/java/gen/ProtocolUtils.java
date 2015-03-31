package gen;


import gen.handler.AnonymousLoginHandler;
import gen.request.AnonymousLoginReq;
import gen.handler.JoinTableHandler;
import gen.request.JoinTableReq;


import java.util.HashMap;
import java.util.Map;

public class ProtocolUtils {
    public static class Tuple{
        private final Class reqClass;
        private final Class handlerClass;

        public Tuple(Class reqClass, Class handlerClass) {
            this.reqClass = reqClass;
            this.handlerClass = handlerClass;
        }

        public Class getReqClass() {
            return reqClass;
        }

        public Class getHandlerClass() {
            return handlerClass;
        }
    }

    public static final Map<Integer, Tuple> REQ_MAPPINGS = new HashMap<Integer, Tuple>();

    static {
        REQ_MAPPINGS.put(0, new Tuple(AnonymousLoginReq.class,AnonymousLoginHandler.class));
        REQ_MAPPINGS.put(1, new Tuple(JoinTableReq.class,JoinTableHandler.class));
    }
}