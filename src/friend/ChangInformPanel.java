package friend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import login.*;

// 사용자 정보 수정을 위한 패널 클래스
public class ChangInformPanel extends JFrame {
    
    private String myId;  // 현재 사용자 ID
    private JTextField idField;  // ID 입력 필드
    private JPasswordField passwordField;  // 비밀번호 입력 필드 (보안을 위해)
    private JTextField emailField;  // 이메일 입력 필드
    private JTextField nameField;  // 이름 입력 필드

    // 생성자
    public ChangInformPanel(String myId) {
        this.myId = myId;

        // 타이틀과 프로그램 이름 설정
        setTitle("홍톡 2.0");

        setBounds(300, 300, 500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);

        // 타이틀 패널 설정
        JLabel title = new JLabel("내 정보", JLabel.CENTER);
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Dialog", Font.BOLD, 30));
        add(title, BorderLayout.NORTH);

        // 내 정보 수정 버튼 스타일 설정
        JButton chang = new JButton("수정");
        chang.setBackground(Color.WHITE);
        chang.setForeground(Color.BLACK);

        // 탈퇴 버튼 스타일 설정
        JButton resign = new JButton("탈퇴");
        resign.setBackground(Color.WHITE);
        resign.setForeground(Color.BLACK);

        JPanel buttonPanel = createButtonPanel(chang, resign);
        add(buttonPanel, BorderLayout.SOUTH);

        // 사용자 정보 수정을 위한 입력 필드 설정
        idField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        nameField = new JTextField(20);

        // 사용자 정보 로드
        loadUserInfo();

        // 사용자 정보 입력 필드를 담은 패널 설정
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.CENTER);

        // 버튼에 액션 리스너 연결
        attachActionListeners(chang, resign);
    }

    // 버튼 패널 생성 메서드
    private JPanel createButtonPanel(JButton chang, JButton resign) {
        JPanel panel = new JPanel();
        panel.add(chang);
        panel.add(resign);
        return panel;
    }

    // 정보 입력 필드 패널 생성 메서드
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        return panel;
    }

    // 사용자 정보 로드 메서드
    private void loadUserInfo() {
        // 데이터베이스에서 사용자 정보 로드하여 필드에 설정
        GetInformDB getInformDB = new GetInformDB(myId);
        User userInfo = getInformDB.getInform();

        idField.setText(userInfo.getUserId());
        passwordField.setText(userInfo.getUserPassword());
        emailField.setText(userInfo.getUserEmail());
        nameField.setText(userInfo.getUserName());
    }

    // 액션 리스너 연결 메서드
    private void attachActionListeners(JButton chang, JButton resign) {
        chang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 수정된 정보를 데이터베이스에 업데이트
                String newId = idField.getText();
                String newPwd = new String(passwordField.getPassword());
                String newEmail = emailField.getText();
                String newName = nameField.getText();

                UpdateInformDB updateinform = new UpdateInformDB(myId, newId, newPwd, newName, newEmail);
                updateinform.updateUserInfo();
                JOptionPane.showMessageDialog(null, "정보 수정 완료", "확인", JOptionPane.INFORMATION_MESSAGE);
                new FriendInformPanel(myId);
                dispose();  // 현재 창 닫기
            }
        });

        resign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "탈퇴" 버튼 클릭 시 처리
                int response = JOptionPane.showConfirmDialog(null, "회원 탈퇴하시겠습니까?", "회원 탈퇴 확인", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // MySQL 데이터베이스에서 사용자 정보 삭제
                    DeleteInformDB.deleteInform(myId);

                    new Login();  // 로그인 패널 열기
                    dispose();  // 현재 창 닫기
                }
            }
        });
    }
}
