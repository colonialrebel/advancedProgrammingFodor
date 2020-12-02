package homework7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui {
	private final static JTextArea _textArea = new JTextArea(10,30);
	private ArrayList<SlowThread> alst = new ArrayList<SlowThread>();
	private static ArrayBlockingQueue<String> _abq = new ArrayBlockingQueue<String>(100);
	public Gui() {
				// Build my gui
				JFrame frame = new JFrame("Countdown Gui");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
		        frame.setSize(500,500);
		        
		        //Lets make the center area
		        JPanel center = new JPanel();
		        center.setLayout(new BoxLayout(center,BoxLayout.PAGE_AXIS));

		        _textArea.setEditable(false);
		        JScrollPane scrollPane = new JScrollPane(_textArea);
		        center.add(scrollPane);

		        //lets make the lower area where the answers are input and the important buttons are
		       JPanel quizLower = new JPanel();
		       JLabel enterText = new JLabel("Answer>>");
		       JTextField answerArea = new JTextField(20);
		       answerArea.addActionListener(new ActionListener(){
		    	   public void actionPerformed(ActionEvent e) {
		    		   try {
		    			   int input = Integer.parseInt(answerArea.getText());
			    		   _textArea.append(String.valueOf(input)+"\n");
			    		   answerArea.setText("");
			    		   beginNewThread(input);
		    		   }catch(Exception e2) {
		    			   _textArea.append("That is not an integer\n");
			    		   answerArea.setText("");
		    		   }
		    	   }
		       });
		       JButton cancel = new JButton("Cancel");
		       cancel.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e){
		                for(SlowThread thread : alst) {
		                	try {
								thread.cancel();
								alst.remove(thread);//this serves as clean up
							} catch (InterruptedException e1) {
								//e1.printStackTrace(); -> this just gets messy and I dont wanna see it
							}
		                }
		            }
		        });
		       quizLower.add(enterText);
		       quizLower.add(answerArea);
		       quizLower.add(cancel);
		       enterText.setVisible(true);
		       answerArea.setVisible(true);
		       cancel.setVisible(true);
		       
		       
		       frame.getContentPane().add(BorderLayout.CENTER,center);
		       frame.getContentPane().add(BorderLayout.SOUTH,quizLower);

		       frame.setVisible(true);
		       writer();

	}
	private void writer() {
		while(true) {
			if(!_abq.isEmpty()) {
				_textArea.append(_abq.remove());
			}
		}
	}
	public void pushToTextArea(String str) {
		_abq.add(str);
	}
	private void beginNewThread(int input) {
		   SlowThread st = new SlowThread(this,input);
		   alst.add(st);
		   st.start();
	}
}
