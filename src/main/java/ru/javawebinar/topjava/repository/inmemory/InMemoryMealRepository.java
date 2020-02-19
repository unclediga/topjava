package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(it -> this.save(it, MealsUtil.DEFAULT_USERID));
        MealsUtil.MEALS_U2.forEach(it -> this.save(it, MealsUtil.DEFAULT_USERID2));
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        return repository.computeIfAbsent(userId, HashMap::new);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = getUserMeals(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMeals
                .computeIfPresent(
                        meal.getId(),
                        (id, oldMeal) -> meal.getUserId() == userId ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = getUserMeals(userId);
        // TODO избыточно  : userMeals.remove(id) != null ?
        return userMeals.get(id) != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = getUserMeals(userId);
        return userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getFiltered(userId,
                meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate));
    }

    public List<Meal> getFiltered(int userId, Predicate<Meal> predicate) {
        return getUserMeals(userId).values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

