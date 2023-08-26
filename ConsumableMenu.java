import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ConsumableMenu extends JFrame implements ActionListener{
	protected ArrayList<Item> inventory;
	protected Pet joshagachi;
	Container p = this.getContentPane();

	public static void main(String[] args) {
		ArrayList<Item> items = new ArrayList<Item>();
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Cool Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Neat","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Cool Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Neat","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Cool Item","Stinky Poop",2,"You eat it, gross!"));
//		//items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Cool Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"First Item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Long name item","Stinky Poop",2,"You eat it, gross!"));
//		items.add(new Food(1.0,"Neat","Stinky Poop",2,"You eat it, gross!"));
		
		Pet p = new Pet();
		new ConsumableMenu(p, items);
	}
	
	public ConsumableMenu() {
		
	}
	
	public ConsumableMenu(Pet j, ArrayList<Item> inventory) {
		super("What do you choose?");
		this.inventory = inventory;
		this.joshagachi = j;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		p.setLayout(new BorderLayout());
		
		JLabel header = new JLabel();
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setVerticalAlignment(SwingConstants.CENTER);
		header.setPreferredSize(new Dimension(30,30));
		
		// in case inventory is empty
		if(inventory.size() != 0) {
			// finding out what's in the inventory
			if(inventory.get(0) instanceof Food) {
				header.setText("What will you feed "+joshagachi.name+"?");
			}else if(inventory.get(0) instanceof Accessory) {
				header.setText("What will you equip "+joshagachi.name+" with?");
			}else {
				header.setText("I don't know what type of inventory this is, please ask Josh for help");
			}
		} else {
			header.setText("You don't have any items to display here, buy some at a shop!");
			this.setSize(400,90);
		}
		p.add(header, BorderLayout.NORTH);
		
		JPanel centerGrid = new JPanel();
		centerGrid.setLayout(new GridLayout (0,3));
		p.add(centerGrid, BorderLayout.CENTER);
		
		for(int i = 0; i<this.inventory.size(); i++) {
			
			
			Item thing = this.inventory.get(i);
			JButton butt;
			if(thing instanceof Food) {
				butt = new JButton(new AbstractAction("Consume") {
					@Override
					public void actionPerformed(ActionEvent e) {
						joshagachi.feed(((Food) thing).hunger);
						inventory.remove(thing);
						// might need to remove thing from User menu too
						((JButton)e.getSource()).setVisible(false);
					}
				});
			
			}else if(thing instanceof Accessory) {
				Accessory a = (Accessory)thing;
				butt = new JButton(new AbstractAction("PlayGame") {
					@Override
					public void actionPerformed(ActionEvent e) {
						joshagachi.play(a);
						// inventory.remove(thing);
						// keep the game in inventory
						
						//((JButton)e.getSource()).setVisible(false);
						
					}
				});

			}else {
				butt = new JButton("I don't know what this is");
			}
			butt.setText(thing.name);
			butt.setToolTipText(thing.itemDesc);
			//System.out.println("Adding butt");
			centerGrid.add(butt);
			
			JButton exit = new JButton("Exit");
			exit.addActionListener(this);
			p.add(exit, BorderLayout.SOUTH);
			
		}
		
		if(inventory.size()>0)
			this.pack();
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}