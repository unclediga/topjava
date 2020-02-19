package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final int userId = SecurityUtil.authUserId();
    private final int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();

    @Autowired
    private MealService service;

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealTo> getTos() {
        return MealsUtil
                .getTos(
                        service.getAll(userId),
                        userCaloriesPerDay);
    }

    public List<MealTo> getFilteredTos(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil
                .getFilteredTos(
                        service.getFiltered(
                                userId,
                                startDate == null ? LocalDate.MIN : startDate,
                                endDate == null ? LocalDate.MAX : endDate),
                        userCaloriesPerDay,
                        startTime == null ? LocalTime.MIN : startTime,
                        endTime == null ? LocalTime.MAX : endTime);
    }
}