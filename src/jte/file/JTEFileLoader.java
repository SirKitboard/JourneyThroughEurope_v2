package jte.file;

import application.Main.JTEPropertyType;
import jte.game.City;
import jte.game.JTEGameData;
import jte.game.Player;
import jte.ui.JTEUI;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class JTEFileLoader {
	public JTEFileLoader() {

	}
	public void createHistoryPage() {
		String htmlCode = "";
		try {
			Scanner in = new Scanner(new File("data/statsHTML_base.html"));
			while(in.hasNext()) {
				htmlCode+=in.nextLine()+"\n";
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(new File("data/statsHTML.html")));
			out.write(htmlCode);
			out.flush();in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addToHistory(String city, String name) {
		String htmlCode = "";
		try {
			Scanner in = new Scanner(new File("data/statsHTML.html"));
			while(in.hasNext()) {
				htmlCode+=in.nextLine()+"\n";
			}
			htmlCode = htmlCode.replaceAll("</table>", "<tr><td>"+name+"</td><td>" + city + "</td></tr>\n</table>");
			File file = new File("data/statsHTML.html");
			file.delete();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(htmlCode);
			out.flush();in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getCityList() {
		JTEUI ui = JTEUI.getUI();
		ArrayList<String> list = new ArrayList<>();
		try {
			File file = new File("data/cities.csv");
			Scanner cities = new Scanner(file);
			cities.nextLine();
			while(cities.hasNext()) {
				String value = cities.nextLine();
				String[] array = value.split(",");
				list.add(array[0]);
			}
		} catch (IOException e) {
			ui.getErrorHandler().processError(JTEPropertyType.ERROR_INVALID_FILE, ui.getPrimaryStage());
		}
		return list;
	}

	public HashMap<String, City> getCityData() {
		JTEUI ui = JTEUI.getUI();
		HashMap<String, City> data = new HashMap<String,City>();
		try {
			File file = new File("data/cities.csv");
			Scanner cities = new Scanner(file);
			cities.nextLine();
			while(cities.hasNext()) {
				String value = cities.nextLine();
				String[] array = value.split(",");
				City city = new City(array[0].trim(),array[1],Integer.parseInt(array[2]),Integer.parseInt(array[3]),Integer.parseInt(array[4]),Integer.parseInt(array[7]));
				if(!(array[8].equals("-"))) {
					File temp = new File("data/cityInfo/"+array[8]);
					Scanner cityScan = new Scanner(temp);
					String citydata = cityScan.nextLine();
					city.setData(citydata);
				}
				else {
					city.setData("");
				}
				System.out.println(array[0]);
				data.put(array[0],city);
			}

			cities = new Scanner(file);
			cities.nextLine();
			while(cities.hasNext()) {
				String value = cities.nextLine();
				String[] array = value.split(",");
				City temp = data.get(array[0]);
				String[] lCon = array[5].split("!");
				String[] sCon = array[6].split("!");
				for(int j=0;j<lCon.length;j++) {
					if(!lCon[j].trim().equals("")) {
						if(data.containsKey(lCon[j].trim())) {
							temp.getLandConnections().add(data.get(lCon[j].trim()));
						}
						else {
							System.out.println(temp.getName() + " -> " + lCon[j]);
						}
					}
				}
				for(int j=0;j<sCon.length;j++) {
					if(!sCon[j].trim().equals("")) {
						if(data.containsKey(sCon[j].trim())) {
							temp.getSeaConnections().add(data.get(sCon[j].trim()));
						}
						else {
							System.out.println(temp.getName() + " -> " + sCon[j]);
						}
					}
				}
			}
//			System.out.println("VERIFICATION");
//			cities = new Scanner(file);
//			cities.nextLine();
//			while(cities.hasNext()) {
//				String value = cities.nextLine();
//				String[] array = value.split(",");
//				City temp = data.get(array[0]);
//				ArrayList<City> land = temp.getLandConnections();
//				ArrayList<City> sea = temp.getSeaConnections();
//				for(int i=0;i<sea.size();i++) {
//					City temp2 = data.get(sea.get(i).getName());
//					if(temp2.getSeaConnections().indexOf(temp)==-1) {
//						System.out.println(temp2.getName() + " -> " + temp.getName());
//					}
//				}
//			}

		} catch (IOException e) {
			ui.getErrorHandler().processError(JTEPropertyType.ERROR_INVALID_FILE, ui.getPrimaryStage());
		}
		return data;
	}

	public void saveGame(JTEGameData data, int numplayers, int activePlayer) {
		JTEUI ui = JTEUI.getUI();
		try {
			File file = new File("data/savedgame.dat");
			FileOutputStream fos = new FileOutputStream(file.getPath());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(numplayers);
			ArrayList<Player> players = data.getPlayer();

			oos.writeObject(activePlayer);
			for(int i=0;i<players.size();i++) {
				oos.writeObject(players.get(i));
			}
		} catch (FileNotFoundException e) {
			ui.getErrorHandler().processError(JTEPropertyType.ERROR_INVALID_FILE, ui.getPrimaryStage());
		} catch (IOException e) {
			ui.getErrorHandler().processError(JTEPropertyType.ERROR_INVALID_FILE, ui.getPrimaryStage());
		}

	}

	public ArrayList<Player> loadGame(int temp[]) throws ClassNotFoundException, IOException {
		JTEUI ui = JTEUI.getUI();

		File file = new File("data/savedgame.dat");
		FileInputStream fis = new FileInputStream(file.getPath());
		ObjectInputStream ois = new ObjectInputStream(fis);
		int numplayers = (int)ois.readObject();;
		int activePlayer = (int)ois.readObject();
		ArrayList<Player> players = new ArrayList<Player>();
		for(int i=0;i<(numplayers);i++) {
			Object temp2 = ois.readObject();
			if(temp2 instanceof Player) {
				players.add((Player)temp2);
			}
			else {
				temp[0] = numplayers;
				temp[1] = activePlayer;
				return players;
			}
		}
		temp[0] = numplayers;
		temp[1] = activePlayer;
		return players;

	}
}
