package biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import entity.FormationTree;

public class Main {

	public static void readByLine(String fileName) throws Exception {
		File file = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String tmp;
		while ((tmp = reader.readLine()) != null) {
			if (tmp.startsWith("//") || (tmp.equals("")))
				continue;
			FormationTree t = Parser.parsePropositon(tmp);
			System.out.println(t.toStringYN());
		}
		reader.close();
	}

	public static void main(String[] args) {
		try {
			readByLine("../test.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
