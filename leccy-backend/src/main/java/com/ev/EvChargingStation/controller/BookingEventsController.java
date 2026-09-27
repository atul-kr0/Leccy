package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.entity.User;
import com.ev.EvChargingStation.helper.UserHelper;
import com.ev.EvChargingStation.service.booking.BookingSseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingEventsController {

    private final BookingSseService bookingSseService;
    private final UserHelper userHelper;

    @GetMapping(
            value = "/events",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter subscribe() {

        User user = userHelper.getLoggedInUser();

        return bookingSseService.subscribe(user.getId());
    }
}