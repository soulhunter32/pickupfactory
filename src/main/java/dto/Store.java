package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dto.abstracts.AbstractDish;
import exceptions.StoreBlockException;

/**
 * The store where the dishes are stored after leaving the oven.-
 *
 * @author skapcitzky
 *
 */
public class Store {

	private static final int SIZE = 10;

	private final List<AbstractDish> completedDishesList = new ArrayList<>();

	public double size() {
		return SIZE;
	};

	public synchronized void moveIn(final AbstractDish dish) throws StoreBlockException {
		if ((completedDishesList.stream().map(AbstractDish::size).collect(Collectors.counting())
				+ dish.size()) <= size()) {
			completedDishesList.add(dish);
		} else {
			throw new StoreBlockException(String.format("Cannot add dish %d, store is full!", dish.getId()));
		}
	}

	public synchronized AbstractDish moveOut(final AbstractDish dish) {
		final Predicate<? super AbstractDish> predicate = d -> d.getId() == dish.getId();
		completedDishesList.removeIf(predicate);

		return completedDishesList.stream().filter(predicate).collect(Collectors.toList()).get(0);
	};
}
