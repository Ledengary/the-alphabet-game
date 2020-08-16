package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletionException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SignIn extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JLabel lblContactImage;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignIn frame = new SignIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 431);
		setDefaultLookAndFeelDecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(new Color(17, 21, 20));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Sign In");
		contentPane.setBackground(new Color(165, 207, 195));

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUserName.setBounds(116, 178, 107, 44);
		contentPane.add(lblUserName);

		txtUserName = new JTextField();
		txtUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtUserName.getText().equals("Ledengary")) {
					txtUserName.setText("");
					txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
					txtUserName.setForeground(new Color(17, 21, 20));
				}
			}
		});
		txtUserName.setText("Ledengary");
		txtUserName.setForeground(Color.GRAY);
		txtUserName.setFont(new Font("Tahoma", Font.ITALIC, 15));
		txtUserName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (txtUserName.getText().equals("Ledengary")) {
					txtUserName.setText("");
					txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
					txtUserName.setForeground(new Color(17, 21, 20));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (txtUserName.getText().isEmpty()) {
					txtUserName.setText("Ledengary");
					txtUserName.setForeground(Color.GRAY);
					txtUserName.setFont(new Font("Tahoma", Font.ITALIC, 15));
				}
			}
		});
		txtUserName.setHorizontalAlignment(JTextField.CENTER);
		txtUserName.setBackground(new Color(165, 207, 195));
		txtUserName.setBounds(57, 215, 222, 36);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		JLabel lblPassword = new JLabel("Password ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(120, 252, 90, 44);
		contentPane.add(lblPassword);

		lblContactImage = new JLabel("");
		lblContactImage.setBounds(77, 10, 183, 181);
		Image imgWelcome = new ImageIcon(this.getClass().getResource("/Contact.png")).getImage()
				.getScaledInstance(lblContactImage.getWidth(), lblContactImage.getHeight(), Image.SCALE_DEFAULT);
		lblContactImage.setIcon(new ImageIcon(imgWelcome));
		contentPane.add(lblContactImage);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPassword.setBounds(57, 288, 222, 36);
		txtPassword.setHorizontalAlignment(JTextField.CENTER);
		txtPassword.setBackground(new Color(165, 207, 195));
		contentPane.add(txtPassword);

		JButton btnSignUp = new JButton("Sign In");
		btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtUserName.getText().isEmpty() && txtPassword.getPassword().length != 0
						&& !txtUserName.getText().equals("Ledengary")) {
					String givenPassword = new String(txtPassword.getPassword());
					String fullStr = "";
					try {
						FileReader fr = new FileReader("Contacts.txt");
						BufferedReader br = new BufferedReader(fr);
						fullStr = br.readLine();
					} catch (Exception e2) {

					}
					boolean founOrNot = false;
					if (fullStr != null) {
						String[] records = fullStr.split("/");
						for (String record : records) {
							String[] part = record.split("_");
							if (txtUserName.getText().equals(part[0]) && givenPassword.equals(part[1])) {
								if (!part[2].equals("Contact2.png")) {
									Image imgWelcome = new ImageIcon(this.getClass().getResource("/" + txtUserName.getText() + ".jpg")).getImage()
											.getScaledInstance(lblContactImage.getWidth(), lblContactImage.getHeight(), Image.SCALE_DEFAULT);
									lblContactImage.setIcon(new ImageIcon(imgWelcome));
								}
								founOrNot = true;
								JOptionPane.showMessageDialog(null, "Welcome " + part[0] + " !", "Sign In Verified",
										JOptionPane.INFORMATION_MESSAGE);
								ProfileLast.userName = txtUserName.getText();
								if (part[2].equals("Contact2.png")) {
									ProfileLast.main(txtUserName.getText() , "Contact2.png");
								}
								else {
									ProfileLast.main(txtUserName.getText() , part[0] + ".jpg");
								}
								dispose();
								break;
							}
						}
						if (!founOrNot) {
							JOptionPane.showMessageDialog(null, "Not Found !", ":(", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "No Contacts Available !", "Warning :/",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Enter The Fields Correctly !", "Error :(",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSignUp.setForeground(new Color(165, 207, 195));
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSignUp.setFocusable(false);
		btnSignUp.setBackground(new Color(17, 21, 20));
		btnSignUp.setBounds(105, 334, 120, 50);
		contentPane.add(btnSignUp);
	}
}
