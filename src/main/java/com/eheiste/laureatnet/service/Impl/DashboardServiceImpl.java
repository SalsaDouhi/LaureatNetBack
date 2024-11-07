package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.dto.DashboardDTO;
import com.eheiste.laureatnet.repository.*;
import com.eheiste.laureatnet.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserAccountRepository userAccountRepository;
    private final PostRepository postRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ScientificArticlePostRepository scientificArticlePostRepository;

    public DashboardDTO getDashboardData() {
        int yearly = postRepository.countPostsThisYear() +
                internshipOfferRepository.countPostsThisYear() +
                jobOfferRepository.countPostsThisYear() +
                scientificArticlePostRepository.countPostsThisYear();

        int monthly = postRepository.countPostsThisMonth() +
                internshipOfferRepository.countPostsThisMonth() +
                jobOfferRepository.countPostsThisMonth() +
                scientificArticlePostRepository.countPostsThisMonth();

        int[] yearlyChart = new int[12];
        for (int i = 0; i < 12; i++) {
            yearlyChart[i] = postRepository.countPostsByMonth(i + 1) +
                    internshipOfferRepository.countPostsByMonth(i + 1) +
                    jobOfferRepository.countPostsByMonth(i + 1) +
                    scientificArticlePostRepository.countPostsByMonth(i + 1);
        }

        DashboardDTO.Total total = DashboardDTO.Total.builder()
                .normalPost(postRepository.countTotal())
                .internshipOffer(internshipOfferRepository.countTotal())
                .jobOffer(jobOfferRepository.countTotal())
                .scientificArticle(scientificArticlePostRepository.countTotal())
                .build();

        int totalUsers = userAccountRepository.countTotalUsers();
        int[] usersPerMonth = userAccountRepository.countNewUsersLast12Months();

        return DashboardDTO.builder()
                .total(total)
                .yearlyChart(yearlyChart)
                .yearly(yearly)
                .monthly(monthly)
                .totalUsers(totalUsers)
                .usersPerMonth(usersPerMonth)
                .build();
    }
}
