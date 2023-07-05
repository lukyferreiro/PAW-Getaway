package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.ExperienceModel;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class AnonymousLandingPageDto implements Serializable {
    private Collection<ExperienceDto> Aventura;
    private Collection<ExperienceDto> Gastronomia;
    private Collection<ExperienceDto> Hoteleria;
    private Collection<ExperienceDto> Relax;
    private Collection<ExperienceDto> Vida_nocturna;
    private Collection<ExperienceDto> Historico;

    public AnonymousLandingPageDto() {
        // Used by Jersey
    }

    public AnonymousLandingPageDto(List<List<ExperienceModel>> experiencesList, UriInfo uriInfo) {
        this.Aventura = ExperienceDto.mapExperienceToDto(experiencesList.get(0), uriInfo)  ;
        this.Gastronomia = ExperienceDto.mapExperienceToDto(experiencesList.get(1), uriInfo);
        this.Hoteleria = ExperienceDto.mapExperienceToDto(experiencesList.get(2), uriInfo);
        this.Relax = ExperienceDto.mapExperienceToDto(experiencesList.get(3), uriInfo);
        this.Vida_nocturna = ExperienceDto.mapExperienceToDto(experiencesList.get(4), uriInfo);
        this.Historico = ExperienceDto.mapExperienceToDto(experiencesList.get(5), uriInfo);
    }

    public Collection<ExperienceDto> getAventura() {
        return Aventura;
    }

    public void setAventura(Collection<ExperienceDto> aventura) {
        Aventura = aventura;
    }

    public Collection<ExperienceDto> getGastronomia() {
        return Gastronomia;
    }

    public void setGastronomia(Collection<ExperienceDto> gastronomia) {
        Gastronomia = gastronomia;
    }

    public Collection<ExperienceDto> getHoteleria() {
        return Hoteleria;
    }

    public void setHoteleria(Collection<ExperienceDto> hoteleria) {
        Hoteleria = hoteleria;
    }

    public Collection<ExperienceDto> getRelax() {
        return Relax;
    }

    public void setRelax(Collection<ExperienceDto> relax) {
        Relax = relax;
    }

    public Collection<ExperienceDto> getVida_nocturna() {
        return Vida_nocturna;
    }

    public void setVida_nocturna(Collection<ExperienceDto> vida_nocturna) {
        Vida_nocturna = vida_nocturna;
    }

    public Collection<ExperienceDto> getHistorico() {
        return Historico;
    }

    public void setHistorico(Collection<ExperienceDto> historico) {
        Historico = historico;
    }
}
