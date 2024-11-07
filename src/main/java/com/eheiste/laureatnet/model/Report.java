package com.eheiste.laureatnet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean status;
    private String content;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private UserAccount reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id")
    private UserAccount reported;

    private LocalDateTime createdAt;

}
