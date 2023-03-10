package net.sf.juoserver.model;

import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.ItemVisitor;

public class UOItem implements Item {
	private int serialId;
	private int modelId;
	private int hue;
	private String name;
	private int baseDamage;

	public UOItem(int serialId, int modelId, int hue) {
		super();
		this.serialId = serialId;
		this.modelId = modelId;
		this.hue = hue;
		this.name = "Unknown";
	}

	public UOItem(int serialId, int modelId, int hue, String name, int baseDamage) {
		super();
		this.serialId = serialId;
		this.modelId = modelId;
		this.hue = hue;
		this.name = name;
		this.baseDamage = baseDamage;
	}

	@Override
	public int getSerialId() {
		return serialId;
	}

	@Override
	public int getModelId() {
		return modelId;
	}

	@Override
	public int getHue() {
		return hue;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBaseDamage() {
		return baseDamage;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serialId;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UOItem other = (UOItem) obj;
		if (serialId != other.serialId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UOItem [serialId=" + serialId + ", modelId=" + modelId + "]";
	}

	@Override
	public void accept(ItemVisitor itemManager) {
		itemManager.visit(this);
	}
}
