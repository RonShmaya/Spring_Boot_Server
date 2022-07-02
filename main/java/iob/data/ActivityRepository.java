package iob.data;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<ActivityEntity, String>{

}
