package ru.laba2.MySecondTestAppBoot.service;

import org.junit.jupiter.api.Test;
import ru.laba2.MySecondTestAppBoot.model.Positions;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        // then
        int currentYear = LocalDate.now().getYear();
        int daysInYear = LocalDate.of(currentYear, 12, 31).getDayOfYear();
        double expected = salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;

        assertThat(result).isEqualTo(expected);
    }



    @Test
    void calculateQuarterlyBonus_ForManager() {
        // given
        Positions position = Positions.TL;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus, workDays);

        // then
        int currentYear = LocalDate.now().getYear();
        int daysInYear = LocalDate.of(currentYear, 12, 31).getDayOfYear();
        double annualBonus = salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;
        double expected = annualBonus / 4;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus_ForNonManager() {
        // given
        Positions position = Positions.DEV;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus, workDays);

        // then
        assertThat(result).isEqualTo(0.0);
    }
}