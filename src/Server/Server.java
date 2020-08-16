package Server;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.swing.text.AbstractDocument.Content;

import Main.GamePage;
import Main.ProfileLast;

public class Server {

	// khob, miresim be bakhsh asli project 6 :)

	/**
	 * - hostUsersName list i az tamami karbarani ke khahan host shodan dar safhe
	 * ProfileLast boodan e - hostUsersSocket list i az tamami socket karbarani ke
	 * khahan host shodan dar safhe ProfileLast boodan e - joinUsersName list i az
	 * tamami karbarani ke khahan join shodan dar safhe ProfileLast boodan e -
	 * joinUsersSocket list i az tamami socket karbarani ke khahan join shodan dar
	 * safhe ProfileLast boodan e
	 */
	/**
	 * - rooms arraylist e ke zamani ke host va join i ba ham match mishan add new
	 * mishe ke dar har khanash, socket 2D dare ke dar khoone aval oon socket,
	 * socket host, va khoone badi socket join save shode ;) - roomsNames daghighan
	 * mesle hamoon room e ba in tafavot ke name karbararo save mikone ! - -
	 * roomsFinalScore int i e natije nahayie room i ke har 5 round ro bazi karde ro
	 * negah midare ke dar khoone aval natije nahayie host va dovom join e - -
	 * eachRoomChar character i e ke har room dar har round dare bahash bazi mikone
	 * ke hamvare too in variable save mishe
	 */
	/**
	 * - hostInputs array i be andaze 4 e ke har khoonash hamoon khooneie ke code
	 * dar ghaleb GET-VALUES az safhe bazi az table bazi ke host / join oonaro por
	 * karde migire
	 */
	/**
	 * isHostInputsSet & isJoinInputsSet variable hayi hastan ke agar true bashan
	 * mirim soragh mohasebe score oon round
	 */
	/**
	 * listener ham ke serverSocket e hamechi be paye oone :)
	 */
	/**
	 * list haye firstNames & ListlastNamesList & animalsList & countriesList
	 * harkodoom baraye anjam marahel Hashing estefade shodan Note : in chahar list
	 * do bar be seda dar mian va dobar write shodan, ke baste be noe bala oomadan
	 * server yek seri az oonha khoonde mishe
	 */

	private static int port = 9512;
	private static ArrayList<String> hostUsersName = new ArrayList<String>();
	private static ArrayList<Socket> hostUsersSocket = new ArrayList<Socket>();
	private static ArrayList<String> joinUsersName = new ArrayList<String>();
	private static ArrayList<Socket> joinUsersSocket = new ArrayList<Socket>();
	public static ArrayList<Socket[]> rooms = new ArrayList<Socket[]>();
	public static ArrayList<String[]> roomsNames = new ArrayList<String[]>();
	public static ArrayList<int[]> roomsFinalScore = new ArrayList<int[]>();
	public static ArrayList<Character> eachRoomsChar = new ArrayList<Character>();
	public static String[] hostInputs = new String[4];
	public static String[] joinInputs = new String[4];
	public static String[] hostInputColors = { "RED", "RED", "RED", "RED" };
	public static String[] joinInputColors = { "RED", "RED", "RED", "RED" };
	public static boolean isHostInputsSet = false;
	public static boolean isJoinInputsSet = false;
	public static ServerSocket listener;
	public static ArrayList<ArrayList<String>> firstNamesList = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> lastNamesList = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> animalsList = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> countriesList = new ArrayList<ArrayList<String>>();

	public Server() {

	}

	public Server(int port) throws IOException {
		this.port = port;
		listener = new ServerSocket(port);
		System.out.println("the server is running :D (Constructor)");
		fillFirstNamesList();
		fillLastNamesList();
		fillAnimalsList();
		fillCountriesList();
		try {
			// waiting for clients to connect
			while (true) {
				new Handler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}

	private static void fillCountriesList() {
		ArrayList<String> innerArrayList = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		String leftBehindWords = "";
		String lastWord = "";
		/*
		 * dar ebteda file ro mikhonim
		 */
		try {
			FileReader fr = new FileReader("countries.txt");
			br = new BufferedReader(fr);
			/**
			 * shoroo mikonim be khoondan az avalesh, (az oonjai ke to safhe Welcome oonaro
			 * sort kardim ba khial rahat be tartib A ta Z dar nazareshooon migirim) az 65
			 * ke mishe A ta akharin adadesh ke mishe Z, mikhonim, va har kodoom ro dar
			 * khoone morede nazaresh mirizim
			 */
			/**
			 * taein khoone mored nazar = bebin, list i dari ke baraye Countries e, har
			 * khoonash khodesh tamami country hayi ke ba A shoroo mishan ta Z ro dare, ke
			 * mishe arraylist haye A-Z ke har khoonash, khodesh tamami kalamat i ke ba A-Z
			 * shoroo mishe ro dare
			 */
			for (int i = 65; i <= 90 && fullStr != null;) {
				if (!fullStr.isEmpty()) {
					lastWord = fullStr;
					if (fullStr.charAt(0) == (char) i) {
						innerArrayList.add(fullStr);
					} else {
						countriesList.add(new ArrayList(innerArrayList));
						leftBehindWords += "_" + fullStr;
						innerArrayList.clear();
						i++;
					}
				}
				fullStr = br.readLine();
				if (fullStr == null) {
					countriesList.add(innerArrayList);
					innerArrayList.clear();
				}
			}
			leftBehindWords = leftBehindWords.substring(1, leftBehindWords.length());
			String[] lbWords = leftBehindWords.split("_");
			/**
			 * in ghesmat marboot mishe be kaamati khas ke ja oftadan, che kalamti ja
			 * oftadan? ==> oon kalamati ke baraye mesak az A mirim be B, avallin kalame B
			 * dar ja miofte ke hameye onaro jam mikonam var dar nahayat dar jaye
			 * dorosteshoon insert mikonam ;)
			 */
			for (int i = 0; i < lbWords.length; i++) {
				int whichArrayIndex = (int) lbWords[i].charAt(0);
				whichArrayIndex -= 65;
				countriesList.get(whichArrayIndex).add(0, lbWords[i]);
			}
			countriesList.get(countriesList.size() - 1).add(countriesList.get(countriesList.size() - 1).size(),
					lastWord);
			System.out.println(countriesList.size());
			// System.out.println(
			// "COUNTRIES
			// -----------------------------------------------------------------------------------------0");
			// for (int i = 0; i < countriesList.size(); i++) {
			// System.out.println(" new !!!!!!!!!!!!!!!");
			// for (int j = 0; j < countriesList.get(i).size(); j++) {
			// System.out.println(countriesList.get(i).get(j));
			// }
			// System.out.println(" Done !!!");
			// }
			// System.out.println(
			// "COUNTRIES
			// -----------------------------------------------------------------------------------------1");
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

	private static void fillFirstNamesList() {
		ArrayList<String> innerArrayList = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		String leftBehindWords = "";
		String lastWord = "";
		try {
			FileReader fr = new FileReader("firstNames.txt");
			br = new BufferedReader(fr);
			for (int i = 65; i <= 90 && fullStr != null;) {
				if (!fullStr.isEmpty()) {
					lastWord = fullStr;
					if (fullStr.charAt(0) == (char) i) {
						innerArrayList.add(fullStr);
					} else {
						firstNamesList.add(new ArrayList(innerArrayList));
						leftBehindWords += "_" + fullStr;
						innerArrayList.clear();
						i++;
					}
				}
				fullStr = br.readLine();
				if (fullStr == null) {
					firstNamesList.add(innerArrayList);
					innerArrayList.clear();
				}
			}
			leftBehindWords = leftBehindWords.substring(1, leftBehindWords.length());
			String[] lbWords = leftBehindWords.split("_");
			for (int i = 0; i < lbWords.length; i++) {
				int whichArrayIndex = (int) lbWords[i].charAt(0);
				whichArrayIndex -= 65;
				firstNamesList.get(whichArrayIndex).add(0, lbWords[i]);
			}
			firstNamesList.get(firstNamesList.size() - 1).add(firstNamesList.get(firstNamesList.size() - 1).size(),
					lastWord);
			// System.out.println(
			// "FIRSTNAMES
			// -----------------------------------------------------------------------------------------0");
			// for (int i = 0; i < firstNamesList.size(); i++) {
			// System.out.println(" new !!!!!!!!!!!!!!!");
			// for (int j = 0; j < firstNamesList.get(i).size(); j++) {
			// System.out.println(firstNamesList.get(i).get(j));
			// }
			// System.out.println(" Done !!!!");
			// }
			// System.out.println(
			// "FIRSTNAMES
			// -----------------------------------------------------------------------------------------1");
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

	private static void fillLastNamesList() {
		ArrayList<String> innerArrayList = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		String leftBehindWords = "";
		String lastWord = "";
		try {
			FileReader fr = new FileReader("lastNames.txt");
			br = new BufferedReader(fr);
			for (int i = 65; i <= 90 && fullStr != null;) {
				if (!fullStr.isEmpty()) {
					lastWord = fullStr;
					if (fullStr.charAt(0) == (char) i) {
						innerArrayList.add(fullStr);
					} else {
						lastNamesList.add(new ArrayList(innerArrayList));
						leftBehindWords += "_" + fullStr;
						innerArrayList.clear();
						i++;
					}
				}
				fullStr = br.readLine();
				if (fullStr == null) {
					lastNamesList.add(innerArrayList);
					innerArrayList.clear();
				}
			}
			leftBehindWords = leftBehindWords.substring(1, leftBehindWords.length());
			String[] lbWords = leftBehindWords.split("_");
			for (int i = 0; i < lbWords.length; i++) {
				int whichArrayIndex = (int) lbWords[i].charAt(0);
				whichArrayIndex -= 65;
				lastNamesList.get(whichArrayIndex).add(0, lbWords[i]);
			}
			lastNamesList.get(lastNamesList.size() - 1).add(lastNamesList.get(lastNamesList.size() - 1).size(),
					lastWord);
			// System.out.println(
			// "lastNamesList
			// -----------------------------------------------------------------------------------------0");
			// for (int i = 0; i < lastNamesList.size(); i++) {
			// System.out.println(" new !!!!!!!!!!!!!!!");
			// for (int j = 0; j < lastNamesList.get(i).size(); j++) {
			// System.out.println(lastNamesList.get(i).get(j));
			// }
			// System.out.println(" Done !!!!");
			// }
			// System.out.println(
			// "lastNamesList
			// -----------------------------------------------------------------------------------------1");
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

	private static void fillAnimalsList() {
		ArrayList<String> innerArrayList = new ArrayList<String>();
		String fullStr = "";
		BufferedReader br = null;
		BufferedWriter brw = null;
		String leftBehindWords = "";
		String lastWord = "";
		try {
			FileReader fr = new FileReader("animals.txt");
			br = new BufferedReader(fr);
			for (int i = 65; i <= 90 && fullStr != null;) {
				if (!fullStr.isEmpty()) {
					lastWord = fullStr;
					if (fullStr.charAt(0) == (char) i) {
						innerArrayList.add(fullStr);
					} else {
						animalsList.add(new ArrayList(innerArrayList));
						leftBehindWords += "_" + fullStr;
						innerArrayList.clear();
						i++;
					}
				}
				fullStr = br.readLine();
				if (fullStr == null) {
					animalsList.add(innerArrayList);
					innerArrayList.clear();
				}
			}
			leftBehindWords = leftBehindWords.substring(1, leftBehindWords.length());
			String[] lbWords = leftBehindWords.split("_");
			for (int i = 0; i < lbWords.length; i++) {
				int whichArrayIndex = (int) lbWords[i].charAt(0);
				whichArrayIndex -= 65;
				animalsList.get(whichArrayIndex).add(0, lbWords[i]);
			}
			animalsList.get(animalsList.size() - 1).add(animalsList.get(animalsList.size() - 1).size(), lastWord);
			// System.out.println(
			// "animalsList
			// -----------------------------------------------------------------------------------------0");
			// for (int i = 0; i < animalsList.size(); i++) {
			// System.out.println(" new !!!!!!!!!!!!!!!");
			// for (int j = 0; j < animalsList.get(i).size(); j++) {
			// System.out.println(animalsList.get(i).get(j));
			// }
			// System.out.println("Done !!!!!!");
			// }
			// System.out.println(
			// "animalsList
			// -----------------------------------------------------------------------------------------1");
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

	public static void main(String[] args) throws IOException {
		listener = new ServerSocket(port);
		System.out.println("the server is running :D (MAIN)");

		System.out.println("the server is running :D (Constructor)");
		fillFirstNamesList();
		fillLastNamesList();
		fillAnimalsList();
		fillCountriesList();
		try {
			// waiting for clients to connect
			while (true) {
				new Handler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}

	// a handler thread class
	private static class Handler extends Thread {
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		// set socket
		public Handler(Socket socket) throws IOException {
			this.socket = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}

		public void run() {
			try {
				String type = in.readLine();
				System.out.println("Type : " + type);
				/**
				 * amadan darkhat host az taraf safhe asli bazi dar ebteda be saken
				 */
				if (type.startsWith("HOST")) {
					while (true) {
						out.println("SUBMIT-NAME");
						name = in.readLine();
						if (!hostUsersName.contains(name)) {
							hostUsersName.add(name);
							hostUsersSocket.add(socket);
							break;
						}
					}
					/**
					 * amadan darkhat Join az taraf safhe asli bazi dar ebteda be saken
					 */
				} else if (type.startsWith("JOIN")) {
					while (true) {
						out.println("SUBMIT-NAME");
						name = in.readLine();
						if (!hostUsersName.contains(name)) {
							joinUsersName.add(name);
							joinUsersSocket.add(socket);
						}
						String hostUsersNames = null;
						for (String str : hostUsersName) {
							hostUsersNames += "_" + str;
						}
						hostUsersNames = hostUsersNames.substring(5, hostUsersNames.length());
						out.println("NAMES-ON-THE-WAY" + " " + hostUsersNames);
						int getSelectedRowOfHostSockets = Integer.parseInt(in.readLine());
						System.out.println(name + " and " + hostUsersName.get(getSelectedRowOfHostSockets)
								+ " got selected... (Server)");
						Socket[] newRoom = { hostUsersSocket.get(getSelectedRowOfHostSockets), socket };
						String[] newRoomNames = { hostUsersName.get(getSelectedRowOfHostSockets), name };
						int[] finalScores = { 0, 0 };
						/*
						 * anjam amaliat sakhtan yek room jadid ba moshakhasat dade shode ke shamek do
						 * socket karbaran host va join shode ast
						 */
						rooms.add(newRoom);
						roomsFinalScore.add(finalScores);
						roomsNames.add(newRoomNames);
						System.out.println("room added !");
						System.out.println("-----------------------------0");
						for (Socket[] sc : rooms) {
							System.out.println(sc[0] + " and " + sc[1]);
						}
						System.out.println("-----------------------------1");
						// Lets be making host and join final sockets

						/**
						 * dar in part ma be har do socket host va join niaz darim ke bekhahim beeshoon
						 * etela bedim ke shoma har do be ham connect shodid va safhe hale hazeretoon ro
						 * be safhe asli bazi change konid
						 */

						// host
						String hostName = hostUsersName.get(getSelectedRowOfHostSockets).toString();
						Socket hostSocket = hostUsersSocket.get(getSelectedRowOfHostSockets);
						BufferedReader inHost = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
						PrintWriter outHost = new PrintWriter(hostSocket.getOutputStream(), true);
						// host Done
						// join
						String joinName = name;
						Socket joinSocket = socket;
						BufferedReader inJoin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter outJoin = new PrintWriter(socket.getOutputStream(), true);
						// join Done...

						outHost.println("SET" + " " + hostName + "_" + joinName + "_" + (rooms.size() - 1));
						outJoin.println("SET" + " " + joinName + "_" + hostName + "_" + (rooms.size() - 1));

						// Done...
						break;
					}
				} else if (type.startsWith("SETTING-UP-GAME-PAGE")) {
					/**
					 * hala omadim too safhe bazi va darim sohbat mikonimba client haye dar hale
					 * bazi kardan
					 */
					while (true) {
						String order = in.readLine();
						System.out.println("Order : " + order);
						if (order.startsWith("UPDATE-SOCKET")) {
							String line = order.substring(15, order.length());
							System.out.println("Line : " + line);
							String[] parts = line.split("_");
							rooms.get(Integer.parseInt(parts[0]))[Integer.parseInt(parts[1]) - 1] = socket;
							System.out.println("Room updated ...");
							if (Integer.parseInt(parts[1]) == 2) {
								Random randLast = new Random();
								int rnd = randLast.nextInt(25) + 1;
								rnd += 97;
								eachRoomsChar.add(((char) rnd));
								System.out.println("a new character (" + ((char) rnd) + ") was added !");
							}
						} else if (order.startsWith("GAME-PAGE-REQUEST-NAME")) {
							String line = order;
							int roomNamesId = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));
							if (line.charAt(23) == '1') {
								out.println("GET-NAME" + " " + roomsNames.get(roomNamesId)[0]);
							} else if (line.charAt(23) == '2') {
								out.println("GET-NAME" + " " + roomsNames.get(roomNamesId)[1]);
							}
						} else if (order.startsWith("DONE")) {
							if (eachRoomsChar.size() != 0) {
								out.println("GAME-CHAR" + " " + Character.toString(eachRoomsChar.get(Integer
										.parseInt(order.substring(order.lastIndexOf(" ") + 1, order.length())))));
							} else {
								out.println("!");
								System.out.println("!");
							}
						} else if (order.startsWith("STOP-CLICKED")) {
							/**
							 * az mohemtarin bakhsh ha zamanie ke yek karbar rooye dokme stop esh click
							 * mikone va bedoone moshkel ta injaye code miad,
							 */
							System.out.println("STOP-CLICKED came...");
							String[] parts = order.split(" ");
							int whichRoom = Integer.parseInt(parts[1]);
							int playerNumber = Integer.parseInt(parts[2]);
							System.out.println("searching in room " + whichRoom);

							// host
							Socket hostSocket = rooms.get(whichRoom)[0];
							String hostName = roomsNames.get(whichRoom)[0];
							BufferedReader inHost = new BufferedReader(
									new InputStreamReader(hostSocket.getInputStream()));
							PrintWriter outHost = new PrintWriter(hostSocket.getOutputStream(), true);
							// host Done
							// join
							Socket jointSocket = rooms.get(whichRoom)[1];
							String joinName = roomsNames.get(whichRoom)[1];
							BufferedReader inJoin = new BufferedReader(
									new InputStreamReader(jointSocket.getInputStream()));
							PrintWriter outJoin = new PrintWriter(jointSocket.getOutputStream(), true);
							// join Done...

							/**
							 * hala bayesti dastoor STOP ro be har do bedim ke bazihashoon ro stop konan va
							 * montazer elam natayej bashan
							 */

							if (playerNumber == 1) {
								outHost.println("STOP" + " " + hostName);
								System.out.println("HOST STOPED..." + " " + hostName);
								outJoin.println("STOP" + " " + hostName);
								System.out.println("JOIN STOPED..." + " " + hostName);
							} else if (playerNumber == 2) {
								outHost.println("STOP" + " " + joinName);
								System.out.println("HOST STOPED..." + " " + joinName);
								outJoin.println("STOP" + " " + joinName);
								System.out.println("JOIN STOPED..." + " " + joinName);
							}
						} else if (order.startsWith("SENDING-VALUES")) {
							int whichRoom;
							while (true) {
								System.out.println("SENDING-VALUES came...");
								/**
								 * nobat gereftan value hayie ke karbara dar table insert kardan
								 */
								int playerNumber = order.charAt(15) - '0';
								whichRoom = order.charAt(17) - '0';
								if (playerNumber == 1) {
									isHostInputsSet = true;
								} else if (playerNumber == 2) {
									isJoinInputsSet = true;
								} else {
									System.out.println("DA FUCK :|");
								}
								order = order.substring(19, order.length());
								System.out.println("innerOrder : " + order);
								String[] values = order.split(" and ");
								System.out.println("Values given by (" + order + ")");
								System.out.println("--------------------------------------- 0");
								for (int i = 0; i < values.length; i++) {
									System.out.println("value " + i + " : " + values[i]);
									if (playerNumber == 1) {
										hostInputs[i] = values[i];
										System.out.println("hostInput " + i + " : " + hostInputs[i]);
									} else {
										joinInputs[i] = values[i];
										System.out.println("hostInput " + i + " : " + joinInputs[i]);
									}
								}
								System.out.println("--------------------------------------- 1");

								break;
							}
							if (isHostInputsSet && isJoinInputsSet) {
								/**
								 * hal be ghesmati az code residim ke midoonim har do karbar input haye
								 * khodeshoon ro insert kardan dar arraylist i ke dar hamin safhe barashoon
								 * moshakhas shode, va mirim soragh elam natayej round bazi
								 */
								System.out.println("YES !");
								int hostScore = calculateScore(1);
								int joinScore = calculateScore(2);

								// host
								Socket hostSocket = rooms.get(whichRoom)[0];
								String hostName = roomsNames.get(whichRoom)[0];
								BufferedReader inHost = new BufferedReader(
										new InputStreamReader(hostSocket.getInputStream()));
								PrintWriter outHost = new PrintWriter(hostSocket.getOutputStream(), true);
								// host Done
								// join
								Socket jointSocket = rooms.get(whichRoom)[1];
								String joinName = roomsNames.get(whichRoom)[1];
								BufferedReader inJoin = new BufferedReader(
										new InputStreamReader(jointSocket.getInputStream()));
								PrintWriter outJoin = new PrintWriter(jointSocket.getOutputStream(), true);
								// join Done...

								Random randLast = new Random();
								int rnd = randLast.nextInt(25) + 1;
								rnd += 97;
								eachRoomsChar.set(whichRoom, (((char) rnd)));
								outHost.println("SCORE" + " " + hostScore + " " + hostInputColors[0] + " "
										+ hostInputColors[1] + " " + hostInputColors[2] + " " + hostInputColors[3] + " "
										+ (((char) rnd)));
								outJoin.println("SCORE" + " " + joinScore + " " + joinInputColors[0] + " "
										+ joinInputColors[1] + " " + joinInputColors[2] + " " + joinInputColors[3] + " "
										+ (((char) rnd)));
								isHostInputsSet = false;
								isJoinInputsSet = false;
							} else {
								System.out.println("NO !");
							}
						} else if (order.startsWith("GAME-IS-FINISHED")) {
							/**
							 * agar dastoor payani ke mishe GAME-IS-FINISHED be samt server oomad, be in
							 * manie ke har 5 round bazi tamoome va bayest natayej nahay bazi ro ke mishe
							 * hamoon jam kol emtiazat har round ro az karabara begirim va mohasebe konim ;)
							 */
							String[] parts = order.split(" ");
							int whichRoom = Integer.parseInt(parts[3]);
							int playerNumber = Integer.parseInt(parts[1]);
							if (playerNumber == 1) {
								roomsFinalScore.get(whichRoom)[0] = Integer.parseInt(parts[2]);
							} else {
								roomsFinalScore.get(whichRoom)[1] = Integer.parseInt(parts[2]);
								int hostFinalResult = roomsFinalScore.get(whichRoom)[0];
								int joinFinalResult = roomsFinalScore.get(whichRoom)[1];
								if (hostFinalResult > joinFinalResult) {
									// HOST WON
									Socket hostSocket = rooms.get(whichRoom)[0];
									PrintWriter outHost = new PrintWriter(hostSocket.getOutputStream(), true);
									Socket jointSocket = rooms.get(whichRoom)[1];
									PrintWriter outJoin = new PrintWriter(jointSocket.getOutputStream(), true);
									outHost.println(
											"RESULTS" + " " + "WON" + " " + hostFinalResult + " " + joinFinalResult);
									outJoin.println(
											"RESULTS" + " " + "LOST" + " " + joinFinalResult + " " + hostFinalResult);
								} else if (hostFinalResult < joinFinalResult) {
									// JOIN WON
									Socket hostSocket = rooms.get(whichRoom)[0];
									PrintWriter outHost = new PrintWriter(hostSocket.getOutputStream(), true);
									Socket jointSocket = rooms.get(whichRoom)[1];
									PrintWriter outJoin = new PrintWriter(jointSocket.getOutputStream(), true);
									outJoin.println(
											"RESULTS" + " " + "WON" + " " + joinFinalResult + " " + hostFinalResult);
									outHost.println(
											"RESULTS" + " " + "LOST" + " " + hostFinalResult + " " + joinFinalResult);
								} else {
									// TIE GAME
									Socket hostSocket = rooms.get(whichRoom)[0];
									PrintWriter outHost = new PrintWriter(hostSocket.getOutputStream(), true);
									Socket jointSocket = rooms.get(whichRoom)[1];
									PrintWriter outJoin = new PrintWriter(jointSocket.getOutputStream(), true);
									outJoin.println(
											"RESULTS" + " " + "TIE" + " " + joinFinalResult + " " + hostFinalResult);
									outHost.println(
											"RESULTS" + " " + "TIE" + " " + hostFinalResult + " " + joinFinalResult);
								}
							}
							break;
						}
					}
				} else {
					System.out.println("(SERVER) else came...");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private int calculateScore(int playerNumber) {
			/**
			 * khob, hala resifim be bakhsh calculate kardan score ha, aval az hame ye
			 * switch case mizanim ke age host bood input haye host khoonde beshe va 1 dar
			 * array list begardimesh age nabood 0 ebdim va age bood va ba input jin yeki
			 * bood 5 bedim va age nabood 10, va belaks
			 */
			int score = 0;
			switch (playerNumber) {
			case 1:
				System.out.println("Host came to be calculated");
				// check first name
				if (!hostInputs[0].isEmpty() && hostInputs[0] != null) {
					System.out.println("wants to check first name...");
					int indexToSearchIn = ((int) hostInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < firstNamesList.get(indexToSearchIn).size(); i++) {
					// System.out.println(firstNamesList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < firstNamesList.get(indexToSearchIn).size(); i++) {
						if (hostInputs[0].toString().toLowerCase()
								.equals(firstNamesList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host first name found");
							if (!hostInputs[0].equals(joinInputs[0])) {
								score += 10;
								hostInputColors[0] = "GREEN";
							} else {
								score += 5;
								hostInputColors[0] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				// check last name
				if (!hostInputs[1].isEmpty() && hostInputs[1] != null) {
					System.out.println("wants to check last name...");
					int indexToSearchIn = ((int) hostInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < lastNamesList.get(indexToSearchIn).size(); i++) {
					// System.out.println(lastNamesList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < lastNamesList.get(indexToSearchIn).size(); i++) {
						if (hostInputs[1].toString().toLowerCase()
								.equals(lastNamesList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host last name found");
							if (!hostInputs[1].equals(joinInputs[1])) {
								score += 10;
								hostInputColors[1] = "GREEN";
							} else {
								score += 5;
								hostInputColors[1] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				// check country
				if (!hostInputs[2].isEmpty() && hostInputs[2] != null) {
					System.out.println("wants to check country...");
					int indexToSearchIn = ((int) hostInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < countriesList.get(indexToSearchIn).size(); i++) {
					// System.out.println(countriesList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < countriesList.get(indexToSearchIn).size(); i++) {
						if (hostInputs[2].toString().toLowerCase()
								.equals(countriesList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host country found");
							if (!hostInputs[2].equals(joinInputs[2])) {
								score += 10;
								hostInputColors[2] = "GREEN";
							} else {
								score += 5;
								hostInputColors[2] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				// check animal
				if (!hostInputs[3].isEmpty() && hostInputs[3] != null) {
					System.out.println("wants to check animal...");
					int indexToSearchIn = ((int) hostInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < animalsList.get(indexToSearchIn).size(); i++) {
					// System.out.println(animalsList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < animalsList.get(indexToSearchIn).size(); i++) {
						if (hostInputs[3].toString().toLowerCase()
								.equals(animalsList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host animal found");
							if (!hostInputs[3].equals(joinInputs[3])) {
								score += 10;
								hostInputColors[3] = "GREEN";
							} else {
								score += 5;
								hostInputColors[3] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				break;
			case 2:
				System.out.println("Join came to be calculated");
				// check first name
				if (!joinInputs[0].isEmpty() && joinInputs[0] != null) {
					System.out.println("wants to check first name...");
					int indexToSearchIn = ((int) joinInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < firstNamesList.get(indexToSearchIn).size(); i++) {
					// System.out.println(firstNamesList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < firstNamesList.get(indexToSearchIn).size(); i++) {
						if (joinInputs[0].toString().toLowerCase()
								.equals(firstNamesList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host first name found");
							if (!joinInputs[0].equals(hostInputs[0])) {
								score += 10;
								joinInputColors[0] = "GREEN";
							} else {
								score += 5;
								joinInputColors[0] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				// check last name
				if (!joinInputs[1].isEmpty() && joinInputs[1] != null) {
					System.out.println("wants to check last name...");
					int indexToSearchIn = ((int) joinInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < lastNamesList.get(indexToSearchIn).size(); i++) {
					// System.out.println(lastNamesList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < lastNamesList.get(indexToSearchIn).size(); i++) {
						if (joinInputs[1].toString().toLowerCase()
								.equals(lastNamesList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host last name found");
							if (!joinInputs[1].equals(hostInputs[1])) {
								score += 10;
								joinInputColors[1] = "GREEN";
							} else {
								score += 5;
								joinInputColors[1] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				// check country
				if (!joinInputs[2].isEmpty() && joinInputs[2] != null) {
					System.out.println("wants to check country...");
					int indexToSearchIn = ((int) joinInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < countriesList.get(indexToSearchIn).size(); i++) {
					// System.out.println(countriesList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < countriesList.get(indexToSearchIn).size(); i++) {
						if (joinInputs[2].toString().toLowerCase()
								.equals(countriesList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host country found");
							if (!joinInputs[2].equals(hostInputs[2])) {
								score += 10;
								joinInputColors[2] = "GREEN";
							} else {
								score += 5;
								joinInputColors[2] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				// check animal
				if (!joinInputs[3].isEmpty() && joinInputs[3] != null) {
					System.out.println("wants to check animal...");
					int indexToSearchIn = ((int) joinInputs[0].charAt(0)) - 65;
					// System.out.println("-------*");
					// for (int i = 0; i < animalsList.get(indexToSearchIn).size(); i++) {
					// System.out.println(animalsList.get(indexToSearchIn).get(i));
					// }
					// System.out.println("-------*");
					for (int i = 0; i < animalsList.get(indexToSearchIn).size(); i++) {
						if (joinInputs[3].toString().toLowerCase()
								.equals(animalsList.get(indexToSearchIn).get(i).toString().toLowerCase())) {
							System.out.println("host animal found");
							if (!joinInputs[3].equals(hostInputs[3])) {
								score += 10;
								joinInputColors[3] = "GREEN";
							} else {
								score += 5;
								joinInputColors[3] = "YELLOW";
							}
							break;
						}
					}
				}
				// finished
				break;
			default:
				System.out.println("damn...");
				break;
			}
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------");
			return score;
		}
	}
}
