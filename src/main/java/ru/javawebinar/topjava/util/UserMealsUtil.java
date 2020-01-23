package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        List<UserMealWithExcess> mealsTo;
        mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------------------");
        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> me = new ArrayList<>(meals.size());
        HashMap<LocalDate, Integer> h = new HashMap<>(); // обшая сумма калорий за день
        LocalDate ldate;
        LocalTime ltime;
        // вычисляем общую сумму калорий за каждый день
        for (UserMeal m : meals) {
            ldate = m.getDateTime().toLocalDate();
            h.put(ldate, h.getOrDefault(ldate, 0) + m.getCalories());
        }
        // готовим список: отбираем по времени, ставим признак превышения дневной нормы по калориям
        for (UserMeal m : meals) {
            ldate = m.getDateTime().toLocalDate();
            ltime = m.getDateTime().toLocalTime();
            if (ltime.isAfter(startTime) && ltime.isBefore(endTime))
                me.add(new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), h.get(ldate) > caloriesPerDay));
        }
        return me;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors
                        .collectingAndThen(Collectors
                                        .toMap(it -> it.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum),
                                p -> meals
                                        .stream()
                                        .filter(it -> it.getDateTime().toLocalTime().isAfter(startTime) && it.getDateTime().toLocalTime().isBefore(endTime))
                                        .map(it -> new UserMealWithExcess(it.getDateTime(), it.getDescription(), it.getCalories(), p.get(it.getDateTime().toLocalDate()) > caloriesPerDay))
                                        .collect(Collectors.toList())));
    }
}
