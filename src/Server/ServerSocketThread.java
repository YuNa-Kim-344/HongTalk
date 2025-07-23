package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.awt.image.BufferedImage;

public class ServerSocketThread extends Thread {
    Socket socket;
    ChatServer server;
    BufferedReader in;
    PrintWriter out;
    String name;
    String threadName;
    private ObjectOutputStream objectOutputStream;
    
    public ServerSocketThread(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        threadName = super.getName();
        System.out.println(socket.getInetAddress() + "님이 입장하였습니다.");
        System.out.println("Thread Name : " + threadName);
    }

    public void sendMessage(String str) {
        out.println(str);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            
            sendMessage("대화자 이름을 입력하세요");
            name = in.readLine();
            server.broadCasting("[" + name + "]님이 입장하셨습니다.");

            while (true) {
                String str_in = in.readLine();
                server.broadCasting("[" + name + "] " + str_in);
            }
        } catch (IOException e) {
            System.out.println(threadName + " 퇴장했습니다.");
            server.removeClient(this);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 이미지를 클라이언트로 전송하는 메서드
    public void sendImage(Serializable image) {
        try {
            if (objectOutputStream == null) {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            }
            objectOutputStream.writeObject(image);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
