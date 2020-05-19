package service.impl;

import dto.Store;
import dto.abstracts.AbstractDish;
import exceptions.StoreBlockException;

public class OvenListener implements Runnable {

	private final Store dishStore = new Store();

	/**
	 * Method to be called by the oven instance when a dish is cooked.-
	 *
	 * @param dish
	 *            the cooked dish
	 * @throws StoreBlockException
	 */
	public void sendDishToStore(final AbstractDish dish) throws StoreBlockException {
		dishStore.moveIn(dish);
		System.out
		.println(
			String.format("%s - OL - Send dish %d to store", Thread.currentThread().getName(), dish.getId()));
	}

	@Override
	public void run() {
	}
}
