package org.project.spring.tax_office.logic.repository;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.report.ReportData;
import org.project.spring.tax_office.logic.entity.report.ReportInfo;
import org.project.spring.tax_office.logic.entity.report.ReportStatus;
import org.project.spring.tax_office.logic.repository.extractor.ReportDataResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportDataRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReportDataResultSetExtractor reportDataResultSetExtractor;

    private static final String SELECT_REPORT_DATA = "select * from report_data where id = ?;";
    private static final String UPDATE_REPORT_DATA_AFTER_EDIT = """
            update report_data join report on report_data.id=report.id
             set status = ?, info = ?, person = ?, nationality = ?,
             tax_year = ?, quarter = ?, month_number = ?, tax_group = ?,
             activity = ?, income = ? where report_data.id = ?;
            """;

    public int updateReportData(ReportData editedReportData) {
        return jdbcTemplate.update(UPDATE_REPORT_DATA_AFTER_EDIT, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(),
                editedReportData.getPerson(), editedReportData.getNationality(), editedReportData.getTaxYear(),
                editedReportData.getQuarter(), editedReportData.getMonthNumber(), editedReportData.getTaxGroup(),
                editedReportData.getActivity(), editedReportData.getIncome(), editedReportData.getId());
    }

    public Optional<ReportData> getReportData(Long reportId) {
        return jdbcTemplate.query(SELECT_REPORT_DATA, reportDataResultSetExtractor, reportId);
    }
}
