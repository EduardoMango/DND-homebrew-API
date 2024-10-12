package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EscuelaMagiaNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEscuelaMagiaService;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IHechizoService;
import com.fasterxml.jackson.annotation.JsonView;
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
    private final IHechizoService hechizoService;
    private final IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO>   escuelaMagiaMapper;
    private final IMapper<HechizoEntity, HechizoDTO> hechizoMapper;
    private final PagedResourcesAssembler<EscuelaMagiaDTO> pagedResourcesAssembler;
    private final PagedResourcesAssembler<HechizoDTO> pagedResourcesAssemblerHechizos;

    public EscuelaMagiaController(IEscuelaMagiaService escuelaMagiaService, IHechizoService hechizoService, IMapper<HechizoEntity, HechizoDTO> hechizoMapper, PagedResourcesAssembler<EscuelaMagiaDTO> pagedResourcesAssembler, IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO> escuelaMagiaMapper, PagedResourcesAssembler<HechizoDTO> pagedResourcesAssemblerHechizos) {
        this.escuelaMagiaService = escuelaMagiaService;
        this.hechizoService = hechizoService;
        this.hechizoMapper = hechizoMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.escuelaMagiaMapper = escuelaMagiaMapper;
        this.pagedResourcesAssemblerHechizos = pagedResourcesAssemblerHechizos;
    }

    @GetMapping
    @JsonView(Views.Public.class)
    public ResponseEntity<PagedModel<EntityModel<EscuelaMagiaDTO>>> getEscuelasMagia(
            @RequestParam(name = "nombre", required = false) String nombre_escuela,
            Pageable pageable) {

        Page<EscuelaMagiaEntity> page;
        if(nombre_escuela != null && !nombre_escuela.isEmpty()){
            page = escuelaMagiaService.findByNombreEscuela(nombre_escuela, pageable);
        } else{
            page = escuelaMagiaService.findAll(pageable);
        }

        List <EscuelaMagiaEntity> filteredList = page.getContent().stream()
                .filter(escuela -> !escuela.getEstaBorrado())
                .toList();

        Page<EscuelaMagiaEntity> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<EscuelaMagiaDTO>> pagedModel = pagedResourcesAssembler.toModel(pageFiltrada.map(escuelaMagiaMapper::mapTo));

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id_escuela}")
    @JsonView(Views.Public.class)
    public ResponseEntity<EscuelaMagiaDTO> getEscuelaMagiaById(@PathVariable("id_escuela") Long id_escuela) {
        Optional<EscuelaMagiaEntity> foundEntity = escuelaMagiaService.findById(id_escuela);

        if (foundEntity.isPresent()){
            EscuelaMagiaEntity escuelaMagiaEntity = foundEntity.get();

            if (!escuelaMagiaEntity.getEstaBorrado()){
                EscuelaMagiaDTO dto = escuelaMagiaMapper.mapTo(escuelaMagiaEntity);
                return new ResponseEntity<>(dto,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id_escuela}/hechizos")
    @JsonView(Views.Internal.class)
    public ResponseEntity<PagedModel<EntityModel<HechizoDTO>>>getHechizosByEscuelaID(@PathVariable("id_escuela") Long id_escuela, Pageable pageable){
        Optional<EscuelaMagiaEntity> foundEntity = escuelaMagiaService.findById(id_escuela);

        if (foundEntity.isPresent()){

            EscuelaMagiaEntity escuelaEncontrada = foundEntity.get();
            if(!escuelaEncontrada.getEstaBorrado())
            {
                // Obtén los hechizos paginados directamente desde la base de datos
                Page<HechizoEntity> hechizoPage = hechizoService.findHechizosByEscuelaId(id_escuela, pageable);

                // Mapea los hechizos a DTO
                Page<HechizoDTO> hechizoDTOPage = hechizoPage.map(hechizoMapper::mapTo);

                // Convierte a PagedModel
                PagedModel<EntityModel<HechizoDTO>> pagedModel = pagedResourcesAssemblerHechizos.toModel(hechizoDTOPage);

                return new ResponseEntity<>(pagedModel,HttpStatus.OK);
            }
        }
        throw new EscuelaMagiaNotFoundException(id_escuela);
    }

    @PostMapping
    public ResponseEntity<EscuelaMagiaDTO> postEscuelaMagia(@RequestBody EscuelaMagiaDTO escuela) {

        EscuelaMagiaEntity aGuardar = escuelaMagiaMapper.mapFrom(escuela);
        EscuelaMagiaEntity savedEntity = escuelaMagiaService.save(aGuardar);

            return new ResponseEntity<>(escuelaMagiaMapper.mapTo(savedEntity),HttpStatus.CREATED);
    }

    @PutMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> putEscuelaMagia(@PathVariable("id_escuela") Long id_escuela, @RequestBody EscuelaMagiaDTO escuela) {

    if (!escuelaMagiaService.isExist(id_escuela)) {
        throw new EscuelaMagiaNotFoundException(id_escuela);
    }

    escuela.setId_escuela(id_escuela);
    EscuelaMagiaEntity aActualizar = escuelaMagiaMapper.mapFrom(escuela);
    escuelaMagiaService.save(aActualizar);
    return new ResponseEntity<>(escuelaMagiaMapper.mapTo(aActualizar),HttpStatus.OK);
    }

    @PatchMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> patchEscuelaMagia(@PathVariable("id_escuela") Long id_escuela, @RequestBody EscuelaMagiaDTO escuela) {

        if (!escuelaMagiaService.isExist(id_escuela)) {
            throw new EscuelaMagiaNotFoundException(id_escuela);
        }
        EscuelaMagiaEntity escuelaMagiaEntity = escuelaMagiaMapper.mapFrom(escuela);
        EscuelaMagiaEntity escuelaMagiaActualizada = escuelaMagiaService.update(id_escuela,escuelaMagiaEntity);

        return new ResponseEntity<>(escuelaMagiaMapper.mapTo(escuelaMagiaActualizada),HttpStatus.OK);
    }

    @DeleteMapping("/{id_escuela}")
    public ResponseEntity<Void> deleteEscuelaMagia(@PathVariable("id_escuela") Long id_escuela) {
        if (escuelaMagiaService.isExist(id_escuela)) {

            Optional<EscuelaMagiaEntity> escuelaOptional = escuelaMagiaService.findById(id_escuela);

            if (escuelaOptional.isPresent()){
                EscuelaMagiaEntity escuelaMagiaEntity = escuelaOptional.get();
                escuelaMagiaEntity.setEstaBorrado(true);
                escuelaMagiaService.save(escuelaMagiaEntity);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        throw new EscuelaMagiaNotFoundException(id_escuela);
    }
}
