package com.example.taxiservicespring.controller.converter;

import com.example.taxiservicespring.service.model.TripStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTripStatusConverter implements Converter<String, TripStatus> {

  @Override
  public TripStatus convert(String source) {
    return TripStatus.valueOf(source.toUpperCase());
  }
}
