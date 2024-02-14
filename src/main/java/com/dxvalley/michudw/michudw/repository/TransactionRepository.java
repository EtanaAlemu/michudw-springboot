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
            "WITH MVMT AS("
                    + "SELECT BUSINESS_DATE AS BOOKING_DATE,"
                    + "CREDIT_ACCT_NO AS TXN_ACCOUNT,"
                    + "TXN_REF_NO AS REFERENCE,"
                    + "NULL AS DESCRIPTION,"
                    + "NULL AS NARATIVE,"
                    + "CREDIT_VALUE_DATE AS VALUE_DATE,"
                    + "NULL AS DEBIT,"
                    + "CREDIT_LOCAL_AMOUNT AS CREDIT,"
                    + "DEBIT_ACCT_NO AS FROMACCT,"
                    + "CREDIT_ACCT_NO AS TOACCT "
                    + "FROM target.W_FUNDS_MVMT_TXN_F "
                    + "WHERE BUSINESS_DATE BETWEEN :startDate AND :endDate "
                    + "AND CREDIT_ACCT_NO = :accountNumber "
                    + "UNION ALL "
                    + "SELECT BUSINESS_DATE AS BOOKING_DATE,"
                    + "DEBIT_ACCT_NO AS TXN_ACCOUNT,"
                    + "TXN_REF_NO AS REFERENCE,"
                    + "NULL AS DESCRIPTION,"
                    + "NULL AS NARATIVE,"
                    + "CREDIT_VALUE_DATE AS VALUE_DATE,"
                    + "CREDIT_LOCAL_AMOUNT AS DEBIT,"
                    + "NULL AS CREDIT,"
                    + "DEBIT_ACCT_NO AS FROMACCT,"
                    + "CREDIT_ACCT_NO AS TOACCT "
                    + "FROM target.W_FUNDS_MVMT_TXN_F "
                    + "WHERE BUSINESS_DATE BETWEEN :startDate AND :endDate "
                    + "AND DEBIT_ACCT_NO = :accountNumber "
                    + ")"
                    + "SELECT B.*, "
                    + "CASE WHEN B.BOOKING_DATE = (SELECT MIN(D.BUSINESS_DATE) FROM TARGET.W_DATE_D D WHERE D.BUSINESS_DATE >= :startDate) THEN A1.LCY_CLOSING_BALANCE END AS BBF, "
                    + "CASE WHEN B.BOOKING_DATE = (SELECT MAX(D.BUSINESS_DATE) FROM TARGET.W_DATE_D D WHERE D.BUSINESS_DATE <= :endDate) THEN A2.LCY_CLOSING_BALANCE END AS Closing_Balance "
                    + "FROM MVMT B "
                    + "JOIN (SELECT A.business_date, A.CONTRACT_CODE, A.LCY_CLOSING_BALANCE "
                    + "FROM TARGET.VW_GL_BAL_CRB_F A "
                    + "WHERE LINE_NO BETWEEN 3704 AND 4199 "
                    + "AND A.business_date = (SELECT MAX(D.BUSINESS_DATE) FROM TARGET.W_DATE_D D WHERE D.BUSINESS_DATE<:startDate) "
                    + "AND A.CONTRACT_CODE = :accountNumber) A1 ON A1.CONTRACT_CODE = B.TXN_ACCOUNT "
                    + "JOIN (SELECT A.business_date, A.CONTRACT_CODE, A.LCY_CLOSING_BALANCE "
                    + "FROM TARGET.VW_GL_BAL_CRB_F A WHERE LINE_NO BETWEEN 3704 AND 4199 AND A.CONTRACT_CODE = :accountNumber AND A.business_date = :endDate) A2 ON A2.CONTRACT_CODE = B.TXN_ACCOUNT "
                    + "ORDER BY BOOKING_DATE")
    List<Transaction> findTransactions(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("accountNumber") String accountNumber);
}