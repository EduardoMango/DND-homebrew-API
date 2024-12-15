package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.SubEspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Exceptions.SubEspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEspecieService;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.ISubEspecieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especies")
@Validated
public class EspecieController {

    private final IEspecieService especieService;
    private final ISubEspecieService subEspecieService;
    private final PagedResourcesAssembler<EspecieDTO> pagedResourcesAssembler;
    private final PagedResourcesAssembler<SubEspecieDTO> pagedResourcesAssemblerSubEspecie;

    public EspecieController(IEspecieService especieService, ISubEspecieService subEspecieService,PagedResourcesAssembler<EspecieDTO> pagedResourcesAssembler, PagedResourcesAssembler<SubEspecieDTO> pagedResourcesAssemblerSubEspecie) {
        this.especieService = especieService;
        this.subEspecieService = subEspecieService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pagedResourcesAssemblerSubEspecie = pagedResourcesAssemblerSubEspecie;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<EspecieDTO>>> getEspecies(
            @RequestParam(name = "nombreEspecie", required = false) String nombreEspecie, Pageable pageable) {

        Page<EspecieDTO> page = especieService.findAll(pageable);
        List<EspecieDTO> filteredList = page.getContent().stream()
                .filter(especie -> nombreEspecie == null ||  nombreEspecie.isEmpty() || especie.getNombreEspecie().toLowerCase().contains(nombreEspecie.toLowerCase()))
                .toList();
        Page<EspecieDTO> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<EspecieDTO>> pagedModel = pagedResourcesAssembler.toModel(pageFiltrada);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieDTO> getEspecieById(@PathVariable Long id) throws EspecieNotFoundException {

        EspecieDTO found = especieService.findById(id);

        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}/subespecies")
    public ResponseEntity<PagedModel<EntityModel<SubEspecieDTO>>> getSubespecies(
            @PathVariable Long id, Pageable pageable,
            @RequestParam(name = "nombreSubEspecie", required = false) String nombreSubEspecie) {

        EspecieDTO especie = especieService.findById(id);

        Page<SubEspecieDTO> page = subEspecieService.findByEspecie(especie, pageable);

        List<SubEspecieDTO> filteredList = page.getContent().stream()
                .filter(subEspecieEntity -> nombreSubEspecie == null ||  nombreSubEspecie.isEmpty() || subEspecieEntity.getNombreSubespecie().toLowerCase().contains(nombreSubEspecie.toLowerCase()))
                .toList();

        Page<SubEspecieDTO> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<SubEspecieDTO>> pagedModel = pagedResourcesAssemblerSubEspecie.toModel(pageFiltrada);
        return ResponseEntity.ok(pagedModel);
    }


    @PostMapping
    public ResponseEntity<EspecieDTO> postEspecie(@RequestBody @Valid EspecieDTO especieDTO) {
        EspecieDTO saved = especieService.save(especieDTO);
        return ResponseEntity.ok(saved);
    }

    @PostMapping({"/{id}/subespecies"})
    public ResponseEntity<SubEspecieDTO> postSubEspecie(@PathVariable Long id, @RequestBody @Valid SubEspecieDTO subEspecieDTO) {

        EspecieDTO especie = especieService.findById(id);

        SubEspecieDTO saved = subEspecieService.save(especie,subEspecieDTO);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieDTO> putEspecie(@PathVariable Long id, @RequestBody @Valid EspecieDTO especieDTO) throws EspecieNotFoundException {

        if (!especieService.isExist(id)) {
            throw new EspecieNotFoundException(id);
        }
        especieDTO.setIdEspecie(id);
        EspecieDTO saved = especieService.save(especieDTO);
        return ResponseEntity.ok(saved);
    }

    @PutMapping({"/{id}/subespecies/{id_subespecie}"})
    public ResponseEntity<SubEspecieDTO> putSubEspecie(
            @PathVariable Long id,
            @PathVariable Long id_subespecie,
            @RequestBody @Valid SubEspecieDTO subEspecieDTO) throws SubEspecieNotFoundException {

        EspecieDTO especie = especieService.findById(id);
        //Checks if the given subEspecie exists
        if (!subEspecieService.isExist(id_subespecie)) {
            throw new SubEspecieNotFoundException(id_subespecie);
        }

        subEspecieDTO.setIdSubespecie(id_subespecie);

        SubEspecieDTO saved = subEspecieService.save(especie, subEspecieDTO);
        return ResponseEntity.ok(saved);
    }

  @PatchMapping("/{id}")
  public ResponseEntity<EspecieDTO> patchEspecie(
      @PathVariable Long id, @RequestBody @Valid EspecieDTO especieDTO) throws EspecieNotFoundException {

    EspecieDTO updated = especieService.update(id, especieDTO);
    return ResponseEntity.ok(updated);
    }

    @PatchMapping({"/{id}/subespecies/{id_subespecie}"})
    public ResponseEntity<SubEspecieDTO> patchSubEspecie(
            @PathVariable Long id,
            @PathVariable Long id_subespecie,
            @RequestBody  SubEspecieDTO subEspecieDTO) throws SubEspecieNotFoundException {

        EspecieDTO especie = especieService.findById(id);
        //Checks if the given subEspecie exists
        if (!subEspecieService.isExist(id_subespecie)) {
            throw new SubEspecieNotFoundException(id_subespecie);
        }
        subEspecieDTO.setIdSubespecie(id_subespecie);
        SubEspecieDTO updated = subEspecieService.update(id_subespecie, subEspecieDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecie(@PathVariable Long id) throws EspecieNotFoundException {

        especieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping({"/{id}/subespecies/{id_subespecie}"})
    public ResponseEntity<Void> deleteSubEspecie(@PathVariable Long id,
                                                 @PathVariable Long id_subespecie) throws SubEspecieNotFoundException {

        if (!especieService.isExist(id)) {
            throw new EspecieNotFoundException(id);
        }
        subEspecieService.delete(id_subespecie);
        return ResponseEntity.noContent().build();
    }


}

