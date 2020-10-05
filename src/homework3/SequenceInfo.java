package homework3;

public class SequenceInfo {
	String _sequence;
	String _seqID;
	long _a,_c,_t,_g;
	public SequenceInfo(String str, String seqID) {
		_sequence = str.toUpperCase();
		_seqID=seqID;
		_a = countOccurrences(_sequence,'A'); 
		_c = countOccurrences(_sequence,'C');
		_t = countOccurrences(_sequence,'T');
		_g = countOccurrences(_sequence,'G');
	}
	private static long countOccurrences(String str, char ch) {
		return str.chars()
				.filter(c -> c == ch) //uses a lambda function to quickly parse and count the string
				.count();
	}
}
