package login;

import javax.swing.*;

import chatting.AddNewChatRoomInDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinPanel extends JFrame {
	
    private CheckUserInDB checker = new CheckUserInDB(); // 회원가입할때 사용할 checker 객체
    private JTextField id = new JTextField(16);
    private JPasswordField pwd = new JPasswordField(26);
    private JPasswordField pwdCheck = new JPasswordField(26);
    private JTextField name = new JTextField(26);
    private JTextField email = new JTextField(26);

    public JoinPanel() {
        setTitle("홍톡 2.0 회원가입");
        
        //타이틀 패널 설정
        JLabel title = new JLabel("회원가입", JLabel.CENTER);
        title.setForeground(new Color(5, 0, 153));
        title.setFont(new Font("Dialog", Font.BOLD, 40));
        
        // 회원가입 버튼 스타일 설정
        JButton join = new JButton("회원가입");
        join.setBackground(Color.WHITE);
        join.setForeground(Color.BLACK);

        //취소 버튼 스타일 설정
        JButton cancel = new JButton("취소");
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);

        JPanel idPanel = createPanelWithLabelAndField("아이디 : ", id);
        
        // 중복확인 버튼 추가
 		JButton checkDuplicate = new JButton("중복확인");
 		// 중복확인 버튼 클릭 시 아이디 중복 검사 진행
 		checkDuplicate.addActionListener(new ActionListener() {
 		    @Override
 		    public void actionPerformed(ActionEvent e) {
 		        String enteredId = id.getText();
 		        // 아이디 입력 안되어있을 시에 중복확인 버튼 누르면 입력하라는 메시지 출력
 		        if (enteredId.isEmpty()) {
 		            JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
 		        }
 		        else {
 		        	// CheckUserInDB 클래스의 메소드를 호출하여 아이디 중복을 확인
 	 		        boolean isDuplicate = checker.checkUserID(enteredId);
 	 		        if (isDuplicate) {
 	 		            JOptionPane.showMessageDialog(null, "이미 사용 중인 아이디입니다.");
 	 		        } else {
 	 		            JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
 	 		        }
 		        } 
 		    }
 		});
     	idPanel.add(checkDuplicate);

        JPanel pwdPanel = createPanelWithLabelAndField("비밀번호 : ", pwd);
        JPanel pwdCheckPanel = createPanelWithLabelAndField("비밀번호 확인 : ", pwdCheck);
        JPanel namePanel = createPanelWithLabelAndField("이름 : ", name);
        JPanel emailPanel = createPanelWithLabelAndField("이메일 : ", email);

        JPanel formPanel = new JPanel(new GridLayout(5, 1));
        formPanel.add(idPanel);
        formPanel.add(pwdPanel);
        formPanel.add(pwdCheckPanel);
        formPanel.add(namePanel);
        formPanel.add(emailPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(formPanel);

        JPanel buttonPanel = createButtonPanel(join, cancel);
        
        //타이틀 패널을 상단, 콘텐트 패널을 센터, 회원가입과 취소 버튼을 하단에 배치
        add(title, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setBounds(200, 200, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false); // 패널 크기 조정 불가능하도록 설정

        attachActionListeners(join, cancel);
    }

    private JPanel createPanelWithLabelAndField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private JPanel createButtonPanel(JButton join, JButton cancel) {
        JPanel panel = new JPanel();
        panel.add(join);
        panel.add(cancel);
        return panel;
    }
    
    /**
     * 회원가입 버튼 클릭 시에 검사진행 메서드
     * @param join
     * @param cancel
     */
    private void attachActionListeners(JButton join, JButton cancel) {
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myId = id.getText();
                String myPwd = new String(pwd.getPassword());
                String myPwdCheck = new String(pwdCheck.getPassword());
                String myName = name.getText();
                String myEmail = email.getText();

                if (myId.isEmpty() || myPwd.isEmpty() || myPwdCheck.isEmpty() || myName.isEmpty() || myEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "모든 정보를 입력하세요.");
                } else if (!myPwd.equals(myPwdCheck)) {
                    JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
                } else if (myId.length() < 8 || myId.length() > 16) {
                    JOptionPane.showMessageDialog(null, "아이디는 8자 이상 16자 이하로 입력해주세요.");
                } else if (checker.checkUserEmail(myEmail)) {
                    JOptionPane.showMessageDialog(null, "이미 존재하는 이메일입니다.");
                } else {
                	AddNewUserInDB newUser = new AddNewUserInDB(myId, myPwd, myName, myEmail);
                	newUser.addNewUser();
                    JOptionPane.showMessageDialog(null, "가입 완료되었습니다.");
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPanel();
                dispose();
            }
        });
    }

}
