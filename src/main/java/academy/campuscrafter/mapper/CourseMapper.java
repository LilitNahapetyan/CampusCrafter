package academy.campuscrafter.mapper;

import academy.campuscrafter.dto.CreateCourseDto;import academy.campuscrafter.model.Course;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")public interface CourseMapper {
    Course map(CreateCourseDto createCourseDto);
}