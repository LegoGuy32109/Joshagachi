
public class Accessory extends Item {
	protected int looksBuff;
	protected String equipingDesc;
	
	public Accessory() {
		equipingDesc = "This accessory does not exist";
	}
	public Accessory(double price, String name, String itemDesc, int looksBuff, String equipingDesc) {
		this.price = price;
		this.name = name;
		this.itemDesc = itemDesc;
		this.looksBuff = looksBuff;
		this.equipingDesc = equipingDesc;
	}
}