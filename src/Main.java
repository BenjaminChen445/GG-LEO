import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import lt.ekgame.beatmap_analyzer.beatmap.mania.ManiaBeatmap;
import lt.ekgame.beatmap_analyzer.parser.BeatmapException;
import lt.ekgame.beatmap_analyzer.parser.BeatmapParser;

public class Main {

	static File songs;
	static int keys;
	static long duration;

	public static void main(String[] args) throws BeatmapException, IOException {
		// TODO Auto-generated method stub
		InputStream stream = System.in;
		Scanner scanner = new Scanner(stream);
		System.out.println("Input your osu songs folder directory");
		String input = scanner.nextLine();
		getDirectory(input);
		/*System.out.println("Input the type of mania map you want to delete (number of keys)");
		keys = scanner.nextInt();
		System.out.println("Deleting... This may take a while if you have lots of maps");*/

		//delete();
		getDirectories();
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

				if (subSubFile.listFiles()!= null) {
					for (File subSubSubFile : subSubFile.listFiles()) {
						if (subSubSubFile.getName().contains(".osu")) {
							String content = "";
							String line;
							try
							{      
								BufferedReader in = new BufferedReader( new FileReader(subSubSubFile));
								while ((line = in.readLine()) != null)
								{
									content = content + line + "\n";
								}
								in.close();
							}
							catch ( IOException iox )
							{
								System.out.println("Problem reading " + songs);
							}
							if (content.contains("Mode: 3") && content.contains("CircleSize:" + keys))
								subSubSubFile.delete();
						}
					}
				}

				if (subSubFile.getName().contains(".osu")) {
					String content = "";
					String line;
					try
					{      
						BufferedReader in = new BufferedReader( new FileReader(subSubFile));
						while ((line = in.readLine()) != null)
						{
							content = content + line + "\n";
						}
						in.close();
					}
					catch ( IOException iox )
					{
						System.out.println("Problem reading " + songs);
					}
					if (content.contains("Mode: 3") && content.contains("CircleSize:" + keys))
						subSubFile.delete();
				}
			}

		for (File subFile : songs.listFiles()) {
			int count = 0;

			for (File subSubFile : subFile.listFiles()) {

				if (subSubFile.listFiles() != null) {

					for (File subSubSubFile : subSubFile.listFiles()) {
						if (subSubSubFile.getName().contains(".osu"))
							count++;
					}

				}

				if (subSubFile.getName().contains(".osu"))
					count++;
			}

			if (count == 0) {
				for (File subSubFile : subFile.listFiles()) {
					if (subSubFile.listFiles() != null) {
						for (File subSubSubFile : subSubFile.listFiles()) {
							subSubSubFile.delete();
						}
					}
					subSubFile.delete();
				}
			}


			subFile.delete();
		}

		long endTime = System.currentTimeMillis();
		duration = (endTime - startTime) / 1000l;
	}

	public static void getDirectories() throws BeatmapException, IOException {
		BeatmapParser parser = new BeatmapParser();
		for (File subFile : songs.listFiles()) {
			for (File subSubFile : subFile.listFiles()) {

				if (subSubFile.listFiles()!= null)
					for (File subSubSubFile : subSubFile.listFiles())
						if (subSubSubFile.getName().contains(".osu")) {
							String content = "";
							String line;
							try
							{      
								BufferedReader in = new BufferedReader( new FileReader(subSubSubFile));
								while ((line = in.readLine()) != null)
								{
									content = content + line + "\n";
								}
								in.close();
							}
							catch ( IOException iox )
							{
								System.out.println("Problem reading " + songs);
							}
							if (content.contains("Mode: 3")) {
								System.out.println("hi");
								ManiaBeatmap beatmap = parser.parse(subSubSubFile, ManiaBeatmap.class);
								writeFile(beatmap.getDifficulty().getStars() + "", subSubFile.getPath() + subSubSubFile.getName() + "difficulty.txt");
							}
						}

				if (subSubFile.getName().contains(".osu")) {
					String content = "";
					String line;
					try
					{      
						BufferedReader in = new BufferedReader( new FileReader(subSubFile));
						while ((line = in.readLine()) != null)
						{
							content = content + line + "\n";
						}
						in.close();
					}
					catch ( IOException iox )
					{
						System.out.println("Problem reading " + songs);
					}
					if (content.contains("Mode: 3")) {
						//System.out.println("hi");
						ManiaBeatmap beatmap = parser.parse(subSubFile, ManiaBeatmap.class);
						writeFile(beatmap.getDifficulty() + "", subFile.getPath() + subSubFile.getName() + "difficulty.txt");
					}
				}
			}
		}
	}

	public static void writeFile(String data, String outputFile) throws IOException {
		FileWriter writer = new FileWriter(outputFile);
		try
		{
			writer.write(data);
		}
		catch ( IOException iox )
		{
			System.out.println("Problem reading " + outputFile );
		}
		writer.close();
	}

}
