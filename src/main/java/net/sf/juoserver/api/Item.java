package net.sf.juoserver.api;

public interface Item extends JUoEntity, Point3D, PropertyChangeSupported {

	void accept(ItemVisitor itemManager);

	int baseDamage();

	int amount();

	/**
	 * 1 - Amount of items to be created<br/>>
	 * 2 - Graphic id for deaths when (modelId = 0x2006)
	 * @param amount value
	 */
	Item amount(int amount);

	Item location(int x, int y, int z);

	Item location(Point3D point3D);

	Item name(String name);

	Item hue(int hue);

	Item script(EventHandler<Item> script);

	EventHandler<Item> script();
}
