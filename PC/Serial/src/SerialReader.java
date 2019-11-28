import java.io.IOException;
import java.io.InputStream;

public class SerialReader implements Runnable {
	InputStream in;
	KeyboardControl keyboardControl;
	
	public SerialReader(InputStream in) {
		this.in = in;
	}
	
	public void run() {
		byte[] buffer = new byte[1024];
		int len = -1;
		
		try {
			while((len = this.in.read(buffer)) > -1) {
				System.out.print(new String(buffer, 0, len));
				String s_buffer = new String(buffer, 0, len);
				for (int i = 0; i < s_buffer.length(); i++) {
					keyboardControl.control(s_buffer.charAt(i));
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
