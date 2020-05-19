package dto;

import java.util.LinkedList;
import java.util.Queue;

import dto.abstracts.AbstractDish;
import enums.AssemblyLineState;
import exceptions.AssemblyLineBlockException;

/**
 * An assembly line that contains dishes to be cooked.-
 *
 * @author skapcitzky
 *
 */
public class AssemblyLine {

	private AssemblyLineState state = AssemblyLineState.OPERATIVE;
	private final Queue<AbstractDish> dishQueue = new LinkedList<>();

	public synchronized AbstractDish take() {
		if (isOperative() && !dishQueue.isEmpty()) {
			final AbstractDish removedDish = dishQueue.remove();
			System.out
			.println(
				String.format("%s - AL -Removing dish %d", Thread.currentThread().getName(), removedDish.getId()));
			return removedDish;
		}
		return null;
	}

	public AbstractDish peek() {
		return dishQueue.peek();
	}

	/**
	 * Puts a dish in the queue if there is room for it, if there's no, it halts.-
	 *
	 * @param dish
	 *            the dish to be added to the line
	 * @throws AssemblyLineBlockException
	 */
	public synchronized void put(final AbstractDish dish) throws AssemblyLineBlockException {
		System.out.println(String.format("%s - AL -Adding dish %d", Thread.currentThread().getName(), dish.getId()));
		dishQueue.add(dish);
	};

	public void halt() {
		System.out.println(String.format("%s - AL -Halting Line", Thread.currentThread().getName()));
		state = AssemblyLineState.HALTED;
	}

	public boolean isOperative(){
		return AssemblyLineState.OPERATIVE.equals(state);
	}

	public boolean isLineEmpty() {
		return dishQueue.isEmpty();
	}
}
