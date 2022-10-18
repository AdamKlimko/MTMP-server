package com.mtmp.mtmp_server;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CalculationData {
    List<Double> x;
    List<Double> y;
    List<Double> time;
}

