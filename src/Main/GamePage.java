package Main;

import Server.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument.Content;

import javax.swing.JLabel;

public class GamePage extends JFrame {
	/**
	 * khob, miresim be safhe bazi ke bakhsh ziadi az karha haminja anjam mishe
	 */
	private static JPanel contentPane;
	private static JTable table;
	private JScrollPane scrollPane;
	public static String playerName = "Damn";
	public static String serverAddress = "";
	public static int whichRow = 0;
	public static char gameChar = 'a';
	private static Socket socket;
	private static BufferedReader in;
	private static PrintWriter out;
	public static int playerNumber;
	public static int roomId;
	private JLabel lblRoundChar;
	private static JLabel lblCharacter;
	private static DefaultTableModel model;
	public static JLabel lblTimer;
	public static JLabel lblTimeTeller;
	public static int secondsPassed = 180;
	public static Timer gameTimer = new Timer();
	public static TimerTask gameTimerTask = new TimerTask() {
		public void run() {
			secondsPassed--;
			lblTimer.setText(Integer.toString(secondsPassed));
			if (secondsPassed == 0) {
				out.println("STOP-CLICKED" + " " + roomId + " " + playerNumber);
			}
		}
	};
	public static int finalTime = 0;

	/**
	 * Launch the application.
	 */

	public static void main(Socket sentSocket, String serverAddress, String name, int playerNumber, int roomId) {
		GamePage frame = new GamePage(sentSocket, serverAddress, name, playerNumber, roomId);
		/**
		 * shah code in bakhsh yani Thread mast ke hamvare amade ast ta hargoone
		 * dastoori ro az server bekhoone, va ba tavajoh be etelaat va dastoor oon,
		 * vakonesh neshoon bede
		 */
		/**
		 * montaha ghabl az khondan thread be bakhsh akhar hamin safhe ke mishe akhar
		 * GUI refer kon...
		 */
		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					String line = null;
					try {
						line = in.readLine();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Line recieved : " + line);
					if (line.startsWith("GET-NAME")) {
						playerName = line.substring(9, line.length());
						System.out.println("Name is : " + playerName);
					} else if (line.startsWith("GAME-CHAR")) {
						gameChar = line.charAt(10);
						System.out.println("gameChar is set to (" + gameChar + ")...");
						lblCharacter.setText(Character.toString(gameChar));
						table.setValueAt(Character.toString(gameChar), whichRow, 4);
					} else if (line.startsWith("STOP")) {
						/*
						 * STOP sign i e ke harvaght bazikoni rooye button stop ! safhe khodesh click
						 * kard baghie ham stop beshan !
						 */
						lblTimer.setForeground(Color.WHITE);
						lblTimeTeller.setForeground(Color.white);
						System.out.println("STOP came...");
						String stopedByPlayerName = line.substring(line.indexOf(" ") + 1, line.length());
						if (stopedByPlayerName.equals(playerName)) {
							JOptionPane.showMessageDialog(null, "[" + playerName + "] : Game Stoped by you.");
						} else {
							JOptionPane.showMessageDialog(null,
									"[" + playerName + "] : Game Stoped by " + stopedByPlayerName + ".");
						}
						table.setEnabled(false);
						String firstNameField = "";
						if (!model.getValueAt(whichRow, 0).toString().isEmpty()) {
							firstNameField = model.getValueAt(whichRow, 0).toString().substring(0, 1).toUpperCase()
									+ model.getValueAt(whichRow, 0).toString().substring(1).toLowerCase();
						}
						String lsatNameField = "";
						if (!model.getValueAt(whichRow, 1).toString().isEmpty()) {
							lsatNameField = model.getValueAt(whichRow, 1).toString().substring(0, 1).toUpperCase()
									+ model.getValueAt(whichRow, 1).toString().substring(1).toLowerCase();
						}
						String CountryField = "";
						if (!model.getValueAt(whichRow, 2).toString().isEmpty()) {
							CountryField = model.getValueAt(whichRow, 2).toString().substring(0, 1).toUpperCase()
									+ model.getValueAt(whichRow, 2).toString().substring(1).toLowerCase();
						}
						String animalField = "";
						if (!model.getValueAt(whichRow, 3).toString().isEmpty()) {
							animalField = model.getValueAt(whichRow, 3).toString().substring(0, 1).toUpperCase()
									+ model.getValueAt(whichRow, 3).toString().substring(1).toLowerCase();
						}
						/*
						 * SENDING-VALUES sign i ke etelaat dade shode az taraf karbar ro be samt server
						 * pass mide ta davari bar rooye anha anjam beshe
						 */
						out.println("SENDING-VALUES" + " " + playerNumber + " " + roomId + " " + firstNameField
								+ " and " + lsatNameField + " and " + CountryField + " and " + animalField);
						System.out.println("SENDING-VALUES sent...");
					} else if (line.startsWith("SCORE")) {
						line = line.substring(6);
						String[] parts = line.split(" ");
						JOptionPane.showMessageDialog(null, "[" + playerName + "] : Your Score is :" + parts[0]);
						table.setValueAt(parts[0], whichRow, 5);
						whichRow++;
						if (whichRow == 5) {
							/*
							 * agar row badi ma pas az ++ shodan be 5 tabdil shod be manzale in ast ke 5
							 * round bazi anjam shode pas mirim soragh hesab kardan final score ;)
							 */
							JOptionPane.showMessageDialog(null, "Game is Finished !");
							table.setEnabled(false);
							int sumOfScores = Integer.parseInt(table.getValueAt(0, 5).toString())
									+ Integer.parseInt(table.getValueAt(1, 5).toString())
									+ Integer.parseInt(table.getValueAt(2, 5).toString())
									+ Integer.parseInt(table.getValueAt(3, 5).toString())
									+ Integer.parseInt(table.getValueAt(4, 5).toString());
							out.println("GAME-IS-FINISHED" + " " + playerNumber + " " + sumOfScores + " " + roomId);
							System.out.println("game is finished");
						} else {
							/**
							 * agar dar in bakhsh az code boodim yani round aye ma hanooz baghist va dar
							 * natije timer dobare az aval start mikhore, va TEXT-PROTOCOL haye avalie
							 * baraye start bazi i jadid va amade shodan server ersal mishe...
							 */
							GamePage.secondsPassed = 180;
							table.setEnabled(true);
							gameChar = parts[5].charAt(0);
							String[] Object = { "", "", "", "", parts[5], "" };
							lblCharacter.setText(parts[5]);
							model.addRow(Object);
							lblTimer.setForeground(Color.BLACK);
							lblTimeTeller.setForeground(Color.BLACK);
							out.println("UPDATE-SOCKET" + " _" + roomId + "_" + playerNumber + "_" + socket);
							System.out.println("UPDATE-SOCKET sent...");
							out.println("GAME-PAGE-REQUEST-NAME" + " " + playerNumber + " " + roomId);
							System.out.println("GAME-PAGE-REQUEST-NAME sent...");
							out.println("DONE" + " " + roomId);
							System.out.println("DONE " + roomId + " sent...");
						}
					} else if (line.startsWith("RESULTS")) {
						String[] parts = line.split(" ");
						if (parts[1].equals("WON")) {
							JOptionPane.showMessageDialog(null, "YOU WON " + parts[2] + " to " + parts[3] + " !");
						} else if (parts[1].equals("LOST")) {
							JOptionPane.showMessageDialog(null, "YOU LOST " + parts[2] + " to " + parts[3] + " !");
						} else if (parts[1].equals("TIE")) {
							JOptionPane.showMessageDialog(null, "TIE GAME at " + parts[2] + " !");
						} else {
							System.out.println("DAMN :|");
						}
					} else {
						System.out.println("UNKNOWN");
					}
				}
			}
		});
		frame.setVisible(true);
		mainThread.start();
	}

	public GamePage() {

	}

	/**
	 * Create the frame.
	 */
	public GamePage(Socket sentSocket, String serverAddress, String name, int playerNumber, int roomId) {
		gameTimer.scheduleAtFixedRate(gameTimerTask, 1000, 1000);
		socket = sentSocket;
		playerName = name;
		this.serverAddress = serverAddress;
		this.playerNumber = playerNumber;
		this.roomId = roomId;
		System.out.println("Socket Set = " + sentSocket);
		setTitle(playerName + " (Player " + playerNumber + ")");

		try {
			socket = new Socket(serverAddress, 9512);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 992, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 23, 946, 423);
		scrollPane.getViewport().setBackground(new Color(26, 31, 25));
		contentPane.add(scrollPane);
		String[] colNames = { "Name", "Last Name", "Country", "Animal", "Letter", "Point" };
		model = new DefaultTableModel(colNames, 0);
		table = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 4 || column == 5) {
					return false;
				}
				return true;
			};
		};
		table.setShowGrid(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setRowHeight(30);
		String[] Object = { "", "", "", "", "", "" };
		model.addRow(Object);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);

		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setFont(new Font("Tahoma", Font.BOLD, 15));
		tableHeader.setBackground(new Color(26, 31, 25));
		tableHeader.setForeground(Color.WHITE);
		scrollPane.setViewportView(table);

		JButton btnStop = new JButton("STOP !");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstNameField = model.getValueAt(whichRow, 0).toString().toLowerCase();
				String lsatNameField = model.getValueAt(whichRow, 1).toString().toLowerCase();
				String CountryField = model.getValueAt(whichRow, 2).toString().toLowerCase();
				String animalField = model.getValueAt(whichRow, 3).toString().toLowerCase();
				if (!model.getValueAt(whichRow, 0).toString().isEmpty()
						&& !model.getValueAt(whichRow, 1).toString().isEmpty()
						&& !model.getValueAt(whichRow, 2).toString().isEmpty()
						&& !model.getValueAt(whichRow, 3).toString().isEmpty()) {
					if (firstNameField.charAt(0) == gameChar && lsatNameField.charAt(0) == gameChar
							&& CountryField.charAt(0) == gameChar && animalField.charAt(0) == gameChar) {

						out.println("STOP-CLICKED" + " " + roomId + " " + playerNumber);
						System.out.println("STOP-CLICKED sent...");

					} else {
						JOptionPane.showMessageDialog(null, "Wrong input !");
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Empty Field Found !");
				}
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnStop.setBounds(856, 456, 110, 39);
		contentPane.add(btnStop);

		lblTimeTeller = new JLabel();
		lblTimeTeller = new JLabel("TIMER :");
		lblTimeTeller.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimeTeller.setForeground(Color.BLACK);
		lblTimeTeller.setBounds(455, 458, 110, 39);
		contentPane.add(lblTimeTeller);

		lblTimer = new JLabel("");
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTimer.setForeground(Color.BLACK);
		lblTimer.setBounds(510, 458, 110, 39);
		contentPane.add(lblTimer);

		lblRoundChar = new JLabel("Round Character :");
		lblRoundChar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRoundChar.setBounds(20, 456, 132, 39);
		contentPane.add(lblRoundChar);

		lblCharacter = new JLabel(Character.toString(gameChar));
		lblCharacter.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblCharacter.setBounds(158, 456, 144, 39);
		contentPane.add(lblCharacter);

		/**
		 * SETTING-UP-GAME-PAGE dastoorist ke farman shoroo safhe bazi ro mide va
		 * etelaat avalie i hamchon name bazikon i ke dar safhast ro migire va...
		 */
		/**
		 * UPDATE-SOCKET az ooonjai ke dar in safhe socket jadidi baraye bargharari
		 * ertebat sakhte mishe darkhast update socket haye ghabli ro ba jadida darim
		 * hol mehvar room i ke dar hale haze daresh hastim
		 */
		/**
		 * GAME-PAGE-REQUEST-NAME darkhast name bazikon ra dare
		 */
		/**
		 * DONE be manzor etmam anjam marahel set kardan page baraye avalin bar ro dare
		 */
		out.println("SETTING-UP-GAME-PAGE");
		System.out.println("SETTING-UP-GAME-PAGE sent...");
		out.println("UPDATE-SOCKET" + " _" + roomId + "_" + playerNumber + "_" + socket);
		System.out.println("UPDATE-SOCKET sent...");
		out.println("GAME-PAGE-REQUEST-NAME" + " " + playerNumber + " " + roomId);
		System.out.println("GAME-PAGE-REQUEST-NAME sent...");
		out.println("DONE" + " " + roomId);
		System.out.println("DONE " + roomId + " sent...");

	}

}
