package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.abstracts.AbstractDish;
import exceptions.OvenCookException;
import exceptions.StoreBlockException;
import service.impl.OvenListener;;

public class Oven implements Runnable {

	private static final Double SIZE = Double.valueOf("20");

	private final List<AbstractDish> dishList = new ArrayList<>();
	private OvenListener listener = null;

	public Oven(final OvenListener ovenlistener) {
		listener = ovenlistener;
	}

	public Double size() {
		return SIZE;
	}

	public synchronized void put(final AbstractDish dish) {
		dishList.add(dish);
	}

	public synchronized void take(final AbstractDish dish) {
		dishList.remove(dish);
	}

	public void cook(final int time) {
	}

	public void cook(final AbstractDish dish) throws OvenCookException, StoreBlockException {
		try {
			System.out
			.println(String.format("%s - Oven- Cooking dish %d", Thread.currentThread().getName(), dish.getId()));
			Thread.sleep(Long.valueOf(dish.cookingTime() * 1000L));
			take(dish);
			listener.sendDishToStore(dish);
			notifyAll();
		} catch (final InterruptedException e) {
			notifyAll();
			e.printStackTrace();
			throw new OvenCookException(e.getMessage());
		}
	}

	public synchronized boolean isAvailableForDish(final AbstractDish dish) {
		return (dishList.stream().map(AbstractDish::size).collect(Collectors.counting()).doubleValue()
				+ dish.size()) <= size();
	}

	@Override
	public void run() {
	}

	// @Override
	// public Oven run() {
	// dishList.forEach(dish -> {
	// try {
	// cook(dish, listener);
	// } catch (final OvenCookException e) {
	// System.out.println(e.getMessage());
	// }
	// });
	// return this;
	// }
}
