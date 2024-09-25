package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEscuelaMagiaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/escuelasmagia")
public class EscuelaMagiaController {


    private final IEscuelaMagiaService escuelaMagiaService;
    private final IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO>   escuelaMagiaMapper;
    private final PagedResourcesAssembler<EscuelaMagiaDTO> pagedResourcesAssembler;
    private final PagedResourcesAssembler<HechizoDTO> pagedResourcesAssemblerHechizos;

    public EscuelaMagiaController(IEscuelaMagiaService escuelaMagiaService, PagedResourcesAssembler<EscuelaMagiaDTO> pagedResourcesAssembler, IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO> escuelaMagiaMapper, PagedResourcesAssembler<HechizoDTO> pagedResourcesAssemblerHechizos) {
        this.escuelaMagiaService = escuelaMagiaService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.escuelaMagiaMapper = escuelaMagiaMapper;
        this.pagedResourcesAssemblerHechizos = pagedResourcesAssemblerHechizos;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<EscuelaMagiaDTO>>> getEscuelasMagia(Pageable pageable) {
        Page<EscuelaMagiaEntity> page = escuelaMagiaService.findAll(pageable);
        PagedModel<EntityModel<EscuelaMagiaDTO>> pagedModel = pagedResourcesAssembler.toModel(page.map(escuelaMagiaMapper::mapTo));
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> getEscuelaMagiaById(@PathVariable("id_escuela") Integer id_escuela) {
        Optional<EscuelaMagiaEntity> foundEntity = escuelaMagiaService.findById(id_escuela);

        return foundEntity.map(escuelaMagiaEntity -> { //Utiliza un lambda.
            EscuelaMagiaDTO escuelaMagiaDTO = escuelaMagiaMapper.mapTo(escuelaMagiaEntity); //Pasa el autor obtenido a un autor Dto. Si no se puede porque es empty, devuelve el response "NOT FOUND"
            return new ResponseEntity<>(escuelaMagiaDTO,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id_escuela}/hechizos")
    public ResponseEntity<PagedModel<EntityModel<HechizoDTO>>>getHechizosByEscuelaID(@PathVariable("id_escuela") Integer id_escuela, Pageable pageable){
        Optional<EscuelaMagiaEntity> foundEntity = escuelaMagiaService.findById(id_escuela);

        if (foundEntity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EscuelaMagiaDTO foundDTO = escuelaMagiaMapper.mapTo(foundEntity.get());
        List<HechizoDTO> hechizos = foundDTO.getHechizos();

        // Aplica paginación a la lista de hechizos usando el Pageable
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hechizos.size());
        Page<HechizoDTO> page = new PageImpl<>(hechizos.subList(start, end), pageable, hechizos.size());

        PagedModel<EntityModel<HechizoDTO>> pagedModel = pagedResourcesAssemblerHechizos.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping
    public ResponseEntity<EscuelaMagiaDTO> postEscuelaMagia(@RequestBody EscuelaMagiaDTO escuela) {

        EscuelaMagiaEntity aGuardar = escuelaMagiaMapper.mapFrom(escuela);
        EscuelaMagiaEntity savedEntity = escuelaMagiaService.save(aGuardar);

            return new ResponseEntity<>(escuelaMagiaMapper.mapTo(savedEntity),HttpStatus.CREATED);
    }

    @PutMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> putEscuelaMagia(@PathVariable("id_escuela") Integer id_escuela, @RequestBody EscuelaMagiaDTO escuela) {

    if (!escuelaMagiaService.isExist(id_escuela)) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    escuela.setId_escuela(id_escuela);
    EscuelaMagiaEntity aActualizar = escuelaMagiaMapper.mapFrom(escuela);
    escuelaMagiaService.save(aActualizar);
    return new ResponseEntity<>(escuelaMagiaMapper.mapTo(aActualizar),HttpStatus.OK);
    }

    @PatchMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> patchEscuelaMagia(@PathVariable("id_escuela") Integer id_escuela, @RequestBody EscuelaMagiaDTO escuela) {

        if (!escuelaMagiaService.isExist(id_escuela)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EscuelaMagiaEntity escuelaMagiaEntity = escuelaMagiaMapper.mapFrom(escuela);
        EscuelaMagiaEntity escuelaMagiaActualizada = escuelaMagiaService.update(id_escuela,escuelaMagiaEntity);

        return new ResponseEntity<>(escuelaMagiaMapper.mapTo(escuelaMagiaActualizada),HttpStatus.OK);
    }

    @DeleteMapping("/{id_escuela}")
    public ResponseEntity<Void> deleteEscuelaMagia(@PathVariable("id_escuela") Integer id_escuela) {
        if (!escuelaMagiaService.isExist(id_escuela)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        escuelaMagiaService.delete(id_escuela);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
