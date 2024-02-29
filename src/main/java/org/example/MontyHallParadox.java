package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class MontyHallParadox {
    private static final int numberOfGames = 1000;

    public static void startNewGame() {

        MontyHallGameSimulator simulator = new MontyHallGameSimulator();

        for (int i = 0; i < numberOfGames; i++) {
            simulator.simulateGame();
        }

        simulator.displayResults();
    }
}

@Data
@AllArgsConstructor
class  GameResult {
    private boolean win;
}

class MontyHallGameSimulator {
    Door[] doors = Door.values();
    private List<GameResult> resultsWithChangeDoor = new ArrayList<>();
    private List<GameResult> resultsWithoutChangeDoor = new ArrayList<>();

    private int results = 0;
    private final RandomDataGenerator generator = new RandomDataGenerator();

    public void simulateGame() {


        int carBehindDoor = generator.nextSecureInt(0, 2);
        int firstChoice = generator.nextSecureInt(0, 2);

        int finalChoice = generator.nextSecureInt(0, 2);
        boolean openOtherDoor = (firstChoice!=carBehindDoor) && (firstChoice!=finalChoice);

        if (!openOtherDoor) {
            finalChoice = firstChoice;
        }


        boolean win = (finalChoice == carBehindDoor);
        if(openOtherDoor) saveResultWithChangeDoor(new GameResult(win));
        else saveResultWithoutChangeDoor(new GameResult(win));
    }

    public void saveResultWithChangeDoor(GameResult result) {
        resultsWithChangeDoor.add(result);
    }
    public void saveResultWithoutChangeDoor(GameResult result) {
        resultsWithoutChangeDoor.add(result);
    }

    public void displayResults() {
        results = resultsWithChangeDoor.size() + resultsWithoutChangeDoor.size();
        int victoriesChangeDoor = (int) resultsWithChangeDoor.stream().filter(GameResult::isWin).count();
        int victoriesWithoutChangeDoor = (int) resultsWithoutChangeDoor.stream().filter(GameResult::isWin).count();
        int victories = victoriesChangeDoor + victoriesWithoutChangeDoor;
        int defeats = resultsWithChangeDoor.size() + resultsWithoutChangeDoor.size() - victories;
        System.out.println("Victories with change door: " + victoriesChangeDoor);
        System.out.println("Victories without change door: " + victoriesWithoutChangeDoor);
        System.out.println("Defeats: " + defeats);
        System.out.printf("Winning choices: %.02f", ((double) victories / results) * 100);
        System.out.println("%");
    }
}

