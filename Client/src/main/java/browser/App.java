package browser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Socket s = new Socket("localhost", 3000);
        try(InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();){
                String message = "GET /index.html HTTP/1.1\n";
                PrintStream ps = new PrintStream(os);
                ps.print(message);
                ps.flush();
                System.out.println("message sent");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                Stream<String> response = br.lines();
                response.forEach(res-> System.out.println(res));
                
            }catch(IOException e){
                e.printStackTrace();
            }
        s.close();
    }
}
