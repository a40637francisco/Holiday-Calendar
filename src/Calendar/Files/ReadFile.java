package Calendar.Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    private static BufferedReader br;

    public static String readLine(String yearFileName) throws IOException{
        if(br == null)
            br = new BufferedReader(new FileReader(yearFileName));
        return br.readLine();
    }
}
