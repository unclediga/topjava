package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static List<MealTo> mealList;
    private static Logger log = getLogger(MealServlet.class);

    @Override
    public void init() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 10, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 10, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 10, 20, 0), "Ужин", 450)
        );
        mealList = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("set meals list and forward to meals");
        for (MealTo meal : mealList) {
            log.debug("meal : " + meal);
        }
        req.setAttribute("list", mealList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
