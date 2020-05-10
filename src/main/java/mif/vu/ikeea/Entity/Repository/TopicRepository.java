package mif.vu.ikeea.Entity.Repository;

import mif.vu.ikeea.Entity.LearningDay;
import mif.vu.ikeea.Entity.Topic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long>, JpaSpecificationExecutor<LearningDay>{
    Iterable<Topic> findAllByLearningDays(LearningDay learningDays);
}
