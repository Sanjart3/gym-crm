package org.example.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.converters.TraineeConverter;
import org.example.dto.AuthDto;
import org.example.dto.PasswordChangeDto;
import org.example.dto.TraineeUpdateRequestDto;
import org.example.entities.Trainee;
import org.example.entities.Trainer;
import org.example.services.TraineeService;
import org.example.utils.TransactionLogger;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping(value = "/api/trainee", consumes = {"application/json"}, produces = {"application/json", "application/XML"})
public class TraineeController {
    private final Logger LOGGER = LogManager.getLogger(TraineeController.class);
    private TraineeConverter traineeConverter;

    @Autowired
    private TraineeService traineeService;

    @PostMapping("sign-up")
    public ResponseEntity registerNewTrainee(@RequestBody Trainee trainee) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] POST /trainee/sign-up: Trainee sign-up initiated", transactionId);
        try {
            AuthDto loginInfo = traineeService.save(trainee);
            LOGGER.info("[Transaction id: {}] POST /api/trainee/sign-up  Status code: 201 Created", transactionId);
            return ResponseEntity.ok(loginInfo);
        } catch (ValidatorException ve){
            LOGGER.error("[Transation id: {}] POST /api/trainee/sign-up: Status code: 422 Unprocessable Trainee", transactionId, ve);
            return ResponseEntity.badRequest().body(ve);
        } finally {
            TransactionLogger.clear();
        }
    }

    @GetMapping("login")
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] GET /api/trainee/login: Trainee login initiated", transactionId);
        try {
            traineeService.authenticate(authDto);
            LOGGER.info("[Transaction id: {}] GET /api/trainee/login: Status code: 200 OK. Trainee login successful for username: {}", transactionId, authDto.getUsername());
            return ResponseEntity.ok().body(authDto);
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] GET /api/trainee/login: Status code: 400 Bad Request. Trainee login failed for username: {}", transactionId, authDto.getUsername(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @PutMapping("change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] PUT /api/trainee/change-password: Trainee password change initiated", transactionId);
        AuthDto auth = traineeConverter.fromPasswordChangeDtoToAuthDto(passwordChangeDto);
        try {
            traineeService.changePassword(auth, passwordChangeDto);
            LOGGER.info("[Transaction id: {}] PUT /api/trainee/change-password: Status code: 200 OK", transactionId);
            return ResponseEntity.ok().body("Password changed successfully");
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] PUT /api/trainee/change-password: Status code: 404 Not found", transactionId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @GetMapping("{username}/profile")
    public ResponseEntity getProfile(@PathVariable String username,
                                     @RequestHeader("auth") AuthDto authDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] GET /api/trainee/profile: Profile retrieval initiated for username: {}", transactionId, username);
        try {
            Trainee trainee = traineeService.findByUsername(authDto, username);
            LOGGER.info("[Transaction id: {}] GET /api/trainee/{}/profile: Status code: 200 OK. Profile retrieved for username: {}", transactionId, username, username);
            return ResponseEntity.ok().body(trainee);
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] GET /api/trainee/{}/profile: Status code: 404 Not Found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @PutMapping("{username}/update-profile")
    public ResponseEntity updateProfile(@PathVariable String username,
                                        @RequestHeader("auth") AuthDto authDto,
                                        @RequestBody TraineeUpdateRequestDto traineeRequestDto){
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] PUT /api/trainee/{}/update-profile: Trainee update profile initiated", transactionId, username);
        try {
            Trainee updatedTrainee = traineeService.update(authDto, traineeConverter.updateRequestDtoToTrainee(traineeRequestDto));
            LOGGER.info("[Transaction id: {}] PUT /api/trainee/{}/update-profile: Status code: 200 OK. Profile updated for username: {}", transactionId, username, username);
            return ResponseEntity.ok().body(updatedTrainee);
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] PUT /api/trainee/{}/update-profile: Status code: 404 Not found. No trainee found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ValidatorException e) {
            LOGGER.error("[Transaction id: {}] PUT /api/trainee/{}/update-profile: Status code: 422 Unprocessable Trainee. Not valid trainee to update for username: {}", transactionId, username, username);
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @DeleteMapping("{username}/delete")
    public ResponseEntity<Void> deleteProfile(@PathVariable String username,
                                              @RequestHeader AuthDto authDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] DELETE /api/trainee/{}/delete: Trainee delete initiated", transactionId, username);
        try {
            traineeService.deleteByUsername(authDto, username);
            LOGGER.info("[Transaction id: {}] DELETE /api/trainee/{}/delete: Status code: 204 No Content. Trainee deleted for username: {}", transactionId, username, username);
            return ResponseEntity.noContent().build();
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] DELETE /api/trainee/{}/delete: Status code: 404 Not found. No trainee found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } finally {
            TransactionLogger.clear();
        }
    }

    @GetMapping("{username}/not-assigned-trainers")
    public ResponseEntity getNotAssignedTrainers(@PathVariable String username,
                                                 @RequestHeader AuthDto authDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] GET /api/trainee/{}/not-assigned-trainers: Retrieve not assigned trainers initiated", transactionId, username);
        try {
            List<Trainer> trainerList = traineeService.findUnassignedTrainers(authDto, username);
            LOGGER.info("[Transaction id: {}] GET /api/trainee/{}/not-assigned-trainers: Status code: 200. Not assigned trainers retrieved for username: {}", transactionId, username, username);
            return ResponseEntity.ok(trainerList);
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] GET /api/trainee/{}/not-assigned-trainers: Status code: 404. Trainee not found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

//    @PutMapping("{username}/update-trainers")
//    public ResponseEntity updateTrainerList(@PathVariable String username,
//                                            )

    @PatchMapping("{username}/change-status")
    public ResponseEntity changeStatus(@PathVariable String username,
                                       @RequestHeader AuthDto authDto,
                                       @RequestBody Boolean newStatus){
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] PATCH /api/trainee/{}/change-status: Trainee change status initiated", transactionId, username);
        try {
            traineeService.changeStatus(authDto, username, newStatus);
            LOGGER.info("[Transaction id: {}] PATCH /api/trainee/{}/change-status: Status code: 200 OK. Status change for username: ", transactionId, username, username);
            return ResponseEntity.ok().build();
        } catch (TraineeNotFoundException e) {
            LOGGER.error("[Transaction id: {}] PATCH /api/trainee/{}/change-status: Status code: 404 Not found. No trainee found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }
}
