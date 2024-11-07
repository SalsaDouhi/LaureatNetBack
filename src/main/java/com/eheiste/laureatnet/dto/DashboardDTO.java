package com.eheiste.laureatnet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDTO {
    private Total total;
    private int[] yearlyChart;
    private int yearly;
    private int monthly;
    private int totalUsers;
    private int[] usersPerMonth;

    @Data
    @Builder
    public static class Total {
        private int normalPost;
        private int internshipOffer;
        private int jobOffer;
        private int scientificArticle;
    }
}
