package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static List<MealTo> mealList;
    private static Logger log = getLogger(MealServlet.class);

    @Override
    public void init() {
        mealList = MealsUtil.filteredByStreams(TestUtil.getMeals(), LocalTime.MIN, LocalTime.MAX, 2500);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("set meals list and forward to meals");
        req.setAttribute("list", mealList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
