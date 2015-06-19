import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A wrapper class for an Object, to make it Serializable
 * Note that if the Object stored isn't serializable, this will not work
 * @author Dylan
 */
class ReadWritable implements Serializable {
	private static final long serialVersionUID = 1L;
	Object data;
	ReadWritable(Object o) {data = o;}
	public String toString() {return data.toString();}
}

/**
 * Writes and reads objects to file and back
 * @author Dylan
 */
class ReadWriter {
	/** The Object to write */
	ReadWritable contents;
	public ReadWritable getContents() {return contents;}
	public void setContents(ReadWritable contents) {this.contents = contents;}
	
	public boolean write() {
		try {
		FileOutputStream fout = new FileOutputStream("ReadWriterfile.dat");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(contents);
		out.close();
		} catch (Exception e) {p("WriteException: "+e);return false;}
		return true;
	}
	
	public boolean read() {
		contents = null;
		try {
			FileInputStream fin = new FileInputStream("ReadWriterfile.dat");
			ObjectInputStream in = new ObjectInputStream(fin);
			contents = (ReadWritable)in.readObject();
			in.close();
		} catch (Exception e) {p("ReadException: "+e);return false;}
		return true;
	}
	
	public void p(String s) {System.out.println(s);}
}

public class serializing {
	public static void main (String [] main) {
		//Write and read back a String
		ReadWriter rw = new ReadWriter();
		rw.setContents(new ReadWritable("Hello"));
		rw.write();	rw.read();
		System.out.println("Output of rw: "+rw.getContents());
		//Write and read back an int
		ReadWriter rw2 = new ReadWriter();
		rw2.setContents(new ReadWritable(1094));
		rw2.write();	rw2.read();
		System.out.println("Output of rw2: "+rw2.getContents());
		//Write and read back a non-serializable Object
		//This will throw exceptions
		ReadWriter rw3 = new ReadWriter();
		rw3.setContents(new ReadWritable(new Object(){
			public void methodOne(String s) {System.out.println("Hi");}
			public String methodTwo() {return "Lorem Ipsum";}
			}));
		rw3.write(); rw3.read();
		System.out.println("Output of rw3: "+rw3.getContents());
		
		//
		//!!!!!			Output spoilers			!!!!!
		//
		//Output of rw: Hello
		//Output of rw2: 1094
		//WriteException: java.io.NotSerializableException: serializing$1
		//ReadException: java.io.WriteAbortedException: writing aborted; java.io.NotSerializableException: serializing$1
		//Output of rw3: null

	}
}
