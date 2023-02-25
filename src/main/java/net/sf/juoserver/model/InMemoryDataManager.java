package net.sf.juoserver.model;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.core.UOCore;

import java.util.*;

public class InMemoryDataManager implements DataManager {
	private static final int ACCOUNT_ID = 0;

	private static final int PLAYER2_ID = 1;
	private static final int PLAYER2_SERIAL = 2;

	private static final int FIRST_SERIAL_ID = 1;

	private int itemSerialId = 1;

	@Override
	public List<Mobile> loadMobiles() {
		return Arrays.asList(
				dressUp(new UOMobile(FIRST_SERIAL_ID, "Loller", 15,
				20, false, StatusFlag.UOML, SexRace.MaleHuman, 10, 10, 10, 10,
				10, 10, 10, 1000, 5, 5, 50, RaceFlag.Human)),
				dressUp(new UOMobile(PLAYER2_SERIAL,
				"Asder", 10, 10, false, StatusFlag.UOML, SexRace.MaleHuman, 10,
				10, 10, 10, 10, 10, 10, 1000, 5, 5, 50, RaceFlag.Human)));
	}

	private Mobile dressUp(Mobile mobile) {
		// The items' serialIDs must NOT overlap, otherwise the last character "wearing" them will "steal" them from the others
		UOItem brick = new UOItem(mobile.getSerialId() + 1800 + UOCore.ITEMS_MAX_SERIAL_ID, 0x1F9E, 0, "pitcher of water", 0);
		HashMap<Item, Point2D> positions = new HashMap<Item, Point2D>();
		positions.put(brick, new Point2D() {
			@Override
			public int getY() {
				return 0x7E;
			}
			@Override
			public int getX() {
				return 0x68;
			}
		});
		mobile.setItemOnLayer(Layer.Backpack, new UOContainer(mobile.getSerialId() + 1000 + UOCore.ITEMS_MAX_SERIAL_ID, 0x0E75, 0, "backpack",0x003C,
				Arrays.asList(brick),
				positions));
		mobile.setItemOnLayer(Layer.InnerTorso, new UOItem(mobile.getSerialId() + 0x1A + UOCore.ITEMS_MAX_SERIAL_ID, 0x1F7B, 0x7E, "doublet", 0));
		mobile.setItemOnLayer(Layer.OuterTorso, new UOItem(mobile.getSerialId() + 1200 + UOCore.ITEMS_MAX_SERIAL_ID, 0x1F03, 0x3F, "robe", 0));
		mobile.setItemOnLayer(Layer.FirstValid, new UOItem(mobile.getSerialId() + 1300 + UOCore.ITEMS_MAX_SERIAL_ID, 0x143A, 0, "maul", 5));
		mobile.setItemOnLayer(Layer.MiddleTorso, new UOItem(mobile.getSerialId() + 1400 + UOCore.ITEMS_MAX_SERIAL_ID, 0x13BF, 0x0381, "chainmail tunic", 0));
		mobile.setItemOnLayer(Layer.Pants, new UOItem(mobile.getSerialId() + 1500 + UOCore.ITEMS_MAX_SERIAL_ID, 0x13BE, 0x0090, "chainmail leggings", 0));
		mobile.setItemOnLayer(Layer.Shoes, new UOItem(mobile.getSerialId() + 1600 + UOCore.ITEMS_MAX_SERIAL_ID, 0x26AF, 0x06A8, "Arcane Thigh Boots", 0));
		mobile.setItemOnLayer(Layer.Hair, new UOItem(mobile.getSerialId() + 1700 + UOCore.ITEMS_MAX_SERIAL_ID, 0x203C, 0x044E, "long hair", 0));
		return mobile;
	}

	@Override
	public List<Account> loadAccounts() {
		return new ArrayList<Account>(Arrays.asList(UOAccount
				.createAccount(ACCOUNT_ID, "admin", "admin", FIRST_SERIAL_ID), UOAccount.createAccount(PLAYER2_ID, "user", "user", PLAYER2_SERIAL)));
	}

	@Override
	public List<Item> loadItems() {
		return List.of(new UOItem(UOCore.ITEMS_MAX_SERIAL_ID + itemSerialId++, 0x1411)
				.name("platemail legs")
				.location(747, 2147,0),
				new UOItem(UOCore.ITEMS_MAX_SERIAL_ID + itemSerialId++, 0x1530)
						.name("cloak")
						.location(743, 2147,0),
				new UOItem(UOCore.ITEMS_MAX_SERIAL_ID + itemSerialId++, 0x14EC)
						.name("map")
						.location(753, 2145,3),
				new UOItem(UOCore.ITEMS_MAX_SERIAL_ID + itemSerialId++, 0x117C)
						.name("gravestone")
						.location(748, 2142,0),
				new UOItem(UOCore.ITEMS_MAX_SERIAL_ID + itemSerialId++, 0x0F36)
						.name("sheaf of hay")
						.location(742, 2164,0),
				new UOItem(UOCore.ITEMS_MAX_SERIAL_ID + itemSerialId++, 0x156C)
						.name("decorative shield")
						.location(750, 2160,3)
						.script((item, context)-> System.out.println(context.session().getMobile().getName())));
	}

	@Override
	public Integer getItemSerial() {
		return itemSerialId;
	}
}
