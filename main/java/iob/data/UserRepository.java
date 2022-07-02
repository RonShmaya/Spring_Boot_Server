package iob.data;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String>{
	
	public Optional<UserEntity> findByPk (String pk);

}
