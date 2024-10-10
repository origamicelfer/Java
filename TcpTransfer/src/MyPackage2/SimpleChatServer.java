package MyPackage2;

import java.io.*;
import java.net.*;
import java.util.*;
public class SimpleChatServer {

    ArrayList<PrintWriter> clientOutputStreams;

    public static void main(String[] args) {
        new SimpleChatServer().go();
    }
    public void go() {
        //请手动输入代码
        clientOutputStreams = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                //创建一个线程,用于接收客户消息
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;
        public ClientHandler(Socket clientSocket) {
            //请手动输入代码
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                reader = new BufferedReader(isReader);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    String printMessage = socket.getPort() + "端口："+message;
                    System.out.println("read： " + printMessage);
                    tellEveryone(printMessage);
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }
    }
    public void tellEveryone(String message) {
        //请手动输入代码
        Iterator<PrintWriter> it = clientOutputStreams.iterator();
        while(it.hasNext()){
            try {
                PrintWriter writer = it.next();
                writer.println(message);
                writer.flush();//刷新缓冲区
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
