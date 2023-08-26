
public abstract class Item {
	protected double price;
	protected String name;
	protected String itemDesc;
	
	public Item() {
		price = 0.0;
		name = "Default Item";
		itemDesc = "This item should not exist";
	}
	
	public Item(double price, String name, String itemDesc) {
		this.price = price;
		this.name = name;
		this.itemDesc = itemDesc;
	}
}