package mif.vu.ikeea.Controller;

import mif.vu.ikeea.Entity.LearningDay;
import mif.vu.ikeea.Entity.Repository.LearningDayRepository;
import mif.vu.ikeea.Manager.LearningDayManager;
import mif.vu.ikeea.Payload.LearningDayRequest;
import mif.vu.ikeea.Payload.UpdateLearningDayRequest;
import mif.vu.ikeea.RepositoryService.LearningDayService;
import mif.vu.ikeea.Responses.ApiResponse;
import mif.vu.ikeea.Responses.LearningDayResponse;
import mif.vu.ikeea.Specifications.LearningDaySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/api/learning-day")
public class LearningDayController {

    @Autowired
    private LearningDayManager learningDayManager;

    @Autowired
    private LearningDayService learningDayService;

    @Autowired
    private LearningDayRepository learningDayRepository;

    @PostMapping("/add")
    public ResponseEntity<?> createLearningDay(@Valid @RequestBody LearningDayRequest learningDayRequest) {
        learningDayManager.create(learningDayRequest);

        return ResponseEntity.ok(new ApiResponse(true, "Learning day added successfully"));
    }

    @GetMapping(path = "/{managerId}/list")
    public List<LearningDayResponse> getLearningDaysListSearch(@PathVariable Long managerId,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date date,
                                                               @RequestParam(required = false) Long userId, @RequestParam(required = false) Long topicId){

        List<LearningDay> learningDays = learningDayRepository.findAll(Specification.where(LearningDaySpecification.withManager(managerId))
                .and(Specification.where(LearningDaySpecification.withDate(date)))
                .and(Specification.where(LearningDaySpecification.withTopic(topicId)))
                .and(Specification.where(LearningDaySpecification.withUser(userId))));
        List<LearningDayResponse> learningDayResponses = new ArrayList<>();

        for (LearningDay learningDay : learningDays) {
            learningDayResponses.add(new LearningDayResponse(learningDay));
        }

        return learningDayResponses;
    }

    @GetMapping(path = "/{userId}/user-list")
    public @ResponseBody List<LearningDayResponse> getUserLearningDaysList(@PathVariable Long userId) {
        List<LearningDay> learningDays = learningDayService.getAllByUserId(userId);
        List<LearningDayResponse> learningDayResponses = new ArrayList<>();

        for (LearningDay learningDay : learningDays) {
            learningDayResponses.add(new LearningDayResponse(learningDay));
        }

        return learningDayResponses;
    }

    @DeleteMapping(path = "/{id}/delete")
    public @ResponseBody ResponseEntity delete(@PathVariable Long id) {
        learningDayService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Learning day deleted successfully"));
    }

    @PutMapping(path = "/{id}/update")
    public @ResponseBody ResponseEntity updateLearningDay(@PathVariable Long id, @RequestBody UpdateLearningDayRequest updateLearningDayRequest) {
        LearningDay learningDay = learningDayService.loadById(id);
        learningDayManager.update(learningDay, updateLearningDayRequest);

        return ResponseEntity.ok(new ApiResponse(true, "Learning day updated successfully"));
    }

    @GetMapping(path = "/{id}/get")
    public @ResponseBody
    LearningDayResponse get(@PathVariable Long id){
        LearningDay learningDay = learningDayService.loadById(id);

        return new LearningDayResponse(learningDay);
    }
}
