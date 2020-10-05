package homework3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class to read text files
public class Main {
	private static Queue<SequenceInfo> _q = new LinkedList();
	private static File _fp;
	private static int num = 0;
	public static void main(String[] args) throws IOException {
		read();
		write();
	}//main end
	
	
	public static void read() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("E:\\eclipse\\eclipse workspace\\AdvProgrammingHomeworks\\src\\homework3\\testfile.fasta"));
		String curSeq = "";
		String curSeqID = "";
		SequenceInfo si = null;
		boolean b = true;
		while(b){
			String nextLine = reader.readLine();
			if(nextLine==null) {
				_q.add(new SequenceInfo(curSeq, curSeqID));
				b=false;
			}else {
				if(nextLine.chars().filter(ch -> ch =='>').count() > 0) {
					if(curSeqID!="") {
						_q.add(new SequenceInfo(curSeq, curSeqID));
						curSeq = "";
						curSeqID = "";
					}
					if(curSeqID=="") {
						curSeqID=nextLine.substring(1,nextLine.length());
					}
				}else {
					curSeq = curSeq+nextLine;
				}
			}
		}
		reader.close();
	}//read end
	public static void write() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("E:\\eclipse\\eclipse workspace\\AdvProgrammingHomeworks\\src\\homework3\\lab3out.txt")));
		writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");
		while(_q.isEmpty()==false) {
			SequenceInfo si = _q.remove();
			String line = si._seqID +"\t"+si._a+"\t"+si._c+"\t"+si._g+"\t"+si._t+"\t"+si._sequence+"\n";
			writer.write(line);
		}
		
		writer.flush();
		writer.close();
	}
}