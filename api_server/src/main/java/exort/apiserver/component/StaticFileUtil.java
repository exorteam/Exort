package exort.apiserver.component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class StaticFileUtil {

	private final String FOLDER_NAME = "static_file";

	public String storeFile(byte[] content){

		final String filename = UUID.randomUUID().toString();

		Path rootp = Paths.get(FOLDER_NAME);
		
		try{
			if(Files.notExists(rootp)){
				Files.createDirectories(rootp);
			}

			Path filep = rootp.resolve(filename);
			Files.write(filep,content);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return filename;

	}

	public byte[] getFile(String filename){

		Path rootp = Paths.get(FOLDER_NAME);

		try{
			if(Files.notExists(rootp)){
				throw new FileNotFoundException();
			}

			Path filep = rootp.resolve(filename);
			return Files.readAllBytes(filep);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return null;

	}

}
			
