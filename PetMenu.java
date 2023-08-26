import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class PetMenu extends JFrame implements ActionListener {
	protected User user;
	Container p = this.getContentPane();
	JPanel petsPanel;
	
	protected ArrayList<Pet> joshagachi;
	
	protected Shop foodShop;
	protected Shop accessoryShop;
	
	
	// main for testing purposes in the PetMenu
	public static void main(String[] args) {
		ArrayList<Pet> j = new ArrayList<Pet>();
		for(int i = 0; i<7; i++) {
			j.add(new Pet());
		}
		PetMenu p = new PetMenu("Default Name", j);
		// I call the load for testing purposes, the User does it usually
		p.load();
	}
	
	public PetMenu() {
		// only run with an ownerName using below constructor
	}
	
	// I plan on overwriting this for the buttons anyway
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
	}
	
	// this is the constructor I want to use in the project
	public PetMenu(User u) {
		super(u.ownerName+"'s Joshagachi");
		this.user = u;
		this.joshagachi = user.joshagachi;
		this.foodShop = user.foodShop;
		this.accessoryShop = user.accessoryShop;
		
		setUI();
	}
	public PetMenu(String ownerName, ArrayList<Pet> joshagachi){
		super(ownerName+"'s Joshagachi");
		this.joshagachi = joshagachi;
		
		setUI();
	}
	
	public void refreshNames() {
		for(int i = 0; i<joshagachi.size(); i++) {
			JButton curj = (JButton)petsPanel.getComponent(i);
			curj.setText(joshagachi.get(i).name);
		}
	}
	
	public void findJoshagachi(JPanel hiddenPanel) {
		hiddenPanel.setVisible(false);
		JPanel center = new JPanel();
		center.setLayout(new FlowLayout());
		p.add(center, BorderLayout.CENTER);
		
		// HERE TO ADD MORE JOSHAGACHI'S!!
		// choose random species
		Random r = new Random();
		ArrayList<String> speciesList = new ArrayList<String>();
		speciesList.add("Snake");
		speciesList.add("Ghost");
		speciesList.add("Blob");
		speciesList.add("Pumpkin");
		speciesList.add("Octopus");
		
		int numChoices = 3;
		for(int i = 0; i<numChoices; i++) {
			String species = speciesList.get(r.nextInt(speciesList.size()));
			speciesList.remove(species);
			
			
			JButton petChoice = new JButton(new AbstractAction("AddToPetList") {
				@Override
				public void actionPerformed(ActionEvent e) {
					joshagachi.add(new Pet(user, species, "New "+species, 0.0, 0.0));
					// hide pick Joshagachi menu after done
					center.setVisible(false);
					p.remove(hiddenPanel);
					listJoshagachis();
					//hiddenPanel.setVisible(true);
					p.remove(center);
				}
			});
			petChoice.setText("");
			
			petChoice.setIcon(new ImageIcon("assets/"+species+"/default.png"));
			//petChoice.setText(species);
			center.add(petChoice);
			//tiny spacer
			JLabel space = new JLabel("     ");
			center.add(space);
		}
		JButton back = new JButton(new AbstractAction("BackToMenu") {
				@Override
				public void actionPerformed(ActionEvent e) {
					center.setVisible(false);
					hiddenPanel.setVisible(true);
					p.remove(center);
					p.remove(hiddenPanel);
					listJoshagachis();
				}
			});
		back.setText("Back to menu");
		center.add(back);
	} // end findJoshagachi
	
	public void listJoshagachis() {
		petsPanel = new JPanel(new GridLayout(3,3));

		p.add(petsPanel, BorderLayout.CENTER);
		
		for(int i = 0; i<joshagachi.size(); i++) {
			Pet curj = joshagachi.get(i);
			JButton jButt = new JButton(new AbstractAction("PetScreen") {
				@Override
				public void actionPerformed(ActionEvent e) {
					curj.display();
				}
			});
			System.out.println(curj.name);
			jButt.setText(curj.name);
			petsPanel.add(jButt);
		}
		// message to get a Joshagachi
		if(joshagachi.size() == 0) {
			petsPanel.add(new JLabel("Click here to get a Joshagachi!"));
		}
		
		
	}
	
	public void setUI() {
		
		this.setDefaultCloseOperation(User.EXIT_ON_CLOSE);
		
		
		p.setLayout(new BorderLayout());
		
		listJoshagachis();
		
		Panel header = new Panel();
		header.setLayout(new FlowLayout());
		JLabel title = new JLabel(new ImageIcon("assets/TempTitle.png"));
		header.add(title);
		p.add(header, BorderLayout.NORTH);
		
		// for tiny spacer
		header.add(new JLabel("        "));
		JButton quit = new JButton(new AbstractAction("quitMenu") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// this is the saving that makes the data persistent
				save();
				dispose();
				
			}
		});
		quit.setText("Save and Exit");
		header.add(quit);
		
		Panel sideMenu = new Panel();
		sideMenu.setLayout(new GridLayout(0,1));
		p.add(sideMenu, BorderLayout.WEST);
		JButton newPet = new JButton(new AbstractAction("NewPet") {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				findJoshagachi(petsPanel);
				
				
			}
		});
		newPet.setText("Find Joshagachi");
		newPet.setBackground(Color.decode("#fff75e"));
		
		JButton foodStore = new JButton(new AbstractAction("FoodStore") {
				@Override
				public void actionPerformed(ActionEvent e) {
					refreshNames();
					foodShop.shopMenu();
				}
		});
		foodStore.setText("Food Store");
		foodStore.setBackground(Color.decode("#34baeb"));
		
		JButton shop = new JButton(new AbstractAction("SwagStore") {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshNames();
				accessoryShop.shopMenu();
			}
		});
		shop.setText("Swag Shop");
		shop.setBackground(Color.decode("#5abf56"));
		
		sideMenu.add(newPet);
		sideMenu.add(foodStore);
		sideMenu.add(shop);
		
		
        this.setLocation(500, 50);
		// sets window to center of screen, sorta
		this.setVisible(true);
        this.setSize(700,400);
	}
	
	public void save() {
		System.out.println("Saving and quiting Pet Menu");
		// saving user inventories
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("foods.txt"));
			for(int i = 0; i < user.purchasedFoods.size(); i++) {
				Item item = user.purchasedFoods.get(i);
				Food f = (Food)item;
				out.write(String.format("%f/%s/%s/%d/%s\n", f.price, f.name, f.itemDesc, f.hunger, f.eatingDesc));
			}
			// double price, String name, String itemDesc, int hunger, String eatingDesc
			System.out.println("Saved: Food inventory");
			out.close();
		}
		catch(FileNotFoundException r){
            System.out.println("No file found");
        }
        catch(IOException r){
            System.out.println("Problem reading 'foods.txt'");
        }
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("accessories.txt"));
			for(int i = 0; i < user.purchasedSwag.size(); i++) {
				Item item = user.purchasedSwag.get(i);
				Accessory a = (Accessory)item;
				out.write(String.format("%f/%s/%s/%d/%s\n", a.price, a.name, a.itemDesc, a.looksBuff, a.equipingDesc));
			}
			// double price, String name, String itemDesc, int looksBuff, String eatingDesc
			System.out.println("Saved: Game inventory");
			out.close();
		}
		catch(FileNotFoundException r){
            System.out.println("No file found");
        }
        catch(IOException r){
            System.out.println("Problem reading 'foods.txt'");
        }
		// saving Joshagachis
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("pets.txt"));
			System.out.println("Wrote "+user.ownerName);
			out.write(user.ownerName+"\n");
			for(int i = 0; i<joshagachi.size(); i++) {
				Pet j = joshagachi.get(i);
				String output = String.format("%s/%s/%f/%f\n", j.species, j.name, j.foodLevel, j.energyLevel);
				out.write(output);
				System.out.println("Saved: "+output);
			}
			
			out.close();
		}
		catch(FileNotFoundException r){
            System.out.println("No file found");
        }
        catch(IOException r){
            System.out.println("Problem reading 'pets.txt'");
        }
	}
	
	// this load does not run in full program, only for testing purposes, load() is in User.Java
	public void load() {
		System.out.println("Loading Pet Menu");
		try{
			FileReader file = new FileReader("foods.dat");
			BufferedReader fileStream = new BufferedReader(file);
			String line = fileStream.readLine();
			ArrayList<Item> tempFoods = new ArrayList<Item>();
			while(line!=null){
				//System.out.println(line);
				if(line!=null){
					String[] data = line.split("/");
					Food f = new Food(Double.parseDouble(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4]);
					tempFoods.add(f);
				}
				line = fileStream.readLine();
				
			}
			// done with file, close it
			fileStream.close();
			
			//create foodShop
			foodShop = new Shop(user, "Yummy Yummies", "To feed your hungry Joshagachi", tempFoods, "assets/Yummies.png");
		}
		catch(FileNotFoundException e){
			System.out.println("No pets.dat file found");
		}
		catch(IOException e){
			System.out.println("Problem reading 'pets.dat'\n"+e);
		}
		
		try{
			FileReader file = new FileReader("accessories.dat");
			BufferedReader fileStream = new BufferedReader(file);
			String line = fileStream.readLine();
			ArrayList<Item> tempSwag = new ArrayList<Item>();
			while(line!=null){
				System.out.println(line);
				if(line!=null){
					String[] data = line.split("/");
					Accessory a = new Accessory(Double.parseDouble(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4]);
					tempSwag.add(a);
				}
				line = fileStream.readLine();
				
			}
			// done with file, close it
			fileStream.close();
			
			//create foodShop
			accessoryShop = new Shop(user, "Games and JFrames", "Actually just games", tempSwag, "assets/Games.png");
		}
		catch(FileNotFoundException e){
			System.out.println("No accessories.dat file found");
		}
		catch(IOException e){
			System.out.println("Problem reading 'accessories.dat'\n"+e);
		}
	}

}
