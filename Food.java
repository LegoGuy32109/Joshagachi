
public class Food extends Item {
	//hunger maxes at 100, anything above will make pet bloated
	// average val 1-8 bigger vals for bigger expensive foods
	int hunger;
	String eatingDesc;
	
	public Food() {
		eatingDesc = "This food doesn't exist";
	}
	public Food(double price, String name, String itemDesc, int hunger, String eatingDesc) {
		this.price = price;
		this.name = name;
		this.itemDesc = itemDesc;
		this.hunger = hunger;
		this.eatingDesc = eatingDesc;
	}
}