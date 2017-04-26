package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final RowMapper<Meal> ROW_MAPPER = (resultSet, i) -> new Meal(
                    resultSet.getInt("meal_id"),
                    resultSet.getTimestamp("registered").toLocalDateTime(),
                    resultSet.getString("description"),
                    resultSet.getInt("calories"));



    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals").usingGeneratedKeyColumns("meal_id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("meal_id", meal.getId())
                .addValue("registered", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET meal_id=:meal_id, user_id=:user_id, registered=:registered, " +
                            "description=:description, calories=:calories WHERE meal_id=:meal_id", map);
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {

        return jdbcTemplate.update("DELETE FROM meals WHERE user_id=? AND meals.meal_id=?",userId, id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND meal_id=?", ROW_MAPPER, userId, id);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY registered DESC ", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id =? AND registered >=? AND registered <? ORDER BY registered DESC ", ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }
}
