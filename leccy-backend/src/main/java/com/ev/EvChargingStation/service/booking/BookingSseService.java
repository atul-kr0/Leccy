package com.ev.EvChargingStation.service.booking;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingSseService {

    private final ConcurrentHashMap<Long, Set<SseEmitter>>
            emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long userId) {
        // Keep the connection open for up to 30 minutes.
        SseEmitter emitter = new SseEmitter(0L);

        emitters.computeIfAbsent(
                userId,
                id -> ConcurrentHashMap.newKeySet()
        ).add(emitter);

        Runnable removeEmitter = () -> remove(userId, emitter);

        emitter.onCompletion(removeEmitter);
        emitter.onTimeout(() -> {
            removeEmitter.run();
            emitter.complete();
        });
        emitter.onError(error -> removeEmitter.run());

        // Send an initial event to confirm the connection.
        try {
            emitter.send(
                    SseEmitter.event()
                            .name("connected")
                            .data("SSE connection established")
            );
        } catch (IOException e) {
            removeEmitter.run();
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void notifyBookingUpdated(Long userId) {
        Set<SseEmitter> userEmitters = emitters.get(userId);

        if (userEmitters == null) {
            return;
        }

        for (SseEmitter emitter : userEmitters) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("booking-updated")
                                .data("refresh")
                );
            } catch (IOException e) {
                remove(userId, emitter);
                emitter.completeWithError(e);
            }
        }
    }

    private void remove(Long userId, SseEmitter emitter) {
        Set<SseEmitter> userEmitters = emitters.get(userId);

        if (userEmitters != null) {
            userEmitters.remove(emitter);

            if (userEmitters.isEmpty()) {
                emitters.remove(userId, userEmitters);
            }
        }
    }
}