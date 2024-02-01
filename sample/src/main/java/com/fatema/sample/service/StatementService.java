package com.fatema.sample.service;

import com.fatema.sample.entity.Statement;
import com.fatema.sample.exception_handling.InvalidDateFormatException;
import com.fatema.sample.exception_handling.UnauthorizedAccessException;
import com.fatema.sample.mapper.GeneralMapper;
import com.fatema.sample.repository.StatementRepository;
import com.fatema.sample.util.Encryptor;
import com.fatema.sample.web.api.v1.dto.request.GetStatementRequestDto;
import com.fatema.sample.web.api.v1.dto.request.StatementRequestDto;
import com.fatema.sample.web.api.v1.dto.response.StatementResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Log4j2
@Slf4j
public class StatementService{

    private final StatementRepository statementRepository;
    private final GeneralMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(StatementService.class);

    /**
     * get Statements list based on some filters
     * @param filter
     * @param authentication
     * @return list of Statements with encrypted account number
     */
    public List<StatementResponse> getAllStatements(GetStatementRequestDto filter, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return getStatementResponses(filter);
        } else {
            if(filter != null){
                throw new UnauthorizedAccessException("Unauthorized access: Parameters are not allowed");
            }
            GetStatementRequestDto restrictedFilter = restrictToLastThreeMonths(new GetStatementRequestDto());
            return getStatementResponses(restrictedFilter);
        }
    }

    private List<StatementResponse> getStatementResponses(GetStatementRequestDto filter) {
        List<Statement> statements = statementRepository.findAll(buildSpecification(filter));
        statements.forEach(statement -> {
            // Encrypt the account number
            String encryptedAccountNumber = Encryptor.encryptAccountNumber(statement.getAccount().getAccountNumber());
            statement.getAccount().setAccountNumber(encryptedAccountNumber);
        });
        return mapper.mapList(statements, StatementResponse.class);
    }

    /**
     * get statement by id
     * @param id
     * @return
     */
    public StatementResponse getStatementById(Integer id) {
        Statement statement = statementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Statement not found"));
        return mapper.map(statement,StatementResponse.class);
    }

    public StatementResponse save(StatementRequestDto statementDTO) {
        Statement statement = mapper.map(statementDTO,Statement.class);
        statement = statementRepository.save(statement);
        return mapper.map(statement,StatementResponse.class);
    }

    public void deleteStatement(Integer id) {
        statementRepository.deleteById(id);
    }

    /**
     * applying filters
     * @param filter
     * @return
     */
    public Specification<Statement> buildSpecification(GetStatementRequestDto filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            // Define the date formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            if (StringUtils.hasText(filter.getFromDate()) && StringUtils.hasText(filter.getToDate())) {
                if (!isValidDateFormat(filter.getFromDate(), formatter) || !isValidDateFormat(filter.getToDate(), formatter)) {
                    throw new InvalidDateFormatException("Invalid date format in fromDate or toDate. Please provide dates in the format dd.MM.yyyy");
                }

                    predicatesList.add(criteriaBuilder.between(root.get("dateField"), filter.getFromDate(), filter.getFromDate()));

            } else if (filter.getAccountId() == null && Strings.isBlank(filter.getFromDate()) && Strings.isBlank(filter.getToDate())) {
                // If both from date and to date are not provided, get data from the last three months
                // If the user passes an account id as filter, ignore the three-month filter
                LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
                predicatesList.add(criteriaBuilder.between(root.get("dateField"), threeMonthsAgo.format(formatter), LocalDate.now().format(formatter)));
            }

            if (StringUtils.hasText(filter.getFromAmount()) && StringUtils.hasText(filter.getToAmount())) {
                BigDecimal fromAmount = new BigDecimal(filter.getFromAmount());
                BigDecimal toAmount = new BigDecimal(filter.getToAmount());

                predicatesList.add(criteriaBuilder.between(
                        root.get("amount"),
                        fromAmount,
                        toAmount
                ));
            }

            if (filter.getAccountId() != null) {
                predicatesList.add(criteriaBuilder.equal(root.get("id"), filter.getAccountId()));
            }

            return criteriaBuilder.and(predicatesList.toArray(new Predicate[0]));
        };
    }

    public GetStatementRequestDto restrictToLastThreeMonths(GetStatementRequestDto filter) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedThreeMonthsAgo = threeMonthsAgo.format(formatter);

        filter.setFromDate(formattedThreeMonthsAgo);
        filter.setToDate(LocalDate.now().format(formatter));
        return filter;
    }

    public boolean isValidDateFormat(String dateStr, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

