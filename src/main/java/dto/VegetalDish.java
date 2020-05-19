package dto;

import dto.abstracts.AbstractDish;
import enums.DishType;

public class VegetalDish extends AbstractDish {

	private static final int COOK_TIME = 3;
	private static final int SIZE = 2;

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
		return DishType.VEGETAL;
	}
}
