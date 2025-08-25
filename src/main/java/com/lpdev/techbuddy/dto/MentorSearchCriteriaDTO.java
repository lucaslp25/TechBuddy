package com.lpdev.techbuddy.dto;

import java.util.Set;

public record MentorSearchCriteriaDTO(

        Set<String> stacks,
        Integer minExperienceYears,
        String professionalTitle,
        String company,
        Boolean availableOnly
) {
}
