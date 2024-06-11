package com.dxvalley.michudw.michudw.repository;

import com.dxvalley.michudw.michudw.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query(nativeQuery = true, value =
            "select stmt.*\n" +
                    ",(select ACCOUNT_TITLE_1 from W_ACCOUNT_STATIC_D@DATA21 where contract_code=stmt.dr_acc_no and  status='Y') as Dr_account_name\n" +
                    ",(select ACCOUNT_TITLE_1 from W_ACCOUNT_STATIC_D@DATA21 where contract_code=stmt.cr_acc_no and  status='Y') as Cr_account_name\n" +
                    ",(select branch_name from W_COMPANY_D@DATA21 where BRANCH_CODE=stmt.DEBIT_COMPANY_CODE) Debit_br_name\n" +
                    ",(select branch_name from W_COMPANY_D@DATA21 where BRANCH_CODE=stmt.CREDIT_COMPANY_CODE) Credit_br_name\n" +
                    "from\n" +
                    "(\n" +
                    "select * from \n" +
                    "(\n" +
                    "select * /*+ INDEX(FBNK_STMT_ENTRY ACCT_NO_IDX)*/\n" +
                    "/*+ parallel(16)  enable_parallel_dml */\n" +
                    "from the(select cast(master.myStmtEntry(:tdate,:acc) as master.stmttype) from dual)\n" +
                    "where to_date(substr(ttime, 1,6),'YYMMDD') between :fdate and :tdate\n" +
                    ") a left outer join\n" +
                    "(\n" +
                    "select trn.*\n" +
                    ",nvl(trn_mv.CREDIT_COMPANY_CODE,(select CO_code from W_ACCOUNT_STATIC_D@DATA21 where contract_code=trn.cr_acc_no and status='Y')) CREDIT_COMPANY_CODE\n" +
                    ",nvl(trn_mv.DEBIT_COMPANY_CODE,(select CO_code from W_ACCOUNT_STATIC_D@DATA21 where contract_code=trn.dr_acc_no and status='Y')) DEBIT_COMPANY_CODE\n" +
                    "from\n" +
                    "(\n" +
                    "select a.recid\n" +
                    "--, extractValue(a.XMLRECORD,'/row/c67[@m=77]') mobile\n" +
                    ", to_date(substr(extractValue(a.XMLRECORD,'/row/c222[position()=1]'),1,6),'YYMMDD') dtime\n" +
                    ", extractValue(a.XMLRECORD,'/row/c2[position()=1]') as dr_acc_no\n" +
                    ", extractValue(a.XMLRECORD,'/row/c11[position()=1]') as cr_acc_no\n" +
                    "--,(select extractValue(b.XMLRECORD,'/row/c3[position()=1]') from master.FBNK_account b where b.recid= extractValue(a.XMLRECORD,'/row/c2[position()=1]') ) as Dr_account_name\n" +
                    "from (select * from master.fbnk_funds_transfer a where extractValue(a.XMLRECORD,'/row/c2[position()=1]') =:acc\n" +
                    "UNION ALL\n" +
                    "select * from master.fbnk_funds_transfer a where extractValue(a.XMLRECORD,'/row/c11[position()=1]') =:acc) a\n" +
                    "where /*extractValue(a.XMLRECORD,'/row/c67[@m=77]') is not null and */\n" +
                    "to_date(substr(extractValue(a.XMLRECORD,'/row/c222[position()=1]'), 1,6),'YYMMDD') between :fdate and :tdate\n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "select a.TXN_REF_NO recid\n" +
                    "--, MMT_REF mobile\n" +
                    ", a.BUSINESS_DATE dtime\n" +
                    ",a.debit_acct_no as dr_acc_no\n" +
                    ",a.credit_acct_no as cr_acc_no\n" +
                    "from W_FUNDS_MVMT_TXN_F@DATA21 a\n" +
                    "where (a.debit_acct_no=:acc or a.credit_acct_no=:acc )\n" +
                    "--and MMT_REF is not null\n" +
                    "and a.BUSINESS_DATE between :fdate and :tdate \n" +
                    "\n" +
                    "\n" +
                    "union\n" +
                    "\n" +
                    "select dc.REF as recid\n" +
                    ",to_date(substr(dc_det.date_time, 1,6),'YYMMDD') as dtime\n" +
                    ",case when dc.sign='D' then dc.account_number else null end as dr_acc_no\n" +
                    ",case when dc.sign='C' then dc.account_number else null end as cr_acc_no\n" +
                    "from stage.EFZ_DATA_CAPTURE@DATA21 DC,stage.EFZ_DATA_CAPTURE_DETAILS@DATA21 dc_det\n" +
                    "where dc.ref=dc_det.ref\n" +
                    "and dc_det.m=1\n" +
                    "and dc_det.s=1\n" +
                    "and dc.account_number=:acc\n" +
                    "and to_date(substr(dc_det.date_time, 1,6),'YYMMDD') between :fdate and :tdate \n" +
                    ")trn, W_FUNDS_MVMT_TXN_F@DATA21 trn_mv where trn.recid=trn_mv.TXN_REF_NO\n" +
                    "and trn_mv.BUSINESS_DATE between :fdate and :tdate \n" +
                    "and (trn_mv.debit_acct_no=:acc or trn_mv.credit_acct_no=:acc )\n" +
                    "\n" +
                    ") b on nvl(substr(b.recid, 1, instr(b.recid,';',1,1)-1), b.recid) = nvl(substr(a.reference, 1, instr(a.reference,'\\',1,1)-1), a.reference)\n" +
                    "order by to_number(date_time)\n" +
                    ")stmt\n" +
                    "\n"
    )
    List<Transaction> findTransactions(
            @Param("fdate") String fDate,
            @Param("tdate") String tDate,
            @Param("acc") String acc);

}