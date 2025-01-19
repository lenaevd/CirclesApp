package app.circles.controllers;

import app.circles.mappers.CreateEventReqToEvent;
import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.requests.ChangeImageRequest;
import app.circles.requests.CreateEventRequest;
import app.circles.requests.GetEventsByTypeRequest;
import app.circles.responses.GetEventResponse;
import app.circles.services.EventService;
import app.circles.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*Мероприятия(в первую очередь):
        PUT method sets up the entity with the exact information provided in the request.
        In this way, the request must contain the entire entity, not only specific fields.

        the PATCH method allows the data update of particular fields of an entity.

        ой блин а нам нужно поиск сделать??
 */

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final TypeService typeService;
    private final CreateEventReqToEvent mapper;

    @Autowired
    public EventController(EventService service, TypeService typeService, CreateEventReqToEvent mapper) {
        this.eventService = service;
        this.typeService = typeService;
        this.mapper = mapper;
    }

    /**
     * создать новый ивент
     * @param request информация об ивенте от пользователя
     */
    @PostMapping("/new")
    public ResponseEntity<Void> createNewEvent(@RequestBody CreateEventRequest request) {
        Event event = mapper.Map(request);
        List<Type> types = typeService.findTypesByNamesList(request.typesNames);
        if (eventService.createEvent(event, types, request.organizerId)) {
            return ResponseEntity.status(HttpStatus.CREATED).build(); //TODO: ВОЗМОЖНО СТОИТ ВЕРНУТЬ АЙДИ ИЛИ МОДЕЛЬ
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * удалить ивент с концами
     * @param eventId id ивента
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEvent(@RequestParam UUID eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * завершить или сделать активным ивент
     * @param eventId id ивента
     * @param isActive true для активных, false для завершенных
     */
    @PatchMapping("/changeActive")
    public ResponseEntity<Void> changeActive(@RequestParam UUID eventId, @RequestParam boolean isActive) {
        eventService.changeActive(eventId, isActive);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * поменять image для ивента
     * @param eventId id ивента
     * @param image base64 format
     */
    @PatchMapping("/changeImage")
    public ResponseEntity<Void> changeImage(@RequestParam UUID eventId, @RequestBody ChangeImageRequest request) {
        eventService.changeImage(eventId, request.imageUrl);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * получить ивент по id
     * @param eventId id ивента
     * @return ивент
     */
    @GetMapping("/getById")
    public ResponseEntity<GetEventResponse> getEventById(@RequestParam UUID eventId) {
        GetEventResponse eventResponce = eventService.getById(eventId); //TODO: МЫ ТУТ СЛИШКОМ МНОГО ИНФОРМАЦИИ О ЮЗЕРЕ ОТДАЕМ
        if (eventResponce == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(eventResponce);

        }
    }

    /**
     * получить все ивенты
     * @return список ивентов
     */
    @GetMapping("/all")
    public ResponseEntity<List<GetEventResponse>> getAllEvents() {
        List<GetEventResponse> events = eventService.getAll();
        return ResponseEntity.ok(events);
    }

    /**
     * получить все активные ивенты
     * @return список ивентов
     */
    @GetMapping()
    public ResponseEntity<List<Event>> getActiveEvents() {
        List<Event> events = eventService.getActive();
        return ResponseEntity.ok(events);
    }

    /**
     * получить активные ивенты по типу
     * @param typesRequest список названий типов
     * @return список ивентов
     */
    @PostMapping("/types")
    public ResponseEntity<List<Event>> getActiveEventsByTypes(@RequestBody GetEventsByTypeRequest typesRequest) {
        List<Type> types = typeService.findTypesByNamesList(typesRequest.typesNames);
        List<Event> events = eventService.getActiveByTypes(types);
        return ResponseEntity.ok(events);
    }

    /**
     * получить все ивенты по типу
     * @param typesRequest список названий типов
     * @return список ивентов
     */
    @PostMapping("/all/types")
    public ResponseEntity<List<GetEventResponse>> getAllEventsByTypes(@RequestBody GetEventsByTypeRequest typesRequest) {
        List<Type> types = typeService.findTypesByNamesList(typesRequest.typesNames);
        List<GetEventResponse> events = eventService.getAllByTypes(types);
        return ResponseEntity.ok(events);
    }

}
