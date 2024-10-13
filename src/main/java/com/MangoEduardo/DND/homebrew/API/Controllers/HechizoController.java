package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Models.DamageTypes;
import com.MangoEduardo.DND.homebrew.API.Exceptions.HechizoNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IHechizoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/hechizos")
public class HechizoController {

    private final IHechizoService hechizoService;
    private final PagedResourcesAssembler<HechizoDTO> pagedResourcesAssembler;
    private final IMapper<HechizoEntity, HechizoDTO> hechizoMapper;

    public HechizoController(IHechizoService hechizoService, PagedResourcesAssembler<HechizoDTO> pagedResourcesAssembler, IMapper<HechizoEntity, HechizoDTO> hechizoMapper) {
        this.hechizoService = hechizoService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.hechizoMapper =hechizoMapper;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<HechizoDTO>>> getHechizos(
            @RequestParam(name = "nombreHechizo", required = false) String nombreHechizo,
            @RequestParam(name = "nivelHechizo", required = false) Integer nivelHechizo,
            @RequestParam(name = "damageType", required = false) String damageType,
            @RequestParam(name = "concentracion", required = false) Optional<Boolean> concentracion,
            @RequestParam(name = "esAtaque", required = false) Optional<Boolean> esAtaque,
            @RequestParam(name = "esRitual", required = false) Optional<Boolean> esRitual,
            @RequestParam(name = "tiradaSalvacion", required = false) Optional<Boolean> tiradaSalvacion,
            Pageable pageable) {

        Page<HechizoEntity> page = hechizoService.findAll(pageable);


        // Filtrar los hechizos eliminados (getEstaBorrado() == false)
        // y filtra por los demas params, para permitir multiples filtrados
        List<HechizoEntity> filteredList = page.getContent().stream()
                .filter(hechizo -> !hechizo.getEstaBorrado())
                .filter(hechizo -> nombreHechizo == null || nombreHechizo.isEmpty() || hechizo.getNombreHechizo().toLowerCase().contains(nombreHechizo.toLowerCase()))
                .filter(hechizo -> nivelHechizo == null || Objects.equals(hechizo.getNivelHechizo(), nivelHechizo))
                .filter(hechizo -> damageType == null || damageType.isEmpty() || hechizo.getDamageTypes().toString().toLowerCase().contains(damageType.toLowerCase()))
                .filter(hechizo -> concentracion.isEmpty() || hechizo.isConcentracion() == concentracion.get())
                .filter(hechizo -> esAtaque.isEmpty() || hechizo.isConcentracion() == esAtaque.get())
                .filter(hechizo -> esRitual.isEmpty() || hechizo.isConcentracion() == esRitual.get())
                .filter(hechizo -> tiradaSalvacion.isEmpty() || hechizo.isConcentracion() == tiradaSalvacion.get())
                .toList();

        Page<HechizoEntity> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<HechizoDTO>> pagedModel = pagedResourcesAssembler.toModel(pageFiltrada.map(hechizoMapper::mapTo));
        return ResponseEntity.ok(pagedModel);
    }


    @GetMapping("/{id_hechizo}")
    public ResponseEntity<HechizoDTO> getHechizoById(@PathVariable("id_hechizo") Long id_hechizo) {
        Optional<HechizoEntity> foundEntity = hechizoService.findById(id_hechizo);

        if (foundEntity.isPresent()) {
            HechizoEntity hechizoEntity = foundEntity.get();

            // Verifica si está borrado
            if (!hechizoEntity.getEstaBorrado()) {
                // Si no está borrado, convierte a DTO y devuelve
                HechizoDTO hechizoDTO = hechizoMapper.mapTo(hechizoEntity);
                return new ResponseEntity<>(hechizoDTO, HttpStatus.OK);
            }
        }

     //Si no se encuentra el hechizo o está borrado, devuelve 404
        throw new HechizoNotFoundException(id_hechizo);
    }

    @PostMapping
    public ResponseEntity<HechizoDTO> postHechizo(@RequestBody HechizoDTO hechizoDTO){


        HechizoEntity aGuardar = hechizoMapper.mapFrom(hechizoDTO);
        HechizoEntity saved = hechizoService.save(aGuardar);

        return new ResponseEntity<>(hechizoMapper.mapTo(saved),HttpStatus.CREATED);
    }

    @PutMapping("/{id_hechizo}")
    public ResponseEntity<HechizoDTO> putHechizo(@PathVariable Long id_hechizo, @RequestBody HechizoDTO hechizoDTO){
        if(!hechizoService.isExist(id_hechizo)){
            throw new HechizoNotFoundException(id_hechizo);
        }
        hechizoDTO.setId_hechizo(id_hechizo);

        HechizoEntity aGuardar = hechizoMapper.mapFrom(hechizoDTO);
        HechizoEntity saved = hechizoService.save(aGuardar);

        return new ResponseEntity<>(hechizoMapper.mapTo(saved),HttpStatus.OK);
    }

    @PatchMapping("/{id_hechizo}")
    public ResponseEntity<HechizoDTO> patchHechizo(@PathVariable("id_hechizo") Long id_hechizo, @RequestBody HechizoDTO hechizo) {
        if (!hechizoService.isExist(id_hechizo)) {
            throw new HechizoNotFoundException(id_hechizo);
        }
        HechizoEntity hechizoActualizada = hechizoService.update(id_hechizo,hechizoMapper.mapFrom(hechizo));
        return new ResponseEntity<>(hechizoMapper.mapTo(hechizoActualizada),HttpStatus.OK);
    }

    @DeleteMapping("/{id_hechizo}")
    public ResponseEntity<Void> deleteHechizo(@PathVariable("id_hechizo") Long id_hechizo) {
        if (!hechizoService.isExist(id_hechizo)) {
            throw new HechizoNotFoundException(id_hechizo);
        }
        Optional<HechizoEntity> hechizo = hechizoService.findById(id_hechizo);

        if (hechizo.isPresent()){
            hechizo.get().setEstaBorrado(true);
            hechizoService.save(hechizo.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
