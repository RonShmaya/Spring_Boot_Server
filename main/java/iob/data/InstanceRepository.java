package iob.data;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;



public interface InstanceRepository extends MongoRepository<InstanceEntity, String>{

	public List<InstanceEntity> findAllByName (
			@Param("name") String name,
			Pageable pageable);

	public List<InstanceEntity> findAllByType(
			@Param("type") String type,
			Pageable pageable);
	
	public List<InstanceEntity> findAllByLatBetweenAndLngBetween(
			@Param("latMin") Double latMin,
			@Param("latMax") Double latMax,
			@Param("lngMin") Double lngMin,
			@Param("lngMax") Double lngMax,
			Pageable pageable);
	
	public List<InstanceEntity> findAllByActive(
			@Param("active") Boolean active,
			Pageable pageable);
	
	public List<InstanceEntity> findAllByNameAndActive (
			@Param("name") String name,
			@Param("active") Boolean active,
			Pageable pageable);
	
	public List<InstanceEntity> findAllByTypeAndActive(
			@Param("type") String type,
			@Param("active") Boolean active,
			Pageable pageable);
	
	public List<InstanceEntity> findAllByLatBetweenAndLngBetweenAndActive(
			@Param("latMin") Double latMin,
			@Param("latMax") Double latMax,
			@Param("lngMin") Double lngMin,
			@Param("lngMax") Double lngMax,
			@Param("active") Boolean active,
			Pageable pageable);
	
	

	
}
