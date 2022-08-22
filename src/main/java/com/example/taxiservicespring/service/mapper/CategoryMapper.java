package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.context.i18n.LocaleContextHolder;

import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.model.Language;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "category", target = "name", qualifiedByName = "translateName")
    CategoryDto mapCategoryDto(Category category);

    @Named("translateName")
    default String translateName(Category category) {
        String result = category.getName();
        String locale = LocaleContextHolder.getLocale().toLanguageTag();

        if (Language.contains(locale)) {
            result = category.getTranslations()
                    .stream()
                    .filter(t -> t.getLanguage().name().equalsIgnoreCase(locale))
                    .map(t -> t.getName())
                    .findFirst()
                    .get();
        }
        return result;
    }
}
