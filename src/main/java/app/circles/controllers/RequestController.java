package app.circles.controllers;

import app.circles.enums.RequestStatus;
import app.circles.requests.CreateReqRequest;
import app.circles.responses.GetRequestResponse;
import app.circles.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService service) {
        this.requestService = service;
    }

    /**
     * создать заявку
     *
     * @param req информация id ивента и id юзера
     */
    @PostMapping("/new")
    public ResponseEntity<Void> createRequest(@RequestBody CreateReqRequest req) {
        if (requestService.createRequest(req.eventId, req.userId)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * одобрить заявку
     *
     * @param requestId id заявки
     */
    @PatchMapping("/accept")
    public ResponseEntity<Void> acceptRequest(@RequestParam Integer requestId) {
        if (requestService.acceptRequest(requestId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * отклонить заявку
     *
     * @param requestId id заявки
     */
    @PatchMapping("/reject")
    public ResponseEntity<Void> rejectRequest(@RequestParam Integer requestId) {
        if (requestService.rejectRequest(requestId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * получить заявки
     * @param eventId
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<List<GetRequestResponse>> getRequestsByEvent(@RequestParam UUID eventId) {
        List<GetRequestResponse> requests = requestService.getRequests(eventId);
        return ResponseEntity.ok(requests);
    }

    /**
     * узнать статус заявки
     *
     * @param eventId
     * @param userId
     * @return NO_REQUEST - если такой заявки нет в БД, т.е. либо никогда не создавалась, либо отклонена
     * REVIEWING - если заявка есть, но еще на рассмотрении
     * ACCEPTED - если заявка одобрена
     */
    @GetMapping("/status")
    public ResponseEntity<RequestStatus> checkRequestStatus(@RequestParam UUID eventId, @RequestParam UUID userId) {
        RequestStatus status = requestService.checkStatus(eventId, userId);
        return ResponseEntity.ok(status);
    }
}
