package cn.gavin.springbucks.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
public class UserResource {
    @NotNull
    private final String name;
    @NotNull
    private final String mail;
    private LocalDateTime registrationDate;

    public UserResource(
            @JsonProperty("name") String name,
            @JsonProperty("email") String mail) {
        this.name = name;
        this.mail = mail;
        this.registrationDate = null;
    }
}
