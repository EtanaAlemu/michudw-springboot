package com.dxvalley.michudw.michudw.repository;

import com.dxvalley.michudw.michudw.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(nativeQuery = true, value = "SELECT stmt.*, " +
            "(SELECT ACCOUNT_TITLE_1 FROM W_ACCOUNT_STATIC_D@DATA21 WHERE contract_code = stmt.dr_acc_no AND status = 'Y') AS Dr_account_name, " +
            "(SELECT ACCOUNT_TITLE_1 FROM W_ACCOUNT_STATIC_D@DATA21 WHERE contract_code = stmt.cr_acc_no AND status = 'Y') AS Cr_account_name, " +
            "(SELECT branch_name FROM W_COMPANY_D@DATA21 WHERE BRANCH_CODE = stmt.DEBIT_COMPANY_CODE) AS Debit_br_name, " +
            "(SELECT branch_name FROM W_COMPANY_D@DATA21 WHERE BRANCH_CODE = stmt.CREDIT_COMPANY_CODE) AS Credit_br_name " +
            "FROM ( " +
            "    SELECT * FROM ( " +
            "        SELECT * /*+ INDEX(FBNK_STMT_ENTRY ACCT_NO_IDX)*/ " +
            "        /*+ parallel(16) enable_parallel_dml */ " +
            "        FROM the (SELECT CAST(master.myStmtEntry(:tdate, :acc) AS master.stmttype) FROM dual) " +
            "        WHERE TO_DATE(SUBSTR(ttime, 1, 6), 'YYMMDD') BETWEEN :fdate AND :tdate " +
            "    ) a LEFT OUTER JOIN ( " +
            "        SELECT trn.*, " +
            "        NVL(trn_mv.CREDIT_COMPANY_CODE, (SELECT CO_code FROM W_ACCOUNT_STATIC_D@DATA21 WHERE contract_code = trn.cr_acc_no AND status = 'Y')) AS CREDIT_COMPANY_CODE, " +
            "        NVL(trn_mv.DEBIT_COMPANY_CODE, (SELECT CO_code FROM W_ACCOUNT_STATIC_D@DATA21 WHERE contract_code = trn.dr_acc_no AND status = 'Y')) AS DEBIT_COMPANY_CODE " +
            "        FROM ( " +
            "            SELECT a.recid, " +
            "            TO_DATE(SUBSTR(EXTRACTVALUE(a.XMLRECORD, '/row/c222[position()=1]'), 1, 6), 'YYMMDD') AS dtime, " +
            "            EXTRACTVALUE(a.XMLRECORD, '/row/c2[position()=1]') AS dr_acc_no, " +
            "            EXTRACTVALUE(a.XMLRECORD, '/row/c11[position()=1]') AS cr_acc_no " +
            "            FROM (SELECT * FROM master.fbnk_funds_transfer a WHERE EXTRACTVALUE(a.XMLRECORD, '/row/c2[position()=1]') = :acc " +
            "            UNION ALL " +
            "            SELECT * FROM master.fbnk_funds_transfer a WHERE EXTRACTVALUE(a.XMLRECORD, '/row/c11[position()=1]') = :acc) a " +
            "            WHERE TO_DATE(SUBSTR(EXTRACTVALUE(a.XMLRECORD, '/row/c222[position()=1]'), 1, 6), 'YYMMDD') BETWEEN :fdate AND :tdate " +
            "            UNION " +
            "            SELECT a.TXN_REF_NO AS recid, " +
            "            a.BUSINESS_DATE AS dtime, " +
            "            a.debit_acct_no AS dr_acc_no, " +
            "            a.credit_acct_no AS cr_acc_no " +
            "            FROM W_FUNDS_MVMT_TXN_F@DATA21 a " +
            "            WHERE (a.debit_acct_no = :acc OR a.credit_acct_no = :acc) " +
            "            AND a.BUSINESS_DATE BETWEEN :fdate AND :tdate " +
            "            UNION " +
            "            SELECT dc.REF AS recid, " +
            "            TO_DATE(SUBSTR(dc_det.date_time, 1, 6), 'YYMMDD') AS dtime, " +
            "            CASE WHEN dc.sign = 'D' THEN dc.account_number ELSE NULL END AS dr_acc_no, " +
            "            CASE WHEN dc.sign = 'C' THEN dc.account_number ELSE NULL END AS cr_acc_no " +
            "            FROM stage.EFZ_DATA_CAPTURE@DATA21 DC, stage.EFZ_DATA_CAPTURE_DETAILS@DATA21 dc_det " +
            "            WHERE dc.ref = dc_det.ref AND dc_det.m = 1 AND dc_det.s = 1 AND dc.account_number = :acc " +
            "            AND TO_DATE(SUBSTR(dc_det.date_time, 1, 6), 'YYMMDD') BETWEEN :fdate AND :tdate " +
            "        ) trn, W_FUNDS_MVMT_TXN_F@DATA21 trn_mv " +
            "        WHERE trn.recid = trn_mv.TXN_REF_NO " +
            "        AND trn_mv.BUSINESS_DATE BETWEEN :fdate AND :tdate " +
            "        AND (trn_mv.debit_acct_no = :acc OR trn_mv.credit_acct_no = :acc) " +
            "    ) b ON NVL(SUBSTR(b.recid, 1, INSTR(b.recid, ';', 1, 1) - 1), b.recid) = NVL(SUBSTR(a.reference, 1, INSTR(a.reference, '\\', 1, 1) - 1), a.reference) " +
            "    ORDER BY TO_NUMBER(date_time) " +
            ") stmt")
    List<Transaction> findTransactions(@Param("fdate") String fDate, @Param("tdate") String tDate, @Param("acc") String acc);
}
