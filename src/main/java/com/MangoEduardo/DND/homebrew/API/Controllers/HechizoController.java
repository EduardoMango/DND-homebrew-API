package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IHechizoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PagedModel<EntityModel<HechizoDTO>>> getHechizos(Pageable pageable) {
        Page<HechizoEntity> page = hechizoService.findAll(pageable);
        PagedModel<EntityModel<HechizoDTO>> pagedModel = pagedResourcesAssembler.toModel(page.map(hechizoMapper::mapTo));
        return ResponseEntity.ok(pagedModel);
    }
    @GetMapping("/{id_hechizo}")
    public ResponseEntity<HechizoDTO> getHechizoById(@PathVariable("id_hechizo") Integer id_hechizo) {
        Optional<HechizoEntity> foundEntity = hechizoService.findById(id_hechizo);

        return foundEntity.map(hechizoEntity -> {
            HechizoDTO hechizoDTO = hechizoMapper.mapTo(hechizoEntity);
            return new ResponseEntity<>(hechizoDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<HechizoDTO> postHechizo(@RequestBody HechizoDTO hechizoDTO){

        HechizoEntity aGuardar = hechizoMapper.mapFrom(hechizoDTO);
        HechizoEntity saved = hechizoService.save(aGuardar);

        return new ResponseEntity<>(hechizoMapper.mapTo(saved),HttpStatus.CREATED);
    }

    @PutMapping("/{id_hechizo}")
    public ResponseEntity<HechizoDTO> putHechizo(@PathVariable Integer id_hechizo, @RequestBody HechizoDTO hechizoDTO){
        if(!hechizoService.isExist(id_hechizo)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        hechizoDTO.setId_hechizo(id_hechizo);

        HechizoEntity aGuardar = hechizoMapper.mapFrom(hechizoDTO);
        HechizoEntity saved = hechizoService.save(aGuardar);

        return new ResponseEntity<>(hechizoMapper.mapTo(saved),HttpStatus.OK);
    }

    @PatchMapping("/{id_hechizo}")
    public ResponseEntity<HechizoDTO> patchHechizo(@PathVariable("id_hechizo") Integer id_hechizo, @RequestBody HechizoDTO hechizo) {
        if (!hechizoService.isExist(id_hechizo)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HechizoEntity hechizoActualizada = hechizoService.update(id_hechizo,hechizoMapper.mapFrom(hechizo));
        return new ResponseEntity<>(hechizoMapper.mapTo(hechizoActualizada),HttpStatus.OK);
    }

    @DeleteMapping("/{id_hechizo}")
    public ResponseEntity<Void> deleteHechizo(@PathVariable("id_hechizo") Integer id_hechizo) {
        if (!hechizoService.isExist(id_hechizo)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        hechizoService.delete(id_hechizo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
