package app.circles.controllers;

import app.circles.mappers.CreateEventReqToEvent;
import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.requests.CreateEventRequest;
import app.circles.requests.GetEventsByTypeRequest;
import app.circles.services.EventService;
import app.circles.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*Мероприятия(в первую очередь):

        редактировать (тут надо решить патч или пут запрос) думаю патч все-таки

        PUT method sets up the entity with the exact information provided in the request.
        In this way, the request must contain the entire entity, not only specific fields.

        the PATCH method allows the data update of particular fields of an entity.
 */

@CrossOrigin(origins = "*", maxAge = 3600)
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
     * получить ивент по id
     * @param eventId id ивента
     * @return ивент
     */
    @GetMapping("/getById")
    public ResponseEntity<Event> getEventById(@RequestParam UUID eventId) {
        Optional<Event> event = eventService.getById(eventId); //TODO: МЫ ТУТ СЛИШКОМ МНОГО ИНФОРМАЦИИ О ЮЗЕРЕ ОТДАЕМ
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * получить все ивенты
     * @return список ивентов
     */
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAll();
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
    public ResponseEntity<List<Event>> getAllEventsByTypes(@RequestBody GetEventsByTypeRequest typesRequest) {
        List<Type> types = typeService.findTypesByNamesList(typesRequest.typesNames);
        List<Event> events = eventService.getAllByTypes(types);
        return ResponseEntity.ok(events);
    }

}
