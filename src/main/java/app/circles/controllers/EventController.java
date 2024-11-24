package app.circles.controllers;

import app.circles.models.Event;
import app.circles.models.Type;
import app.circles.requests.getEventsByTypeRequest;
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
        + создать
        - редактировать (тут надо решить патч или пут запрос) думаю патч все-таки

        PUT method sets up the entity with the exact information provided in the request.
        In this way, the request must contain the entire entity, not only specific fields.

        the PATCH method allows the data update of particular fields of an entity.

        + удалить (если добавляем свойство IsActive то можно поменять его на false когда мероприятие заканчивается)
        пока сделала просто удалить вообще из бд, надо доделать активность/неактивность
        +- Получить все (тут фильтр думаю нужен, можно включить категорию и мб ещё по времени ограничения)
        + получить одно по айди

 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private TypeService typeService;

    @Autowired
    public EventController(EventService service, TypeService typeService) {
        this.eventService = service;
        this.typeService = typeService;
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createNewEvent(@RequestBody Event event) {
        eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEvent(@RequestParam UUID eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getById")
    public ResponseEntity<Event> getEventById(@RequestParam UUID eventId) {
        Optional<Event> event = eventService.getById(eventId);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/getByTypes")
    public ResponseEntity<List<Event>> getEventsByTypes(@RequestBody getEventsByTypeRequest typesRequest) {
        List<Type> types = typeService.findTypesByNamesList(typesRequest.getTypesNames());

        List<Event> events = eventService.getByTypes(types);
        return ResponseEntity.ok(events);
    }

}
