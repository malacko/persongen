package com.salgo.baji.persongen.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salgo.baji.persongen.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Person implements Serializable {
  private static final String NAME_REGEX =
      "[A-Z\\u00c1-\\u0170\\u00c4][a-z\\u00e1-\\u0171\\-\\.\\/]*";
  private static final long serialVersionUID = 4L;
  private static final String LENGTH_PROBLEM = "lenght between 3-40";

  @NotBlank(message = "firstName cannot be blank")
  @Size(min = 3, max = 40, message = LENGTH_PROBLEM)
  @Pattern(regexp = NAME_REGEX, message = "invalid  firstName")
  private String firstName;

  @NotBlank(message = "lastName cannot be blank")
  @Size(min = 3, max = 80, message = LENGTH_PROBLEM)
  @Pattern(regexp = NAME_REGEX, message = "invalid lastName")
  private String lastName;

  @DateTimeFormat
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @NotNull(message = "email cannot be null")
  @Email(message = "invalid e-mail")
  private String email;

  @NotNull(message = "sex cannot be null")
  private Sex sex;

  @NotBlank private String picturePath;

  @NotBlank private String wordPath;

  @NotBlank private String mp3Path;
}
