package com.jobportal.jobservice.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryRange {
    BigDecimal minSalary;
    BigDecimal maxSalary;
}
