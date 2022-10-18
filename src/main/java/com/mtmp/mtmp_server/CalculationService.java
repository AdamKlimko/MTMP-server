package com.mtmp.mtmp_server;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CalculationService {
    private final Double G = 9.80665;

    public CalculationData calculateData(
            CalculationRequest input
    ) throws IllegalArgumentException {
        if (input.getAngle() == null || input.getAngle() <= 0)
            throw new IllegalArgumentException("Illegal angle: value must be greater than 0");

        if (input.getVelocity() == null || input.getVelocity() <= 0)
            throw new IllegalArgumentException("Illegal velocity: value must be greater than 0");

        CalculationData data = CalculationData.builder()
                .time(new ArrayList<>())
                .x(new ArrayList<>())
                .y(new ArrayList<>())
                .build();

        double x=0.0, y=0.0, time=0.0;
        double stopTime = 2 * input.getVelocity() * Math.sin(Math.toRadians(input.getAngle())) / G;

        while(time < stopTime) {
            if (y < 0)
                break;

            x = this.calcX(time, input.getVelocity(), input.getAngle());
            y = this.calcY(time, input.getVelocity(), input.getAngle());

            data.getX().add(x);
            data.getY().add(y);
            data.getTime().add(time);
            time += 0.1;
        }

        time -= 0.1;
        time = - ((0.0 - input.getVelocity() * Math.sin(Math.toRadians(input.getAngle()))) / (G/2));

        data.getX().add(calcX(time, input.getVelocity(), input.getAngle()));
        data.getY().add(0.0);
        data.getTime().add(time);

        return data;
    }

    private double calcX(double t, double v, double a){
        return v * t * Math.cos(Math.toRadians(a));
    }

    private double calcY(double t, double v, double a){
        return (v * t * Math.sin(Math.toRadians(a))) - (0.5 * G * Math.pow(t, 2));
    }
}
