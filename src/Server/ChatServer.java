package Server;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.Serializable;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Canvas.SerializableImage;
import Client.ClientGui;

/**
 * 채팅 서버를 담당하는 클래스. 채팅 서버를 쓰레드로 관리하여서 각 채팅 방이 개별적으로 작동할수 있도록 한다
 */
public class ChatServer {
	ServerSocket serverSocket;
	Socket socket;
	List<Thread> list;	// ServerSocketThread 객체 저장할 리스트
	
	public ChatServer() {
		list = new ArrayList<Thread>();
		System.out.println("쓰레드 동적 리스트 객체 생성.");
	}
	public void giveAndTake(int portNum) {
	    try {
	        ServerSocket serverSocket = new ServerSocket(portNum); // 서버소켓 생성
	        System.out.println("포트번호" + portNum + " 서버 소켓 생성.");
	        serverSocket.setReuseAddress(true); // 소켓으로 전송된 데이터가 남아있어도 다른 소켓이 와서 해당 포트를 바인드

	        // 서버 접속을 기다리는 쓰레드 생성 및 실행 -> 서버를 만들고 바로 클라이언트로 접속하기 위함
	        Thread acceptThread = new Thread(() -> {
	            while (true) {
	                try {
	                    socket = serverSocket.accept(); // accept -> 1. 소켓 접속 대기 2. 소켓 접속 허락
	                    ServerSocketThread thread = new ServerSocketThread(this, socket); // this -> ChatServer 자신
	                    addClient(thread); // 리스트에 쓰레드 객체 저장
	                    thread.start();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        acceptThread.start(); // 쓰레드 실행

	    } catch (BindException e) {
	        System.out.println("바인딩 오류 발생");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	// synchronized로 쓰레드들이 공유데이터를 함께 사용하지 못하도록 한다
	// 클라이언트가 입장 시 호출되며, 리스트에 클라이언트 담당 쓰레드 저장
	private synchronized void addClient(ServerSocketThread thread) {
		// 리스트에 ServerSocketThread 객체 저장
		list.add(thread);
		System.out.println("Client 1명 입장. 총 " + list.size() + "명");
	}		
	// 클라이언트가 퇴장 시 호출되며, 리스트에 클라이언트 담당 쓰레드 제거
	public synchronized void removeClient(Thread thread) {
		list.remove(thread);
		System.out.println("Client 1명 퇴장. 총 " + list.size() + "명");
	}
	// 모든 클라이언트에게 채팅 내용 전달
	public synchronized void broadCasting(String str) {
		for(int i = 0; i < list.size(); i++) {
			ServerSocketThread thread = (ServerSocketThread)list.get(i);
			thread.sendMessage(str);
		}
	}
	
	 // 이미지를 모든 클라이언트에게 전송
    public synchronized void sendImageToAllClients(BufferedImage image) {
        SerializableImage serializableImage = new SerializableImage(image);
        for (Thread thread : list) {
            if (thread instanceof ServerSocketThread) {
                ServerSocketThread socketThread = (ServerSocketThread) thread;
                socketThread.sendImage(serializableImage);
            }
        }
    }
}