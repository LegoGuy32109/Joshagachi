import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Shop extends JFrame implements ActionListener{
	protected ArrayList<Item> inventory;
	protected String shopName;
	protected String shopDesc;
	protected String imageLink;
	
	User user;
	Container p = this.getContentPane();
	protected JLabel logo;
	
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
	
	public Shop() {
		super("The __ Shop");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//shopMenu();
	}
	
	// made by the user, pass it the user so it can access the other attributes
	public Shop(User user, String shopName, String shopDesc, ArrayList<Item> inventory, String imageLink) {
		super(shopName);
		this.user = user;
		this.shopName = shopName;
		this.shopDesc = shopDesc;
		this.inventory = inventory;
		this.imageLink = imageLink;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton quit = new JButton();
		quit.addActionListener(this);
		quit.setText("Exit");
		
		JPanel items = new JPanel();
		items.setLayout(new GridLayout(0,4));
		for(int i =0; i<inventory.size(); i++) {
			Item thing = inventory.get(i);
			JButton item = new JButton(new AbstractAction(thing.name) {
				@Override
				// need to save choices to User data
				public void actionPerformed(ActionEvent e) {
					if(thing instanceof Food) {
						// System.out.println("This is a food");
						// ooh casting works, nice
						Food f = (Food)thing;
						System.out.println(f.eatingDesc);
						logo.setText("        Adding "+f.name+" to inventory");
						user.purchasedFoods.add(f);
					}else {
						System.out.println("This is an accessory");
						Accessory a = (Accessory)thing;
						System.out.println(a.equipingDesc);
						
						boolean isthere = false;
						for (int i = 0; i<user.purchasedSwag.size(); i++) {
							if(user.purchasedSwag.get(i).name==a.name)
								isthere = true;
						}
						if(!isthere) {
							logo.setText("        Adding "+a.name+" to inventory");
							user.purchasedSwag.add(a);
						}else {
							logo.setText("        You already have "+a.name+" in your inventory");
						}
					}
				}
			});
			item.setText(thing.name);
			item.setToolTipText(thing.itemDesc);
			// functionality in constructor for JButton later
			items.add(item);
		}
		JPanel header = new JPanel(new GridLayout(0,1));
		
		logo = new JLabel(new ImageIcon(imageLink));
		//logo.setText("       You spent $5");
		header.add(logo);
		header.add(new JLabel(shopDesc, SwingConstants.CENTER));
		
		p.setLayout(new BorderLayout());
		p.add(quit, BorderLayout.EAST);
		p.add(items, BorderLayout.CENTER);
		p.add(header, BorderLayout.NORTH);
	}
	
	public void shopMenu() {
		
		
		this.setVisible(true);
		this.setSize(600, 470);

	}
	
	
	
	public static void main(String[] args) {
		// this is just for testing purposes guys, I don't have a problem.
		ArrayList<Item> i = new ArrayList<Item>();
		i.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Last","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Help","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Pretty far now","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		i.add(new Food(1.0,"Poop","Stinky Poop",2,"You eat it, gross!"));
		i.add(new Food(1.0,"Scat","From an animal",1,"You eat it, gross!"));
		// multiples of 4 work best
		//i.add(new Food(1.0,"Wow it's got ery thang","From an animal",1,"You eat it, gross!"));
		//Shop p = new Shop("The Poop Shop","For all your poop needs.",i, "assets/Yummies.png");
		//p.shopMenu();
		ArrayList<Item> j = new ArrayList<Item>();
		j.add(new Accessory(4.0,"Jacket","From HotTopic",1,"You put it on feeling cooler"));
		j.add(new Accessory(4.0,"Jacket","From HotTopic",1,"You put it on feeling cooler"));
		j.add(new Accessory(4.0,"Jacket","From HotTopic",1,"You put it on feeling cooler"));
		j.add(new Accessory(4.0,"Jacket","From HotTopic",1,"You put it on feeling cooler"));
		//Shop a = new Shop("Stuff shop", "Got some stuff", j,"assets/Yummies.png");
		//a.shopMenu();
	}
}