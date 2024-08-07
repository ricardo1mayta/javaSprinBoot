package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.ArticuloCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.ArticuloUnidadMedidaQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.auxiliares.guia_remision.UnidadMedida;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Articulo;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.ArticuloUnidadMedida;
import pe.com.avivel.sistemas.guiaselectronicas.repository.auxiliares.guia_remision.UnidadMedidaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ArticuloRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.ArticuloUnidadMedidaRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.ArticuloCommandService;

import java.util.Date;
import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticuloCommandServiceImpl implements ArticuloCommandService {

    private final ArticuloRepository articuloRepository;

    private final UnidadMedidaRepository unidadMedidaRepository;

    private final ArticuloUnidadMedidaRepository articuloUnidadMedidaRepository;

    private final ModelMapper modelMapper;

    @Override
    public ArticuloQueryDTO create(ArticuloCommandDTO dto) {
        Optional<Articulo> articuloOpByCodigo = articuloRepository
                .findByCodigo(dto.getCodigo());

        if(articuloOpByCodigo.isPresent()){
            throw new ValidationException(ArticuloResponse.EXISTE_CODIGO);
        }

        Articulo articulo = modelMapper.map(dto, Articulo.class);

        Articulo articuloNew = articuloRepository.save(articulo);

        return modelMapper.map(articuloNew, ArticuloQueryDTO.class);
    }

    @Override
    public ArticuloQueryDTO update(Integer id, ArticuloCommandDTO dto) {
        Optional<Articulo> articuloOp = articuloRepository.findById(id);
        Optional<Articulo> articuloOpByCodigo = articuloRepository
                .findByCodigo(dto.getCodigo());

        if(articuloOp.isEmpty()){
            throw new ValidationException(ArticuloResponse.NO_ENCONTRADO);
        }

        if(articuloOpByCodigo.isPresent() &&
                !articuloOpByCodigo.get().getId().equals(id) ){
            throw new ValidationException(ArticuloResponse.EXISTE_CODIGO);
        }

        Articulo articulo = articuloOp.get();

        modelMapper.map(dto, articulo);

        Articulo articuloUpdate = articuloRepository.save(articulo);

        return modelMapper.map(articuloUpdate, ArticuloQueryDTO.class);
    }

    @Override
    public int updateEstadoById(Integer id, boolean activo) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ArticuloResponse.NO_ENCONTRADO));

        return articuloRepository.updateEstadoById(articulo.getId(), activo);
    }

    @Override
    public ArticuloUnidadMedidaQueryDTO createUnidadMedida(Integer articuloId, Integer unidadMedidaId) {
        Optional<ArticuloUnidadMedida> articuloUnidadMedidaOp = articuloUnidadMedidaRepository
                .findByArticuloIdAndUnidadId(articuloId, unidadMedidaId);

        if(articuloUnidadMedidaOp.isPresent()){
            throw new ValidationException(ArticuloUnidadMedidaResponse.EXISTE_ARTICULO_UNIDAD);
        }

        Articulo articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new ValidationException(ArticuloResponse.NO_ENCONTRADO));

        UnidadMedida unidadMedida = unidadMedidaRepository.findById(unidadMedidaId)
                .orElseThrow(() -> new ValidationException(UnidadMedidaResponse.NO_ENCONTRADO));

        ArticuloUnidadMedida articuloUnidadMedida = ArticuloUnidadMedida
                .builder()
                .unidadMedida(unidadMedida)
                .articulo(articulo)
                .articuloId(articuloId)
                .fechaIngreso(new Date())
                .build();

        ArticuloUnidadMedida articuloUnidadMedidaNew = articuloUnidadMedidaRepository.save(articuloUnidadMedida);

        return modelMapper.map(articuloUnidadMedidaNew, ArticuloUnidadMedidaQueryDTO.class);
    }

    @Override
    public void deleteUnidadMedida(Integer articuloUnidadMedidaId) {
        ArticuloUnidadMedida articuloUnidadMedida = articuloUnidadMedidaRepository
                .findById(articuloUnidadMedidaId)
                .orElseThrow(() -> new ValidationException(ArticuloUnidadMedidaResponse.NO_ENCONTRADO));

        articuloUnidadMedidaRepository.deleteById(articuloUnidadMedida.getId());
    }
}
