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
	Item amount(int amount);

	Item location(int x, int y, int z);

	Item location(Point3D point3D);

	Item name(String name);

	Item hue(int hue);
}
