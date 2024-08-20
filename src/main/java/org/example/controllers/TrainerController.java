package org.example.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.converters.TrainerConverter;
import org.example.dto.AuthDto;
import org.example.dto.PasswordChangeDto;
import org.example.dto.TrainerUpdateRequestDto;
import org.example.entities.Trainer;
import org.example.services.TrainerService;
import org.example.utils.TransactionLogger;
import org.example.utils.exception.TrainerNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/trainer")
public class TrainerController {

    private final Logger LOGGER = LogManager.getLogger(TrainerController.class);
    private TrainerConverter converter;

    @Autowired
    private TrainerService trainerService;

    @PostMapping("sign-up")
    public ResponseEntity signUp(@RequestBody Trainer trainer) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] POST /api/trainer/sign-up: Trainer sign-up initiated", transactionId);
        try {
            AuthDto authDto = trainerService.save(trainer);
            LOGGER.info("[Transaction id: {}] POST /api/trainer/sign-up: Status code: 201 Created. Trainer sign-up successful for username: {}", transactionId, authDto.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(authDto);
        } catch (ValidatorException e) {
            LOGGER.error("[Transaction id: {}] POST /api/trainer/sign-up: Status code: 400 Bad Request. Trainer sign-up failed: {}", transactionId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @GetMapping("login")
    public ResponseEntity login(@RequestBody AuthDto authDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] GET /api/trainer/login: Trainer login initiated", transactionId);
        try {
            trainerService.authenticate(authDto);
            LOGGER.info("[Transaction id: {}] GET /api/trainer/login: Status code: 200 OK. Trainer login successful for username: {}", transactionId, authDto.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(authDto);
        } catch (TrainerNotFoundException e) {
            LOGGER.error("[Transaction id: {}] GET /api/trainer/login: Status code: 404 Not Found. Trainer login failed: {}", transactionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @PutMapping("{username}/change-password")
    public ResponseEntity changePassword(@PathVariable("username") String username,
                                         @RequestHeader AuthDto authDto,
                                         @RequestBody PasswordChangeDto passwordChangeDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] PUT /api/trainer/{}/change-password: Trainer change password initiated", transactionId, username);
        try {
            trainerService.changePassword(authDto, passwordChangeDto);
            LOGGER.info("[Transaction id: {}] PUT /api/trainer/{}/change-password: Status code: 200 OK. Trainer change password successful for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
        } catch (TrainerNotFoundException e) {
            LOGGER.error("[Transaction id: {}] PUT /api/trainer/{}/change-password: Status code: 404 Not Found. Password change unsuccessful for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }


    @GetMapping("{username}/profile")
    public ResponseEntity profile(@PathVariable("username") String username,
                                  @RequestHeader AuthDto authDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] GET /api/trainer/{}/profile: Trainer profile initiated", transactionId, username);
        try {
            Trainer trainer = trainerService.findByUsername(authDto, username);
            LOGGER.info("[Transaction id: {}] GET /api/trainer/{}/profile: Status code: 200 OK. Trainer profile successful for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.OK).body(trainer);
        } catch (TrainerNotFoundException e) {
            LOGGER.error("[Transaction id: {}] GET /api/trainer/{}/profile: Status code: 404 Not Found. Trainer profile not found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @PutMapping("{username}/update-profile")
    public ResponseEntity updateProfile(@PathVariable String username,
                                        @RequestHeader AuthDto authDto,
                                        @RequestBody TrainerUpdateRequestDto trainerUpdateRequestDto) {
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] PUT /api/trainer/{}/update-profile: Trainer profile update initiated", transactionId, username);
        try {
            Trainer trainer = trainerService.update(authDto, converter.fromTrainerUpdateRequestToTrainer(trainerUpdateRequestDto));
            LOGGER.info("[Transaction id: {}] PUT /api/trainer/{}/update-profile: Status code: 200 OK. Trainer profile update successful for username: {}", transactionId, username);
            return ResponseEntity.status(HttpStatus.OK).body(trainer);
        } catch (TrainerNotFoundException te) {
            LOGGER.error("[Transaction id: {}] PUT /api/trainer/{}/update-profile: Status code: 404 Not Found. No trainer found for username: {}", transactionId, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(te.getMessage());
        } catch (ValidatorException e) {
            LOGGER.error("[Transaction id: {}] PUT /api/trainer/{}/update-profile: Status code: 422 Unprocessable Entity. Not valid trainer to update for username: {}", transactionId, username);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }

    @PatchMapping("{username}/change-status")
    public ResponseEntity changeStatus(@PathVariable String username,
                                       @RequestHeader AuthDto authDto,
                                       @RequestBody Boolean newStatus){
        String transactionId = TransactionLogger.getTransactionId();
        LOGGER.info("[Transaction id: {}] PATCH /api/trainer/{}/change-status: Trainer status change initiated", transactionId, username);
        try {
            trainerService.changeStatus(authDto, username, newStatus);
            LOGGER.info("[Transaction id: {}] PATCH /api/trainer/{}/change-status: Status code: 200 OK. Status change for username: ", transactionId, username, username);
            return ResponseEntity.ok().build();
        } catch (TrainerNotFoundException e) {
            LOGGER.error("[Transaction id: {}] PATCH /api/trainer/{}/change-status: Status code: 404 Not found. No trainer found for username: {}", transactionId, username, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } finally {
            TransactionLogger.clear();
        }
    }
}
