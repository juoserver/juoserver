package net.sf.juoserver.model;

import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.ItemVisitor;
import net.sf.juoserver.api.Point3D;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class UOItem implements Item, Comparable<Item> {
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private final int serialId;
	private final int modelId;
	private int hue;
	private String name = "no name";
	private int baseDamage;
	private int amount;
	private int x;
	private int y;
	private int z;

	public UOItem(int serialId, int modelId) {
		this.serialId = serialId;
		this.modelId = modelId;
	}

	public UOItem(int serialId, int modelId, int hue) {
		super();
		this.serialId = serialId;
		this.modelId = modelId;
		this.hue = hue;
		this.name = "Unknown";
		this.amount = 1;
	}

	public UOItem(int serialId, int modelId, int hue, String name, int baseDamage) {
		super();
		this.serialId = serialId;
		this.modelId = modelId;
		this.hue = hue;
		this.name = name;
		this.baseDamage = baseDamage;
		this.amount = 1;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(property, listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(property, listener);
	}

	@Override
	public void accept(ItemVisitor itemManager) {
		itemManager.visit(this);
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
	public int getAmount() {
		return amount;
	}

	@Override
	public UOItem setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public Item setLocation(int x, int y, int z) {
		var oldLocation = new PointInSpace(this.x, this.y, this.z);
		changeSupport.firePropertyChange("x", this.x, this.x = x);
		changeSupport.firePropertyChange("y", this.y, this.y = y);
		changeSupport.firePropertyChange("z", this.z, this.z = z);
		changeSupport.firePropertyChange("location", oldLocation, new PointInSpace(this.x, this.y, this.z));
		return this;
	}

	@Override
	public Item setLocation(Point3D newLocation) {
		return setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ());
	}

	@Override
	public Item setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Item setHue(int hue) {
		this.hue = hue;
		return this;
	}

	@Override
	public String toString() {
		return "UOItem{" +
				"serialId=" + serialId +
				", modelId=" + modelId +
				", name='" + name + '\'' +
				", x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UOItem uoItem = (UOItem) o;
		return serialId == uoItem.serialId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(serialId);
	}

	@Override
	public int compareTo(Item other) {
		if (this.serialId > other.getSerialId()) {
			return 1;
		}
		if (serialId < other.getSerialId()) {
			return -1;
		}
		return 0;
	}
}
