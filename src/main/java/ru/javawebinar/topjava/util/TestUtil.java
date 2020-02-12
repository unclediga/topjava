package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

public class TestUtil {
    public static List<Meal> getMeals() {
        Random random = new Random(1000);
        List<Integer> days = Arrays.asList(1, 5, 10, 15, 20);
        List<AbstractMap.SimpleEntry<Integer, String>> hours = Arrays.asList(
                new AbstractMap.SimpleEntry<Integer, String>(7, "Завтрак"),
                new AbstractMap.SimpleEntry<Integer, String>(12, "Полдник"),
                new AbstractMap.SimpleEntry<Integer, String>(14, "Обед"),
                new AbstractMap.SimpleEntry<>(19, "Ужин"));

        List<Meal> mealList = new ArrayList<>(20);
        for (Integer day : days) {
            for (AbstractMap.SimpleEntry<Integer, String> hour : hours) {
                int calories = random.nextInt(1500);
                calories = hour.getKey() != 14 && calories > 1000 ? 700 : calories;
                mealList.add(new Meal(LocalDateTime.of(2020, 1, day, hour.getKey(), 0, 0), hour.getValue(), calories));
            }
        }
        return mealList;
    }
}
