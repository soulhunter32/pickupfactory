package dto.abstracts;

import enums.DishType;

/**
 * Absract dish.-
 *
 * @author skapcitzky
 *
 */
public abstract class AbstractDish extends Thread {

	private long id;

	public abstract double size();

	public abstract int cookingTime();

	public abstract DishType getType();

	@Override
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}
}
