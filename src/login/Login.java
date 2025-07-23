package login;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import Server.ChatServer;
import chatting.ChatRoomPanel;

class LoginPanel extends JFrame {

    public LoginPanel() {
        // 타이틀과 프로그램 이름
        setTitle("홍톡 2.0 로그인");

        // 생성 메서드 호출
        createPanels();

        setBounds(300, 300, 400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false); // 패널 크기 조정 불가능하도록 설정
    }

    private void createPanels() {
    	// 타이틀 패널 설정
        JPanel title = new JPanel();
        JLabel login = new JLabel("HongTalk 2.0");
        login.setForeground(new Color(5, 0, 153));
        login.setFont(new Font("Dialog", Font.BOLD, 30));
        title.add(login);

        // 입력 필드 패널 설정
        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(3, 2));
		
        // 아이디 레이블 생성,추가
		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb1 = new JLabel("아이디 : ", JLabel.CENTER);	
		idPanel.add(jlb1);
		//아이디 텍스트필드 생성,추가
		JPanel idPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField jtf1 = new JTextField(16);
		idPanel2.add(jtf1);
		// 아이디 패널에 추가
		jp1.add(idPanel);
		jp1.add(idPanel2);
		
		// 비밀번호 레이블 생성, 추가
		JPanel pwdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb2 = new JLabel("비밀번호 : ", JLabel.CENTER);
		// 비밀번호 텍스트필드 생성, 추가
		JPanel pwdPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPasswordField jtf2 = new JPasswordField(16);
		pwdPanel.add(jlb2); pwdPanel2.add(jtf2);
		// 비밀번호 패널에 추가
		jp1.add(pwdPanel); 
		jp1.add(pwdPanel2);
		
		//로그인 버튼 스타일 설정
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jLogin = new JButton("로그인");
        jLogin.setBackground(Color.WHITE);
        jLogin.setForeground(Color.BLACK); // 버튼 텍스트 색상
        loginPanel.add(jLogin);
        	
        //회원가입 버튼 스타일 설정
        JPanel joinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton join = new JButton("회원가입");
        join.setBackground(Color.WHITE);
        join.setForeground(Color.BLACK); // 버튼 텍스트 색상
        joinPanel.add(join);
        
        //로그인 버튼과 회원가입 버튼 패널에 추가
        jp1.add(loginPanel);
        jp1.add(joinPanel);
		
        JPanel jp2 = new JPanel();
        jp2.setLayout(new FlowLayout());
        jp2.add(jp1);

        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(jp2, BorderLayout.CENTER);

        // 로그인 버튼 눌렀을 때 이벤트 처리
        jLogin.addActionListener(new ActionListener() {            
        	@Override
            public void actionPerformed(ActionEvent e) {
                String myId = jtf1.getText();
                String myPwd = new String(jtf2.getPassword());

                handleLogin(myId, myPwd);
            }
        });

        // 회원가입 버튼 눌렀을 때 이벤트 처리
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleJoin();
            }
        });
        
        // 비밀번호 입력하고 enter 누르면 로그인하도록 ActionListener 추가
        jtf2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myId = jtf1.getText();
                String myPwd = new String(jtf2.getPassword());

                handleLogin(myId, myPwd);
            }
        });
    }

    private void handleLogin(String myId, String myPwd) {
    	CheckUserInDB checker = new CheckUserInDB(); // 회원가입할때 사용할 checker 객체
    	
        // 모든 정보 입력되었는지 확인
        if (myId.isEmpty() || myPwd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "모든 정보를 입력하세요.");
        }
          // 아이디 존재여부 체크 -> Id와 Password의 쌍이 일치해야 함
          else if (!checker.checkUserID(myId)) {
            JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");
        } else if (!checker.checkUserInform(myId, myPwd)) {
            JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 일치하지 않습니다.");
        } 
        /*
         *  아이디 비밀번호 일치하면 로그인. 이후 메인 패널을 띄운다 
         */
         else {
           new ChatRoomPanel(myId);
           dispose(); 
        }
    }

    private void handleJoin() {
        new JoinPanel();
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }
}

public class Login extends JFrame {
	
    public static void main(String[] args) {
    	new LoginPanel();
    }
}
