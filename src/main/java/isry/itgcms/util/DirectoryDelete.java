package isry.itgcms.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class DirectoryDelete {
	
	public static void deleteDirectoryJava8(String dir) throws IOException {

		Path path = Paths.get(dir);

		// read java doc, Files.walk need close the resources.
		// try-with-resources to ensure that the stream's open directories are closed
		try (Stream<Path> walk = Files.walk(path)) {
			walk.sorted(Comparator.reverseOrder()).forEach(DirectoryDelete::deleteDirectoryJava8Extract);
		} catch (IOException e) {
			//System.out.println(e.getMessage());
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}

	}

	// extract method to handle exception in lambda
	public static void deleteDirectoryJava8Extract(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			//System.err.printf("Unable to delete this path : %s%n%s", path, e);
		}
	}
}
