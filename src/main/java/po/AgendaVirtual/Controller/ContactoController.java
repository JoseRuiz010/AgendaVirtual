package po.AgendaVirtual.Controller;

 import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import po.AgendaVirtual.Model.Contacto;
import po.AgendaVirtual.Repository.ContactoRepository;

@Controller
@RequestMapping("contacto")
public class ContactoController {
	
	@Autowired
	private ContactoRepository contactoRepository;
   
	@GetMapping("/")
	public String index(Pageable pageable ,Model model, @RequestParam(required = false) String busqueda) {
		Page<Contacto>contactoPage;
		if(busqueda!= null && busqueda.trim().length()>0) {
			contactoPage=contactoRepository.findByNombreContaining(busqueda,pageable);
		}else {	
			contactoPage=contactoRepository.findAll(pageable);
		}
		model.addAttribute("contactoPage",contactoPage);
 		return "index";
	}
	  
	@GetMapping("/nuevo")
	public String nuevoContacto(Model model) {
 		model.addAttribute("titulo","Nuevo Contacto");
 		model.addAttribute("contacto", new Contacto());
 		return "nuevo";
	}
	
	@PostMapping("/nuevo")
	public String crearContacto(@Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes ra, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("titulo","Nuevo Contacto");
			model.addAttribute("contacto", contacto);
			return "nuevo";
		}
		
		contacto.setFechaRegistro(LocalDate.now());
 		 contactoRepository.save(contacto);
 		 ra.addFlashAttribute("msgExito","El Contacto se ha creado correctamente");
 		return "redirect:/contacto/";
	}
	@GetMapping("/{id}/editar")
	public String editarContacto(@PathVariable Integer id,  Model model) {
 		model.addAttribute("titulo","Editar Contacto");
 		Contacto contacto= contactoRepository.findById(id).get();
 		model.addAttribute("contacto", contacto);
 		return "nuevo";
	}
	
	@PostMapping("/{id}/editar")
	public String editar(
			@PathVariable Integer id,
			@Validated Contacto contacto, 
			BindingResult bindingResult, 
			RedirectAttributes ra, 
			Model model) {
				
		if(bindingResult.hasErrors()) { 
			model.addAttribute("titulo","Editar Contacto");
			model.addAttribute("contacto", contacto);
			return "nuevo";
		}
		Contacto contactoEditar= contactoRepository.findById(id).get();
		contactoEditar.setNombre(contacto.getNombre());
		contactoEditar.setCelular(contacto.getCelular());
		contactoEditar.setEmail(contacto.getEmail());
		contactoEditar.setFechaNacimiento(contacto.getFechaNacimiento());
		contactoEditar.setFechaRegistro(LocalDate.now());
		
	 	 contactoRepository.save(contactoEditar);
 		 ra.addFlashAttribute("msgExito","El Contacto se ha actualizado correctamente");
 		return "redirect:/contacto/";
	}
	
	@PostMapping("/{id}/eliminar")
	public String eliminar(@PathVariable Integer id, 
			RedirectAttributes ra, Model model) {
		
		Contacto contacto = contactoRepository.findById(id).get();
		contactoRepository.delete(contacto);
		
 		 ra.addFlashAttribute("msgExito","El Contacto se ha eliminado correctamente");
 		return "redirect:/contacto/";
	}
	
}
