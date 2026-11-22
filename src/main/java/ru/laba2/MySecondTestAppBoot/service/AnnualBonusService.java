package ru.laba2.MySecondTestAppBoot.service;

import org.springframework.stereotype.Service;
import ru.laba2.MySecondTestAppBoot.model.Positions;

@Service
public interface AnnualBonusService {

    double calculate(Positions positions, double salary, double bonus, int workDays);

}