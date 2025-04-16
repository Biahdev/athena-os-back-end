package dev.abeatriz.athena_os.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api")
    public ResponseEntity<String> apiCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/db")
    public ResponseEntity<String> dbCheck() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Database error");
        }
    }

}
