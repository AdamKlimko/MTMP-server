package com.mtmp.mtmp_server;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CalculationService {
    private final Double G = 9.80665;

    public CalculationData calculateData(
            CalculationRequest calculationRequest
    ) throws IllegalArgumentException {
        if (calculationRequest.getAngle() == null || calculationRequest.getAngle() <= 0)
            throw new IllegalArgumentException("Illegal angle: value must be greater than 0");

        if (calculationRequest.getVelocity() == null || calculationRequest.getVelocity() <= 0)
            throw new IllegalArgumentException("Illegal velocity: value must be greater than 0");

        CalculationData data = CalculationData.builder()
                .tArray(new ArrayList<>())
                .xArray(new ArrayList<>())
                .yArray(new ArrayList<>())
                .build();

        double x=0.0, y=0.0, time=0.0;
        double stopTime = 2 * calculationRequest.getVelocity() * Math.sin(Math.toRadians(calculationRequest.getAngle())) / G;

        while(time < stopTime) {
            if (y < 0)
                break;

            x = this.calcX(time, calculationRequest.getVelocity(), calculationRequest.getAngle());
            y = this.calcY(time, calculationRequest.getVelocity(), calculationRequest.getAngle());

            data.getXArray().add(x);
            data.getYArray().add(y);
            data.getTArray().add(time);
            time += 0.1;
        }

        time -= 0.1;
        time = - ((0.0 - calculationRequest.getVelocity() * Math.sin(Math.toRadians(calculationRequest.getAngle()))) / (G/2));

        data.getXArray().add(calcX(time, calculationRequest.getVelocity(), calculationRequest.getAngle()));
        data.getYArray().add(0.0);
        data.getTArray().add(time);

        return data;
    }

    private double calcX(double t, double v, double a){
        return v * t * Math.cos(Math.toRadians(a));
    }

    private double calcY(double t, double v, double a){
        return (v * t * Math.sin(Math.toRadians(a))) - (0.5 * G * Math.pow(t, 2));
    }
}
