package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEspecieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    private final IEspecieService especieService;
    private final IMapper<EspecieEntity, EspecieDTO> especieMapper;
    private final PagedResourcesAssembler<EspecieDTO> pagedResourcesAssembler;

    public EspecieController(IEspecieService especieService, IMapper<EspecieEntity, EspecieDTO> especieMapper, PagedResourcesAssembler<EspecieDTO> pagedResourcesAssembler) {
        this.especieService = especieService;
        this.especieMapper = especieMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
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


    @PostMapping
    public ResponseEntity<EspecieDTO> postEspecie(@RequestBody EspecieDTO especieDTO) {
        EspecieEntity aGuardar = especieMapper.mapFrom(especieDTO);
        EspecieEntity saved = especieService.save(aGuardar);
        return ResponseEntity.ok(especieMapper.mapTo(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieDTO> putEspecie(@PathVariable Long id, @RequestBody EspecieDTO especieDTO) throws EspecieNotFoundException {
        if (!especieService.isExist(id)) {
            throw new EspecieNotFoundException(id);
        }
        especieDTO.setIdEspecie(id);
        EspecieEntity aGuardar = especieMapper.mapFrom(especieDTO);
        EspecieEntity saved = especieService.save(aGuardar);
        return ResponseEntity.ok(especieMapper.mapTo(saved));
    }

  @PatchMapping("/{id}")
  public ResponseEntity<EspecieDTO> patchEspecie(
      @PathVariable Long id, @RequestBody EspecieDTO especieDTO) throws EspecieNotFoundException {
    if (!especieService.isExist(id)) {
      throw new EspecieNotFoundException(id);
    }
    especieDTO.setIdEspecie(id);
    EspecieEntity updated = especieService.update(id, especieMapper.mapFrom(especieDTO));

    return ResponseEntity.ok(especieMapper.mapTo(updated));
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


}

