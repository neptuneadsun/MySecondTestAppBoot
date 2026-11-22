package ru.laba2.MySecondTestAppBoot.service;

import org.springframework.stereotype.Service;
import ru.laba2.MySecondTestAppBoot.model.Positions;

import java.time.LocalDate;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int currentYear = LocalDate.now().getYear();
        int daysInYear = LocalDate.of(currentYear, 12, 31).getDayOfYear();

        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays) {
        if (!positions.isManager()) {
            return 0.0; 
        }
        double annualBonus = calculate(positions, salary, bonus, workDays);
        return annualBonus / 4;
    }
}