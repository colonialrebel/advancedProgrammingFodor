package homework4;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class to read text files
public class Main {
	private static Queue<SequenceInfo> _q = new LinkedList();
	private static HashMap<String,Integer> _seqMap = new HashMap<String,Integer>();
	private static ArrayList<Integer> _nums = null;
	public static void main(String[] args) throws IOException {
		/*
		A NOTE, much of the code used here has been from copied from my Lab 3 and is further edited.
		my Class SequenceInfo acts like the FastaSequence object specified from the lab4 write up (also leftover from lab3) as it did the majority of the work needed for this lab.
		The method names are the same as the ones specified in the Lab04 write up.
		*/
		read(); //reads and tallys my sequences
		write();//this will deal with the hashmap of all sequences
		
		/* 
		 * if you want to test the SequenceInfo class methods just pop some off the queue
		 * then call the desired methods Below
		 */
		
		
		
		
	}//main end
	
	
	public static void read() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("E:\\eclipse\\eclipse workspace\\AdvProgrammingHomeworks\\src\\homework4\\testfile.fasta"));
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
		reader.close();//i won't need the reader anymore
		//from here im going to go thru my sequences and tally occurrences of sequences
		for(SequenceInfo seq : _q) {
			try {//this will see if the sequence is already in my hashmap and iterate by 1 the value (meaning the sequence has repeated
			_seqMap.put(seq._sequence,_seqMap.get(seq._sequence)+1);
			}catch(Exception NullPointerException){//if it isn't in the hashmap that means a null pointer exception has occurred and i need to add the element to the hashmap
				_seqMap.put(seq._sequence,1);
			}
			
			//i need to get the unique integers (unique number of repetitions so I know how large to make my holding ordered array
			_nums = new ArrayList<Integer>();
			for(int i : _seqMap.values()){
				if(!_nums.contains(i)) {
					_nums.add(i);
				}
			}
			Collections.sort(_nums); //this contains the sorted array of all the numbers of repeats
		}
	}//read end
	
	
	public static void write() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("E:\\eclipse\\eclipse workspace\\AdvProgrammingHomeworks\\src\\homework4\\lab4out.txt")));	
		for(int i : _nums) {
			for(String str : _seqMap.keySet()) {
				if(i==_seqMap.get(str)) {
					//System.out.println(i + " " + str); troubleshooting thing, pay no mind
					writer.write(">"+i+"\n"+str+"\n");
				}
			}	
		}
		writer.flush();
		writer.close();
	}
}