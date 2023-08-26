import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class User extends JFrame implements ActionListener{
	// this will be the class that runs when application opens, everything else accessed from here
	protected ArrayList<Pet> joshagachi = new ArrayList<Pet>();
	protected String ownerName;
	
	protected ArrayList<Item> purchasedFoods = new ArrayList<Item>();
	protected Shop foodShop;
	protected ArrayList<Item> purchasedSwag = new ArrayList<Item>();
	protected Shop accessoryShop;
	// cutting time element, not fun
	
	protected Container p = this.getContentPane();
	protected JTextField ownerNameField = new JTextField("", 10);
	protected JTextField petNameField = new JTextField("", 10);
	
	public User() {
		super("Welcome to Joshagachi!");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		p.setLayout(new BorderLayout());
		// title
		JLabel title = new JLabel(new ImageIcon("TempTitle.png"));
		Panel header = new Panel();
		header.setLayout(new FlowLayout());
		header.add(title);
		p.add(header, BorderLayout.NORTH);
		
		load();
	
		this.setVisible(true);
		this.setSize(400,300);
	}
	
	// this is the main the whole project will run from
	public static void main(String[] args) {
		new User();
	}
	
	// is never used
	public void save() {
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("pets.txt"));
			// works better when in conjunction with string for some reason  
			for(int i = 0; i<joshagachi.size(); i++) {
				Pet j = joshagachi.get(i);
				out.write(String.format("%s/%s/%d/%d", j.species, j.name, j.foodLevel, j.energyLevel));
				System.out.println("Saved: "+j.name);
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
	
	public void load() {
		//check if pets.txt exists, if not run through name creation and get Joshagachi
		System.out.println("Loading Pets");
		try{
			FileReader file = new FileReader("pets.txt");
			BufferedReader fileStream = new BufferedReader(file);
			String line = fileStream.readLine();
			int counter = 0;
			while(line!=null){
				System.out.println(line);
				if(line!=null){
					if(counter==0)
					ownerName = line;
					else {
						String[] data = line.split("/");
						joshagachi.add(new Pet(this, data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3])));
					}
					
				}
				System.out.println(line);
				line = fileStream.readLine();
				counter++;
			}
			System.out.println("Counter: "+counter);
			// file exists, but no Joshagachi's
			if(counter<=0)
				newUser();
			// if there are Joshagachi's, let em in
			else
				returningUser();
			
			// done with file, close it
			fileStream.close();
			
		}
		catch(FileNotFoundException e){
			// start new user sequence
			newUser();
		}
		catch(IOException e){
			System.out.println("Problem reading 'pets.txt'\n"+e);
		}
		
		
		// need to load shop info for food and accessories
		System.out.println("Loading Food Shop");
		try{
			FileReader file = new FileReader("foods.dat");
			BufferedReader fileStream = new BufferedReader(file);
			String line = fileStream.readLine();
			ArrayList<Item> tempFoods = new ArrayList<Item>();
			while(line!=null){
				System.out.println(line);
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
			foodShop = new Shop(this, "Yummy Yummies", "To feed your hungry Joshagachi", tempFoods, "assets/Yummies.png");
		}
		catch(FileNotFoundException e){
			System.out.println("No foods.dat file found");
		}
		catch(IOException e){
			System.out.println("Problem reading 'foods.dat'\n"+e);
		}
		
		System.out.println("Loading Game Shop");
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
			accessoryShop = new Shop(this, "Fresh Swag", "To keep your Joshagachi Fresh", tempSwag, "assets/Games.png");
		}
		catch(FileNotFoundException e){
			System.out.println("No accessories.dat file found");
		}
		catch(IOException e){
			System.out.println("Problem reading 'accessories.dat'\n"+e);
		}
		
		// load purchased inventories
		System.out.println("Loading purchasedFood");
		try{
			FileReader file = new FileReader("foods.txt");
			BufferedReader fileStream = new BufferedReader(file);
			String line = fileStream.readLine();
			ArrayList<Item> tempFoods = new ArrayList<Item>();
			while(line!=null){
				System.out.println(line);
				if(line!=null){
					String[] data = line.split("/");
					Food f = new Food(Double.parseDouble(data[0]), data[1], data[2], Integer.parseInt(data[3]), data[4]);
					tempFoods.add(f);
				}
				line = fileStream.readLine();
				
			}
			// done with file, close it
			fileStream.close();
			
			//fill out inventory
			purchasedFoods = tempFoods;
		}
		catch(FileNotFoundException e){
			System.out.println("No foods.txt file found");
		}
		catch(IOException e){
			System.out.println("Problem reading 'foods.txt'\n"+e);
		}
		
		System.out.println("Loading purchasedSwag");
		try{
			FileReader file = new FileReader("accessories.txt");
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
			
			//fill out inventory
			purchasedSwag = tempSwag;
		}
		catch(FileNotFoundException e){
			System.out.println("No accessories.txt file found");
		}
		catch(IOException e){
			System.out.println("Problem reading 'accessories.txt'\n"+e);
		}
	}
	public void createPetMenu() {
		new PetMenu(this);
	}
	
	public void returningUser() {
		JPanel welcomePanel = new JPanel();
		welcomePanel.setLayout(new FlowLayout());
		JLabel welcome = new JLabel("Welcome back "+ownerName+"! Your Joshagachis missed you!", SwingConstants.CENTER);
		JButton ok = new JButton(new AbstractAction("Awesome!") {
			@Override
			public void actionPerformed(ActionEvent e) {
	
				dispose();
				createPetMenu();
				//new PetMenu(ownerName, joshagachi);
			}
		});
		//ok.setPreferredSize(new Dimension(70,70));
		
		welcomePanel.add(welcome);
		welcomePanel.add(ok);
		p.add(welcomePanel, BorderLayout.CENTER);
	}
	
	public void newUser() {
		JPanel welcomePanel = new JPanel();
		welcomePanel.setLayout(new FlowLayout());
		JLabel welcome = new JLabel("Welcome to Joshagachi! What's your name?", SwingConstants.CENTER);
		
		JButton ok = new JButton(new AbstractAction("OK gimmie") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// move to picking Joshagachi
				dispose();
				createPetMenu();
				//new PetMenu(ownerName, joshagachi);
			}
		});
		ok.setPreferredSize(new Dimension(0,70));
		
		JButton ownerSubmit = new JButton(new AbstractAction("submitOwnerName") {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(p.getComponents().length);
				ownerName = ownerNameField.getText().replace("\\s+", "");
				welcome.setText("Let's get you a Joshagachi "+ownerName+"!");
				if(p.getComponents().length == 3) {
					p.add(ok, BorderLayout.SOUTH);
				}
			}
		});
		ownerSubmit.setText("Submit");
		
		p.add(welcome, BorderLayout.NORTH);
		welcomePanel.add(ownerNameField);
		welcomePanel.add(ownerSubmit);
		welcomePanel.add(new JLabel(new ImageIcon("assets/TempTitle.png")));
			
		p.add(welcomePanel, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		dispose();
	}
}