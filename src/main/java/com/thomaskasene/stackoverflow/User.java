package com.thomaskasene.stackoverflow;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class User {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfJoining;
    private Timestamp timeStampReg;
}
