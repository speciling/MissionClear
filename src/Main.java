import client.Client;
import server.Server;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("client")) {
            Client client = new Client();
            if (args.length == 2)
                client.setIp(args[1]);
            client.start();
        }
        else if (args[0].equals("server")) {
            Server server = new Server();
            server.start();
        }
        else {
            System.out.println("인자를 다시 확인해주세요.");
        }
    }
}
