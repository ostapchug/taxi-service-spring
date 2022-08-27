package com.example.taxiservicespring.controller.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime[]> {
  private static final String DATE_FORMAT = "dd.MM.yy";

  @Override
  public LocalDateTime[] convert(String source) {
    LocalDateTime[] result = new LocalDateTime[2];
    String[] range = source.split("-");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    result[0] = LocalDate.parse(range[0], formatter).atStartOfDay();
    result[1] = LocalDate.parse(range[1], formatter).atStartOfDay();
    return result;
  }
}
