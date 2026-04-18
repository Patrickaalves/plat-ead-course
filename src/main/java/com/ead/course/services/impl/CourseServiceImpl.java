package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import jakarta.transaction.Transactional;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> modulesModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if(!modulesModelList.isEmpty()){
            for(ModuleModel module : modulesModelList){
                List<LessonModel> lessonsModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if(!lessonsModelList.isEmpty()){
                    lessonRepository.deleteAll(lessonsModelList);
                }
            }
            moduleRepository.deleteAll(modulesModelList);
        }

        courseRepository.delete(courseModel);
    }
}
