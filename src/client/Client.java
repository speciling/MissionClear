package client;

import client.db.ClientDBManager;
import client.login.Login;
import client.net.ClientSocket;

public class Client extends Thread{
    String ip = "localhost";
    int PORT = 8080;

    public static void main(String [] args) {
          ClientSocket socket = new ClientSocket("172.31.99.182",8080);
          socket.start();
          ClientDBManager.init();
          Login login = new Login();
          login.loginpage();
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void run() {
        ClientSocket socket = new ClientSocket(ip, PORT);
        socket.start();
        ClientDBManager.init();
        Login login = new Login();
        login.loginpage();
    }
}
