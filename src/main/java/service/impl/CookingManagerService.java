package service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dto.AssemblyLine;
import dto.MeatDish;
import dto.Oven;
import dto.VegetalDish;
import dto.abstracts.AbstractDish;
import exceptions.AssemblyLineBlockException;
import exceptions.OvenCookException;
import exceptions.StoreBlockException;

/**
 * Main process for dish cooking administration.-
 *
 * @author skapcitzky
 *
 */
public class CookingManagerService {

	private static List<AssemblyLine> assemblyLineList = new ArrayList<>();

	public static void main(final String[] args) {

		final AssemblyLine assemblyLineOne = new AssemblyLine();
		final AssemblyLine assemblyLineTwo = new AssemblyLine();

		try {
			for (int i = 0; i <= 10; i++) {
				final MeatDish meatDish = new MeatDish();
				meatDish.setId(new Random().nextInt(1000));
				final VegetalDish vegetalDish = new VegetalDish();
				vegetalDish.setId(new Random().nextInt(1000));
				assemblyLineOne.put(vegetalDish);
				assemblyLineOne.put(meatDish);

				final MeatDish meatDishTwo = new MeatDish();
				meatDishTwo.setId(new Random().nextInt(1000));
				final VegetalDish vegetalDishTwo = new VegetalDish();
				vegetalDishTwo.setId(new Random().nextInt(1000));
				assemblyLineTwo.put(meatDishTwo);
				assemblyLineTwo.put(vegetalDishTwo);
			}
		} catch (final AssemblyLineBlockException e) {
			System.out.println(e.getMessage());
		}

		assemblyLineList.add(assemblyLineOne);
		assemblyLineList.add(assemblyLineTwo);

		final OvenListener ovenlistener = new OvenListener();
		final Oven ovenOne = new Oven(ovenlistener);
		final Oven ovenTwo = new Oven(ovenlistener);
		final Oven ovenThree = new Oven(ovenlistener);
		final Oven ovenFour = new Oven(ovenlistener);
		final Oven ovenFive = new Oven(ovenlistener);

		final List<Oven> ovenList = Arrays.asList(ovenOne, ovenTwo, ovenThree, ovenFour, ovenFive);
		final ExecutorService ovenPool = Executors.newFixedThreadPool(ovenList.size());

		while (!assemblyLineList.stream().filter(AssemblyLine::isLineEmpty).findAny().isPresent()) {
			assemblyLineList.parallelStream().forEach(line -> {
				try {
					ovenPool
					.invokeAll(
						Arrays.asList(
							getCallable(ovenList, line),
							getCallable(ovenList, line)));
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			});
		}
		ovenPool.shutdown();
	}

	/**
	 * @param ovenList
	 * @param line
	 * @return
	 */
	private static Callable<Object> getCallable(final List<Oven> ovenList, final AssemblyLine line) {
		return () -> {
			final AbstractDish peekDish = line.peek();
			final Optional<Oven> oven = ovenList
					.stream()
					.filter(ov -> ov.isAvailableForDish(peekDish))
					.findAny();
			if (oven.isPresent()) {
				final AbstractDish dish = line.take();
				oven.get().put(dish);
				try {
					oven.get().cook(dish);
				} catch (final OvenCookException e) {
					System.out.println(e.getMessage());
				} catch (final StoreBlockException e2) {
					System.out.println(e2.getMessage());
					line.halt();
				}
			} else {
				line.halt();
			}
			return null;
		};
	}
}