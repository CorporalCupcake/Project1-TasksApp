package com.example.pj1tasksappbackend.repositories;

import com.example.pj1tasksappbackend.models.Task;
import com.example.pj1tasksappbackend.utility.repositoryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * The interface Task repository.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, String>, repositoryUtil {

    Page<Task> findAll(Pageable pageable);

//    @Modifying
//    @Transactional
//    @Query( value = ("DELETE FROM " + taskTable + " WHERE uuid=?1"),
//        nativeQuery = true
//    )
//    public void deleteTaskByUuid(String taskUuid);


    /**
     * Find by created by uuid page.
     *
     * @param createdByUuid the created by uuid
     * @param pageRequest   the page request
     * @return the page
     */
    @Query( value = ("SELECT * FROM "+ taskTable + " WHERE created_by_uuid = ?1") , nativeQuery = true )
    Page<Task> findByCreatedByUuid(String createdByUuid, Pageable pageRequest);

}
