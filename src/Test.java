import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        String sourceFile = "TPO1res.txt";
        String destinationFile = "output.txt";

        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
             FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
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
