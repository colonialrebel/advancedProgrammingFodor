package homework2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.*;

import javax.swing.*;

public class Main {
	private final static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };
	private final static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	static Random _random = new Random();
	private static int _elapsedTime = 0;
	private static long _startTime;
	private static int _timeLimit = 30;
	private static boolean _mode = false; //false means im quizzing for the abbreviation, true means im quizzing for the full name
	private static int _answerIndex;
	private static String _currentAnswer="";
	private static int _correctAnswers = 0;
	private static int _highScore = 0;
	private static Timer _timer;
	private static boolean _quizOn=false;
	public static void main(String[] args) {
		// Build my gui
		JFrame frame = new JFrame("Amino Acid Quiz");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
        frame.setSize(500,500);
        
        //Lets make the center area
        //current timer
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center,BoxLayout.PAGE_AXIS));
        JLabel timeRemaining = new JLabel();
        timeRemaining.setText("Time Remaining: " + Integer.toString(_timeLimit));
        JLabel highScore = new JLabel();
        highScore.setText("High Score: "+"0");
        JLabel separator = new JLabel("============================================");
        JTextArea textArea = new JTextArea(10,30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        center.add(timeRemaining);
        center.add(highScore);
        center.add(separator);
        center.add(scrollPane);
        //build the menu bar to alter how my quiz will work
        JMenuBar menu = new JMenuBar();
        JMenu timeLimit = new JMenu("Time Limit");
        menu.add(timeLimit);
        JMenuItem twenty = new JMenuItem("20 seconds");
        twenty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                _timeLimit=20;
                timeRemaining.setText("Time Remaining: " +Integer.toString(_timeLimit));
            }
        });   
        JMenuItem thirty = new JMenuItem("30 seconds");
        thirty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                _timeLimit=30;
                timeRemaining.setText("Time Remaining: " +Integer.toString(_timeLimit));
            }
        }); 
        JMenuItem fortyfive = new JMenuItem("45 seconds");
        fortyfive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                _timeLimit=45;
                timeRemaining.setText("Time Remaining: " +Integer.toString(_timeLimit));
            }
        }); 
        JMenuItem minute = new JMenuItem("60 seconds");
        minute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                _timeLimit=60;
                timeRemaining.setText("Time Remaining: " +Integer.toString(_timeLimit));
            }
        }); 
        JMenuItem custom = new JMenuItem("Custom");
        custom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
               _timeLimit = Integer.parseInt(JOptionPane.showInputDialog(frame,"Set the time limit for the quiz (in seconds)"));
               timeRemaining.setText("Time Remaining: " +Integer.toString(_timeLimit));
            }
        });
        timeLimit.add(twenty);
        timeLimit.add(thirty);
        timeLimit.add(fortyfive);
        timeLimit.add(minute);
        timeLimit.add(custom);
        
        JMenu mode = new JMenu("Mode");
        menu.add(mode);
        JMenuItem abbrev = new JMenuItem("Abbreviation Quiz");
        abbrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                _mode=false;
             }
         });
        JMenuItem fullName = new JMenuItem("Full Name Quiz");
        fullName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
               _mode=true;
             }
         });
        mode.add(abbrev);
        mode.add(fullName);
        

        
        //lets make the lower area where the answers are input and the important buttons are
       JPanel quizLower = new JPanel();
       JLabel enterText = new JLabel("Answer>>");
       JTextField answerArea = new JTextField(20);
       answerArea.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e) {
    		   textArea.append(quizResponse(answerArea.getText(),textArea));
    		   if(_quizOn)quiz(textArea);
    		   answerArea.setText("");
    	   }
       });
       JButton enter = new JButton("Enter");
       enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                textArea.append(quizResponse(answerArea.getText(),textArea));
     		   if(_quizOn)quiz(textArea);
                answerArea.setText("");
            }
        });
       quizLower.add(enterText);
       quizLower.add(answerArea);
       quizLower.add(enter);
       JButton startQuiz = new JButton("Start Quiz");
       quizLower.add(startQuiz);
       startQuiz.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)            {
              startQuiz.setVisible(false);
              _quizOn=true;
              textArea.append("Quiz Start => "+_timeLimit+" seconds\n");
              quiz(textArea);
              _timer = new Timer(1000, new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    _elapsedTime++;
                    if (_elapsedTime < _timeLimit+1) {
                      timeRemaining.setText("Time Remaining: " + Integer.toString(_timeLimit-_elapsedTime));
                    } else {
                    	_quizOn=false;
                      ((Timer) (e.getSource())).stop();
                      startQuiz.setVisible(true);
                      resetElapsedTime();
                      if(_correctAnswers>_highScore) {
                    	  _highScore=_correctAnswers;
                    	  highScore.setText("High Score: "+Integer.toString(_highScore));
                      }
                      textArea.append("Quiz Over! You got => "+_correctAnswers+" Correct Answers\n");
                      _correctAnswers =0 ;
                    }
                  }
                });
                _timer.setInitialDelay(0);
                _timer.start();

           }
       });
       

       frame.getContentPane().add(BorderLayout.NORTH,menu);//add the menu bar to the quiz window
       frame.getContentPane().add(BorderLayout.CENTER,center);
       frame.getContentPane().add(BorderLayout.SOUTH,quizLower);

       frame.setVisible(true);

	}
	private static void resetElapsedTime() {
		_elapsedTime =0;
	}
	private static String quizResponse(String response,JTextArea textArea) {
		if(_quizOn) {

            if(_mode) {
         	   if(response.toLowerCase().equals(SHORT_NAMES[_answerIndex].toLowerCase())) {
         		   _correctAnswers++;
         		   return (response+" >> Correct!\n");
         	   }else {
         		  _elapsedTime=_timeLimit+3;
         		  _quizOn=false;
         		   return (response+" >> Incorrect! \n The correct Answer was "+SHORT_NAMES[_answerIndex]+"\n");
         		   }
            }else {
         	   if(response.toLowerCase().equals(FULL_NAMES[_answerIndex].toLowerCase())) {
         		   _correctAnswers++;
         		  return (response+" >> Correct!\n");
         	   }else { 
            		_elapsedTime=_timeLimit+3;
            		_quizOn=false;
         		   return (response+" >> Incorrect! \n The correct Answer was "+FULL_NAMES[_answerIndex]+"\n");
         		   }
            }
         	}else {
         		return "There is no Quiz Running!\n";
         	}
	}
	private static void quiz(JTextArea textArea) {
        _answerIndex =_random.nextInt(20);
        if(_mode) {
        	textArea.append("Provide the Abbreviation of "+ FULL_NAMES[_answerIndex]+"\n");
        }else {
        	textArea.append("Provide the Full Name of "+ SHORT_NAMES[_answerIndex]+"\n");
        }
	}
}
