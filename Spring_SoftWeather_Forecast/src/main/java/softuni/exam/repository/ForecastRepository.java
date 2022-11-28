package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.util.Enums.DayOfWeek;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    @Query("SELECT f FROM Forecast f WHERE f.city = :city AND f.dayOfWeek = :day")
    Forecast findForecastByCityWhereDaysOfWeek(@Param(value = "city") City city,
                                               @Param(value = "day") DayOfWeek dayOfWeek);

    @Query("SELECT f FROM Forecast f WHERE f.dayOfWeek = :day AND f.city.population < :population " +
            "ORDER BY f.maxTemperature DESC, f.id")
    List<Forecast> findAllByDayOfWeekAndLessPopulation(@Param(value = "day") DayOfWeek dayOfWeek,
                                                       @Param(value = "population") int lessPopulation);
}
