package Canvas;

import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Canvas.*;
import Server.ChatServer;

public class DrawingThread extends Thread {
    private ObjectOutputStream objectOutputStream;
    private RectPanel rectPanel;
    private ObjectInputStream objectInputStream;
    private ChatServer server;
    private Canvas canvas; // Canvas 객체를 저장할 변수 추가

    public DrawingThread(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, RectPanel rectPanel, ChatServer server) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.rectPanel = rectPanel;
        this.server = server;
    }

    public void run() {
        try {
            while (true) {
                Object receivedObject = objectInputStream.readObject();

                if (receivedObject instanceof SerializableImage) {
                    // 이미지가 수신되면 RectPanel에 이미지 설정
                    SerializableImage serializableImage = (SerializableImage) receivedObject;
                    BufferedImage receivedImage = serializableImage.getImage();
                    rectPanel.setImage(receivedImage);

                    // 서버에서 모든 클라이언트로 이미지 전송
                    server.sendImageToAllClients(receivedImage);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void sendImageToServer(BufferedImage image) {
        try {
            if (objectOutputStream != null) {
                SerializableImage serializableImage = new SerializableImage(image);
                objectOutputStream.writeObject(serializableImage);
                objectOutputStream.flush();
            } else {
                System.out.println("ObjectOutputStream is null. Cannot send the image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




