package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EscuelaMagiaDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EscuelaMagiaNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEscuelaMagiaService;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IHechizoService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escuelasmagia")
@Validated
public class EscuelaMagiaController {


    private final IEscuelaMagiaService escuelaMagiaService;
    private final IHechizoService hechizoService;
    private final PagedResourcesAssembler<EscuelaMagiaDTO> pagedResourcesAssembler;
    private final PagedResourcesAssembler<HechizoDTO> pagedResourcesAssemblerHechizos;

    public EscuelaMagiaController(IEscuelaMagiaService escuelaMagiaService, IHechizoService hechizoService,PagedResourcesAssembler<EscuelaMagiaDTO> pagedResourcesAssembler, PagedResourcesAssembler<HechizoDTO> pagedResourcesAssemblerHechizos) {
        this.escuelaMagiaService = escuelaMagiaService;
        this.hechizoService = hechizoService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pagedResourcesAssemblerHechizos = pagedResourcesAssemblerHechizos;
    }

    @GetMapping
    @JsonView(Views.Public.class)
    public ResponseEntity<PagedModel<EntityModel<EscuelaMagiaDTO>>> getEscuelasMagia(
            @RequestParam(name = "nombreEscuela", required = false) String nombreEscuela,
            Pageable pageable) {

        Page<EscuelaMagiaDTO> page = escuelaMagiaService.findAll(pageable);


        List <EscuelaMagiaDTO> filteredList = page.getContent().stream()
                .filter(escuela -> nombreEscuela == null ||  nombreEscuela.isEmpty() || escuela.getNombreEscuela().toLowerCase().contains(nombreEscuela.toLowerCase()))
                .toList();

        Page<EscuelaMagiaDTO> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<EscuelaMagiaDTO>> pagedModel = pagedResourcesAssembler.toModel(pageFiltrada);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id_escuela}")
    @JsonView(Views.Public.class)
    public ResponseEntity<EscuelaMagiaDTO> getEscuelaMagiaById(@PathVariable("id_escuela") Long id_escuela) {
        EscuelaMagiaDTO found = escuelaMagiaService.findById(id_escuela);
                return new ResponseEntity<>(found,HttpStatus.OK);
    }

    @GetMapping("/{id_escuela}/hechizos")
    @JsonView(Views.Internal.class)
    public ResponseEntity<PagedModel<EntityModel<HechizoDTO>>>getHechizosByEscuelaID(@PathVariable("id_escuela") Long id_escuela, Pageable pageable){
        if(escuelaMagiaService.isExist(id_escuela))
        {
            // Obtén los hechizos paginados directamente desde la base de datos
            Page<HechizoDTO> hechizoPage = hechizoService.findHechizosByEscuelaId(id_escuela, pageable);
            // Convierte a PagedModel
            PagedModel<EntityModel<HechizoDTO>> pagedModel = pagedResourcesAssemblerHechizos.toModel(hechizoPage);

            return new ResponseEntity<>(pagedModel,HttpStatus.OK);
        }
        throw new EscuelaMagiaNotFoundException(id_escuela);
    }

    @PostMapping
    public ResponseEntity<EscuelaMagiaDTO> postEscuelaMagia(@RequestBody @Valid EscuelaMagiaDTO escuela) {
        EscuelaMagiaDTO saved = escuelaMagiaService.save(escuela);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @PutMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> putEscuelaMagia(@PathVariable("id_escuela") Long id_escuela,
                                                           @RequestBody @Valid EscuelaMagiaDTO escuela) {

    if (!escuelaMagiaService.isExist(id_escuela)) {
        throw new EscuelaMagiaNotFoundException(id_escuela);
    }

    escuela.setId_escuela(id_escuela);
    EscuelaMagiaDTO updated = escuelaMagiaService.save(escuela);
    return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @PatchMapping("/{id_escuela}")
    public ResponseEntity<EscuelaMagiaDTO> patchEscuelaMagia(@PathVariable("id_escuela") Long id_escuela,
                                                             @RequestBody EscuelaMagiaDTO escuela) {

        if (!escuelaMagiaService.isExist(id_escuela)) {
            throw new EscuelaMagiaNotFoundException(id_escuela);
        }

        EscuelaMagiaDTO updated = escuelaMagiaService.update(id_escuela,escuela);

        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @DeleteMapping("/{id_escuela}")
    public ResponseEntity<Void> deleteEscuelaMagia(@PathVariable("id_escuela") Long id_escuela) {

        escuelaMagiaService.delete(id_escuela);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
