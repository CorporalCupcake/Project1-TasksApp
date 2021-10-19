package com.example.pj1tasksappbackend.controller;

import com.example.pj1tasksappbackend.models.Task;
import com.example.pj1tasksappbackend.repositories.TaskRepository;
import com.example.pj1tasksappbackend.repositories.UserRepository;
import com.example.pj1tasksappbackend.utility.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.parseBoolean;

/**
 * The Task controller.
 * This REST Controller allows Cross Origin connections from localhost:8081.
 * The URL for this class is /tasks
 */
@RestController
@CrossOrigin(origins="http://localhost:8081")
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Task controller.
     *
     * @param taskRepo the task repo
     * @param userRepo the user repo
     * @param jwtUtil  the jwt util
     */
    public TaskController(TaskRepository taskRepo, UserRepository userRepo, JwtUtil jwtUtil) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }


    /**
     * Create task api response entity.
     *
     * @param authorizationHeader the authorization header
     * @return the response entity
     */
    @GetMapping("/create")
    public ResponseEntity<String> createTaskApi(
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String email, createdByUuid;
        try {
            String jwt = jwtUtil.getJwtFromHeader(authorizationHeader);
            System.out.println(1);
            jwtUtil.validateJwt(jwt);
            System.out.println(2);
            email = jwtUtil.getEmailFromJwt(jwt);
            System.out.println(3);
            createdByUuid = userRepo.getUserUuidFromEmail(email);
            System.out.println(4);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>("Please sign in again (Malformed JWT)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (SignatureException e) {
            return new ResponseEntity<>("Please sign in again (Invalid Signature)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>("Please sign in again (JWT Time over)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        }

        taskRepo.save(new Task(createdByUuid, "", "", 0));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * Gets paginated tasks.
     *
     * @param authorizationHeader the authorization header
     * @param page                the page
     * @param sort                the sort
     * @return the paginated tasks
     */
    @GetMapping("/paginatedTasks")
    public ResponseEntity getPaginatedTasks(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestParam(defaultValue="0") int page,
                                            @RequestParam(defaultValue="1") int sort
    ){
        int PAGE_SIZE = 4;
        try {
            String jwt = jwtUtil.getJwtFromHeader(authorizationHeader);
            jwtUtil.validateJwt(jwt);
            String userEmail = jwtUtil.getEmailFromJwt(jwt);
            String userUuid = userRepo.getUserUuidFromEmail(userEmail);

            Pageable paging;
            switch(sort){
                case 1: paging = PageRequest.of(page, PAGE_SIZE, Sort.by("created_at_timestamp").ascending());
                        break;
                case 2: paging = PageRequest.of(page, PAGE_SIZE, Sort.by("created_at_timestamp").descending());
                        break;
                case 3: paging = PageRequest.of(page, PAGE_SIZE, Sort.by("due_by_timestamp").ascending());
                        break;
                case 4: paging = PageRequest.of(page, PAGE_SIZE, Sort.by("due_by_timestamp").descending());
                        break;
                default: paging = PageRequest.of(page, 4);
            }

            Page<Task> pageTasks = taskRepo.findByCreatedByUuid(userUuid, paging);
            List<Task> tasks = pageTasks.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("tasks", tasks);
            response.put("currentPage", pageTasks.getNumber());
            response.put("totalItems", pageTasks.getTotalElements());
            response.put("totalPages", pageTasks.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>("Please sign in again (Malformed JWT)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (SignatureException e) {
            return new ResponseEntity<>("Please sign in again (Invalid Signature)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>("Please sign in again (JWT Time over)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("Please sign in again (Illegal Argument Exception)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (Exception e) {
                return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete task response entity.
     *
     * @param authorizationHeader the authorization header
     * @param uuid                the uuid
     * @return the response entity
     */
    @GetMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestHeader("Authorization") String authorizationHeader,
                                    @RequestParam String uuid
    ){
        try {
            String jwt = jwtUtil.getJwtFromHeader(authorizationHeader);
            jwtUtil.validateJwt(jwt);

            taskRepo.deleteById(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>("Please sign in again (Malformed JWT)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (SignatureException e) {
            return new ResponseEntity<>("Please sign in again (Invalid Signature)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>("Please sign in again (JWT Time over)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("Please sign in again (Illegal Argument Exception)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update task response entity.
     *
     * @param authorizationHeader the authorization header
     * @param uuid                the uuid
     * @param title               the title
     * @param description         the description
     * @param isCompleted         the is completed
     * @param priority            the priority
     * @param createdByUuid       the created by uuid
     * @param createdAtTimestamp  the created at timestamp
     * @param dueByTimestamp      the due by timestamp
     * @return the response entity
     */
    @GetMapping("/updateTask")
    public ResponseEntity updateTask(@RequestHeader("Authorization") String authorizationHeader,
                                     @RequestParam String uuid,
                                     @RequestParam String title,
                                     @RequestParam String description,
                                     @RequestParam String isCompleted,
                                     @RequestParam String priority,
                                     @RequestParam String createdByUuid,
                                     @RequestParam String createdAtTimestamp,
                                     @RequestParam String dueByTimestamp
    ){
        try {
            String jwt = jwtUtil.getJwtFromHeader(authorizationHeader);
            jwtUtil.validateJwt(jwt);

            Task updatedTask = new Task(uuid, createdByUuid, title, description, parseBoolean(isCompleted), Integer.parseInt(priority),
                    new SimpleDateFormat("yyyy-MM-dd").parse(createdAtTimestamp.substring(0, 10)),
                    new SimpleDateFormat("yyyy-MM-dd").parse(dueByTimestamp.substring(0, 10)));


            taskRepo.save(updatedTask);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>("Please sign in again (Malformed JWT)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (SignatureException e) {
            return new ResponseEntity<>("Please sign in again (Invalid Signature)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>("Please sign in again (JWT Time over)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("Please sign in again (Illegal Argument Exception)", HttpStatus.I_AM_A_TEAPOT); // Code 418
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}