package po.AgendaVirtual.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import po.AgendaVirtual.Model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer>{

	
	Page<Contacto>findByNombreContaining(String nombre,Pageable pageable);
	
}
