package com.fatema.sample.service;

import com.fatema.sample.entity.Statement;
import com.fatema.sample.exception_handling.InvalidDateFormatException;
import com.fatema.sample.exception_handling.UnauthorizedAccessException;
import com.fatema.sample.mapper.GeneralMapper;
import com.fatema.sample.repository.StatementRepository;
import com.fatema.sample.web.api.v1.dto.request.GetStatementRequestDto;
import com.fatema.sample.web.api.v1.dto.request.StatementRequestDto;
import com.fatema.sample.web.api.v1.dto.response.StatementResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatementServiceTest {

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private GeneralMapper mapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private StatementService statementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStatements_AdminRole_AllowedParameters() {
        // Arrange
        GetStatementRequestDto filter = new GetStatementRequestDto();

        authentication = new TestingAuthenticationToken(new SecurityProperties.User(), null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        // Act
        List<StatementResponse> result = statementService.getAllStatements(filter, authentication);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAllStatements_UserRole_AllowedParameters() {
        // Arrange
        GetStatementRequestDto filter = null;
        authentication = new TestingAuthenticationToken(new SecurityProperties.User(), null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        // Act and Assert
        assertThrows(UnauthorizedAccessException.class, () -> statementService.getAllStatements(filter, authentication));
    }

    @Test
    void testGetAllStatements_UserRole_RestrictedParameters() {
        // Arrange
        GetStatementRequestDto filter = new GetStatementRequestDto();
        authentication = new TestingAuthenticationToken(new SecurityProperties.User(), null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        // Act and Assert
        assertThrows(UnauthorizedAccessException.class, () -> statementService.getAllStatements(filter, authentication));
    }

    @Test
    void testGetStatementById() {
        // Arrange
        int statementId = 1;
        Statement mockStatement = new Statement();
        when(statementRepository.findById(statementId)).thenReturn(java.util.Optional.of(mockStatement));
        StatementResponse expectedResponse = new StatementResponse();

        // Act
        StatementResponse actualResponse = statementService.getStatementById(statementId);

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void testSave() {
        // Arrange
        StatementRequestDto statementRequestDto = new StatementRequestDto();
        Statement mockStatement = new Statement();
        when(mapper.map(statementRequestDto, Statement.class)).thenReturn(mockStatement);
        when(statementRepository.save(mockStatement)).thenReturn(mockStatement);
        StatementResponse expectedResponse = new StatementResponse();

        // Act
        StatementResponse actualResponse = statementService.save(statementRequestDto);

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void testDeleteStatement() {
        // Arrange
        int statementId = 1;

        // Act
        assertDoesNotThrow(() -> statementService.deleteStatement(statementId));
    }

    @Test
    void testBuildSpecification() {
        // Arrange
        GetStatementRequestDto filter = new GetStatementRequestDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        filter.setFromDate("01.01.2022");
        filter.setToDate("31.1.2022");

        // Act
        assertThrows(InvalidDateFormatException.class, () -> statementService.buildSpecification(filter));
    }

    @Test
    void testRestrictToLastThreeMonths() {
        // Arrange
        GetStatementRequestDto filter = new GetStatementRequestDto();

        // Act
        GetStatementRequestDto result = statementService.restrictToLastThreeMonths(filter);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testIsValidDateFormat() {
        // Arrange
        String validDateStr = "01.01.2022";
        String invalidDateStr = "2022-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Act
        boolean validResult = statementService.isValidDateFormat(validDateStr, formatter);
        boolean invalidResult = statementService.isValidDateFormat(invalidDateStr, formatter);

        // Assert
        assertTrue(validResult);
        assertFalse(invalidResult);
    }


}