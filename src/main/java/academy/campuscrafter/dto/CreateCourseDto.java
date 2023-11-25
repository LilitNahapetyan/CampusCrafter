package academy.campuscrafter.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCourseDto {
    private String title;
    private String description;
    private String startD;
    private float credit;
    private int enrolmentLimit;
    private String status;


}
