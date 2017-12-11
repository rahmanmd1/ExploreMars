package org.example.em.ExploreMars.repo;


import org.example.em.ExploreMars.model.TourPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel="packages",path="packages")
public interface TourPackageRepo extends CrudRepository<TourPackage, String> {
	
	
	@Override
	@RestResource(exported=false)
	void delete(Iterable<? extends TourPackage> arg0);

	@Override
	@RestResource(exported=false)
	void delete(String arg0) ;

	@Override
	@RestResource(exported=false)
	void delete(TourPackage arg0);

	@Override
	@RestResource(exported=false)
	void deleteAll() ;

	@Override
	@RestResource(exported=false)
	<S extends TourPackage> Iterable<S> save(Iterable<S> arg0);

	@Override
	@RestResource(exported=false)
	<S extends TourPackage> S save(S arg0);
	TourPackage findByName(@Param("name")String name);
}
