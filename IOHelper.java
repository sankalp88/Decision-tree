

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class IOHelper {
	public static DataContainer readFile(String fileName) {
		
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		ArrayList<Feature> features = new ArrayList<>();
		try {
			// Open Input Stream for reading purpose.
			inputStream = new FileInputStream(fileName);
			// Create new Input Stream Reader based on which encoding to use
			inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8").newDecoder());
			// Create new Buffered Reader
			bufferedReader = new BufferedReader(inputStreamReader);
			String currLine = "";
			// Reads to the end of the stream
			while ((currLine = bufferedReader.readLine()) != null) {
				String[] attributes = currLine.split(",");
				if (features.size() == 0) {
					// Add features to collection
					for (String name : attributes) {
						features.add(new Feature(name));
					}
				} else {
					int i = 0;
					// Add feature data to collection 
					for (String val : attributes) {
						features.get(i++).getValue().add(Integer.parseInt(val));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Releases resources associated with the streams
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int outputIndex = features.size() - 1;
		Feature output = features.get(outputIndex);
		features.remove(outputIndex);
		return new DataContainer(features, output);
	}
}
