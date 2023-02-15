package net.sf.juoserver.protocol.item;

import net.sf.juoserver.api.*;
import net.sf.juoserver.protocol.ContainerItems;
import net.sf.juoserver.protocol.DrawContainer;

import java.util.ArrayList;
import java.util.List;

public class ItemManager implements ItemVisitor {
	private List<Message> messages = new ArrayList<>();
	private final PlayerContext context;

	public ItemManager(PlayerContext context) {
		this.context = context;
	}

	public List<Message> use(Item item) {
		item.accept(this);
		return messages;
	}

	@Override
	public void visit(Item item) {
		if (item.script() != null) {
			item.script().execute(item, context);
		}
	}
	
	@Override
	public void visit(Container item) {
		messages.add(new DrawContainer(item));
		messages.add(new ContainerItems(item));
	}
}
