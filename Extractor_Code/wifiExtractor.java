package Extractor_Code;
import java.io.File;
import java.io.FileWriter;  
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;	

public class wifiExtractor {
	public static void main(String args[]) {

		String command = "netsh wlan show profiles";
 
		try {

			File myObj = new File("confidential.txt");
			FileWriter myFile =new FileWriter("confidential.txt");    
      			if (myObj.createNewFile()) {
        				System.out.println("File created: " + myObj.getName() + ".......");
      			} else {
        				System.out.println("\nFile already exists. Overwriting to the File " + myObj.getName() + "......");
      			}

    			Process process = Runtime.getRuntime().exec(command);
 
    			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    			String line,line2;
			String command2 ;
			String netName ;
			int netCount = 0 ;
			System.out.println("");
			myFile.write("------------------------------------------------\n");
			myFile.write("Wifi Passwords Extracted Successfully\n");
			myFile.write("------------------------------------------------\n");
    			while ((line = reader.readLine()) != null) {
				if (line.contains("All User Profile")) {
					int endingPoint = line.length();
    					int startingPoint = line.indexOf(":") + 2;
    					String subString = line.substring(startingPoint, endingPoint);

					if ( subString.contains(" ")) {
						netName =  "\"" + subString + "\"";
					}
					else {
						netName = subString ;
					}
					command2 = "netsh wlan show profiles " + netName + " key=clear" ;					

					Process process2 = Runtime.getRuntime().exec(command2);
					BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));

					while ((line2 = reader2.readLine()) != null) {
						if (line2.contains("Key Content")) {
							int endingPoint2 = line2.length();
    							int startingPoint2 = line2.indexOf(":") + 2;
    							String subString2 = line2.substring(startingPoint2, endingPoint2);
							myFile.write("Network \t: "+subString+"\n"+"Password \t: "+subString2+"\n");
							myFile.write("------------------------------------------------\n");
							netCount++;
						}

					}

				}

    			}

			System.out.println("System Found " + netCount + " WIFI network/s......\n");
 			System.out.println("Passwords Extracted Succesfully.........\n");
			myFile.write("Done....\n");
			myFile.write("------------------------------------------------\n");
    			reader.close();
			myFile.close();
 
		} catch (IOException e) {
			System.out.println("An error occurred.");
    			e.printStackTrace();
		}
	}
}