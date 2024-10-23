package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.SubEspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.SubEspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Exceptions.SubEspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
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
import java.util.Optional;

@RestController
@RequestMapping("/especies")
@Validated
public class EspecieController {

    private final IEspecieService especieService;
    private final ISubEspecieService subEspecieService;
    private final IMapper<EspecieEntity, EspecieDTO> especieMapper;
    private final IMapper<SubEspecieEntity, SubEspecieDTO> subEspecieMapper;
    private final PagedResourcesAssembler<EspecieDTO> pagedResourcesAssembler;
    private final PagedResourcesAssembler<SubEspecieDTO> pagedResourcesAssemblerSubEspecie;

    public EspecieController(IEspecieService especieService, ISubEspecieService subEspecieService, IMapper<EspecieEntity, EspecieDTO> especieMapper, IMapper<SubEspecieEntity, SubEspecieDTO> subEspecieMapper, PagedResourcesAssembler<EspecieDTO> pagedResourcesAssembler, PagedResourcesAssembler<SubEspecieDTO> pagedResourcesAssemblerSubEspecie) {
        this.especieService = especieService;
        this.subEspecieService = subEspecieService;
        this.especieMapper = especieMapper;
        this.subEspecieMapper = subEspecieMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pagedResourcesAssemblerSubEspecie = pagedResourcesAssemblerSubEspecie;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<EspecieDTO>>> getEspecies(
            @RequestParam(name = "nombreEspecie", required = false) String nombreEspecie, Pageable pageable) {

        Page<EspecieEntity> page = especieService.findAll(pageable);
        List<EspecieEntity> filteredList = page.getContent().stream()
                .filter(especie -> nombreEspecie == null ||  nombreEspecie.isEmpty() || especie.getNombreEspecie().toLowerCase().contains(nombreEspecie.toLowerCase()))
                .filter(especie -> especie.getEstaBorrado()!= null &&!especie.getEstaBorrado())
                .toList();
        Page<EspecieEntity> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<EspecieDTO>> pagedModel = pagedResourcesAssembler.toModel(pageFiltrada.map(especieMapper::mapTo));
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieDTO> getEspecieById(@PathVariable Long id) throws EspecieNotFoundException {

        Optional<EspecieEntity> especie = especieService.findById(id);
        if (especie.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }
        return ResponseEntity.ok(especieMapper.mapTo(especie.get()));
    }

    @GetMapping("/{id}/subespecies")
    public ResponseEntity<PagedModel<EntityModel<SubEspecieDTO>>> getSubespecies(
            @PathVariable Long id, Pageable pageable,
            @RequestParam(name = "nombreSubEspecie", required = false) String nombreSubEspecie) {

        Optional<EspecieEntity> especie = especieService.findById(id);
        if (especie.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }

        Page<SubEspecieEntity> page = subEspecieService.findByEspecie(especie.get(), pageable);

        List<SubEspecieEntity> filteredList = page.getContent().stream()
                .filter(subEspecieEntity -> subEspecieEntity.getEstaBorrado()!= null &&!subEspecieEntity.getEstaBorrado())
                .filter(subEspecieEntity -> nombreSubEspecie == null ||  nombreSubEspecie.isEmpty() || subEspecieEntity.getNombreSubespecie().toLowerCase().contains(nombreSubEspecie.toLowerCase()))
                .toList();

        Page<SubEspecieEntity> pageFiltrada = new PageImpl<>(filteredList, pageable, page.getTotalElements());
        PagedModel<EntityModel<SubEspecieDTO>> pagedModel = pagedResourcesAssemblerSubEspecie.toModel(pageFiltrada.map(subEspecieMapper::mapTo));
        return ResponseEntity.ok(pagedModel);
    }


    @PostMapping
    public ResponseEntity<EspecieDTO> postEspecie(@RequestBody @Valid EspecieDTO especieDTO) {
        EspecieEntity aGuardar = especieMapper.mapFrom(especieDTO);
        EspecieEntity saved = especieService.save(aGuardar);
        return ResponseEntity.ok(especieMapper.mapTo(saved));
    }

    @PostMapping({"/{id}/subespecies"})
    public ResponseEntity<SubEspecieDTO> postSubEspecie(@PathVariable Long id, @RequestBody @Valid SubEspecieDTO subEspecieDTO) {
        Optional<EspecieEntity> especie = especieService.findById(id);
        if (especie.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }

        SubEspecieEntity toSave = subEspecieMapper.mapFrom(subEspecieDTO);
        toSave.setEspecie(especie.get());

        SubEspecieEntity saved = subEspecieService.save(toSave);
        return ResponseEntity.ok(subEspecieMapper.mapTo(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieDTO> putEspecie(@PathVariable Long id, @RequestBody @Valid EspecieDTO especieDTO) throws EspecieNotFoundException {
        if (!especieService.isExist(id)) {
            throw new EspecieNotFoundException(id);
        }
        especieDTO.setIdEspecie(id);
        EspecieEntity aGuardar = especieMapper.mapFrom(especieDTO);
        EspecieEntity saved = especieService.save(aGuardar);
        return ResponseEntity.ok(especieMapper.mapTo(saved));
    }

    @PutMapping({"/{id}/subespecies/{id_subespecie}"})
    public ResponseEntity<SubEspecieDTO> putSubEspecie(
            @PathVariable Long id,
            @PathVariable Long id_subespecie,
            @RequestBody @Valid SubEspecieDTO subEspecieDTO) throws SubEspecieNotFoundException {

        Optional<EspecieEntity> especie = especieService.findById(id);
        if (especie.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }
        Optional<SubEspecieEntity> subEspecie = subEspecieService.findById(id_subespecie);
        if (subEspecie.isEmpty()) {
            throw new SubEspecieNotFoundException(id_subespecie);
        }
        subEspecieDTO.setIdSubespecie(id_subespecie);
        SubEspecieEntity aGuardar = subEspecieMapper.mapFrom(subEspecieDTO);
        aGuardar.setEspecie(especie.get());
        SubEspecieEntity saved = subEspecieService.save(aGuardar);
        return ResponseEntity.ok(subEspecieMapper.mapTo(saved));
    }

  @PatchMapping("/{id}")
  public ResponseEntity<EspecieDTO> patchEspecie(
      @PathVariable Long id, @RequestBody @Valid EspecieDTO especieDTO) throws EspecieNotFoundException {
    if (!especieService.isExist(id)) {
      throw new EspecieNotFoundException(id);
    }
    especieDTO.setIdEspecie(id);
    EspecieEntity updated = especieService.update(id, especieMapper.mapFrom(especieDTO));

    return ResponseEntity.ok(especieMapper.mapTo(updated));
    }

    @PatchMapping({"/{id}/subespecies/{id_subespecie}"})
    public ResponseEntity<SubEspecieDTO> patchSubEspecie(
            @PathVariable Long id,
            @PathVariable Long id_subespecie,
            @RequestBody @Valid SubEspecieDTO subEspecieDTO) throws SubEspecieNotFoundException {

        Optional<EspecieEntity> especie = especieService.findById(id);
        if (especie.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }
        Optional<SubEspecieEntity> subEspecie = subEspecieService.findById(id_subespecie);
        if (subEspecie.isEmpty()) {
            throw new SubEspecieNotFoundException(id_subespecie);
        }
        subEspecieDTO.setIdSubespecie(id_subespecie);
        SubEspecieEntity updated = subEspecieService.update(id_subespecie, subEspecieMapper.mapFrom(subEspecieDTO));
        return ResponseEntity.ok(subEspecieMapper.mapTo(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecie(@PathVariable Long id) throws EspecieNotFoundException {
        Optional<EspecieEntity> especieEntity = especieService.findById(id);

        if (especieEntity.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }
        EspecieEntity aBorrar = especieEntity.get();
        aBorrar.setEstaBorrado(true);
        especieService.save(aBorrar);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping({"/{id}/subespecies/{id_subespecie}"})
    public ResponseEntity<Void> deleteSubEspecie(@PathVariable Long id,
                                                 @PathVariable Long id_subespecie) throws SubEspecieNotFoundException {
        Optional<EspecieEntity> especieEntity = especieService.findById(id);
        if (especieEntity.isEmpty()) {
            throw new EspecieNotFoundException(id);
        }
        Optional<SubEspecieEntity> subEspecieEntity = subEspecieService.findById(id_subespecie);
        if (subEspecieEntity.isEmpty()) {
            throw new SubEspecieNotFoundException(id_subespecie);
        }
        SubEspecieEntity aBorrar = subEspecieEntity.get();
        aBorrar.setEstaBorrado(true);
        subEspecieService.save(aBorrar);
        return ResponseEntity.noContent().build();
    }


}

