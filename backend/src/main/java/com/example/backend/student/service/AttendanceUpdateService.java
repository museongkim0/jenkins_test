package com.example.backend.student.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class AttendanceUpdateService {
    private final ConcurrentLinkedQueue<AttendanceUpdateEvent> updateQueue = new ConcurrentLinkedQueue<>();
    private final StudentService studentService;

    public AttendanceUpdateService(StudentService studentService) {
        this.studentService = studentService;
    }

    public static class AttendanceUpdateEvent {
        private final Long userId;
        private final String action;
        public AttendanceUpdateEvent(Long userId, String action) {
            this.userId = userId;
            this.action = action;
        }
        public Long getUserId() { return userId; }
        public String getAction() { return action; }
    }

    public void enqueueAttendanceUpdate(Long userId, String action) {
        updateQueue.add(new AttendanceUpdateEvent(userId, action));
    }


//    @Scheduled(fixedRate = 5000)
    public void processAttendanceUpdates() {
        if (updateQueue.isEmpty()) {
            return;
        }
        Map<String, List<Long>> updatesByAction = new HashMap<>();
        AttendanceUpdateEvent event;
        while ((event = updateQueue.poll()) != null) {
            updatesByAction.computeIfAbsent(event.getAction(), k -> new ArrayList<>())
                    .add(event.getUserId());
        }
        updatesByAction.forEach((action, userIds) -> {
            try {
                studentService.batchUpdate(userIds, action);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

