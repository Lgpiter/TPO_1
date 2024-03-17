package zad1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.File;


public class Futil {
    public static void processDir(String dirName, String resultFilename){
        Path startPath = Paths.get(dirName);

        //Clear value of the current TPO1res.txt file
        try (FileChannel fileChannel = FileChannel.open(Paths.get(resultFilename), StandardOpenOption.WRITE)) {
            fileChannel.truncate(0);
            System.out.println("File content cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Going through user folder and checking the files
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException
                {
                    System.out.println("File name = " + file);
                    readFromFile(String.valueOf(file), resultFilename);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Function for reading from one file and writting it to our TPO1res.txt file
    public static void readFromFile(String fileName, String destinationFile){

        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileOutputStream fileOutputStream = new FileOutputStream(destinationFile, true);
             FileChannel inputChannel = fileInputStream.getChannel();
             FileChannel outputChannel = fileOutputStream.getChannel()) {

            // Create a ByteBuffer to hold the data
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int bytesRead;
            while ((bytesRead = inputChannel.read(buffer)) != -1) {
                // Read data into the buffer
                buffer.flip(); // Prepare buffer for reading

                // Write the data to the output file
                while (buffer.hasRemaining()) {
                    outputChannel.write(buffer);
                }

                buffer.clear(); // Prepare buffer for writing
            }

            System.out.println("File copied successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
