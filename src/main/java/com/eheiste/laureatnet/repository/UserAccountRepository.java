package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);

    @Query("SELECT u FROM UserAccount u WHERE u.id NOT IN (SELECT b.blocked.id FROM Block b WHERE b.blocker.id = ?1)")
    public Collection<UserAccount> findAllWithoutUsersBlocked(Long id);

    @Query("SELECT u FROM UserAccount u WHERE u.id <> ?1")
    List<UserAccount> findAllUsersExceptThisUserId(Long userId);

    @Query("SELECT COUNT(u) FROM UserAccount u")
    int countTotalUsers();

    @Query("SELECT COUNT(u) FROM UserAccount u WHERE u.createdAt BETWEEN ?1 AND ?2")
    int countUsersByMonth(LocalDateTime start, LocalDateTime end);

    default int[] countNewUsersLast12Months() {
        int[] monthlyCounts = new int[12];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 12; i++) {
            // Create LocalDateTime objects for the start and end of each month
            LocalDate firstDayOfMonth = today.minusMonths(11 - i).withDayOfMonth(1);
            LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

            LocalDateTime startOfMonth = LocalDateTime.of(firstDayOfMonth, LocalTime.MIN);
            LocalDateTime endOfMonth = LocalDateTime.of(lastDayOfMonth, LocalTime.MAX);

            monthlyCounts[i] = countUsersByMonth(startOfMonth, endOfMonth);
        }
        return monthlyCounts;
    }
}
