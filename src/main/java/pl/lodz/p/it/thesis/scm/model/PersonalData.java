package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;

import javax.persistence.*;

@Embeddable
@Data
public class PersonalData {

    private String name;
    private String lastName;
    private String phoneNumber;


}
