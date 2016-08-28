package sampleProj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class PeriodicThreadDump {
	public static void main(String[] args) throws IOException, Exception {
		
		//Usage --> java classname pid 12345 durationInMin 15 
		
		Runtime rt = Runtime.getRuntime();
		String port = args[1];
		int duration = Integer.parseInt(args[3]);
		duration = duration * 60 * 1000;
		
		Date now;
		while(true) {
			now = new Date();
			String formatDate = String.join("", now.toString().split(" "));
			String fileName = "JVM_Dump_" + formatDate;
			File file = new File(fileName);
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			Process pr = rt.exec("jstack -l "+port);
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		    String line = null; 

		     try {
		        while ((line = input.readLine()) != null)
					bw.write(line+"\n");
		     } catch (IOException e) {
		            e.printStackTrace();
		  
		     }
			bw.close();
			fw.close();
			
			Thread.sleep(duration);
		}
	}
}
