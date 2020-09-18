package homework1;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class main {
	static Random _random = new Random();
	static String none = "";
	public static void main(String[] args) {
		try {
			partone(none, none, 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			parttwo(none, none, 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static void partone(String whole, String lasttwo, int aaa) throws InterruptedException {
	//get a new random int between 0 and 99
    int randint = _random.nextInt(100);
    TimeUnit.MILLISECONDS.sleep(10); //prevents getting the same number if things run too quickly
	String next = new String();
	//figure out which letter we are adding
	if(randint >75) {
		next="T";
	}else if(randint>50) {
		next="G";
	}else if(randint>25) {
		next="C";
	}else next = "A";
	//now lets see what if when we add the new letter, we create another "AAA" sequence
	if(whole.length()>2) {//We need this check incase the string we are building doesnt have a length of atleast 2 before adding the next letter
		String test = lasttwo+next;
		if(test.equals("AAA")) {
			aaa+=1;
			System.out.println("We have "+aaa+" instances of a sequence of AAA so far.");
		}
	}
	if(whole.length()<1000) {//if i dont have a length of 1000 yet, call this method again and add another nucleic acid
		String newwhole=whole+next;
		int l = newwhole.length();
		if(l>2) {
			String newlasttwo =  newwhole.substring(l-2);
			partone(newwhole,newlasttwo,aaa);
		}else {
			String newlasttwo =  newwhole;
			partone(newwhole,newlasttwo,aaa);		
		}
	}else {
		System.out.println("The Whole sequence using even nucleotide probabilities is: ");
		System.out.println(whole);
		System.out.println("And Contains this many repitions of the AAA motif: ");
		System.out.println(aaa);
		System.out.println("Realistically, there should be around this many motif recurrences: ");
		double value = (0.25*.25*.25) * (1000-3);
		System.out.println(value);
		System.out.println();
	}
}
	private static void parttwo(String whole, String lasttwo, int aaa) throws InterruptedException {
	//get a new random int between 0 and 99
    int randint = _random.nextInt(100);
    TimeUnit.MILLISECONDS.sleep(10); //prevents getting the same number if things run too quickly
	String next = new String();
	//figure out which letter we are adding
	if(randint >88) {//between 89 and 99 inclusive
		next="T";
	}else if(randint>48) {//between 49 and 88 inclusive
		next="G";
	}else if(randint>10) {//between 11 and 48 inclusive
		next="C";
	}else next = "A";//between 0 and 10 inclusive
	//now lets see what if when we add the new letter, we create another "AAA" sequence
	if(whole.length()>2) {//We need this check incase the string we are building doesnt have a length of atleast 2 before adding the next letter
		String test = lasttwo+next;
		if(test.equals("AAA")) {
			aaa+=1;
			System.out.println("We have "+aaa+" instances of a sequence of AAA so far.");
		}
	}
	if(whole.length()<1000) {//if i dont have a length of 1000 yet, call this method again and add another nucleic acid
		String newwhole=whole+next;
		int l = newwhole.length();
		if(l>2) {
			String newlasttwo =  newwhole.substring(l-2);
			parttwo(newwhole,newlasttwo,aaa);
		}else {
			String newlasttwo =  newwhole;
			parttwo(newwhole,newlasttwo,aaa);		
		}
	}else {
		System.out.println("The Whole sequence Using Weighted nucleotide probabilities is: ");
		System.out.println(whole);
		System.out.println("And Contains this many repitions of the AAA motif: ");
		System.out.println(aaa);
		System.out.println("Realistically, there should be around this many motif recurrences: ");
		double value = (0.12*.12*.12) * (1000-3);
		System.out.println(value);
		System.out.println();
	}
}
}