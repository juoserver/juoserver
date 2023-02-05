package net.sf.juoserver.api;

public interface Item extends JUoEntity, Point3D {
	void accept(ItemVisitor itemManager);

	int getBaseDamage();

	int getAmount();

	/**
	 * 1 - Amount of items to be created<br/>>
	 * 2 - Graphic id for deaths when (modelId = 0x2006)
	 * @param amount value
	 */
	Item setAmount(int amount);

	Item setLocation(int x, int y, int z);

	Item setLocation(Point3D point3D);

	Item setName(String name);

	Item setHue(int hue);
}
