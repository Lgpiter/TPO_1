package zad1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.ArrayList;



public class Futil {

    static Charset decoding = Charset.forName("Cp1250");
    static Charset encoding =  StandardCharsets.UTF_8;

    public static void processDir(String dirName, String resultFilename) {
        Path startPath = Paths.get(dirName);
        ArrayList<Path> paths = new ArrayList<>();


        Path resultFilePath = Paths.get(resultFilename);
        try{
            Files.deleteIfExists(resultFilePath);
            Files.createFile(resultFilePath);
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    paths.add(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileChannel fileChannel = FileChannel.open(Paths.get(resultFilename), StandardOpenOption.WRITE)) {
            for (Path p : paths) {
                readFromFile(p.toString(), fileChannel);
            }
            System.out.println("Files processed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(String fileName, FileChannel outputChannel) {
        try (FileChannel inputChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ)) {
            ByteBuffer b = ByteBuffer.allocateDirect((int) inputChannel.size());
            inputChannel.read(b);
            b.flip();
            CharBuffer chb = decoding.decode(b);

            ByteBuffer utf8Bytes = encoding.encode(chb);

            outputChannel.write(utf8Bytes);
            outputChannel.write(ByteBuffer.wrap("\n".getBytes(StandardCharsets.UTF_8))); //dodanie znaku nowej lini
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
