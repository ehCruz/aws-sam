package test;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProcessingService {

	@Inject
	FruitSyncService fruitSyncService;

	public static final String CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames";

	public OutputObject process(InputObject input) {
		Fruit fruit = new Fruit();
		fruit.setName("A FRUIT NAME");
		fruit.setDescription("A FRUIT DESCRIPTION");

		List<Fruit> fruits = fruitSyncService.add(fruit);

		StringBuilder sb = new StringBuilder("Frutas");
		for (Fruit f : fruits) {
			sb.append(f.getName()).append("-").append(f.getDescription()).append("\n");
		}

		OutputObject out = new OutputObject();
		out.setResult(sb.toString());
		return out;
	}
}
