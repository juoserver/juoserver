package net.sf.juoserver.controller;

import net.sf.juoserver.api.Container;
import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.Point2D;
import net.sf.juoserver.model.UOContainer;
import net.sf.juoserver.model.UOItem;
import net.sf.juoserver.protocol.ContainerItems;
import net.sf.juoserver.protocol.DrawContainer;
import net.sf.juoserver.protocol.item.ItemManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemManagerTest {

	private ItemManager itemManager;

	@BeforeEach
	public void setUp() {
		itemManager = new ItemManager();
	}

	@Test
	public void openContainer() {
		Item item = new UOItem(1, 2, 3);
		Container cont = new UOContainer(42, 43, 44,"Backpack", 45, Arrays.asList(item), new HashMap<>());
		assertEquals(Arrays.asList(new DrawContainer(cont), new ContainerItems(cont)), itemManager.use(cont));
	}


}
