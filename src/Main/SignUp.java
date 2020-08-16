package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit.ForegroundAction;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private static JTextField txtUserName;
	private static JPasswordField txtPassword;
	private JLabel lblContactImage;
	public static String profilePictureAddress = "";
	public static String profileAddress = "";
	public static boolean signedUpSuccessfully = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 492);
		setDefaultLookAndFeelDecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setBackground(new Color(17, 21, 20));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Sign In");
		contentPane.setBackground(new Color(17, 21, 20));

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUserName.setForeground(new Color(165, 207, 195));
		lblUserName.setBounds(116, 213, 107, 44);
		contentPane.add(lblUserName);

		txtUserName = new JTextField();
		txtUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtUserName.getText().equals("Ledengary")) {
					txtUserName.setText("");
					txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
					txtUserName.setForeground(new Color(165, 207, 195));
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
					txtUserName.setForeground(new Color(165, 207, 195));
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
		txtUserName.setBackground(new Color(17, 21, 20));
		txtUserName.setBounds(57, 254, 222, 36);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		JLabel lblPassword = new JLabel("Password ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setForeground(new Color(165, 207, 195));
		txtUserName.setBackground(new Color(17, 21, 20));
		lblPassword.setBounds(116, 300, 90, 44);
		contentPane.add(lblPassword);

		lblContactImage = new JLabel("");
		lblContactImage.setBounds(82, 10, 168, 164);
		Image imgWelcome = new ImageIcon(this.getClass().getResource("/Contact2.png")).getImage()
				.getScaledInstance(lblContactImage.getWidth(), lblContactImage.getHeight(), Image.SCALE_DEFAULT);
		lblContactImage.setIcon(new ImageIcon(imgWelcome));
		contentPane.add(lblContactImage);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPassword.setBounds(57, 339, 222, 36);
		txtPassword.setHorizontalAlignment(JTextField.CENTER);
		txtPassword.setForeground(new Color(165, 207, 195));
		txtPassword.setBackground(new Color(17, 21, 20));
		contentPane.add(txtPassword);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * dar in bakhsh check haye ziadi mabni bar inke kasi ba usernname dade shode
				 * ghablan sabt nashode bashe va khali nabashan field ha va... anjam mishe...
				 */
				if (!txtUserName.getText().isEmpty() && txtPassword.getPassword().length != 0) {
					String givenPassword = new String(txtPassword.getPassword());
					if (txtUserName.getText().indexOf("_") == -1 && givenPassword.indexOf("_") == -1
							&& txtUserName.getText().indexOf("/") == -1 && givenPassword.indexOf("/") == -1) {
						SignUp.printRecord(txtUserName.getText().toString(), givenPassword.toString());
						if (signedUpSuccessfully) {
							ProfileLast.userName = txtUserName.getText();
							if (profileAddress.equals("Contact2.png")) {
								ProfileLast.main(txtUserName.getText() , "Contact2.png");
							} else {
								ProfileLast.main(txtUserName.getText() , txtUserName.getText() + ".jpg");
							}
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Wrong Inputs given !");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Enter The Fields Correctly !");
				}
			}
		});
		btnSignUp.setBackground(new Color(165, 207, 195));
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSignUp.setFocusable(false);
		btnSignUp.setForeground(new Color(17, 21, 20));
		btnSignUp.setBounds(102, 392, 120, 44);
		contentPane.add(btnSignUp);

		JButton btnAttach = new JButton("Attach");
		btnAttach.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAttach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * inja aval JFileChooser o baz mikonim va dialog oon dar ayande mohtavaye fle
				 * ro migire va to khodesh addressesh ro negah midare, man oon address ro ke
				 * motmaen misham male ye akse ro negah midaram va be onvan profile picture
				 * taraf save mikonam
				 */
				try {
					JFileChooser chooser = new JFileChooser();
					chooser.showOpenDialog(null);
					File f = chooser.getSelectedFile();
					String filename = f.getAbsolutePath();
					profilePictureAddress = filename;
					String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
					if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")
							|| extension.equals("JPG") || extension.equals("JPEG") || extension.equals("gif")
							|| extension.equals("PNG")) {
						ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(
								lblContactImage.getWidth(), lblContactImage.getHeight(), Image.SCALE_DEFAULT));
						lblContactImage.setIcon(imageIcon);
					} else {
						JOptionPane.showMessageDialog(null, "Please choose an Image !");
					}
				} catch (Exception e2) {

				}
			}
		});
		btnAttach.setForeground(new Color(165, 207, 195));
		btnAttach.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnAttach.setFocusable(false);
		btnAttach.setBackground(new Color(17, 21, 20));
		btnAttach.setBounds(116, 184, 107, 29);
		contentPane.add(btnAttach);
	}

	protected static void copyProfilePicture() {
		/**
		 * hala mirim soragh profile picture i ke addressesh ro darim, va oono too
		 * imgSrc mirizim, imgCopy address jaie ke mikhaim oon aks user ro toosh copy
		 * konim ke hamoon ==>(address bin project e !) ke hamin ro bayesti bedast
		 * biarim ==> folder bin ghatan hamoonjai e ke alan safhe LogIn dare ejra mishe
		 * pas mirim soragh directory too khat 276
		 */
		// ---------------------------------------------------------------------------------------------
		String extension = profilePictureAddress.substring(profilePictureAddress.lastIndexOf(".") + 1,
				profilePictureAddress.length());
		// bebinim ke alan in project to kodoom directory dar hal ejrast ! ke mishe ==>
		// C:\Users\Ledengary\first\Project 5
		String javaApplicationPath = null;
		try {
			javaApplicationPath = new File(".").getCanonicalPath();
		} catch (IOException e1) {

		}
		// till here

		if (!profilePictureAddress.equals("")) {
			Path imgSrc = Paths.get(profilePictureAddress);
			Path imgCopy = Paths.get(javaApplicationPath + "\\bin\\" + txtUserName.getText() + "." + extension);
			File fileCopy = new File(javaApplicationPath + "\\bin\\" + txtUserName.getText() + "." + extension);

			if (fileCopy.exists()) {
				try {
					Files.delete(imgCopy);
				} catch (IOException e1) {

				}
			}
			try {
				/*
				 * anjam amal copy !
				 */
				Files.copy(imgSrc, imgCopy);
			} catch (IOException e1) {

			}
			// ---------------------------------------------------------------------------------------------
		} else {
			Path imgSrc = Paths.get("C:\\Users\\Ledengary\\first\\Project 6\\img\\Contact2.png");
			Path imgCopy = Paths.get(javaApplicationPath + "\\bin\\" + txtUserName.getText() + ".png");
			try {
				Files.copy(imgSrc, imgCopy);
			} catch (IOException e) {
			}
		}

	}

	protected static void printRecord(String username, String password) {
		String fullStr = "";
		try {
			FileReader fr = new FileReader("Contacts.txt");
			BufferedReader br = new BufferedReader(fr);
			fullStr = br.readLine();
		} catch (Exception e2) {

		}
		PrintWriter fileOutMatris = null;
		try {
			fileOutMatris = new PrintWriter("Contacts.txt");
		} catch (FileNotFoundException e) {

		}
		if (profilePictureAddress.equals("")) {
			profileAddress = "Contact2.png";
		} else {
			profileAddress = profilePictureAddress.substring(profilePictureAddress.lastIndexOf("\\") + 1,
					profilePictureAddress.length());
		}
		if (fullStr == null) {
			fileOutMatris.print(username + "_" + password + "_" + profileAddress);
			JOptionPane.showMessageDialog(null, "You Signed Up Seccessgully !");
			signedUpSuccessfully = true;
			SignUp.copyProfilePicture();
		} else {
			if (fullStr.indexOf(username) == -1) {
				fileOutMatris.print(fullStr + "/" + username + "_" + password + "_" + profileAddress);
				JOptionPane.showMessageDialog(null, "You Signed Up Seccessgully !");
				signedUpSuccessfully = true;
				SignUp.copyProfilePicture();
			} else {
				if (fullStr.indexOf(username) != 0) {
					if (fullStr.charAt(fullStr.indexOf(username) - 1) == '/'
							&& fullStr.charAt(fullStr.indexOf(username) + username.length()) == '_') {
						JOptionPane.showMessageDialog(null, "Username is Taken !");
					} else {
						fileOutMatris.print(fullStr + "/" + username + "_" + password + "_" + profileAddress);
						JOptionPane.showMessageDialog(null, "You Signed Up Seccessgully !");
						signedUpSuccessfully = true;
						SignUp.copyProfilePicture();
					}
				} else {
					if (fullStr.charAt(fullStr.indexOf(username) + username.length()) == '_') {
						JOptionPane.showMessageDialog(null, "Username is Taken !");
						fileOutMatris.print(fullStr);
					} else {
						fileOutMatris.print(fullStr + "/" + username + "_" + password + "_" + profileAddress);
						JOptionPane.showMessageDialog(null, "You Signed Up Seccessgully !");
						signedUpSuccessfully = true;
						SignUp.copyProfilePicture();
					}
				}
			}
		}
		fileOutMatris.close();
	}
}
