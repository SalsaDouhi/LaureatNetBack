package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.DashboardDTO;
import com.eheiste.laureatnet.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    ResponseEntity<DashboardDTO> getDashboardData() {
        return ResponseEntity.ok().body(dashboardService.getDashboardData());
    }
}
