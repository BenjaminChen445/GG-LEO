import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

	static File songs;
	static int keys;
	static long duration;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream stream = System.in;
		Scanner scanner = new Scanner(stream);
		System.out.println("Input your osu songs folder directory");
		String input = scanner.nextLine();
		getDirectory(input);
		System.out.println("Input a natural number");
		keys = scanner.nextInt();
		delete();
		System.out.println("Time taken: " + duration + " seconds");
		
	}
	
	public static void getDirectory(String FileName) {
		songs = new File(FileName);
	}
	
	public void getKeyNumber(int num) {
		keys = num;
	}
	
	public static void delete() {
		long startTime = System.currentTimeMillis();
		for (File subFile : songs.listFiles())
			for (File subSubFile : subFile.listFiles()) {
				if (subSubFile.getName().contains(".osu")) {
					String content = "";
					try {
						Scanner reader = new Scanner(subSubFile);
						while (reader.hasNextLine()) {
							String data = reader.nextLine();
							content = content + data;
						}
						reader.close();
					} catch (FileNotFoundException e) {
						System.out.println("Error");
					}
					if (content.contains("Mode: 3") && content.contains("CircleSize:" + keys))
						subSubFile.delete();
				}
			}
		
		for (File subFile : songs.listFiles()) {
			int count = 0;
			
			for (File subSubFile : subFile.listFiles())
				if (subSubFile.getName().contains(".osu"))
					count++;
			
			if (count == 0)
				for (File subSubFile : subFile.listFiles())
					subSubFile.delete();
			
			subFile.delete();
		}
		
		long endTime = System.currentTimeMillis();
		duration = (endTime - startTime) / 1000;
	}

}
