package server;

import server.db.ServerDBManager;
import server.service.Reactor;

import java.io.IOException;

public class Server {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        ServerDBManager.init();
        try {
            Reactor reactor = new Reactor(PORT);
            reactor.run();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
