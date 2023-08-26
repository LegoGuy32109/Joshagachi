import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.math.*;
import java.util.concurrent.TimeUnit;

// for event handling have to implement ActionListener
public class Pet extends JFrame implements ActionListener {

	// local private variables
	Container p = this.getContentPane();
	JButton centerImageLabel = new JButton(); 
	HashMap<String, String> sprites = new HashMap<String, String>();
	
	// variables for eating animation
	boolean mouthOpen = false;
	int gulps = 0;
	
	protected String species;

	protected double foodLevel = 0.0;
	protected double energyLevel = 0.0;
	
	protected String name;
	
	User user;
	
	public static void main(String[] args) {
		Pet j = new Pet();
		j.display();
	} 
	
	// overwritten anyway
	public void actionPerformed(ActionEvent e) {
		if(!mouthOpen)
			centerImageLabel.setIcon(new ImageIcon(sprites.get("eating1")));
		else
			centerImageLabel.setIcon(new ImageIcon(sprites.get("eating2")));
		mouthOpen = !mouthOpen;
		gulps++;
		if(gulps>10) {
			((Timer)e.getSource()).stop();
			gulps = 0;
			updateSprite();
		}
	}
	

	
	// should be overwritten
	public Pet(){
		super("Your Joshagachi!");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		name = "TempName";
		species = "Snake";
		foodLevel = 0.0;
		energyLevel = 0.0;

		this.makeUI();
		
        
	}
	
	public Pet(User user, String species, String name, double foodLevel, double energyLevel) {
		super("Your Joshagachi "+name+"!");
		this.user = user;
		this.species = species;
		this.name = name;
		this.foodLevel = foodLevel;
		this.energyLevel = energyLevel;

		this.makeUI();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	// pulls up the window for the Joshagachi
	public void display() {
		this.setVisible(true);
        this.setSize(500,300);
	}
	
	// creates hashmap for sprites and their file paths
	public void setPictures() {
		String[] words = {"default", "sad1", "sad2", "eating1", "eating2", "full1", "full2", "hungry1", "hungry2", "tired1", "tired2"};
		for (String word : words) {
			sprites.put(word, "assets/"+species+"/"+word+".png");
		}
	}
	
	public void exitPet() {
		
		dispose();
	}
	
	public void updateSprite() {
		// determine which stat is most dire
		String state = "default";
		if(Math.abs(foodLevel)>Math.abs(energyLevel)) {
			if(foodLevel>=.66) {
				state = "full2";
			}else if (foodLevel>=.33) {
				state = "full1";
			}else if (foodLevel <= -.66) {
				state = "hungry2";
			}else if (foodLevel <= -.33) {
				state = "hungry1";
			}else {
				state = "default";
			}
		}else if(Math.abs(foodLevel)<Math.abs(energyLevel)) {
			if(energyLevel>=.66) {
				state = "sad2";
			}else if (energyLevel>=.33) {
				state = "sad1";
			}else if (energyLevel <= -.66) {
				state = "tired2";
			}else if (energyLevel <= -.33) {
				state = "tired1";
			}else {
				state = "default";
			}
		}else {
			if(foodLevel>=.66) {
				state = "full2";
			}else if (foodLevel>=.33) {
				state = "full1";
			}else if (foodLevel <= -.66) {
				state = "hungry2";
			}else if (foodLevel <= -.33) {
				state = "hungry1";
			}else {
				state = "default";
			}
		}
		System.out.println("foodLevel: "+foodLevel+"\nenergyLevel: "+energyLevel);
		System.out.println("Condition 1: "+Math.abs(foodLevel)+">"+Math.abs(energyLevel)+(Math.abs(foodLevel)>Math.abs(energyLevel))+"\nContition 2: "+Math.abs(foodLevel)+"<"+Math.abs(energyLevel)+(Math.abs(foodLevel)<Math.abs(energyLevel)));
		System.out.println("Setting image to "+state);
		centerImageLabel.setIcon(new ImageIcon(sprites.get(state)));
	}
	
	public void feed(int hunger) {
		
		foodLevel+=0.1*hunger;
		Timer timer = new Timer(50, this);
		timer.setInitialDelay(0);
		timer.start();
		

		//System.out.println(foodLevel);
	}
	public void openMenu(ArrayList<Item> list) {
		new ConsumableMenu(this, list);
	}
	public void play(Accessory a) {
		energyLevel-=0.05*a.looksBuff;
		updateSprite();
	}
	
	public void makeUI(){
		
		p.setLayout(new BorderLayout());
		this.setPictures();
		updateSprite();
		p.add(centerImageLabel, BorderLayout.CENTER);
		
		//There buttons are created with an overriding action function for when they're clicked
		JButton Rbutt = new JButton(new AbstractAction("Equip") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// hide current panels and show things to equip
				openMenu(user.purchasedSwag);
			}
		});
		JButton Lbutt = new JButton(new AbstractAction("Feed") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// hide current panels and show foods to feed
				openMenu(user.purchasedFoods);
				

				
			}
		});
		Rbutt.setLabel("Play");
		Lbutt.setLabel("Feed");
		
		JPanel menuActions = new JPanel();
		menuActions.setLayout(new GridLayout(0,1));
		menuActions.add(Rbutt);
		menuActions.add(Lbutt);
		p.add(menuActions, BorderLayout.WEST);
		
		JButton Tbutt = new JButton(new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				exitPet();
			}
		});
		//Replace with pet's name
		Tbutt.setLabel("Save Pet and Exit");
		
		JLabel title = new JLabel(name);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		
		JButton T2butt = new JButton(new AbstractAction("ChangeName") {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
			    Object result = JOptionPane.showInputDialog(frame, "Enter your Pet's new name:");
			    // makes sure name only changes if a thing is inputted
			    if(result != null && !((String)result).equals("")) {
			    	name = (String)result;
			    	System.out.println("New name "+name);
			    	title.setText(name);
			    }
			    System.out.println(result);
			}
		});
		JPanel header = new JPanel(new BorderLayout());
		header.add(T2butt, BorderLayout.WEST);
		header.add(Tbutt, BorderLayout.EAST);
		header.add(title, BorderLayout.CENTER);
		
		p.add(header, BorderLayout.NORTH);
		
		JButton Bbutt = new JButton(new AbstractAction("Sleep") {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Take a nap");
				energyLevel+=0.2;
				foodLevel-=0.2;
				updateSprite();
			}
		});
		//Replace with pet's name
		Bbutt.setLabel("Take a Nap");
		p.add(Bbutt, BorderLayout.SOUTH);
		
		
	}

}
