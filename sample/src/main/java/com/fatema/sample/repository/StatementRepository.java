package com.fatema.sample.repository;

import com.fatema.sample.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatementRepository extends JpaRepository<Statement,Integer>, JpaSpecificationExecutor<Statement> {

//    Optional<Statement> findByAccount(Long accountId);

//    @Query("SELECT s FROM Statement s " +
//            "WHERE (:fromDate IS NULL OR s.datefield >= :fromDate) " +
//            "AND (:toDate IS NULL OR s.datefield <= :toDate) " +
//            "AND (:fromAmount IS NULL OR s.amount >= :fromAmount) " +
//            "AND (:toAmount IS NULL OR s.amount <= :toAmount)")
//    List<Statement> findByDateAndAmountRange(
//            @Param("fromDate") String fromDate,
//            @Param("toDate") String toDate,
//            @Param("fromAmount") BigDecimal fromAmount,
//            @Param("toAmount") BigDecimal toAmount);
}
