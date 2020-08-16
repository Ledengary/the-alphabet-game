package Welcome;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Main.*;

public class Welcome {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Welcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 953, 560);
		frame.setBackground(new Color(165, 207, 195));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setFocusable(false);
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignIn signIn = new SignIn();
				frame.dispose();
				signIn.setVisible(true);
			}
		});
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnSignIn.setBounds(340, 492, 117, 47);
		btnSignIn.setForeground(new Color(165, 207, 195));
		btnSignIn.setBackground(new Color(17, 21, 20));
		btnSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getContentPane().add(btnSignIn);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp signUp = new SignUp();
				frame.dispose();
				signUp.setVisible(true);
			}
		});
		btnSignUp.setForeground(new Color(165, 207, 195));
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnSignUp.setFocusable(false);
		btnSignUp.setBackground(new Color(17, 21, 20));
		btnSignUp.setBounds(467, 492, 117, 47);
		frame.getContentPane().add(btnSignUp);

		JLabel lblMainTheme = new JLabel("");
		lblMainTheme.setBounds(0, 0, 1030, 563);
		frame.getContentPane().add(lblMainTheme);
		Image imgMainTheme = new ImageIcon(this.getClass().getResource("/Welcome.jpg")).getImage();
		lblMainTheme.setIcon(new ImageIcon(imgMainTheme));

		sortTxtFiles();
	}

	private void sortTxtFiles() {
		sortFirstName();
		sortLastName();
		sortCountry();
		sortAnimal();
	}

	private void sortLastName() {
		ArrayList<String> lastNames = new ArrayList<String>();
		ArrayList<String> lastNamesFinal = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		try {
			FileReader fr = new FileReader("lastNames.txt");
			br = new BufferedReader(fr);
			while (fullStr != null) {
				lastNames.add(fullStr);
				fullStr = br.readLine();
			}
			Collections.sort(lastNames);
			
			for (int i = 0; i < lastNames.size(); i++) {
				if (lastNames.get(i).length() > 0) {
					lastNamesFinal.add(lastNames.get(i).substring(0, 1).toUpperCase() + lastNames.get(i).substring(1));
				}
			}
			FileWriter frw = new FileWriter("lastNames.txt");
			brw = new BufferedWriter(frw);
			for (String line : lastNamesFinal) {
				brw.write(line);

				brw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (brw != null) {
					brw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	private void sortAnimal() {
		ArrayList<String> animal = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		try {
			FileReader fr = new FileReader("animals.txt");
			br = new BufferedReader(fr);
			while (fullStr != null) {
				animal.add(fullStr);
				fullStr = br.readLine();
			}
			Collections.sort(animal);

			FileWriter frw = new FileWriter("animals.txt");
			brw = new BufferedWriter(frw);
			for (String line : animal) {
				brw.write(line);

				brw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (brw != null) {
					brw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void sortCountry() {
		ArrayList<String> country = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		try {
			FileReader fr = new FileReader("countries.txt");
			br = new BufferedReader(fr);
			while (fullStr != null) {
				country.add(fullStr);
				fullStr = br.readLine();
			}
			Collections.sort(country);

			FileWriter frw = new FileWriter("countries.txt");
			brw = new BufferedWriter(frw);
			for (String line : country) {
				brw.write(line);

				brw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (brw != null) {
					brw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	private void sortFirstName() {
		ArrayList<String> lastNames = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		try {
			FileReader fr = new FileReader("lastNames.txt");
			br = new BufferedReader(fr);
			while (fullStr != null) {
				lastNames.add(fullStr);
				fullStr = br.readLine();
			}
			Collections.sort(lastNames);

			FileWriter frw = new FileWriter("lastNames.txt");
			brw = new BufferedWriter(frw);
			for (String line : lastNames) {
				brw.write(line);

				brw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (brw != null) {
					brw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
}
