package dto;

import dto.abstracts.AbstractDish;
import enums.DishType;

public class MeatDish extends AbstractDish {

	private static final int COOK_TIME = 5;
	private static final int SIZE = 5;

	@Override
	public double size() {
		return SIZE;
	}

	@Override
	public int cookingTime() {
		return COOK_TIME;
	}

	@Override
	public DishType getType() {
		return DishType.MEAT;
	}
}
