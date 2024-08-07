package pe.com.avivel.sistemas.guiaselectronicas.service.impl.maestros;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.FiltrosListadoAllSeries;
import pe.com.avivel.sistemas.guiaselectronicas.dto.models.query.maestros.SerieQueryDTO;
import pe.com.avivel.sistemas.guiaselectronicas.entities.maestros.Serie;
import pe.com.avivel.sistemas.guiaselectronicas.repository.guia_remision.GuiaRemisionRepository;
import pe.com.avivel.sistemas.guiaselectronicas.repository.maestros.SerieRepository;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ValidationException;
import pe.com.avivel.sistemas.guiaselectronicas.service.impl.ConstansResponse;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.maestros.SerieQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SerieQueryServiceImpl implements SerieQueryService {

    private final SerieRepository serieRepository;

    private final GuiaRemisionRepository guiaRemisionRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<SerieQueryDTO> list() {
        return serieRepository.list()
                .stream()
                .map(s -> modelMapper.map(s, SerieQueryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SerieQueryDTO> listAllByFiltrosPaginado(FiltrosListadoAllSeries filtros, Pageable pageable) {
        return serieRepository.listAllByFiltrosPaginado(filtros, pageable)
                .map(s -> modelMapper.map(s, SerieQueryDTO.class));
    }

    @Override
    public String findCorrelativoActual(Integer serieId) {
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new ValidationException(ConstansResponse.SerieResponse.NO_ENCONTRADO));

        int lastNumeroValue = 0;
        String lastNumero = guiaRemisionRepository.findLastCorrelativoBySerie(serie.getCodigo());

        if (lastNumero != null){
            lastNumeroValue = Integer.parseInt(lastNumero);
        }

        ++lastNumeroValue;
        lastNumero = StringUtils.repeat("0", 7) + lastNumeroValue;
        lastNumero = StringUtils.right(lastNumero, 7);

        return lastNumero;
    }
}
