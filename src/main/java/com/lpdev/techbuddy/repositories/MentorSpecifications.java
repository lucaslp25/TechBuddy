package com.lpdev.techbuddy.repositories;

import com.lpdev.techbuddy.dto.MentorSearchCriteriaDTO;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MentorSpecifications {

    public static Specification<MentorProfile> withCriteria(MentorSearchCriteriaDTO criteria) {
        //lambda que recebe (root, query, builder)
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Stacks
            if (criteria.stacks() != null && !criteria.stacks().isEmpty()) {
                criteria.stacks().forEach(stack ->
                        predicates.add(cb.isMember(stack, root.get("profileStacks")))
                );
            }

            // Filtro por anos mínimos de experiência
            if (criteria.minExperienceYears() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("experienceYears"), criteria.minExperienceYears()));
            }

            // Filtro por título profissional
            if (criteria.professionalTitle() != null && !criteria.professionalTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("professionalTitle")), "%" + criteria.professionalTitle().toLowerCase() + "%"));
            }

            // Filtro por disponibilidade
            if (criteria.availableOnly() != null && criteria.availableOnly()) {
                predicates.add(cb.isTrue(root.get("availableForMentoring")));
            }

            // Combina todos os predicados com AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}