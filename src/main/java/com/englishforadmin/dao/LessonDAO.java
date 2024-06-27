package com.englishforadmin.dao;


import model.Lesson;

import java.sql.SQLException;
import java.util.List;

public interface LessonDAO {
    // CRUD operations

    List<Lesson> getAllLessons() throws SQLException;


    //void addLesson(Lesson lesson);
    Lesson getLessonById(String lessonId) ;
    //void updateLesson(Lesson lesson);
    //void deleteLesson(String lessonId);
}