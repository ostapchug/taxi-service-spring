package com.example.taxiservicespring.util;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DistanceCalculator {

    public static BigDecimal getDistance(final BigDecimal lat1, final BigDecimal lat2, final BigDecimal lon1,
            final BigDecimal lon2) {
        final double r = 6371;
        double latitude1 = Math.toRadians(lat1.doubleValue());
        double latitude2 = Math.toRadians(lat2.doubleValue());
        double longitude1 = Math.toRadians(lon1.doubleValue());
        double longitude2 = Math.toRadians(lon2.doubleValue());
        double dLat = latitude2 - latitude1;
        double dLon = longitude2 - longitude1;
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return BigDecimal.valueOf(c * r);
    }
}
