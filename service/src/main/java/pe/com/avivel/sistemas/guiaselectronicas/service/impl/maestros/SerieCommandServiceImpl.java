package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.command.maestros.SerieCommandDTO;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Serie;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.SerieRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.SerieCommandService;

import java.util.Optional;

import static pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SerieCommandServiceImpl implements SerieCommandService {

    private final SerieRepository serieRepository;

    private final ModelMapper modelMapper;

    @Override
    public SerieQueryDTO create(SerieCommandDTO dto) {
        Optional<Serie> serieOpByCodigo = serieRepository.findAllByCodigo(dto.getCodigo());

        if(serieOpByCodigo.isPresent()){
            throw new ValidationException(SerieResponse.EXISTE_CODIGO);
        }

        Serie serie = modelMapper.map(dto, Serie.class);
        Serie serieNew = serieRepository.save(serie);

        return modelMapper.map(serieNew, SerieQueryDTO.class);
    }

    @Override
    public SerieQueryDTO update(Integer id, SerieCommandDTO dto) {
        Optional<Serie> serieOp = serieRepository.findById(id);
        Optional<Serie> serieOpByCodigo = serieRepository.findAllByCodigo(dto.getCodigo());


        if(serieOp.isEmpty()){
            throw new ValidationException(SerieResponse.NO_ENCONTRADO);
        }

        if(serieOpByCodigo.isPresent() && !serieOpByCodigo.get().getId().equals(id) ){
            throw new ValidationException(SerieResponse.EXISTE_CODIGO);
        }

        Serie serie = serieOp.get();
        modelMapper.map(dto, serie);

        Serie serieUpdate = serieRepository.save(serie);

        return modelMapper.map(serieUpdate, SerieQueryDTO.class);
    }

    @Override
    public int updateEstadoById(Integer id, boolean activo) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ValidationException(SerieResponse.NO_ENCONTRADO));

        return serieRepository.updateEstadoById(serie.getId(), activo);
    }
}
