package org.example.converters;

import org.example.dto.AuthDto;
import org.example.dto.PasswordChangeDto;
import org.example.dto.TraineeUpdateRequestDto;
import org.example.entities.Trainee;
import org.example.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TraineeConverter {
    public AuthDto fromPasswordChangeDtoToAuthDto(PasswordChangeDto passwordChangeDto) {
        String username = passwordChangeDto.getUsername();
        String password = passwordChangeDto.getOldPassword();
        return new AuthDto(username, password);
    }

    public Trainee updateRequestDtoToTrainee(TraineeUpdateRequestDto traineeUpdateRequestDto) {
        String firstName = traineeUpdateRequestDto.getFirstName();
        String lastName = traineeUpdateRequestDto.getLastName();
        String username = traineeUpdateRequestDto.getUsername();
        Boolean isActive = traineeUpdateRequestDto.getIsActive();
        User user = new User(firstName, lastName, username, isActive);
        LocalDate dob = traineeUpdateRequestDto.getDob();
        String address = traineeUpdateRequestDto.getAddress();
        return new Trainee(dob, address, user);
    }
}
