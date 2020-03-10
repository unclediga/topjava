package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAll(@Param("userId") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetweenHalfOpen(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("userId") int userId);

    Optional<Meal> findByIdAndUser_Id(int id, int user_id);

    @Transactional
//    @Modifying
    Meal save(Meal s);
}
