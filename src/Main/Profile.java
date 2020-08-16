package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import Server.*;

public class Profile extends JFrame {

	private JPanel contentPane;
	public static String userName = "Reza";
	public static String profilePictureAddress = "Reza";
	private static BufferedReader in;
	private static PrintWriter out;
	private static Socket socket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profile frame = new Profile();
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
	public Profile() {
		System.out.println("Came");
//		try {
//			Server.main(null);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblProfilePicture = new JLabel();
		lblProfilePicture.setBounds(26, 10, 179, 183);
//		Image imgProfilePicture = new ImageIcon(this.getClass().getResource("/" + profilePictureAddress)).getImage()
//				.getScaledInstance(lblProfilePicture.getWidth(), lblProfilePicture.getHeight(), Image.SCALE_DEFAULT);
//		lblProfilePicture.setIcon(new ImageIcon(imgProfilePicture));
		contentPane.add(lblProfilePicture);
		setLocationRelativeTo(null);
		setResizable(false);

		JButton btnHost = new JButton("Host a Server");
		btnHost.setBackground(new Color(17, 21, 20));
		btnHost.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHost.setFocusable(false);
		btnHost.setForeground(new Color(165, 207, 195));
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnHost.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHost.setBounds(340, 167, 238, 75);
		contentPane.add(btnHost);

		JLabel lblMainTheme = new JLabel("");
		lblMainTheme.setBounds(0, 0, 606, 351);
		Image imgMainTheme = new ImageIcon(this.getClass().getResource("/ProfileMain.jpg")).getImage()
				.getScaledInstance(lblMainTheme.getWidth(), lblMainTheme.getHeight(), Image.SCALE_DEFAULT);

		JButton btnJoin = new JButton("Join a Server");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] groupsToShowForEditNames = { "Reza ", "Mamad", "Ahmad" };
				JComboBox jcd = new JComboBox(groupsToShowForEditNames);
				String[] options = { "OK" };
				JPanel panel = new JPanel();
				JLabel lbl = new JLabel("Available Hosts : ");
				JTextField txt = new JTextField(10);
				panel.add(lbl);
				panel.add(jcd);
				int selectedOption = JOptionPane.showOptionDialog(null, panel, "The Title", JOptionPane.NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (selectedOption == 0) {
					JOptionPane.showMessageDialog(null, jcd.getSelectedItem() + " got selected !");
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
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUserName.setForeground(new Color(165, 207, 195));
		lblUserName.setBounds(26, 235, 304, 40);
		contentPane.add(lblUserName);

		JLabel lblName = new JLabel("Name :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblName.setForeground(new Color(165, 207, 195));
		lblName.setBounds(26, 203, 84, 40);
		contentPane.add(lblName);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Profile profile = new Profile();
				dispose();
				profile.setVisible(true);
			}
		});
		btnNewButton.setBounds(51, 306, 85, 21);
		contentPane.add(btnNewButton);
		lblMainTheme.setIcon(new ImageIcon(imgMainTheme));
		contentPane.add(lblMainTheme);
	}
}
