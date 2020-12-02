package homework7;

public class SlowThread extends Thread{
	private Gui _gui; //i would make this final but it cant because it needs to be established when the thread is created
	private Boolean _active = true;
	private int _countdown;
	public SlowThread(Gui gui, int countdown) {
		_gui=gui;
		_countdown = countdown;
	}

	public void run() {
		System.out.println("Starting a slow thread");
		while(true && _active) {
			for(int x=_countdown;x>=1;x--) {
				if(_active) {
					String output = this.getName() +">> "+ String.valueOf(x) + " Mississippi\n";
					returnValue(output);
					try {
						Thread.sleep(1000);//sleep 1 second
					}catch(InterruptedException e){
					}
				}
				_countdown=x;
			}
			//if(_active=false) {
			//	break;
			//}
			if(_countdown<=0) {
				_active = false; //this is essentially a self terminator
			}
		}
	}
	private void returnValue(String str) {
		_gui.pushToTextArea(str);
	}
	public void cancel() throws InterruptedException {
		returnValue(this.getName()+ ": Countdown Cancelled\n");
		_active=false;
		throw new InterruptedException("Interrupted!");
	}
}
