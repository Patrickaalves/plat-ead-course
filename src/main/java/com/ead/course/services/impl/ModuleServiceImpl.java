package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;

import java.util.List;

public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> lessonsModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if(!lessonsModelList.isEmpty()){
            lessonRepository.deleteAll(lessonsModelList);
        }

        moduleRepository.delete(moduleModel);
    }
}
