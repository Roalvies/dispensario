package com.roalvies.SpringBootCloud.msvcweb.repositories;

import com.roalvies.SpringBootCloud.msvcweb.models.entity.Web;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WebRepository extends CrudRepository<Web, Long>{

    //Cada ves que se realice una modificacion delete inser es necesario el modifying
    @Modifying
    @Query("delete from LoginWeb rl where rl.rolUser=?1")
    void eliminarRolLoginPorId(Long id);
}
