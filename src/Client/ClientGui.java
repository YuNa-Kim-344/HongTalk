package Client;

import Canvas.Canvas;
import Server.ServerSocketThread;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class ClientGui extends JFrame implements ActionListener, Runnable {
    // 클라이언트 화면 필드
    Container container = getContentPane();
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    JTextField textField = new JTextField();
    // 통신 필드
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    String str; // 채팅 문자열 저장 필드

    public ClientGui(String ip, int port) {
        // 프레임 기본 설정
        setTitle("홍톡 2.0");
        setSize(500, 550);
        setLocation(400, 400);
        

        // 프레임 화면 설정
        init();
        start();
        setVisible(true);
        

        // 통신 초기화
        initNet(ip, port);
        System.out.println("ip = " + ip);
        
        
        this.setLocationRelativeTo(null); // 화면 중앙에 표시되도록 설정
        // 창 크기 조절 불가능하도록 설정
        setResizable(false);
        
    }

    // 통신 초기화
    private void initNet(String ip, int port) {
        try {
            // 서버에 접속 시도
            socket = new Socket(ip, port);
            // 통신용 input, output 클래스 설정
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // true : auto flush 설정
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            System.out.println("통신 Reader, Writer 설정 완료");

            // 쓰레드 구동
            Thread thread = new Thread(this); // run 함수 -> this
            thread.start();
        } catch (UnknownHostException e) {
            System.out.println("IP 주소가 다릅니다.");
        } catch (IOException e) {
            System.out.println("접속 실패");
            e.printStackTrace();

            // 접속 실패 시 쓰레드 구동을 막기 위해 예외 발생 시점에서 return하게하기
            return;
        }
    }

    // GUI 구성 메서드
    private void init() {
        container.setLayout(new BorderLayout());
        //채팅 팬
        container.add("Center", scrollPane);
        
        //하단 팬 	
        JPanel southPanel = new JPanel(new BorderLayout());

        JButton sendButton = new JButton("전송");
        sendButton.setBackground(Color.WHITE);
        sendButton.setForeground(Color.BLACK);
        // 'sendButton' ActionListener 추가
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String msgString = textField.getText();
                // 메시지가 비어 있지 않을 때만 전송
                if (!msgString.isEmpty()) {
                    // 서버에게 메시지 전달
                    str = msgString; // 'str'에 메시지 저장
                    out.println(str); // 서버로 메시지 전송
                    textField.setText(""); // 전송 후 textField 비우기
                }
            }
        });

        
        //그림판 버튼을 textField 왼쪽에 위치시킴
        JButton drawButton = new JButton("그림판");
        drawButton.setBackground(Color.WHITE);
        drawButton.setForeground(Color.BLACK);
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 그림판 열기 메서드 작성
            	Canvas canvas = new Canvas(); // 그림판 객체 생성
            }
        });

        southPanel.add("Center", textField);
        southPanel.add("East", sendButton);
        southPanel.add("West", drawButton);
        container.add("South", southPanel);
        

    }

    private void start() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textField.addActionListener(this);
    }
 
    // 응답 대기
    // -> 서버로부터 응답으로 전달된 문자열을 읽어서, textArea에 출력하기
    @Override
    public void run() {
        while (true) {
            try {
                str = in.readLine();
                textArea.append(str + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // textField의 문자열을 읽어옴
        String str = textField.getText();

        // 문자열이 null이 아니고 비어있지 않은 경우에 서버로 전송
        if (str != null && !str.isEmpty()) {
            out.println(str); // 서버로 메시지 전송
            // textField 초기화
            textField.setText("");
        }
    }

    
   
}
