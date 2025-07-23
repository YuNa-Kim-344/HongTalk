 package chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Client.ClientGui;
import Server.ChatServer;

/**
 * 새로운 방이 생성(방 추가 or 방 참가)를 수행하기 위한 기능이 포함된 메서드.
 * 새로운 방이 생성되면 화면의 Center부분에 새로운 패널로 만들어 넣고 패널 새로고침
 */
public class RoomsPanel extends JPanel {
    private JPanel roomsPanel;
    private List<JPanel> roomPanels;
    private JScrollPane scrollPane;
    private int maxRooms = 5;

    public RoomsPanel() {
        // 패널 레이아웃 및 스크롤 설정
        setLayout(new BorderLayout());
        roomsPanel = new JPanel(new GridLayout(0, 1));
        scrollPane = new JScrollPane(roomsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 마우스 휠 이벤트 추가
        scrollPane.addMouseWheelListener(e -> mouseWheelMoves(e));

        // 스크롤 패널을 중앙에 추가
        add(scrollPane, BorderLayout.CENTER);

        // 방 목록 및 빈 공간 패널 초기화
        roomPanels = new ArrayList<>();
        addEmptyPanels(maxRooms - 1);
    }

    // 채팅 방 추가 메서드
    public void addChatRoom(String chatRoomName, int chatRoomId) {
        JPanel newRoom = createChatRoomPanel(chatRoomName, chatRoomId);
        roomPanels.add(newRoom);
        updateRoomsPanel();
    }

    // 채팅 방 패널 생성
    private JPanel createChatRoomPanel(String chatRoomName, int chatRoomId) {
    	
        JPanel newRoom = new JPanel(new BorderLayout());
        newRoom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // 방 이름 레이블 생성
        JLabel roomNameLabel = new JLabel(chatRoomName);
        roomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        roomNameLabel.setFont(roomNameLabel.getFont().deriveFont(Font.BOLD, 19));
        // 방 아이디 레이블 생성
        JLabel roomIdLabel = new JLabel(String.valueOf(chatRoomId));
        roomIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
        roomIdLabel.setFont(roomIdLabel.getFont().deriveFont(Font.PLAIN, 13));
        
        // 방 입장, 퇴장 버튼 가지는 패널 생성후 newRoom에 추가
        
       
    	
        JPanel enterAndExitButtonPanel = new JPanel(new BorderLayout());
        JButton enterRoomButton = new JButton("방 입장");
        enterRoomButton.setBackground(Color.WHITE);
        enterRoomButton.setForeground(Color.BLACK);
        enterAndExitButtonPanel.add(enterRoomButton, BorderLayout.WEST);
        enterRoomButton.addActionListener(new ActionListener() { 
        	/*
             * 방 입장 버튼을 눌렀을 때, 채팅 쓰레드가 실행되도록 함.
             * 1. 현재 해당 방 번호(ChatroomId)로 열린 서버가 없을 경우, 서버를 열고, 자신도 클라이언트로 입장.
             * 2. 이미 열려있을 경우 클라이언트로 입장.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
            	//서버가 열려있지 않으면 방 번호를 포트번호로 서버를 열고 클라이언트로 접속한다
                if (!isServerRunning(chatRoomId)) {
                	try {
                		//서버 생성
                		ChatServer server = new ChatServer();
                        server.giveAndTake(chatRoomId);
                        //클라이언트로 입장
                        System.out.println(chatRoomId + "번 방 입장");
                     
                        InetAddress ia = InetAddress.getLocalHost();
                        String ip_str = ia.toString();
                        String ip = ip_str.substring(ip_str.indexOf("/") + 1);
                        new ClientGui(ip, chatRoomId);
                	}catch (Exception e1) {
						// TODO: handle exception
                		e1.printStackTrace();
					}
                }
                //서버가 이미 열려있다면, 서버의 방 번호(포트번호)와 ip로 방에 접속한다
                else {
                    try {
                        System.out.println(chatRoomId + "번 방 입장");
                        //서버 ip 가져오기   
                        String ia = InetAddress.getLocalHost().getHostAddress();
                        String ip_str = ia.toString();
                        String ip = ip_str.substring(ip_str.indexOf("/") + 1);
                        //ip와 방 번호로 클라이언트 입장
                        new ClientGui(ip, chatRoomId);
                    } catch (UnknownHostException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            /**
             * 해당 포트에서 서버가 실행 중인지 확인하는 메서드. 포트번호를 인수로 받아 확인한다
             * 만약 해당 포트 번호로 연결이 성공한다면, 서버가 실행 중이므로 true를 반환
             * @param portNum
             * @return boolean
             */
            private boolean isServerRunning(int portNum) {
                try {
                    Socket socket = new Socket("localhost", portNum);
                    socket.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }

        });
        
        //방 퇴장 버튼
        JButton exitRoomButton = new JButton("방 나가기");
        exitRoomButton.setBackground(Color.WHITE);
        exitRoomButton.setForeground(Color.BLACK);
        enterAndExitButtonPanel.add(exitRoomButton, BorderLayout.EAST);

        // 레이블과 입퇴장 버튼을 패널에 추가
        newRoom.add(roomNameLabel, BorderLayout.NORTH);
        newRoom.add(roomIdLabel, BorderLayout.CENTER);
        newRoom.add(enterAndExitButtonPanel, BorderLayout.EAST);
        return newRoom;
    }

    // 방 목록 업데이트
    protected void updateRoomsPanel() {
        roomsPanel.removeAll();

        for (JPanel roomPanel : roomPanels) {
            roomsPanel.add(roomPanel);
        }
        for (int i = roomPanels.size(); i < maxRooms; i++) {
            roomsPanel.add(new JPanel());
        }
        scrollPane.setViewportView(roomsPanel);
        revalidate();
        repaint();
    }

    // 빈 공간에 패널 추가
    private void addEmptyPanels(int count) {
        for (int i = 0; i < count; i++) {
            roomsPanel.add(new JPanel());
        }
    }

    // 마우스 휠 이벤트 처리
    private void mouseWheelMoves(MouseWheelEvent e) {
        int n = e.getWheelRotation();

        if (roomPanels.size() >= maxRooms) {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(n < 0 ? vertical.getValue() + vertical.getUnitIncrement()
                                    : vertical.getValue() - vertical.getUnitIncrement());
            revalidate();
            repaint();
        }
        revalidate();
        repaint();
    }
}
