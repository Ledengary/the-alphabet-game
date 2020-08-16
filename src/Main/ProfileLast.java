package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import Server.*;

public class ProfileLast extends JFrame {

	private JPanel contentPane;
	public static String userName = "Reza";
	public static String profilePictureAddress = "Reza.jpg";
	private static BufferedReader in;
	private static PrintWriter out;
	private static Socket socket;
	private static JLabel lblStatusUpdate;

	/**
	 * Launch the application.
	 */
	public static void main(String userName, String profilePictureAddress) {
		System.out.println("MAIN : " + userName + " and " + profilePictureAddress);
		try {
			// Server server = new Server(9512);
			// if (server.listener.isClosed()) {
			// System.out.println("is closed");
			// }
			// System.out.println("Server Opened (ProfileLast Main)");
			ProfileLast frame = new ProfileLast(userName, profilePictureAddress);
			frame.setVisible(true);
		} catch (Exception eNew) {
			// TODO: handle exception
		}
	}

	/**
	 * Create the frame.
	 */
	public ProfileLast(String userName, String profilePictureAddress) {
		this.userName = userName;
		this.profilePictureAddress = profilePictureAddress;
		System.out.println("CONSTRUCTOR : " + this.userName + " and " + this.profilePictureAddress + " are set...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnHost = new JButton("Host a Server");
		btnHost.setBackground(new Color(17, 21, 20));
		btnHost.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHost.setFocusable(false);
		btnHost.setForeground(new Color(165, 207, 195));
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * karbar daraye do bakhsh e, ke agar ro host click kone adress va name karbar
				 * azash porside mishe va socket i ba moshakhasat dade shode sakhte mishe...
				 */
				String serverAddress = JOptionPane.showInputDialog(btnHost, "Enter server address:", "Wellcome",
						JOptionPane.QUESTION_MESSAGE);
				try {
					socket = new Socket(serverAddress, 9512);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(socket.getOutputStream(), true);
					out.println("HOST");
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				while (true) {
					// receive clients input
					String line = null;
					String hostName = "";
					try {
						line = in.readLine();
					} catch (IOException e3) {
						e3.printStackTrace();
					}

					// example for text protocol
					if (line.startsWith("SUBMIT-NAME")) {
						hostName = JOptionPane.showInputDialog(btnHost, "Enter Your Name:", "Name",
								JOptionPane.QUESTION_MESSAGE);
						/**
						 * be server darkhast sabt host ra ferestade va montazer mimanim ta peigham set
						 * az server mabni bar set shodanesh beresad
						 */
						out.println(hostName);
						out.flush();
						setTitle(hostName);
					} else if (line.startsWith("SET")) {
						line = line.substring(4, line.length());
						String[] names = line.split("_");
						JOptionPane.showMessageDialog(null, "[" + names[0] + "]: You've Been Connected to " + names[1]);
						setTitle("HOST");
						int roomId = Integer.parseInt(names[2]);
						lblStatusUpdate.setText("Connected to " + names[1]);
						GamePage.playerName = hostName;
						GamePage.playerNumber = 1;
						GamePage.serverAddress = serverAddress;
						GamePage.main(socket, serverAddress, names[0], 1, roomId);
						dispose();
						break;
					} else {
						System.out.println(line + " :|");
					}
				}
			}
		});
		btnHost.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHost.setBounds(340, 167, 238, 75);
		contentPane.add(btnHost);

		JButton btnJoin = new JButton("Join a Server");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * agar karbar rooye dokme join clickkonad baz ham socket i sakhte mishe ke
				 * neshani join ra darast be server darkhast join shodanash ra dade va server
				 * list i az host konande haye mojood ra barmigardanad va montazer host entekhab
				 * shode az karbar mimanad
				 */
				String serverAddress = JOptionPane.showInputDialog(btnHost, "Enter server address:", "Wellcome",
						JOptionPane.QUESTION_MESSAGE);
				try {
					socket = new Socket(serverAddress, 9512);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(socket.getOutputStream(), true);
					out.println("JOIN");
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				JComboBox jcd = null;
				while (true) {
					// receive clients input
					String line = null;
					String[] getHostNames;
					String joinName = "";
					try {
						line = in.readLine();
					} catch (IOException e3) {
						e3.printStackTrace();
					}

					// example for text protocol
					if (line.startsWith("SUBMIT-NAME")) {
						joinName = JOptionPane.showInputDialog(btnHost, "Enter Your Name:", "Name",
								JOptionPane.QUESTION_MESSAGE);
						out.println(joinName);
						out.flush();
						setTitle(joinName);

					} else if (line.startsWith("NAMES-ON-THE-WAY")) {
						line = line.substring(17, line.length());
						getHostNames = line.split("_");
						jcd = new JComboBox(getHostNames);

						String[] options = { "OK" };
						JPanel panel = new JPanel();
						JLabel lbl = new JLabel("Available Hosts : ");
						JTextField txt = new JTextField(10);
						panel.add(lbl);
						panel.add(jcd);
						int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title",
								JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if (selectedOption == 0) {
							out.println(jcd.getSelectedIndex());
						}
					} else if (line.startsWith("SET")) {
						line = line.substring(4, line.length());
						String[] names = line.split("_");
						JOptionPane.showMessageDialog(null, "[" + names[0] + "]: You've Been Connected to " + names[1]);
						setTitle("JOIN");
						int roomId = Integer.parseInt(names[2]);
						lblStatusUpdate.setText("Connected to " + names[1]);
						GamePage.playerName = joinName;
						GamePage.playerNumber = 2;
						GamePage.serverAddress = serverAddress;
						GamePage.main(socket, serverAddress, names[0], 2, roomId);
						dispose();
						break;
					} else {
						System.out.println(line + " :|");
					}
				}
			}
		});
		btnJoin.setFocusable(false);
		btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnJoin.setForeground(new Color(17, 21, 20));
		btnJoin.setBackground(new Color(165, 207, 195));
		btnJoin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnJoin.setBounds(340, 252, 238, 75);
		contentPane.add(btnJoin);

		JLabel lblUserName = new JLabel(userName);
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUserName.setForeground(new Color(165, 207, 195));
		lblUserName.setBounds(282, 11, 304, 40);
		contentPane.add(lblUserName);

		JLabel lblName = new JLabel("Name :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblName.setForeground(new Color(165, 207, 195));
		lblName.setBounds(215, 10, 84, 40);
		contentPane.add(lblName);

		JLabel lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(25, 11, 179, 183);
		Image imgProfilePicture = new ImageIcon(this.getClass().getResource("/" + profilePictureAddress)).getImage()
				.getScaledInstance(lblProfilePicture.getWidth(), lblProfilePicture.getHeight(), Image.SCALE_DEFAULT);
		lblProfilePicture.setIcon(new ImageIcon(imgProfilePicture));
		contentPane.add(lblProfilePicture);

		JLabel lblStatus = new JLabel("Status :");
		lblStatus.setForeground(new Color(165, 207, 195));
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStatus.setBounds(215, 49, 84, 40);
		contentPane.add(lblStatus);

		lblStatusUpdate = new JLabel("");
		lblStatusUpdate.setForeground(new Color(165, 207, 195));
		lblStatusUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStatusUpdate.setBounds(296, 10, 282, 40);
		contentPane.add(lblStatusUpdate);

		JLabel lblMainTheme = new JLabel("");
		lblMainTheme.setBounds(0, -14, 596, 355);
		Image imgMain = new ImageIcon(this.getClass().getResource("/ProfileMain.jpg")).getImage()
				.getScaledInstance(lblMainTheme.getWidth(), lblMainTheme.getHeight(), Image.SCALE_DEFAULT);
		lblMainTheme.setIcon(new ImageIcon(imgMain));
		contentPane.add(lblMainTheme);

	}
}
