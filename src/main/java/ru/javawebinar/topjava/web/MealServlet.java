package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MealServlet extends HttpServlet {
    private static List<MealTo> mealList;

    @Override
    public void destroy() {
        super.destroy();
        mealList = null;
    }

    @Override
    public void init() {
        mealList = IntStream
                .of(1, 5, 10, 15, 20)
                .mapToObj(d -> LocalDate.of(2020, 1, d))
                .flatMap(d -> IntStream
                        .of(7, 12, 14, 19)
                        .mapToObj(h -> LocalDateTime.of(d, LocalTime.of(h, 0))))
                .map((LocalDateTime it) -> {
                    switch (it.getHour()) {
                        case 7:
                            return new MealTo(it, "Завтрак", 600, it.getDayOfMonth() == 10);
                        case 12:
                            return new MealTo(it, "Полдник", 300, it.getDayOfMonth() == 10);
                        case 14:
                            return new MealTo(it, "Обед", 1500, it.getDayOfMonth() == 10);
                        default:
                            return new MealTo(it, "Ужин", 700, it.getDayOfMonth() == 10);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", mealList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
