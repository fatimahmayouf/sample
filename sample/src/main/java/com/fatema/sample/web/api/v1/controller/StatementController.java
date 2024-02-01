package com.fatema.sample.web.api.v1.controller;


import com.fatema.sample.service.StatementService;
import com.fatema.sample.web.api.v1.dto.request.GetStatementRequestDto;
import com.fatema.sample.web.api.v1.dto.request.StatementRequestDto;
import com.fatema.sample.web.api.v1.dto.response.BaseResponse;
import com.fatema.sample.web.api.v1.dto.response.StatementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Log4j2
@RestController
@RequestMapping("api/v1/statements")
@RequiredArgsConstructor
@Configuration
public class StatementController {

    private final StatementService statementService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getAllStatements(GetStatementRequestDto filter, Authentication authentication) {

            List<StatementResponse> statements = statementService.getAllStatements(filter, authentication);
            return BaseResponse.responseBuilder(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, statements);

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStatementById(@PathVariable Integer id) {
        StatementResponse statement = statementService.getStatementById(id);
        return BaseResponse.responseBuilder(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK,statement);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveStatement(@RequestBody StatementRequestDto statementDTO) {
        StatementResponse savedStatement = statementService.save(statementDTO);

        return BaseResponse.responseBuilder(HttpStatus.CREATED.getReasonPhrase(),HttpStatus.CREATED,savedStatement);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStatement(@PathVariable Integer id) {
        statementService.deleteStatement(id);

        return BaseResponse.responseBuilder(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK,ResponseEntity.noContent().build());
    }
}