package com.jobportal.jobservice.util;

import java.util.function.Predicate;

public class SlugGenerator {

    private SlugGenerator() {
    }

    public static String generateUniqueSlug(String name, Predicate<String> existsBySlug) {
        var base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");

        if (!existsBySlug.test(base)) return base;

        var counter = 1;
        while (existsBySlug.test(base + "-" + counter)) counter++;
        return base + "-" + counter;
    }
}
